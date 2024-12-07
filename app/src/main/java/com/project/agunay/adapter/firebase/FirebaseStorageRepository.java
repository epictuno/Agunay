package com.project.agunay.adapter.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.agunay.application.repository.StorageRepository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseStorageRepository implements StorageRepository {
    private final FirebaseStorage storage;

    public FirebaseStorageRepository() {
        storage = FirebaseStorage.getInstance();
    }

    public void getImageBytes(String imageUrl, SuccessCallback<byte[]> callback, ErrorCallback callError) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            callback.onComplete(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            callError.onError(new Exception("Error fetching image: " + e.getMessage()));
        }
    }

    public void uploadProfilePicture(String userId, byte[] imageData, SuccessCallback<String> callback, ErrorCallback callError) {
        StorageReference profilePicRef = storage.getReference().child("profilePictures/" + userId + ".jpg");
        profilePicRef.putBytes(imageData)
                .addOnSuccessListener(taskSnapshot -> profilePicRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> callback.onComplete(uri.toString()))
                        .addOnFailureListener(e -> callError.onError(new Exception("Error getting download URL: " + e.getMessage()))))
                .addOnFailureListener(e -> callError.onError(new Exception("Error uploading profile picture: " + e.getMessage())));
    }
}
