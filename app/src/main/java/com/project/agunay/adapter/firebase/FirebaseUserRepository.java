package com.project.agunay.adapter.firebase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.project.agunay.application.repository.UserRepository;
import com.project.agunay.domain.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import com.project.agunay.adapter.firebase.FirebaseInitializer;
import java.util.concurrent.ExecutionException;

public class FirebaseUserRepository implements UserRepository {
    private final Firestore db;
    private final FirebaseAuth auth;

    public FirebaseUserRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = (Firestore) initializer.getDb();
        auth = initializer.getAuth();
    }

    @Override
    public void createUser(User user, String password) {
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            if (firebaseUser != null) {
                                user.setId(firebaseUser.getUid());
                                db.collection("users").document(user.getId()).set(user);
                            }
                        } else {
                            // Maneja el error
                        }
                    }
                });
    }

    @Override
    public User login(String email, String password) {
        Task<AuthResult> task = auth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {

                    }
                } else {

                }
            }
        });
        return null;
    }

    @Override
    public void updateUser(User user) {
        ApiFuture<WriteResult> future = db.collection("users").document(user.getId()).set(user, SetOptions.merge());
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("username", username).get();
        try {
            for (DocumentSnapshot document : future.get().getDocuments()) {
                return document.toObject(User.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("email", email).get();
        try {
            for (DocumentSnapshot document : future.get().getDocuments()) {
                return document.toObject(User.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        DocumentReference docRef = db.collection("users").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(User.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
