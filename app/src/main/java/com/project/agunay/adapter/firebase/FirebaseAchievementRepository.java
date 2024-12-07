package com.project.agunay.adapter.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.agunay.application.repository.AchievementRepository;
import com.project.agunay.domain.Achievement;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseAchievementRepository implements AchievementRepository {
    private final FirebaseFirestore db;

    public FirebaseAchievementRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = initializer.getDb();
    }

    public void getAchievementById(String id, SuccessCallback<Achievement> callback, ErrorCallback callError) {
        db.collection("achievements").document(id).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        callback.onComplete(document.toObject(Achievement.class));
                    } else {
                        callError.onError(new Exception("Achievement not found"));
                    }
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching achievement: " + e.getMessage())));
    }

    public void getAllAchievements(SuccessCallback<List<Achievement>> callback, ErrorCallback callError) {
        db.collection("achievements").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Achievement> achievements = queryDocumentSnapshots.getDocuments().stream()
                            .map(document -> document.toObject(Achievement.class))
                            .collect(Collectors.toList());
                    callback.onComplete(achievements);
                })
                .addOnFailureListener(e -> callError.onError(new Exception("Error fetching achievements: " + e.getMessage())));
    }
}
