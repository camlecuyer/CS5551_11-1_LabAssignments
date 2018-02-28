package com.umkc.team11.lab3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ImageClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;

import java.io.File;
import java.io.FileNotFoundException;

public class HomeActivity extends AppCompatActivity
{
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    private DatabaseReference fireRoot = fireDB.getReference();
    private DatabaseReference fireChild = fireRoot.child("imageTag");
    private CameraHelper helper;
    private VisualRecognition service;
    private TextView storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        storage = findViewById(R.id.txt_storage);

        service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("6e667bcb49f3814221accbbf4325f3fa7f2bd7ec");
        helper = new CameraHelper(this);
    }

    @Override
    protected void onStart() {

        super.onStart();
        fireChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storage.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE)
        {
            final Bitmap photo = helper.getBitmap(resultCode);
            final File photoFile = helper.getFile(resultCode);

            ImageView preview = findViewById(R.id.preview);
            preview.setImageBitmap(photo);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    VisualClassification response =
                            service.classify(
                                    new ClassifyImagesOptions.Builder()
                                            .images(photoFile)
                                            .build()
                            ).execute();

                    ImageClassification classification =
                            response.getImages().get(0);

                    VisualClassifier classifier =
                            classification.getClassifiers().get(0);

                    final StringBuffer output = new StringBuffer();
                    for(VisualClassifier.VisualClass object: classifier.getClasses()) {
                        if(object.getScore() > 0.7f)
                            output.append("<")
                                    .append(object.getName())
                                    .append("> ");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView detectedObjects = findViewById(R.id.txt_pic);
                            detectedObjects.setText(output);
                            fireChild.setValue(detectedObjects.getText());
                        }
                    });
                }
            });
        }
    }

    public void scanPhoto(View v) throws FileNotFoundException
    {
        helper.dispatchTakePictureIntent();
    }
}
