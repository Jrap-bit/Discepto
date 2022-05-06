package com.lightiriya.displayed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginfragment extends Fragment {
    public loginfragment() {
    }

  EditText enter_email,enter_password;
    Button signin;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginfragment, container, false);
       enter_email=view.findViewById(R.id.enter_email);
       enter_password=view.findViewById(R.id.enter_password);
       mAuth=FirebaseAuth.getInstance();
       signin=view.findViewById(R.id.signin);
       signin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email=enter_email.getText().toString();
               String password=enter_password.getText().toString();
               if(email.length()<=8)
               {
                   enter_email.setError("Invalid email");
               }else if(password.length()<8)
               {
                   enter_password.setError("cannot be less than 8");
               }else
               {
                   mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                          Intent intent=new Intent(getActivity(),dashboard.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getContext(), "user does not exist", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }
       });

        return view;


    }
}