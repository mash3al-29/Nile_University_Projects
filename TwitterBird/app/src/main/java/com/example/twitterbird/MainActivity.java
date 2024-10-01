// MainActivity.java
package com.example.twitterbird;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = findViewById(R.id.takePictureButton);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        // If no camera is available, modify the button text
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            takePictureButton.setText("Select Photo");
        }

        takePictureButton.setOnClickListener(v -> {
            // Handle taking a picture or selecting a photo
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageView.setImageURI(data.getData());
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        // Implementation for uploading an image
        Toast.makeText(this, "Uploading image...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        // Handle image upload logic, similar to ViewController.swift
    }
}