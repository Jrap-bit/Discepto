package com.lightiriya.displayed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;


public class homefragment extends Fragment {

    public homefragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_homefragment, container, false);
        FirebaseFirestore fStore=FirebaseFirestore.getInstance();

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        CircleImageView circleImageView=view.findViewById(R.id.circleImageView);
        ImageView chat=view.findViewById(R.id.imageView6);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),chatActivity.class));
            }
        });
        DocumentReference documentReference=fStore.collection("users").document(mAuth.getUid().toString());
       documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            DocumentSnapshot documentSnapshot=task.getResult();
             String imageURL=documentSnapshot.getString("imageURL");
             Picasso.get().load(imageURL).into(circleImageView);
           }
       });
        Button anime=view.findViewById(R.id.anime);
        anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/forum/?board=1"));
                startActivity(intent);
            }
        });
        Button travel=view.findViewById(R.id.travel);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trivago.in"));
                startActivity(intent);
            }
        });
        Button education=view.findViewById(R.id.education);
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://math.stackexchange.com"));
                startActivity(intent);
            }
        });
        return view;
    }
}
