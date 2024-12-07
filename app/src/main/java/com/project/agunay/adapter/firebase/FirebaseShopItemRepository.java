package com.project.agunay.adapter.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.agunay.application.repository.ShopItemRepository;
import com.project.agunay.domain.ShopItem;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseShopItemRepository implements ShopItemRepository {
    private final FirebaseFirestore db;

    public FirebaseShopItemRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = initializer.getDb();
    }

    public void getShopItemById(String id, SuccessCallback<ShopItem> callback, ErrorCallback callError) {
        db.collection("shopItems").document(id).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        callback.onComplete(document.toObject(ShopItem.class));
                    } else {
                        callError.onError(new Exception("ShopItem not found"));
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop item: " + e.getMessage())));
    }

    public void getAllShopItems(SuccessCallback<List<ShopItem>> callback, ErrorCallback callError) {
        db.collection("shopItems").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ShopItem> shopItems = queryDocumentSnapshots.getDocuments().stream()
                            .map(document -> document.toObject(ShopItem.class))
                            .collect(Collectors.toList());
                    callback.onComplete(shopItems);
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop items: " + e.getMessage())));
    }
}