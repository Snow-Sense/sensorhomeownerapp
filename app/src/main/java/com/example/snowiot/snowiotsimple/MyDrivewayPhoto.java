package com.example.snowiot.snowiotsimple;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class MyDrivewayPhoto extends AppCompatActivity {


    private TextView mPhotoUploadedOn, mPhotoUploadedBy;
    private ImageView mPlowedDrivewayPhoto;

    private StorageReference mDrivewayPhotoFolder = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_driveway_photo);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.snowiot.snowiotsimple", MODE_PRIVATE);

        mPlowedDrivewayPhoto = (ImageView) findViewById(R.id.drivewayFinishedPhoto);
        mPhotoUploadedBy = (TextView) findViewById(R.id.uploadedBy);

        mPhotoUploadedBy.setText("Last Job Performed By: " + sharedPreferences.getString("lastJobDoneBy", "null"));

        downloadPlowedDrivewayPhoto();

    }

    public void downloadPlowedDrivewayPhoto(){

        String appUserUID = ((GlobalVariables) this.getApplication()).getUserUID();

        mDrivewayPhotoFolder.child("users/" + appUserUID + "/drivewayfinished.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext()).load(uri).into(mPlowedDrivewayPhoto);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Download failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
