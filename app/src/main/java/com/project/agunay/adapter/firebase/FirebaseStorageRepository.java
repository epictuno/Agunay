package com.project.agunay.adapter.firebase;

import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.agunay.application.repository.StorageRepository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirebaseStorageRepository implements StorageRepository {
    private final FirebaseStorage storage;
    private final StorageReference storageReference;
    public FirebaseStorageRepository() {
        storage = FirebaseStorage.getInstance();
        storageReference= storage.getReference();
    }

    public void getImageBytes(String pictureURL, SuccessCallback<byte[]> callback, ErrorCallback errorCallback) {
        try {
            StorageReference imageRef = storageReference.child(pictureURL);
            imageRef.getBytes(1024 * 1024) // Limite de tamaño 1 MB.
                    .addOnSuccessListener(callback::onComplete)
                    .addOnFailureListener(e -> {
                        Log.e("FirebaseStorageRepository", "Error al descargar la imagen: " + e.getMessage());
                        errorCallback.onError(e);
                    });
        } catch (Exception e) {
            Log.e("FirebaseStorageRepository", "Error inesperado al descargar la imagen: " + e.getMessage());
            errorCallback.onError(e);
        }
    }

    @Override
    public void uploadProfilePicture(String userId, byte[] imageData, SuccessCallback<String> callback, ErrorCallback callError) {

    }
}
