package com.example.blogfun;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ViewHolder extends RecyclerView.ViewHolder {
    TextView post_title;
    TextView post_description;
    ImageView imageView;
    TextView from;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

         post_title=(TextView)itemView.findViewById(R.id.post_title);
         post_description=(TextView)itemView.findViewById(R.id.post_description);
         imageView=(ImageView)itemView.findViewById(R.id.post_image);
         from=(TextView)itemView.findViewById(R.id.from);
    }

}
