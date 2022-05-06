package com.lightiriya.displayed;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FollowerChat extends Fragment {



    public FollowerChat() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_follower_chat, container, false);
RecyclerView notifyre=v1.findViewById(R.id.notifyre);
        notifyre.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        ArrayList<fetch8> notifyList=new ArrayList<>();
        customadapter8 customadapter8=new customadapter8(getActivity(),notifyList);
        notifyre.setAdapter(customadapter8);
        firestore.collection("users").document(mAuth.getUid()).collection("notification").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        notifyList.add(dc.getDocument().toObject(fetch8.class));
                    }
                    customadapter8.notifyDataSetChanged();
                }
            }

        });


        return v1;
    }
}
class fetch8{
    String user_profile,username,userid;

    public fetch8(String user_profile, String username, String userid) {
        this.user_profile = user_profile;
        this.username = username;
        this.userid = userid;
    }

    public fetch8() {
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
class customadapter8 extends RecyclerView.Adapter<customadapter8.viewholder8>
{
    Context context8;
    ArrayList<fetch8> notifyList;

    public customadapter8(Context context8, ArrayList<fetch8> notifyList) {
        this.context8 = context8;
        this.notifyList = notifyList;
    }

    @NonNull
    @Override
    public viewholder8 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v1= LayoutInflater.from(context8).inflate(R.layout.followchat,parent,false);

        return new customadapter8.viewholder8(v1);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder8 holder, int position) {
        fetch8 fetch8= notifyList.get(position);

        holder.username.setText(fetch8.getUsername());
        Glide.with(context8).load(fetch8.getUser_profile()).into(holder.profile_dp);

    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }


    class viewholder8 extends RecyclerView.ViewHolder{
        CircleImageView profile_dp;
        TextView username;

        public viewholder8(@NonNull View itemView) {
            super(itemView);
            profile_dp=itemView.findViewById(R.id.profile_dp);
            username=itemView.findViewById(R.id.username);
        }
    }
}