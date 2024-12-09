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
                    AtomicInteger counter = new AtomicInteger(documents.size()); // Contador para manejar el flujo asincrónico

                    for (DocumentSnapshot document : documents) {
                        documentToShopItem(document, shopItem -> {
                            shopItems.add(shopItem); // Agregar el ShopItem a la lista
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(shopItems); // Ejecutar callback cuando todos los items estén listos
                            }
                        }, error -> {
                            Log.e("getAllShopItems", "Error procesando ShopItem: " + error.getMessage());
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(shopItems); // Retornar los que sí se procesaron
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching shop items: " + e.getMessage())));
    }

    public void documentToShopItem(DocumentSnapshot document, SuccessCallback<ShopItem> callback, ErrorCallback callError) {
        try {
            // Extraer datos del documento Firestore
            String id = document.getId();
            String name = document.getString("name");
            String description = document.getString("description");
            int price = document.getLong("price").intValue();
            String pictureURL = document.getString("pictureURL"); // Ruta de la imagen en el storage.

            // Validar URL de imagen
            if (pictureURL == null || pictureURL.isEmpty()) {
                Log.e("documentToShopItem", "URL de la imagen vacía o nula para ShopItem con ID: " + id);
                ShopItem shopItem = new ShopItem(id, name, price, null, description);
                callback.onComplete(shopItem); // Retornar el objeto sin imagen.
                return;
            }

            // Descargar la imagen desde Firebase Storage
            storage.getImageBytes(pictureURL, bytes -> {
                try {
                    // Crear ShopItem con la imagen descargada
                    ShopItem shopItemWithImage = new ShopItem(id, name, price, bytes, description);
                    callback.onComplete(shopItemWithImage); // Retornar el ShopItem completo.
                    Log.d("documentToShopItem", "Imagen descargada correctamente para ShopItem con ID: " + id);
                } catch (Exception e) {
                    Log.e("documentToShopItem", "Error al crear ShopItem con imagen: " + e.getMessage());
                    // En caso de error, retornar un ShopItem sin imagen
                    ShopItem shopItem = new ShopItem(id, name, price, null, description);
                    callback.onComplete(shopItem);
                }
            }, error -> {
                Log.e("documentToShopItem", "Error al descargar la imagen para ShopItem con ID: " + id + ". Error: " + error.getMessage());
                // En caso de error al descargar la imagen, retornar un ShopItem sin imagen
                ShopItem shopItem = new ShopItem(id, name, price, null, description);
                callback.onComplete(shopItem);
            });

        } catch (Exception ex) {
            Log.e("documentToShopItem", "Error general al procesar ShopItem: " + ex.getMessage());
            // En caso de error general, retornar un ShopItem con información básica de error
            ShopItem shopItem = new ShopItem("Unknown", ex.getMessage(), 0, null, "Error creating ShopItem");
            callback.onComplete(shopItem);
        }
    }
    private byte[] loadSampleImage() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/res/raw/falser.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}