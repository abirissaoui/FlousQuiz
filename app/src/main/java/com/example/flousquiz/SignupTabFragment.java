package com.example.flousquiz;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.flousquiz.databinding.SignupTabFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupTabFragment extends Fragment {
    EditText userName,email,pass,confirm;
    Button signup;
    float v = 0;
    SignupTabFragmentBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SignupTabFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("We're creating new account...");

        binding.signup.setOnClickListener(v -> {
            String email, pass, username, confirm;


            email = binding.email.getText().toString();
            pass = binding.password.getText().toString();
            username = binding.username.getText().toString();
            confirm = binding.confirm.getText().toString();


            User user = new User(username, email, pass, confirm);


            if (TextUtils.isEmpty(username)){
                binding.username.setError("Name Required!");
                binding.username.requestFocus();
            }
            if(TextUtils.isEmpty(email)){
                binding.email.setError("Email Required!");
                binding.email.requestFocus();

            }
            if(TextUtils.isEmpty(pass)){
                binding.password.setError("Password Required");
                binding.password.requestFocus();
            }
            if(pass.length() < 6 ){
                binding.password.setError("Password Must be >= 6 caractters");
                binding.password.requestFocus();
                return;
            }
            if(!pass.equals(confirm)){
                binding.confirm.setError("Password Not match Both field");
                binding.confirm.requestFocus();
                return;
            }

                dialog.show();
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {


                        // send verification link
                        FirebaseUser userF = auth.getCurrentUser();




                        String uid = task.getResult().getUser().getUid();
                        database
                                .collection("users")
                                .document(uid)
                                .set(user).addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {
                                if(userF.isEmailVerified()){
                                    dialog.dismiss();
                                    Intent intent = new Intent (getContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else{
                                    userF.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {
                                            Toast.makeText(getContext(), "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getContext(), task1.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

            });

        userName = root.findViewById(R.id.username);
        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.password);
        confirm = root.findViewById(R.id.confirm);
        signup = root.findViewById(R.id.signup);

        userName.setTranslationY(600);
        email.setTranslationY(600);
        pass.setTranslationY(600);
        confirm.setTranslationY(600);
        signup.setTranslationY(600);

        userName.setAlpha(v);
        email.setAlpha(v);
        pass.setAlpha(v);
        confirm.setAlpha(v);
        signup.setAlpha(v);

        userName.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(200).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        confirm.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        return root;
    }
}
