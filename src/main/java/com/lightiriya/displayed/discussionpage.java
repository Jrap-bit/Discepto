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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class discussionpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionpage);
        Intent intent = getIntent();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        String current_uid = firebaseAuth.getUid().toString();
        CircleImageView profile_photo = findViewById(R.id.profile_photo);
        TextView username = findViewById(R.id.username);
        TextView title = findViewById(R.id.title);
        TextView store_uid=findViewById(R.id.store_uid);
        TextView desc = findViewById(R.id.desc);
        EditText comment = findViewById(R.id.comment);
        ImageView send = findViewById(R.id.imageView7);
        String image_url = intent.getStringExtra("imageid");
        Picasso.get().load(image_url).into(profile_photo);
        username.setText(intent.getStringExtra("username"));
        title.setText(intent.getStringExtra("title"));
        desc.setText(intent.getStringExtra("desc"));
        String postid = intent.getStringExtra("postid");
        String userid = intent.getStringExtra("userid");
        TextView c_profile_photo = findViewById(R.id.c_profile_photo);
        TextView store_username = findViewById(R.id.store_username);
        RecyclerView comments = findViewById(R.id.comments);
        DocumentReference documentReference = fStore.collection("users").document(current_uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                c_profile_photo.setText(documentSnapshot.getString("imageURL"));
                store_username.setText(documentSnapshot.getString("username"));

            }
        });
        DocumentReference documentReference1=fStore.collection("PublicDiscussionForum").document(postid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                store_uid.setText(documentSnapshot.getString("userid"));
            }
        });

        ArrayList<fetch2> AnswerArrayList = new ArrayList<fetch2>();
        customadapter2 customadapter2 = new customadapter2(getApplicationContext(), AnswerArrayList);
       comments.setLayoutManager(new LinearLayoutManager(this));
        comments.setAdapter(customadapter2);
       spacedecorator spacedecorator=new spacedecorator(100);
        comments.addItemDecoration(spacedecorator);


        fStore.collection("PublicDiscussionForum").document(postid).collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        AnswerArrayList.add(dc.getDocument().toObject(fetch2.class));
                    }
                    customadapter2.notifyDataSetChanged();
                }
            }




        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_comment=comment.getText().toString();
                String profile=c_profile_photo.getText().toString();
                String c_user=store_username.getText().toString();
                String userid=firebaseAuth.getUid().toString();
                String comment_id=UUID.randomUUID().toString();
                if(get_comment.length()<=5)
                {
                    comment.setError("Answer too short");
                }
                else
                {
                 HashMap <String,Object> data=new HashMap<>();
                 data.put("comment",get_comment);
                 data.put("profile",profile);
                 data.put("userid",userid);
                 data.put("comment_id",comment_id);
                 data.put("username",c_user);
                 data.put("postid",postid);
                 fStore.collection("PublicDiscussionForum").document(postid).collection("comments").document(comment_id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(discussionpage.this, "Send", Toast.LENGTH_SHORT).show();
                         //fStore.collection("users").document(store_uid.getText().toString()).collection("PublicCollection").document(postid).collection("answer").document(comment_id).set(data);
                         comment.setText("");
                     }
                 });

                }
            }
        });
    }
}

class fetch2{
    public String comment;
    public String profile;
    public String userid;
    public String comment_id;
    public String username;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public fetch2(String postid) {
        this.postid = postid;
    }

    public String postid;

    public fetch2() {
    }

    public fetch2(String comment, String profile, String userid, String comment_id, String username) {
        this.comment = comment;
        this.profile = profile;
        this.userid = userid;
        this.comment_id = comment_id;
        this.username = username;
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
}
class customadapter2 extends RecyclerView.Adapter<customadapter2.viewholder2> {
    Context context2;
    ArrayList<fetch2> AnswerArrayList;

    public customadapter2(Context context2, ArrayList<fetch2> answerArrayList) {
        this.context2 = context2;
        AnswerArrayList = answerArrayList;
    }

    @NonNull
    @Override
    public customadapter2.viewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1= LayoutInflater.from(context2).inflate(R.layout.activity_answercard,parent,false);
        return new viewholder2(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter2.viewholder2 holder, int position) {
      fetch2 fetch2=AnswerArrayList.get(position);
      holder.username.setText(fetch2.getUsername());
      holder.title.setText(fetch2.getComment());
        Glide.with(context2).load(fetch2.getProfile()).into(holder.profile_photo);

    }

    @Override
    public int getItemCount() {
        return AnswerArrayList.size();
    }

    public class viewholder2 extends RecyclerView.ViewHolder{
        CircleImageView profile_photo;
        TextView username,title,view_discuss;
        public viewholder2(@NonNull View itemView) {
            super(itemView);
            profile_photo=itemView.findViewById(R.id.profile_photo);
            username=itemView.findViewById(R.id.username);
            title=itemView.findViewById(R.id.title);
            view_discuss=itemView.findViewById(R.id.textView7);
            view_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int pos=getAdapterPosition();
                   fetch2 fetch2=AnswerArrayList.get(pos);
                    Intent intent=new Intent(context2,replyactivity.class);
                    intent.putExtra("username",fetch2.getUsername());
                    intent.putExtra("userid",fetch2.getUserid());
                    intent.putExtra("commentid",fetch2.getComment_id());
                    intent.putExtra("comment",fetch2.getComment());
                    intent.putExtra("profile",fetch2.getProfile());
                    intent.putExtra("postid",fetch2.getPostid());
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context2.startActivity(intent);


                }
            });
        }
    }

}