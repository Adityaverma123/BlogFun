package com.example.blogfun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {
    EditText title,description;
    Button post,addimage;
    ProgressDialog loadingbar;
    ImageView imageView;
    String imagename=UUID.randomUUID().toString()+".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().hide();
        loadingbar=new ProgressDialog(this);
        description=findViewById(R.id.postdescription);
        post=findViewById(R.id.postsubmit);
        addimage=findViewById(R.id.addimage);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
                else
                {
                    grantAccess();
                }
            }
        });

    }

        public void grantAccess()
        {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                grantAccess();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage=data.getData();
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            try {

                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                 imageView=(ImageView)findViewById(R.id.chooseimage);

                imageView.setImageBitmap(bitmap);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void post(View view)
    {
        loadingbar.setTitle("Uploading...");
        loadingbar.setMessage("Please Wait...");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final HashMap<String,Object>userdataMap=new HashMap<>();


        UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("images").child(imagename).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                loadingbar.dismiss();
                Toast.makeText(PostActivity.this,"Something went wrong :(",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                FirebaseStorage.getInstance().getReference().child("images").child(imagename).getDownloadUrl()
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Intent intent=new Intent(PostActivity.this,HomeScreen.class);
                                Uri url=task.getResult();
                                Log.i("URL",url.toString());
                                loadingbar.dismiss();
                                intent.putExtra("imagename",imagename);
                                intent.putExtra("imageurl",url.toString());
                                intent.putExtra("title",title.getText().toString());
                                intent.putExtra("description",description.getText().toString());
                                intent.putExtra("from",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                startActivity(intent);



                            }
                        });


            }
        });

    }

}

