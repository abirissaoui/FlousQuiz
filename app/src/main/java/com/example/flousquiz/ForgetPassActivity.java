package com.example.flousquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.flousquiz.databinding.ActivityForgetPassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPassActivity extends AppCompatActivity {
    ActivityForgetPassBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending...");

        auth = FirebaseAuth.getInstance();
        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailReset.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.emailReset.setError("Please Enter Valid Email !");
                    binding.emailReset.requestFocus();
                    return;
                }
                dialog.show();
                auth.sendPasswordResetEmail(binding.emailReset.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(ForgetPassActivity.this, "Please check your Email to reset Password", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgetPassActivity.this, "Failed to reset Password ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}