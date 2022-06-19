package com.example.flousquiz;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TwitterActivity extends LoginActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        provider.addCustomParameter("lang", "fr");


        Task<AuthResult> pendingResultTask = auth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    FirebaseUser user = auth.getCurrentUser();
                                    String userUid = auth.getCurrentUser().getUid();
                                    DocumentReference docRef = database.collection("users").document(userUid);

                                    Map<String, Object> userM = new HashMap<>();

                                    userM.put("username", user.getDisplayName());
                                    userM.put("email", user.getEmail());
                                    userM.put("pass", null);
                                    userM.put("confirm", null);
                                    userM.put("coins", 0);

                                    docRef.set(userM).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {
                                            Log.d(TAG, "signInWithCredential:success");

                                        }
                                    });
                                    startActivity(new Intent(TwitterActivity.this, MainActivity.class));
                                    Toast.makeText(TwitterActivity.this, "Login Successfil", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TwitterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            auth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    String userUid = auth.getCurrentUser().getUid();
                                    DocumentReference docRef = database.collection("users").document(userUid);

                                    Map<String, Object> userM = new HashMap<>();

                                    userM.put("username", user.getDisplayName());
                                    userM.put("email", user.getEmail());
                                    userM.put("pass", null);
                                    userM.put("confirm", null);
                                    userM.put("coins", 0);

                                    docRef.set(userM).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {
                                            Log.d(TAG, "signInWithCredential:success");

                                        }
                                    });
                                    startActivity(new Intent(TwitterActivity.this, MainActivity.class));
                                    Toast.makeText(TwitterActivity.this, "Login Successfil", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TwitterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }
}