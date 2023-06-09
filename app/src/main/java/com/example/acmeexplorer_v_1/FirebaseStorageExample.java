package com.example.acmeexplorer_v_1;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Calendar;
public class FirebaseStorageExample extends AppCompatActivity {
    protected final SimplifyActivity<Intent, ActivityResult> activityLauncher = SimplifyActivity.registerActivityForResult(this);

    private static final int CAMERA_PERMISSION_REQUEST = 0x234;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 0x235;

    private Button btn_takePicture;
    private ImageView iv_takePicture;

    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_storage_example);

        btn_takePicture = findViewById(R.id.btn_takePicture);
        iv_takePicture = findViewById(R.id.iv_takePicture);

        btn_takePicture.setOnClickListener(listener -> checkPermissions());
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            checkStoragePermissions();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(btn_takePicture, "Permisos de camara requeridos", Snackbar.LENGTH_LONG).setAction("OK", click -> ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, CAMERA_PERMISSION_REQUEST)).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }
    }

    private void checkStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(btn_takePicture, "Permisos de escritura requeridos", Snackbar.LENGTH_LONG).setAction("OK", click -> ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST)).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
        }
    }

    private void takePicture() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String dir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/acmeexplorer";
        File newFile = new File(dir);
        newFile.mkdirs();

        file = dir + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
        File newFilePicture = new File(file);

        try {
            newFilePicture.createNewFile();
        } catch (Exception e) {
            Log.i("epic", "Error creating new file: " + e.getMessage());
        }

        Uri outputFileDir = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", newFilePicture);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileDir);

        Log.i("epic", BuildConfig.APPLICATION_ID + ".fileProvider");


        activityLauncher.launch(cameraIntent, result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                File filePicture = new File(file);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child(filePicture.getName());

                UploadTask uploadTask = storageReference.putFile(Uri.fromFile(filePicture));
                uploadTask.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("epic", "Firebase storage completed: " + task.getResult().getTotalByteCount());

                        storageReference.getDownloadUrl().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                Glide.with(FirebaseStorageExample.this)
                                        .load(task2.getResult())
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .centerCrop()
                                        .into(iv_takePicture);
                            }
                        });
                    }
                }).addOnFailureListener(e -> {
                    Log.e("epic", "Firebase Storage Error: " + e.getMessage());
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkStoragePermissions();
                } else {
                    Toast.makeText(this, "No se puede utilizar esta función sin la camara", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(this, "No se puede almacenar la foto sin permisos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}