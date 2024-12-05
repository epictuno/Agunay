package com.project.agunay.adapter.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;

import com.google.firestore.v1.WriteResult;
import com.project.agunay.application.repository.UserRepository;
import com.project.agunay.domain.User;

public class FirebaseUserRepository implements UserRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public FirebaseUserRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = initializer.getDb();
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
                        // Maneja el usuario autenticado
                    }
                } else {
                    // Maneja el error
                }
            }
        });
        return null;
    }

    @Override
    public void updateUser(User user) {
        db.collection("users").document(user.getId()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            // Maneja el error
                        }
                    }
                });
    }

    @Override
    public User getUserByUsername(String username) {
        Task<QuerySnapshot> task = db.collection("users").whereEqualTo("username", username).get();
        try {
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                return querySnapshot.getDocuments().get(0).toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Task<QuerySnapshot> task = db.collection("users").whereEqualTo("email", email).get();
        try {
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                return querySnapshot.getDocuments().get(0).toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        Task<DocumentSnapshot> task = db.collection("users").document(id).get();
        try {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                return document.toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    }
/*
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
                        // Maneja el usuario autenticado
                    }
                } else {
                    // Maneja el error
                }
            }
        });
        return null;
    }

    @Override
    public void updateUser(User user) {
        db.collection("users").document(user.getId()).set(user)
                .addOnCompleteListener(new OnCompleteListener<WriteResult>() {
                    @Override
                    public void onComplete(@NonNull Task<WriteResult> task) {
                        if (!task.isSuccessful()) {
                            // Maneja el error
                        }
                        else {
                        }
                    }
                }
    }

    @Override
    public User getUserByUsername(String username) {
        Task<QuerySnapshot> task = db.collection("users").whereEqualTo("username", username).get();
        try {
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                return querySnapshot.getDocuments().get(0).toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Task<QuerySnapshot> task = db.collection("users").whereEqualTo("email", email).get();
        try {
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                return querySnapshot.getDocuments().get(0).toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        Task<DocumentSnapshot> task = db.collection("users").document(id).get();
        try {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                return document.toObject(User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    });
}
*/
