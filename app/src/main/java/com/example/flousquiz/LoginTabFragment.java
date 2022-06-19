package com.example.flousquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.flousquiz.databinding.LoginTabFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText email,pass;
    TextView forgetPass;
    Button login;
    float v = 0;
    LoginTabFragmentBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginTabFragmentBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Logging in...");

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getContext(),MainActivity.class));

        }

        binding.login.setOnClickListener(v -> {
            String email, pass;
            email = binding.email.getText().toString();
            pass = binding.pass.getText().toString();

            if(TextUtils.isEmpty(email)){
                binding.email.setError("Email is Required !");
                binding.email.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(pass)) {
                binding.pass.setError("Password is required");
                binding.pass.requestFocus();
                return;
            }
            if(pass.length() < 6 ){
                binding.pass.setError("Password Must be >= 6 caractters");
                binding.pass.requestFocus();
                return;
            }
                dialog.show();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

        });
         binding.forgetPass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(),ForgetPassActivity.class);
                 startActivity(intent);
             }
         });



        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.login);


        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);


        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
