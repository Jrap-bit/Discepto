package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class discussionpagepublic extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionpagepublic);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        EditText title=findViewById(R.id.title);
        EditText desc=findViewById(R.id.editText);
        TextView profileurl=findViewById(R.id.profileurl);
        TextView username=findViewById(R.id.username);
        Button send=findViewById(R.id.send);

 DocumentReference documentReference=fStore.collection("users").document(mAuth.getUid().toString());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                 username.setText(value.getString("username"));
                profileurl.setText(value.getString("imageURL"));


            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1=username.getText().toString();
                String imageURL=profileurl.getText().toString();
                String userid=mAuth.getUid().toString();
                String postID= UUID.randomUUID().toString();
                String title1=title.getText().toString();
                String desc1=desc.getText().toString();
                if(title1.length()<10)
                {
                    title.setError("Title to short");
                }else if(desc1.length()<10)
                {
                    desc.setError("Description too short");
                }
                else
                {
                    Map<String,Object> data=new HashMap<>();
                    data.put("username",username1);
                    data.put("imageURL",imageURL);
                    data.put("userid",userid);
                    data.put("title",title1);
                    data.put("desc",desc1);
                    data.put("postid",postID);
                    fStore.collection("users").document(userid).collection("PublicPost").document(postID).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(discussionpagepublic.this,"Posted Successfully",Toast.LENGTH_SHORT).show();

                            fStore.collection("PublicDiscussionForum").document(postID).set(data);

                            startActivity(new Intent(getApplicationContext(),dashboard.class));
                            Toast.makeText(discussionpagepublic.this, "Forum sent , we will notify you if someone answers it ", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(discussionpagepublic.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

    }
}