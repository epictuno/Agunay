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
import com.project.agunay.application.repository.AchievementRepository;
import com.project.agunay.application.repository.ShopItemRepository;
import com.project.agunay.application.repository.StorageRepository;
import com.project.agunay.application.repository.UserRepository;
import com.project.agunay.domain.Achievement;
import com.project.agunay.domain.ShopItem;
import com.project.agunay.domain.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FirebaseUserRepository implements UserRepository {
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    private final AchievementRepository achievementRepository;
    private final ShopItemRepository shopItemRepository;
    private final StorageRepository storageRepository;
    public FirebaseUserRepository() {
        FirebaseInitializer initializer = FirebaseInitializer.getInstance();
        db = initializer.getDb();
        auth = initializer.getAuth();
        achievementRepository = new FirebaseAchievementRepository();
        shopItemRepository = new FirebaseShopItemRepository();
        storageRepository = new FirebaseStorageRepository();
    }

    @Override
    public void createUser(User user, SuccessCallback<User> callback,ErrorCallback callError) {
        getUserByUsername(user.getUsername(), existingUser -> {
            if (existingUser != null) {
                callError.onError(new Exception("Username " + user.getUsername() + " is already in use."));
            }
        }, error -> {
            System.out.println(error.getMessage());
        });

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
                                        callback.onComplete(user);
                                    } else {
                                        callback.onComplete(null);
                                    }
                                });
                    } else {
                        callback.onComplete(user);
                    }
                } else {
                    callback.onComplete(null);
                }
            });
        }

    @Override
    public void login(String email, String password, SuccessCallback<User> callback, ErrorCallback callError) {
        Task<AuthResult> task = auth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        getUserByEmail(email,callback,callError);
                        return;
                    }
                } else {
                    callError.onError(new Exception("login not succesful a" + email + password));
                    return;
                }
            }
        });
    }

    @Override
    public void updateUser(User user, SuccessCallback<User> callback, ErrorCallback callError) {
        Map<String, Object> userMap = userToMap(user);

        db.collection("users").document(user.getId()).set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onComplete(user);
                        } else {
                            callError.onError(new Exception("Failed to update user"));
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    callError.onError(new Exception("Failed to update user: " + e.getMessage()));
                });
    }

    @Override
    public void getUserByUsername(String username, SuccessCallback<User> callback,ErrorCallback callError) {
        db.collection("users").whereEqualTo("username", username).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        callError.onError(new Exception("user by username not found"));
                        callback.onComplete(null);
                    } else {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        documentToUser(document, callback, callError);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    callError.onError(new Exception("Fail on get user by username"));
                    //callback.onComplete(null);
                });
    }

    @Override
    public void getUserByEmail(String email, SuccessCallback<User> callback, ErrorCallback callError) {
        db.collection("users").whereEqualTo("email", email).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        callback.onComplete(null);
                        callError.onError(new Exception("User not found with that email "));
                    } else {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        documentToUser(document, callback, callError);
                    }
                })
                .addOnFailureListener(e -> {
                    callError.onError(new Exception("User not found: " + e.getMessage()));
                });
    }

    @Override
    public void getUser(String id, SuccessCallback<User> callback, ErrorCallback callError) {
        Task<DocumentSnapshot> task = db.collection("users").document(id).get();
        try {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                callback.onComplete( document.toObject(User.class));
            }
        } catch (Exception e) {
            callError.onError(new Exception("User not found"));
        }
    };

    private void documentToUser(DocumentSnapshot document, SuccessCallback<User> callback, ErrorCallback callError) {
        String id = document.getId();
        String email = document.getString("email");
        String username = document.getString("username");
        int points = document.getLong("points").intValue();
        byte[] profilePicture = document.getBlob("profilePicture") != null ? document.getBlob("profilePicture").toBytes() : null;

        // Obtener todos los logros y artículos de la tienda
        achievementRepository.getAllAchievements(achievements -> {
            shopItemRepository.getAllShopItems(shopItems -> {
                // Convertir la lista de IDs de logros a objetos Achievement
                List<String> achievementIds = (List<String>) document.get("achievements");
                List<Achievement> userAchievements = (achievementIds != null) ? achievements.stream()
                        .filter(achievement -> achievementIds.contains(achievement.getId()))
                        .collect(Collectors.toList()) : new ArrayList<>();

                // Convertir la lista de mapas de inventario a objetos ShopItem con cantidad
                List<Map<String, Object>> inventoryList = (List<Map<String, Object>>) document.get("inventory");
                Map<ShopItem, Integer> inventory = new HashMap<>();
                if (inventoryList != null) {
                    for (Map<String, Object> itemMap : inventoryList) {
                        String itemId = (String) itemMap.get("id");
                        int quantity = ((Long) itemMap.get("quantity")).intValue();
                        shopItems.stream()
                                .filter(item -> item.getId().equals(itemId))
                                .findFirst()
                                .ifPresent(item -> inventory.put(item, quantity));
                    }
                }

                User user = new User(email, "", username, id, points, userAchievements, inventory, profilePicture);
                callback.onComplete(user);
            }, callError);
        }, callError);
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("points", user.getPoints());
        userMap.put("email", user.getEmail());
        userMap.put("profilePicture", user.getProfilePicture());
        userMap.put("username", user.getUsername());

        //list of IDs
        List<String> achievementIds = user.getAchievements().stream()
                .map(Achievement::getId)
                .collect(Collectors.toList());
        userMap.put("achievements", achievementIds);
        //list of maps with shop item ID and quantity
        List<Map<String, Object>> inventoryList = user.getInventory().entrySet().stream()
                .map(entry -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("id", entry.getKey().getId());
                    itemMap.put("quantity", entry.getValue());
                    return itemMap;
                })
                .collect(Collectors.toList());
        userMap.put("inventory", inventoryList);

        return userMap;
    }
    }


