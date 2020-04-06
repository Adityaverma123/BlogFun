package com.example.blogfun;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class holder extends RecyclerView.ViewHolder {
    ImageView feed_image;
    TextView feed_title;
    TextView feed_description;
     Button delete;

    public holder(@NonNull View itemView) {
        super(itemView);
        feed_title=(TextView)itemView.findViewById(R.id.feed_title);
        feed_description=(TextView)itemView.findViewById(R.id.feed_description);
        feed_image=(ImageView)itemView.findViewById(R.id.feed_image);
        delete=(Button)itemView.findViewById(R.id.delete_post);
    }

}
