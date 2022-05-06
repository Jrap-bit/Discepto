package com.lightiriya.displayed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class addfragment extends Fragment {



    public addfragment() {
        // Required empty public constructor
    }


  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_addfragment, container, false);
       Button public_discuss=view.findViewById(R.id.public_discuss);
      CircleImageView profile_dp=view.findViewById(R.id.profile_dp);
       Button share_image=view.findViewById(R.id.share_image);
      FirebaseFirestore firestore=FirebaseFirestore.getInstance();
      FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
       share_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),shareImage.class));
           }
       });
        public_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),discussionpagepublic.class));
            }
        });
      DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getUid());
      documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              DocumentSnapshot documentSnapshot = task.getResult();

              String imageURL= (documentSnapshot.getString("imageURL"));
              Picasso.get().load(imageURL).into(profile_dp);

          }
      });
      ImageView message=view.findViewById(R.id.message);
      message.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getActivity(),chatActivity.class));
          }
      });


        return view;
    }
}