package com.project.agunay.adapter.firebase;

import android.util.Log;

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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
                    ArrayList<String> achievementsArray = new ArrayList<String>();
                    achievementsArray.add("JrwrFBKH5P7GSqHR3ueZ");
                    FirebaseUser firebaseUser = task.getResult().getUser();

                    if (firebaseUser != null) {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("id", firebaseUser.getUid());
                        userMap.put("points", 0);
                        userMap.put("email", user.getEmail());
                        userMap.put("inventory", new ArrayList<Achievement>());
                        userMap.put("profilePicture", null);
                        userMap.put("username", user.getUsername());
                        userMap.put("achievements", achievementsArray);

                        db.collection("users").document(firebaseUser.getUid()).set(userMap)
                                .addOnCompleteListener(setTask -> {
                                    if (setTask.isSuccessful()) {
                                        Log.d("documentToShopItem", "registrado");
                                        this.getUserByEmail(user.getEmail(),callback,callError);
                                    } else {
                                        callback.onComplete(null);
                                    }
                                });
                    } else {
                        Log.d("documentToShopItem", "registrado pero algo else");
                        this.getUserByEmail(user.getEmail(),callback,callError);
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
                        documentToUser2(document, callback, callError);
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
                        documentToUser2(document, callback, callError);
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

    private void documentToUser2(DocumentSnapshot document, SuccessCallback<User> callback, ErrorCallback callError) {
        try {
            String id = document.getId();
            String email = document.getString("email");
            String username = document.getString("username");
            int points = document.getLong("points").intValue();
            String profilePictureUrl = document.getString("profilePicture");
            if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
                String profilePictureCompleteUrl = "/profilePictures/" + profilePictureUrl;
                storageRepository.getImageBytes(profilePictureCompleteUrl, bytes -> {
                    byte[] profilePicture = bytes;
                    createUserObject(document, email, username, id, points, profilePicture,profilePictureUrl, callback, callError);
                }, error -> {
                    Log.e("documentToUser", "Error al descargar la imagen de perfil: " + error.getMessage());
                    createUserObject(document, email, username, id, points,null, profilePictureUrl, callback, callError);
                });
            } else {
                createUserObject(document, email, username, id, points, null,profilePictureUrl, callback, callError);
            }
        } catch (Exception e) {
            Log.e("documentToUser", "Error al procesar el usuario: " + e.getMessage());
            callError.onError(e);
        }
    }

    private void createUserObject(DocumentSnapshot document, String email, String username, String id, int points, byte[] profilePicture,String profilePictureUrl, SuccessCallback<User> callback, ErrorCallback callError) {
        User user = new User(email, "", username, id, points, new HashSet<>(), new HashMap<>(), profilePicture);
        user.setPictureURL(profilePictureUrl);
        achievementRepository.getAllAchievements(achievements -> {
            List<String> achievementIds = (List<String>) document.get("achievements");
            Set<Achievement> userAchievements = (achievementIds != null) ? achievements.stream()
                    .filter(achievement -> achievementIds.contains(achievement.getId()))
                    .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Achievement::getId))))
                    : new HashSet<>();
            Log.d("documentToShopItem", "logros para usuario: " + userAchievements.toString());
            Log.d("documentToShopItem", "logros: " + achievements.toString());
            Log.d("documentToShopItem", String.valueOf(("logrosIDS:" + (Arrays.toString(achievements.get(0).getPicture())))));
            Log.d("documentToShopItem", "logrosSIZE: " + achievementIds.size());
            user.setAchievements(userAchievements);

            shopItemRepository.getAllShopItems(shopItems -> {
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
                user.setInventory(inventory);
                // Se devuelve el usuario con todo hecho
                callback.onComplete(user);
            }, callError);
        }, callError);
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("points", user.getPoints());
        userMap.put("email", user.getEmail());
        userMap.put("profilePicture", user.getPictureURL());
        userMap.put("username", user.getUsername());

        //se guarda la lista de id de los logros obtenidos
        List<String> achievementIds = user.getAchievements().stream()
                .map(Achievement::getId)
                .collect(Collectors.toList());
        userMap.put("achievements", achievementIds);
        //transformacion a mapa de objetos de usuario {id: id_objeto, quantity: numero_objetos}
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


