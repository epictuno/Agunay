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
import com.project.agunay.domain.Achievement;
import com.project.agunay.domain.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseUserRepository implements UserRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public FirebaseUserRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = initializer.getDb();
        auth = initializer.getAuth();
    }

    @Override
    public void createUser(User user, Callback<User> callback) {/*
        getUserByUsername(user.getUsername(), existingUser -> {
            if (existingUser != null) {
                callback.onComplete(null); // Usuario ya existe
                return;
            }

            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("id", firebaseUser.getUid());
                        userMap.put("points", 0);
                        userMap.put("email", user.getEmail());
                        userMap.put("inventory", new ArrayList<Achievement>());
                        userMap.put("profilePicture", null);
                        userMap.put("username", user.getUsername());
                        userMap.put("achievements", new ArrayList<String>());

                        db.collection("users").document(firebaseUser.getUid()).set(userMap)
                                .addOnCompleteListener(setTask -> {
                                    if (setTask.isSuccessful()) {
                                        callback.onComplete(user); // Usuario creado con éxito
                                    } else {
                                        callback.onComplete(null); // Falló al guardar datos
                                    }
                                });
                    } else {
                        callback.onComplete(null); // Error desconocido
                    }
                } else {
                    callback.onComplete(null); // Error en la creación del usuario
                }
            });
        });*/
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
    public void getUserByUsername(String username, Callback<User> callback) {
        db.collection("users").whereEqualTo("username", username).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        callback.onComplete(null);
                    } else {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        callback.onComplete(user);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    callback.onComplete(null);
                });
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

    @Override
    public void createUser(@NotNull User user, @NotNull Object any) {

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
