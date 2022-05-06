package com.lightiriya.displayed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class searchfragment extends Fragment {

    public searchfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_searchfragment, container, false);
        RecyclerView search_recycle=view.findViewById(R.id.search_recycle);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        search_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView image_recycle=view.findViewById(R.id.image_recycle);

        TextView Forum=view.findViewById(R.id.discussion);
        TextView Images=view.findViewById(R.id.Images);
        Forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_recycle.setVisibility(View.VISIBLE);
                Forum.setTextColor(Color.BLUE);
                Images.setTextColor(Color.WHITE);
                image_recycle.setVisibility(View.INVISIBLE);

            }
        });
        Images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Images.setTextColor(Color.BLUE);
                Forum.setTextColor(Color.WHITE);
                search_recycle.setVisibility(View.INVISIBLE);
                image_recycle.setVisibility(View.VISIBLE);
            }
        });
        ArrayList<fetch> userArrayList=new ArrayList<fetch>();
        customsearchadapter customsearchadapter=new customsearchadapter(getActivity(),userArrayList);
        search_recycle.setAdapter(customsearchadapter);
        db.collection("PublicDiscussionForum").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    Log.e("Firestore error",e.getMessage());
                    return;
                }
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        userArrayList.add(dc.getDocument().toObject(fetch.class));
                    }
                    customsearchadapter.notifyDataSetChanged();

                }
            }
        });
image_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
ArrayList<fetch6> AnswerArrayList=new ArrayList<>();
customadapter6 customadapter6=new customadapter6(getActivity(),AnswerArrayList);
image_recycle.setAdapter(customadapter6);
        db.collection("Post").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null)
                {
                    Log.e("Firestore error",e.getMessage());
                    return;
                }
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        AnswerArrayList.add(dc.getDocument().toObject(fetch6.class));
                    }
                    customadapter6.notifyDataSetChanged();

                }
            }
        });

      return  view;
    }
}
class fetch
{
    private String desc;
    private String imageURL;
    private String postid;
    private String title;
    private String userid;
    private String username;

    public fetch(String desc, String imageURL, String postid, String title, String userid, String username) {
        this.desc = desc;
        this.imageURL = imageURL;
        this.postid = postid;
        this.title = title;
        this.userid = userid;
        this.username = username;
    }

    public fetch() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
class customsearchadapter extends RecyclerView.Adapter<customsearchadapter.ViewHolder>{
    Context context;
    ArrayList<fetch> UserArrayList;

    public customsearchadapter(Context context,ArrayList<fetch> userArrayList) {
        this.context = context;
        UserArrayList = userArrayList;
    }

    @NonNull
    @Override
    public customsearchadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1=LayoutInflater.from(context).inflate(R.layout.activity_forumcard,parent,false);
        return new ViewHolder(v1);

    }

    @Override
    public void onBindViewHolder(@NonNull customsearchadapter.ViewHolder holder, int position) {
        fetch fetch=UserArrayList.get(position);
        holder.username.setText(fetch.getUsername());
        holder.title.setText(fetch.getTitle());
        Glide.with(context).load(fetch.getImageURL()).into(holder.profile_photo);
    }

    @Override
    public int getItemCount() {
        return UserArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile_photo;
        TextView username,title,view_discuss;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_photo=itemView.findViewById(R.id.profile_photo);
            username=itemView.findViewById(R.id.username);
            title=itemView.findViewById(R.id.title);
            view_discuss=itemView.findViewById(R.id.textView7);
            view_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                     fetch fetch=UserArrayList.get(pos);
                     String username=fetch.getUsername();
                     String title=fetch.getTitle();
                     String desc=fetch.getDesc();
                     String imageURL= fetch.getImageURL();
                     String userid=fetch.getUserid();
                     String postid=fetch.getPostid();
                     Intent intent=new Intent(context,discussionpage.class);
                     intent.putExtra("username",username);
                     intent.putExtra("title",title);
                     intent.putExtra("desc",desc);
                     intent.putExtra("imageURL",imageURL);
                     intent.putExtra("imageid",imageURL);
                     intent.putExtra("userid",userid);
                     intent.putExtra("postid",postid);
                     context.startActivity(intent);

                }
            });
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    fetch fetch=UserArrayList.get(pos);
                    Intent intent=new Intent(context,showpersonprofile.class);
                    intent.putExtra("userid",fetch.getUserid());
                    context.startActivity(intent);

                }
            });

        }
    }
}

class fetch6{
    String desc,imagePost,post_id,profile_id,uid,username;

    public fetch6(String desc, String imagePost, String post_id, String profile_id, String uid, String username) {
        this.desc = desc;
        this.imagePost = imagePost;
        this.post_id = post_id;
        this.profile_id = profile_id;
        this.uid = uid;
        this.username = username;
    }

    public fetch6() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
class customadapter6 extends RecyclerView.Adapter<customadapter6.ViewHolder6> {
Context context6;
ArrayList<fetch6> ImageArrayList;

    public customadapter6(Context context6, ArrayList<fetch6> imageArrayList) {
        this.context6 = context6;
        ImageArrayList = imageArrayList;
    }

    @NonNull
    @Override
    public customadapter6.ViewHolder6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1=LayoutInflater.from(context6).inflate(R.layout.activity_image_card,parent,false);
        return new ViewHolder6(v1);

    }

    @Override
    public void onBindViewHolder(@NonNull customadapter6.ViewHolder6 holder, int position) {
    fetch6 fetch6=ImageArrayList.get(position);
    holder.username.setText(fetch6.getUsername());
    holder.desc11.setText(fetch6.getDesc());
        Glide.with(context6).load(fetch6.getProfile_id()).into(holder.profile_dp);
        Glide.with(context6).load(fetch6.getImagePost()).into(holder.image_post);

    }

    @Override
    public int getItemCount() {
        return ImageArrayList.size();
    }

    public class ViewHolder6 extends RecyclerView.ViewHolder {
        CircleImageView profile_dp;
        ImageView image_post,comment;
        TextView desc11,username;


        public ViewHolder6(@NonNull View itemView) {
            super(itemView);
            profile_dp=itemView.findViewById(R.id.profile_dp);
            image_post=itemView.findViewById(R.id.image_post);
            comment=itemView.findViewById(R.id.comment);
            desc11=itemView.findViewById(R.id.desc11);
            username=itemView.findViewById(R.id.username);


        }
    }
}