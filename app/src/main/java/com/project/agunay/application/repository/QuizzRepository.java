package com.project.agunay.application.repository;

import com.project.agunay.adapter.firebase.ErrorCallback;
import com.project.agunay.adapter.firebase.SuccessCallback;
import com.project.agunay.domain.Quizz;
import com.project.agunay.domain.ShopItem;

import java.util.List;

public interface QuizzRepository {
    void getQuiz(String name, SuccessCallback<Quizz> callback, ErrorCallback callError);
    void getAllQuizzesNamesAndPictures(SuccessCallback<List<Quizz>> callback, ErrorCallback callError);
}


