package com.lightiriya.displayed;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class shareImage extends AppCompatActivity {
ImageView image,send;
    ActivityResultLauncher<String> mGetContent;
    StorageReference storageReference;
    FirebaseAuth mAuth;

    FirebaseStorage fstore;
    TextView username,uid,profileid;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_image);
        image=findViewById(R.id.image);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        username=findViewById(R.id.username);
        profileid=findViewById(R.id.profileid);
        mAuth=FirebaseAuth.getInstance();
        uid=findViewById(R.id.uid);
        fstore=FirebaseStorage.getInstance();
        EditText description=findViewById(R.id.description);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");

            }
        });
        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    image.setImageURI(result);
                    imageUri=result;
                }

            }
        });

        DocumentReference documentReference = db.collection("users").document(mAuth.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
               profileid.setText(documentSnapshot.getString("imageURL"));
               username.setText(documentSnapshot.getString("username"));

            }
        });

        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc=description.getText().toString();
                if(description.length()<1)
                {
                    description.setError("Image or description cannot be empty");
                }
                else
                {
                    String postid= UUID.randomUUID().toString();
                    HashMap<String,Object> data =new HashMap<>();
                    data.put("desc",desc);
                    data.put("username",username.getText().toString());
                    data.put("uid",mAuth.getUid());
                    data.put("imagePost","nil");
                    data.put("post_id",postid);
                    data.put("profile_id",profileid.getText().toString());
                    db.collection("Post").document(postid).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(shareImage.this, "Sent!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),dashboard.class));

                        }
                    });
                    if (imageUri != null) {

                        storageReference = fstore.getReference().child("postphotos/" + postid);
                        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                Toast.makeText(shareImage.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                if(task.isSuccessful())
                                {storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl=uri.toString();

                                        FirebaseFirestore ff=FirebaseFirestore.getInstance();

                                        ff.collection("Post").document(postid).update("imagePost",imageUrl);

                                    }
                                });


                                    startActivity(new Intent(getApplicationContext(), dashboard.class));
                                    finish();

                                }else{
                                    Toast.makeText(shareImage.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                }
            }
        });




    }


}