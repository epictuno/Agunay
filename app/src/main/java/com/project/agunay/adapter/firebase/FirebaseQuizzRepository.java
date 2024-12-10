package com.project.agunay.adapter.firebase;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.agunay.application.repository.QuizzRepository;
import com.project.agunay.domain.Answer;
import com.project.agunay.domain.Question;
import com.project.agunay.domain.Quizz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseQuizzRepository implements QuizzRepository {
    private final FirebaseFirestore db;
    private final FirebaseStorageRepository storage;


    public FirebaseQuizzRepository() {
        db = FirebaseFirestore.getInstance();
        storage = new FirebaseStorageRepository();
    }


    @Override
    public void getQuiz(String name, SuccessCallback<Quizz> callback, ErrorCallback callError) {
        Log.d("FirebaseQuizzRepository", "quizz load start");
        db.collection("Quizzes").document(name).get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                callError.onError(new Exception("Quizz not found: " + name));
                Log.d("FirebaseQuizzRepository", "Quizz not found "+name);
                return;
            }

            try {
                Quizz quizz = mapDocumentToQuizz(documentSnapshot);
                Log.d("FirebaseQuizzRepository", "quizz load end");
                String pictureURL = documentSnapshot.getString("pictureURL");
                if (pictureURL == null || pictureURL.isEmpty()) {
                    callback.onComplete(quizz);
                    Log.d("FirebaseQuizzRepository", "No picture");
                    Log.d("FirebaseQuizzRepository", "Quizz details: " + quizz.getQuestions().size());
                    Log.d("FirebaseQuizzRepository", "Quizz details: " + quizz.getQuestions().toString());
                } else {
                    storage.getImageBytes(pictureURL, bytes -> {
                        quizz.setPicture(bytes);
                        callback.onComplete(quizz);
                        Log.d("FirebaseQuizzRepository", "Quizz loaded succesfully");
                    }, error -> {
                        Log.e("FirebaseQuizzRepository", "Error downloading image: " + error.getMessage());
                        callback.onComplete(quizz);
                    });
                }
            } catch (Exception e) {
                callError.onError(e);
                Log.d("FirebaseQuizzRepository", "F Quizz loaded nope");
            }
        }).addOnFailureListener(callError::onError);
    }

    private Quizz mapDocumentToQuizz(DocumentSnapshot document) {
        Quizz quizz = new Quizz();
        quizz.setName(document.getString("name"));

        List<Question> questions = new ArrayList<>();
        List<Object> rawQuestions = (List<Object>) document.get("questions");

        if (rawQuestions != null) {
            for (Object rawQuestion : rawQuestions) {
                if (rawQuestion instanceof Map) {
                    Question question = mapToQuestion((Map<String, Object>) rawQuestion);
                    questions.add(question);
                }
            }
        }
        quizz.setQuestions(questions);
        quizz.setPicture(null);
        return quizz;
    }

    private Question mapToQuestion(Map<String, Object> rawQuestion) {
        Question question = new Question();
        question.setQuestionTitle((String) rawQuestion.get("questionTitle"));
        question.setQuestionType((String) rawQuestion.get("questionType"));

        List<Answer> answers = new ArrayList<>();
        List<Object> rawAnswers = (List<Object>) rawQuestion.get("answers");

        if (rawAnswers != null) {
            for (Object rawAnswer : rawAnswers) {
                if (rawAnswer instanceof Map) {
                    Answer answer = mapToAnswer((Map<String, Object>) rawAnswer);
                    answers.add(answer);
                }
            }
        }

        question.setAnswers(answers);
        return question;
    }

    private Answer mapToAnswer(Map<String, Object> rawAnswer) {
        Answer answer = new Answer();
        answer.setAnswerText((String) rawAnswer.get("answerText"));
        answer.setAnswer((Boolean) rawAnswer.get("isAnswer"));
        return answer;
    }


    @Override
    public void getAllQuizzesNamesAndPictures(SuccessCallback<List<Quizz>> callback, ErrorCallback callError) {

    }
}
