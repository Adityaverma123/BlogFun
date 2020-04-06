package com.example.blogfun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class YourFeed extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<getfeed> options;
    FirebaseRecyclerAdapter<getfeed,holder> firebaseRecyclerAdapter;
    RecyclerView mBloglist;
    ArrayList<String> keys=new ArrayList<>();
    ArrayList<String>keys2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_feed);
        setTitle("Your Feeds ");

        mBloglist=findViewById(R.id.recyclerview_personal);
        mDatabase = FirebaseDatabase.getInstance();
        mBloglist.setLayoutManager(new LinearLayoutManager(this));
        mRef = mDatabase.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageinfo");

        LoadData();
    }

    private void LoadData() {

  options=new FirebaseRecyclerOptions.Builder<getfeed>().setQuery(mRef,getfeed.class).build();

  firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<getfeed, holder>(options) {
      @Override
      protected void onBindViewHolder(@NonNull final holder holder, final int i, @NonNull final getfeed getfeed) {
          final int position=i;
          holder.feed_title.setText(getfeed.getTitle());
          holder.feed_description.setText(getfeed.getDescription());
          Glide.with(YourFeed.this).load(getfeed.getImageurl()).override(900,900).centerInside().into(holder.feed_image);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("imageinfo")
                            .child(getRef(i).getKey())
                            .removeValue();

                    FirebaseDatabase.getInstance().getReference().child("users").child("imageinfo").child(getRef(i).getKey()).removeValue();
                    Intent intent=new Intent(YourFeed.this,HomeScreen.class);
                    startActivity(intent);
                }
            });
      }


      @NonNull
      @Override
      public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds,parent,false);
          return new holder(view);
      }
  };
        firebaseRecyclerAdapter.startListening();
        mBloglist.setAdapter(firebaseRecyclerAdapter);
    }
    }
