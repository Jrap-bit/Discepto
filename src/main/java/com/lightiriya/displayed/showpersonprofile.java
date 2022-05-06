package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class showpersonprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpersonprofile);
        Intent intent=getIntent();
        String userid=intent.getStringExtra("userid");
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        Button freind_request=findViewById(R.id.friend_request);
        Button freind_request2=findViewById(R.id.friend_request2);
        TextView usernamestore=findViewById(R.id.usernamestore);
        Button follow=findViewById(R.id.follow);
        Button follow2=findViewById(R.id.follow2);
        TextView imageurl=findViewById(R.id.imageurlll);
        CircleImageView profile_photo=findViewById(R.id.profile_photo);
        TextView username=findViewById(R.id.username);

        if(mAuth.getUid().equals(userid)){
            freind_request.setVisibility(View.INVISIBLE);
            follow.setVisibility(View.INVISIBLE);
            DocumentReference documentReference = firestore.collection("users").document(mAuth.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                   String imageURL=(documentSnapshot.getString("imageURL"));
                    username.setText(documentSnapshot.getString("username"));
                    Picasso.get().load(imageURL).into(profile_photo);

                }
            });


        }
        else
        {
            DocumentReference documentReference = firestore.collection("users").document(userid);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String imageURL=(documentSnapshot.getString("imageURL"));
                    username.setText(documentSnapshot.getString("username"));
                    Picasso.get().load(imageURL).into(profile_photo);


                }
            });
        }
        DocumentReference documentReference = firestore.collection("users").document(mAuth.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                imageurl.setText(documentSnapshot.getString("imageURL"));
                usernamestore.setText(documentSnapshot.getString("username"));


            }
        });

freind_request.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        freind_request.setVisibility(View.INVISIBLE);
        freind_request2.setVisibility(View.VISIBLE);
        String uid=mAuth.getUid();
        HashMap<String,Object> data=new HashMap<>();
        data.put("userid",uid);
        data.put("username",usernamestore.getText().toString());
        data.put("user_profile",imageurl.getText().toString());
        firestore.collection("users").document(userid).collection("FriendRequest").document(mAuth.getUid()).set(data);



    }
});

follow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        follow2.setVisibility(View.VISIBLE);
        follow.setVisibility(View.INVISIBLE);
        String uid=mAuth.getUid();
        HashMap<String,Object> data=new HashMap<>();
        data.put("userid",uid);
        data.put("username",usernamestore.getText().toString());
        data.put("user_profile",imageurl.getText().toString());
        firestore.collection("users").document(userid).collection("Followers").document(mAuth.getUid()).set(data);
        HashMap<String,Object> data2=new HashMap<>();
        data2.put("username",usernamestore.getText().toString());
        data2.put("userid",mAuth.getUid());
        data2.put("user_profile",imageurl.getText().toString());
        firestore.collection("users").document(userid).collection("notification").document(mAuth.getUid()).set(data2);

    }
});

    }
}