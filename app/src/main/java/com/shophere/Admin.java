package com.shophere;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Admin extends AppCompatActivity {
    Button main_chhose,cat_choose,main_upload,cat_upload;
    EditText main_item,cat_item,cat_disc;

    private static final int PICK_IMAGE_MAIN=1;
    private static final int PICK_IMAGE_CAT=2;
    Uri mImageUri;
//    the fire base reference
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        main_chhose=findViewById(R.id.main_choose_image);
        cat_choose=findViewById(R.id.cat_choose_image);
        main_upload=findViewById(R.id.main_upload);
        cat_upload=findViewById(R.id.cat_upload);

        main_item=findViewById(R.id.main_edit_name);
        cat_item=findViewById(R.id.cat_edit_item);
        cat_disc=findViewById(R.id.cat_edit_desc);
        imageView=findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);


//        refence of the data base
        mStorageReference= FirebaseStorage.getInstance().getReference("main");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("main");

//        cat the functionality to chhose the button for main
        main_chhose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openFilechhose();
            }


        });
//        upload the item of main
        main_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadData();
                }
            }
        });

//        chhose the image for the catogaries
        cat_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilechhose();

            }
        });
//       upload the cattogaries

    }



    private void openFilechhose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_MAIN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_MAIN && resultCode==RESULT_OK
                && data !=null && data.getData() !=null){
            mImageUri= data.getData();
            Picasso.get().load(mImageUri).into(imageView);

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadData(){
        if (mImageUri != null){
          StorageReference fileReference=mStorageReference
          .child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            mUploadTask =fileReference.putFile(mImageUri)
                  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Handler handler = new Handler();
                          handler.postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                  mProgressBar.setProgress(0);
                              }
                          }, 500);
                          Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                          Main_list_item main_list_item=new Main_list_item(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),
                                  main_item.getText().toString().trim());
                          String uploadId=mDatabaseReference.push().getKey();
                          mDatabaseReference.child(uploadId).setValue(main_list_item);

                      }
                  })

                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                      }
                  })
                  .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                          double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                          mProgressBar.setProgress((int) progress);
                      }
                  });
        }
        else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }
}