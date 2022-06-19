package com.example.flousquiz;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flousquiz.databinding.RowLeaderboardsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardViewHolder>{

    Context context;
    ArrayList<User> users;
    StorageReference storageReference;
    FirebaseAuth auth;
    String currentUid;


    public LeaderBoardAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboards, parent, false);

        return new LeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {

        User user = users.get(position);

        holder.binding.namePlayer.setText(user.getUsername());
        holder.binding.coins.setText(String.valueOf(user.getCoins()));

        if (position+1 == 1 && user.getCoins() != 0){
            holder.binding.rank.setImageResource(R.drawable.first_place);
        } else if (position+1 == 2 && user.getCoins() != 0){
            holder.binding.rank.setImageResource(R.drawable.second_place);
        }else if (position+1 == 3 && user.getCoins() != 0){
            holder.binding.rank.setImageResource(R.drawable.third_place);
        } else {
            holder.binding.rank.setImageResource(R.drawable.no_rank);

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {

        RowLeaderboardsBinding binding;
        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowLeaderboardsBinding.bind(itemView);

        }
    }
}
