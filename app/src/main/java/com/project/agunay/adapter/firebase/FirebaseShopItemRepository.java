package com.project.agunay.adapter.firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.agunay.R;
import com.project.agunay.application.repository.ShopItemRepository;
import com.project.agunay.domain.ShopItem;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseShopItemRepository implements ShopItemRepository {
    private final FirebaseFirestore db;
    private final FirebaseStorageRepository storage;


    public FirebaseShopItemRepository() {
        db = FirebaseFirestore.getInstance();
        storage = new FirebaseStorageRepository();
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
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    List<ShopItem> shopItems = new ArrayList<>();
                    AtomicInteger counter = new AtomicInteger(documents.size());

                    for (DocumentSnapshot document : documents) {
                        documentToShopItem(document, shopItem -> {
                            shopItems.add(shopItem);
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(shopItems);
                            }
                        }, error -> {
                            Log.e("getAllShopItems", "Error procesando ShopItem: " + error.getMessage());
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(shopItems);
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop items: " + e.getMessage())));
    }

    public void documentToShopItem(DocumentSnapshot document, SuccessCallback<ShopItem> callback, ErrorCallback callError) {
        try {
            String id = document.getId();
            String name = document.getString("name");
            String description = document.getString("description");
            int price = document.getLong("price").intValue();
            String pictureURL = document.getString("pictureURL");
            if (pictureURL == null || pictureURL.isEmpty()) {
                Log.e("documentToShopItem", "URL de la imagen vacía o nula para ShopItem con ID: " + id);
                ShopItem shopItem = new ShopItem(id, name, price, null, description);
                callback.onComplete(shopItem);
                return;
            }
            storage.getImageBytes(pictureURL, bytes -> {
                try {
                    ShopItem shopItemWithImage = new ShopItem(id, name, price, bytes, description);
                    callback.onComplete(shopItemWithImage); // Retornar el ShopItem completo.
                    Log.d("documentToShopItem", "Imagen descargada correctamente para ShopItem con ID: " + id);
                } catch (Exception e) {
                    Log.e("documentToShopItem", "Error al crear ShopItem con imagen: " + e.getMessage());
                    ShopItem shopItem = new ShopItem(id, name, price, null, description);
                    callback.onComplete(shopItem);
                }
            }, error -> {
                Log.e("documentToShopItem", "Error al descargar la imagen para ShopItem con ID: " + id + ". Error: " + error.getMessage());
                ShopItem shopItem = new ShopItem(id, name, price, null, description);
                callback.onComplete(shopItem);
            });

        } catch (Exception ex) {
            Log.e("documentToShopItem", "Error general al procesar ShopItem: " + ex.getMessage());
            ShopItem shopItem = new ShopItem("Unknown", ex.getMessage(), 0, null, "Error creating ShopItem");
            callback.onComplete(shopItem);
        }
    }

}