package com.example.blogfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {
    RecyclerView mBloglist;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseRecyclerOptions<getvalues>options;
    FirebaseRecyclerAdapter<getvalues,ViewHolder>firebaseRecyclerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.postblog) {
            Intent intent = new Intent(HomeScreen.this, PostActivity.class);
            startActivity(intent);

        }
        if(item.getItemId()==R.id.your_feed) {
            Intent intent=new Intent(HomeScreen.this,YourFeed.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("User Feed");
        mBloglist = findViewById(R.id.recyclerview);
        Intent intent = getIntent();

        HashMap<String, Object> imageinfo = new HashMap<>();
        imageinfo.put("imagename", intent.getStringExtra("imagename"));
        imageinfo.put("imageurl", intent.getStringExtra("imageurl"));
        imageinfo.put("title", intent.getStringExtra("title"));
        imageinfo.put("description", intent.getStringExtra("description"));
        imageinfo.put("from", intent.getStringExtra("from"));
        String id = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("imageinfo").child(id).setValue(imageinfo);
        FirebaseDatabase.getInstance().getReference().child("users").child("imageinfo").child(id).setValue(imageinfo);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("users").child("imageinfo");

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBloglist.setLayoutManager(new LinearLayoutManager(this));
        options=new FirebaseRecyclerOptions.Builder<getvalues>().setQuery(mRef,getvalues.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<getvalues, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull getvalues getvalues) {
                    viewHolder.post_title.setText(getvalues.getTitle());
                    viewHolder.post_description.setText(getvalues.getDescription());
                    viewHolder.from.setText(getvalues.getFrom());

                Glide.with(HomeScreen.this).load(getvalues.getImageurl()).override(900,900).centerInside().into(viewHolder.imageView);
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
                        return new ViewHolder(view);
            }

        };
        firebaseRecyclerAdapter.startListening();
        mBloglist.setAdapter(firebaseRecyclerAdapter);
    }
}
