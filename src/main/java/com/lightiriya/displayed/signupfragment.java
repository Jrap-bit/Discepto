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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupfragment extends Fragment {
    FirebaseAuth mAuth;
    EditText email,password,repassword;
    Button Login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_signupfragment, container, false);
       email=view.findViewById(R.id.email);
       password=view.findViewById(R.id.password);
       repassword=view.findViewById(R.id.repassword);
       Login=view.findViewById(R.id.Login);
       mAuth=FirebaseAuth.getInstance();
       Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String  email1=email.getText().toString();
               String password1=password.getText().toString();
               String repassword1=repassword.getText().toString();
               if(email1.length()<8){
                   email.setError("Invalid email");
               }else if(password1.length()<8){
                   password.setError("Cannot be less than 8 characters");
               }else if(repassword1.equals(password1)){
                   mAuth.createUserWithEmailAndPassword(email1,repassword1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           startActivity(new Intent(getActivity(),usernameprofile.class));
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }
       });



       return view;
    }
}