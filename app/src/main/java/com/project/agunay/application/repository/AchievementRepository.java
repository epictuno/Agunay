package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.SuccessCallback;
import com.project.agunay.domain.Achievement;

import java.util.List;

public interface AchievementRepository {
    void getAchievementById(String id, SuccessCallback<Achievement> callback, ErrorCallback callError);
    void getAllAchievements(SuccessCallback<List<Achievement>> callback, ErrorCallback callError);
}
