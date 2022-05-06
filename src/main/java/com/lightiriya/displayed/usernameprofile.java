package com.lightiriya.displayed;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

public class usernameprofile extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseStorage fstore;
FirebaseFirestore db;
ActivityResultLauncher<String> mGetContent;
StorageReference storageReference;

Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usernameprofile);
        Button next=findViewById(R.id.next);
        CircleImageView profile=findViewById(R.id.profile);
        EditText username=findViewById(R.id.username);

        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();



        fstore=FirebaseStorage.getInstance();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1=username.getText().toString();
                String document=mAuth.getUid().toString();
                if(username1.length()<=4)
                {
                    username.setError("cannot be less than 4 characters");
                }else
                {
                    upload_image();

                    Map<String,Object> usernames=new HashMap<>();
                    usernames.put("username",username1);
                    usernames.put("imageURL","nil");
                    usernames.put("bio","nil");
                    db.collection("users").document(document).set(usernames);


                }

            }


        });

        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    profile.setImageURI(result);
                    imageUri=result;
                }

            }
        });


    }
    public void upload_image() {
        if (imageUri != null) {
            String id = mAuth.getUid().toString();
            storageReference = fstore.getReference().child("profilephotos/" + id);
            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(usernameprofile.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    if(task.isSuccessful())
                    {storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl=uri.toString();
                            String doc=mAuth.getUid().toString();
                            FirebaseFirestore ff=FirebaseFirestore.getInstance();

                            ff.collection("users").document(doc).update("imageURL",imageUrl);

                        }
                    });


                        startActivity(new Intent(getApplicationContext(), dashboard.class));
                        finish();

                    }else{
                        Toast.makeText(usernameprofile.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }


    }


