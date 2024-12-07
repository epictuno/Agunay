package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.SuccessCallback;

public interface StorageRepository {
    void getImageBytes(String imageUrl, SuccessCallback<byte[]> callback, ErrorCallback callError);
    void uploadProfilePicture(String userId, byte[] imageData, SuccessCallback<String> callback, ErrorCallback callError);
}
