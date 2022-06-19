package com.example.flousquiz;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.flousquiz.databinding.FragmentWalletBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {



    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWalletBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        //binding.emailBox.requestFocus();
        String uid = FirebaseAuth.getInstance().getUid();

        database = FirebaseFirestore.getInstance();
        DocumentReference docRef = database.collection("users").document(uid);
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(documentSnapshot -> {

                  user = documentSnapshot.toObject(User.class);
                    binding.currentCoins.setText(String.valueOf(user.getCoins()));

                    //binding.currentCoins.setText(user.getCoins() + "");

                });

        binding.sendRequest.setOnClickListener(v -> {

            if(user.getCoins() > 100000){


                String payPal = binding.emailBox.getText().toString();

                WithdrawRequest request = new WithdrawRequest(uid, payPal, user.getUsername());
                database
                        .collection("withdraws")
                        .document(uid)
                        .set(request).addOnSuccessListener(unused -> Toast.makeText(getContext(), "Request sent successfly", Toast.LENGTH_SHORT).show());
                 docRef.update("coins", 0);
            } else {
                Toast.makeText(getContext(), "You need more coins to get withdraw !", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }


}