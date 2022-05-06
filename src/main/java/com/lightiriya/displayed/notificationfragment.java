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


public class notificationfragment extends Fragment {



    public notificationfragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_notificationfragment, container, false);
        RecyclerView notify=v1.findViewById(R.id.notify);
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        TextView notification=v1.findViewById(R.id.notification);
        ArrayList<fetch7> notifyArrayList=new ArrayList<>();
        customadapter7 customadapter7=new customadapter7(getActivity(),notifyArrayList);
        notify.setLayoutManager(new LinearLayoutManager(getActivity()));
        notify.setAdapter(customadapter7);
        firestore.collection("users").document(mAuth.getUid()).collection("notification").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                      notifyArrayList.add(dc.getDocument().toObject(fetch7.class));
                    }
                    customadapter7.notifyDataSetChanged();
                }
            }

        });



        return v1;
    }
}
class fetch7
{
    String userid,username,user_profile;

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public fetch7(String user_profile) {
        this.user_profile = user_profile;
    }

    public fetch7(String userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    public fetch7() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
class customadapter7 extends RecyclerView.Adapter<customadapter7.viewholder7>
{
    Context context7;
    ArrayList<fetch7> notifyArrayList;

    public customadapter7(Context context7, ArrayList<fetch7> notifyArrayList) {
        this.context7 = context7;
        this.notifyArrayList = notifyArrayList;
    }

    @NonNull
    @Override
    public customadapter7.viewholder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1= LayoutInflater.from(context7).inflate(R.layout.notifycard,parent,false);

        return new customadapter7.viewholder7(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter7.viewholder7 holder, int position) {
        fetch7 fetch7=notifyArrayList.get(position);
        holder.username.setText(fetch7.getUsername());
        Glide.with(context7).load(fetch7.getUser_profile()).into(holder.profile_dp);

    }

    @Override
    public int getItemCount() {
        return notifyArrayList.size();
    }
    class viewholder7 extends RecyclerView.ViewHolder{
        CircleImageView profile_dp;
        TextView username;

        public viewholder7(@NonNull View itemView) {
            super(itemView);
            profile_dp=itemView.findViewById(R.id.profile_dp);
            username=itemView.findViewById(R.id.username);
        }
    }
}