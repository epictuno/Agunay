package com.project.agunay.adapter.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.agunay.application.repository.AchievementRepository;
import com.project.agunay.domain.Achievement;

import java.util.List;
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

public class FirebaseAchievementRepository implements AchievementRepository {
    private final FirebaseFirestore db;
    private final FirebaseStorageRepository storage;

    public FirebaseAchievementRepository() {
        db = FirebaseFirestore.getInstance();
        storage = new FirebaseStorageRepository();
    }

    @Override
    public void getAchievementById(String id, SuccessCallback<Achievement> callback, ErrorCallback callError) {
        db.collection("achievements").document(id).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        documentToAchievement(document, callback, callError);
                    } else {
                        callError.onError(new Exception("Achievement not found"));
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching achievement: " + e.getMessage())));
    }

    @Override
    public void getAllAchievements(SuccessCallback<List<Achievement>> callback, ErrorCallback callError) {
        db.collection("achievements").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    List<Achievement> achievements = new ArrayList<>();
                    AtomicInteger counter = new AtomicInteger(documents.size());

                    for (DocumentSnapshot document : documents) {
                        documentToAchievement(document, achievement -> {
                            achievements.add(achievement);
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(achievements);
                            }
                        }, error -> {
                            Log.e("getAllAchievements", "Error procesando Achievement: " + error.getMessage());
                            if (counter.decrementAndGet() == 0) {
                                callback.onComplete(achievements);
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching achievements: " + e.getMessage())));
    }

    public void documentToAchievement(DocumentSnapshot document, SuccessCallback<Achievement> callback, ErrorCallback callError) {
        try {
            String id = document.getId();
            String description = document.getString("description");
            String title = document.getString("title");
            String pictureURL = document.getString("pictureURL");
            if (pictureURL == null || pictureURL.isEmpty()) {
                Log.e("documentToAchievement", "URL de la imagen vacía o nula para Achievement con ID: " + id);
                Achievement achievement = new Achievement(id, description, null, title);
                callback.onComplete(achievement);
                return;
            }
            storage.getImageBytes(pictureURL, bytes -> {
                try {
                    Achievement achievementWithImage = new Achievement(id, description, bytes, title);
                    callback.onComplete(achievementWithImage);
                    Log.d("documentToAchievement", "Imagen descargada correctamente para Achievement con ID: " + id);
                } catch (Exception e) {
                    Log.e("documentToAchievement", "Error al crear Achievement con imagen: " + e.getMessage());
                    Achievement achievement = new Achievement(id, description, null, title);
                    callback.onComplete(achievement);
                }
            }, error -> {
                Log.e("documentToAchievement", "Error al descargar la imagen para Achievement con ID: " + id + ". Error: " + error.getMessage());
                Achievement achievement = new Achievement(id, description, null, title);
                callback.onComplete(achievement);
            });

        } catch (Exception ex) {
            Log.e("documentToAchievement", "Error general al procesar Achievement: " + ex.getMessage());
            Achievement achievement = new Achievement("Unknown", ex.getMessage(), null, "Error creating Achievement");
            callback.onComplete(achievement);
        }
    }
}
