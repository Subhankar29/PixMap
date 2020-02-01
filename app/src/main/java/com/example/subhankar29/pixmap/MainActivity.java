package com.example.subhankar29.pixmap;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mChooseBtn;
    Button edit;
    Bitmap bitmap;

    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChooseBtn = findViewById(R.id.chooseImage);

        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Runtime permissions.
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                        requestPermissions(new String[]{permission},PERMISSION_CODE);

                    }else{

                        pickImageFromGalley();
                    }

                }
            }
        });

        }

        private void pickImageFromGalley(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGalley();
                }else{
                    Toast.makeText(this,"Permission denied..", Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            Uri uri = data.getData();
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //mImageView.setImageBitmap(bitmap);
               /* Intent intent =new Intent();
                intent.setClass(MainActivity.this, ImageEditing.class);
                intent.putExtra("Bitmap", bitmap);
                startActivity(intent);
                */
                Intent intent = new Intent(this, ImageEditing.class);
                intent.putExtra("ImageUri", uri.toString());
                startActivity(intent);
                finish();
               // mImageView.setImageURI(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
           // mImageView.setImageURI(data.getData());

        }
    }


}
