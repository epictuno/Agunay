package com.project.agunay.adapter.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.agunay.application.repository.ShopItemRepository;
import com.project.agunay.domain.ShopItem;

import java.util.List;
import java.util.stream.Collectors;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseShopItemRepository implements ShopItemRepository {
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;

    public FirebaseShopItemRepository() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public void getShopItemById(String id, SuccessCallback<ShopItem> callback, ErrorCallback callError) {
        db.collection("shopItems").document(id).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        documentToShopItem(document, callback, callError);
                    } else {
                        callError.onError(new Exception("ShopItem not found"));
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop item: " + e.getMessage())));
    }

    @Override
    public void getAllShopItems(SuccessCallback<List<ShopItem>> callback, ErrorCallback callError) {
        db.collection("shop").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ShopItem> shopItems = queryDocumentSnapshots.getDocuments().stream()
                            .map(document -> {
                                ShopItem[] item = new ShopItem[1];
                                documentToShopItem(document, shopItem -> item[0] = shopItem, callError);
                                return item[0];
                            })
                            .collect(Collectors.toList());
                    callback.onComplete(shopItems);
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop items: " + e.getMessage())));
    }

    private void documentToShopItem(DocumentSnapshot document, SuccessCallback<ShopItem> callback, ErrorCallback callError) {
        String id = document.getId();
        String name = document.getString("name");
        String description = document.getString("description");
        int price = document.getLong("price").intValue();
        //String pictureURL = document.getString("pictureURL");
        // Obtener la imagen
        /*StorageReference pictureRef = storage.getReferenceFromUrl(pictureURL);
        pictureRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            ShopItem shopItem = new ShopItem(id, name, price, bytes, description);
            callback.onComplete(shopItem);
        }).addOnFailureListener(e -> {
            // Si falla,imagen a null*/
            ShopItem shopItem = new ShopItem(id, name, price, null, description);
            callback.onComplete(shopItem);
        };
}