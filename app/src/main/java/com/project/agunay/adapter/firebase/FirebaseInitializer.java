package com.project.agunay.adapter.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseInitializer {
    private static FirebaseFirestore db;
    private static FirebaseAuth auth;

    private FirebaseInitializer() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    public static FirebaseInitializer getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final FirebaseInitializer INSTANCE = new FirebaseInitializer();
    }
    public FirebaseFirestore getDb() {
        return db;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
}
