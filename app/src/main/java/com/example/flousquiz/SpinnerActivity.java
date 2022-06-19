package com.example.flousquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.flousquiz.SpinWheel.LuckyWheelView;
import com.example.flousquiz.SpinWheel.model.LuckyItem;
import com.example.flousquiz.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        List<LuckyItem> data = new ArrayList<>();


        LuckyItem item1 = new LuckyItem();
        item1.topText = "0";
        item1.secondaryText = "coins";
        item1.color = Color.parseColor("#FCECA5");
        item1.textColor = Color.parseColor("#FFFFFF");
        data.add(item1);

        LuckyItem item2 = new LuckyItem();
        item2.topText = "5";
        item2.secondaryText = "coins";
        item2.color = Color.parseColor("#FBB328"); //F6AC2F
        item2.textColor = Color.parseColor("#FFFFFF");
        data.add(item2);

        LuckyItem item3 = new LuckyItem();
        item3.topText = "10";
        item3.secondaryText = "coins";
        item3.color = Color.parseColor("#000000"); //4CAF50
        item3.textColor = Color.parseColor("#FFFFFF");
        data.add(item3);

        LuckyItem item4 = new LuckyItem();
        item4.topText = "0";
        item4.secondaryText = "coins";
        item4.color = Color.parseColor("#FBB328"); //EB332D
        item4.textColor = Color.parseColor("#FFFFFF");
        data.add(item4);

        LuckyItem item5 = new LuckyItem();
        item5.topText = "15";
        item5.secondaryText = "coins";
        item5.color = Color.parseColor("#000000");//00DCFC
        item5.textColor = Color.parseColor("#FFFFFF");
        data.add(item5);

        LuckyItem item6 = new LuckyItem();
        item6.topText = "20";
        item6.secondaryText = "coins";
        item6.color = Color.parseColor("#FBB328");//169A6D
        item6.textColor = Color.parseColor("#FFFFFF");
        data.add(item6);

        LuckyItem item7 = new LuckyItem();
        item7.topText = "0";
        item7.secondaryText = "coins";
        item7.color = Color.parseColor("#000000");//00DCFC
        item7.textColor = Color.parseColor("#FFFFFF");
        data.add(item7);

        LuckyItem item8 = new LuckyItem();
        item8.topText = "25";
        item8.secondaryText = "coins";
        item8.color = Color.parseColor("#FCECA5");
        item8.textColor = Color.parseColor("#FFFFFF");
        data.add(item8);

        LuckyItem item9 = new LuckyItem();
        item9.topText = "30";
        item9.secondaryText = "coins";
        item9.color = Color.parseColor("#FBB328");//00DCFC
        item9.textColor = Color.parseColor("#FFFFFF");
        data.add(item9);

        LuckyItem item10 = new LuckyItem();
        item10.topText = "35";
        item10.secondaryText = "coins";
        item10.color = Color.parseColor("#000000");
        item10.textColor = Color.parseColor("#FFFFFF");
        data.add(item10);



        binding.wheelView.setData(data);
        binding.wheelView.setRound(5);


        binding.spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomNumber = r.nextInt(10);
                binding.wheelView.startLuckyWheelWithTargetIndex(randomNumber);
            }
        });

        binding.wheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCash(index);
            }
        });


        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility == 0){
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
    }

    void updateCash(int index) {

        long cash = 0;

        switch (index) {
            case 0:
                cash = 0;
                break;
            case 1:
                cash = 5;
                break;
            case 2:
                cash = 10;
                break;
            case 3:
                cash = 0;
                break;
            case 4:
                cash = 15;
                break;
            case 5:
                cash = 20;
                break;
            case 6:
                cash = 0;
                break;
            case 7:
                cash = 25;
                break;
            case 8:
                cash = 30;
                break;
            case 9:
                cash = 35;
                break;

        }
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SpinnerActivity.this, "Coins added in account.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}