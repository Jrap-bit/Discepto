package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class replyactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replyactivity);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        CircleImageView profile_photo = findViewById(R.id.profile_photo);
        TextView username = findViewById(R.id.username);
        TextView title = findViewById(R.id.title);
        EditText comment = findViewById(R.id.comment);
        ImageView send = findViewById(R.id.imageView7);
        RecyclerView comments = findViewById(R.id.comments);
        TextView c_profile_photo = findViewById(R.id.c_profile_photo);
        TextView store_username = findViewById(R.id.store_username);
        TextView store_uid=findViewById(R.id.store_uid);

        Intent intent=getIntent();
        String p_usernmae= intent.getStringExtra("username");
        String p_userid=intent.getStringExtra("userid");
        String p_commentid=intent.getStringExtra("commentid");
        String p_comment=intent.getStringExtra("comment");
        String p_profile=intent.getStringExtra("profile");
        String postid=intent.getStringExtra("postid");

        Picasso.get().load(p_profile).into(profile_photo);
        username.setText(p_usernmae);
        title.setText(p_comment);

        DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                c_profile_photo.setText(documentSnapshot.getString("imageURL"));
                store_username.setText(documentSnapshot.getString("username"));

            }
        });
        DocumentReference documentReference1=firestore.collection("PublicDiscussionForum").document(postid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                store_uid.setText(documentSnapshot.getString("userid"));
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_comment=comment.getText().toString();
                String comment_id= UUID.randomUUID().toString();
                String c_uid=firebaseAuth.getUid();
                String c_username=store_username.getText().toString();
                String c_profile=c_profile_photo.getText().toString();
                if(get_comment.length()<=1){
                    comment.setError("Cannot be empty");
                }else {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("comment",get_comment);
                    data.put("profile",c_profile);
                    data.put("userid",c_uid);
                    data.put("comment_id",comment_id);
                    data.put("username",c_username);
                    firestore.collection("PublicDiscussionForum").document(postid).collection("comments").document(p_commentid).collection("on_comments").document(comment_id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(replyactivity.this, "Sent", Toast.LENGTH_SHORT).show();
                          //  firestore.collection("users").document(store_uid.getText().toString()).collection("PublicCollection").document(postid).collection("answer").document(p_commentid).collection("commemts").document(comment_id).set(data);

                            comment.setText("");
                        }
                    });
                }

            }
        });

        ArrayList<fetch3> CommentArrayList=new ArrayList<>();
        customadapter3 customadapter3 = new customadapter3(getApplicationContext(), CommentArrayList);
        comments.setLayoutManager(new LinearLayoutManager(this));
        comments.setAdapter(customadapter3);
        comments.setLayoutManager(new LinearLayoutManager(this));

       firestore.collection("PublicDiscussionForum").document(postid).collection("comments").document(p_commentid).collection("on_comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        CommentArrayList.add(dc.getDocument().toObject(fetch3.class));
                    }
                    customadapter3.notifyDataSetChanged();
                }
            }

        });




    }
}
class fetch3
{
    String  comment,profile,userid,comment_id,username;

    public fetch3() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public fetch3(String comment, String profile, String userid, String comment_id, String username) {
        this.comment = comment;
        this.profile = profile;
        this.userid = userid;
        this.comment_id = comment_id;
        this.username = username;
    }
}

class customadapter3 extends RecyclerView.Adapter<customadapter3.viewholder3>
{
    Context context3;
    ArrayList<fetch3> CommentArrayList;

    public customadapter3(Context context3, ArrayList<fetch3> commentArrayList) {
        this.context3 = context3;
        CommentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public customadapter3.viewholder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1= LayoutInflater.from(context3).inflate(R.layout.activity_commentcard,parent,false);
        return new customadapter3.viewholder3(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter3.viewholder3 holder, int position) {
        fetch3 fetch3=CommentArrayList.get(position);
        holder.username.setText(fetch3.getUsername());
        holder.title.setText(fetch3.getComment());
        Glide.with(context3).load(fetch3.getProfile()).into(holder.profile_photo);
    }

    @Override
    public int getItemCount() {
        return CommentArrayList.size();
    }
    public class viewholder3 extends RecyclerView.ViewHolder{
        CircleImageView profile_photo;
        TextView username,title;
        public viewholder3(@NonNull View itemView) {
            super(itemView);
            profile_photo=itemView.findViewById(R.id.profile_photo);
            username=itemView.findViewById(R.id.username);
            title=itemView.findViewById(R.id.title);

        }
    }
}