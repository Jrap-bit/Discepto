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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class profilefragment extends Fragment {





    public profilefragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profilefragment, container, false);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseFirestore fstore=FirebaseFirestore.getInstance();
        TextView username=view.findViewById(R.id.username);
        TextView Forum=view.findViewById(R.id.Forum);

        RecyclerView profileforum=view.findViewById(R.id.profile_forum);

        CircleImageView profile_photo=view.findViewById(R.id.profile_photo);
        DocumentReference documentReference=fstore.collection("users").document(firebaseAuth.getUid().toString());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                String imageURL=documentSnapshot.getString("imageURL");
                username.setText(documentSnapshot.getString("username"));

                Picasso.get().load(imageURL).into(profile_photo);
            }
        });
        ArrayList<fetch4> UserpostList=new ArrayList<>();
        customadapter4 customadapter4=new customadapter4(getActivity(),UserpostList);
        profileforum.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileforum.setAdapter(customadapter4);

        fstore.collection("users").document(firebaseAuth.getUid()).collection("PublicPost").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    Log.e("Firestore error",e.getMessage());
                    return;
                }
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    if(dc.getType()==DocumentChange.Type.ADDED){
                       UserpostList.add(dc.getDocument().toObject(fetch4.class));
                    }
                   customadapter4.notifyDataSetChanged();

                }
            }
        });



        return view;
    }
}
class fetch4
{
    String desc,imageURL,postid,title,userid,username;

    public fetch4(String desc, String imageURL, String postid, String title, String userid, String username) {
        this.desc = desc;
        this.imageURL = imageURL;
        this.postid = postid;
        this.title = title;
        this.userid = userid;
        this.username = username;
    }

    public fetch4() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
class customadapter4 extends RecyclerView.Adapter<customadapter4.ViewHolder4>{
Context context4;
        ArrayList<fetch4> UserpostList;

    public customadapter4(Context context4, ArrayList<fetch4> userpostList) {
        this.context4 = context4;
        UserpostList = userpostList;
    }

    @NonNull
    @Override
    public customadapter4.ViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v1=LayoutInflater.from(context4).inflate(R.layout.activity_forumcard,parent,false);
        return new ViewHolder4(v1) ;
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter4.ViewHolder4 holder, int position) {
        fetch4 fetch4=UserpostList.get(position);
        holder.username.setText(fetch4.getUsername());
        holder.title.setText(fetch4.getTitle());
        Glide.with(context4).load(fetch4.getImageURL()).into(holder.profile_photo);
    }


    @Override
    public int getItemCount() {
        return UserpostList.size();

    }
    public class ViewHolder4 extends RecyclerView.ViewHolder {
        CircleImageView profile_photo;
        TextView username,title,view_discuss;
        public ViewHolder4(@NonNull View itemView) {
            super(itemView);
            profile_photo=itemView.findViewById(R.id.profile_photo);
            username=itemView.findViewById(R.id.username);
            title=itemView.findViewById(R.id.title);
            view_discuss=itemView.findViewById(R.id.textView7);
        }
    }
    }