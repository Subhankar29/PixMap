package com.example.subhankar29.pixmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageEditing extends AppCompatActivity {

    public Bitmap bitmap;
    public ImageView image;
    private String uri;
    public int buttonPress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmap =  (Bitmap)this.getIntent().getParcelableExtra("Bitmap");
        setContentView(R.layout.activity_image_editing);
        image = findViewById(R.id.imageView);
        Button flipHorizontal = findViewById(R.id.fHorizontal);
        Button flipVertical = findViewById(R.id.fVertical);
        Button opacity = findViewById(R.id.opacity);
        Button addText = findViewById(R.id.text);
        Button save = findViewById(R.id.save);
        Button addLogo = findViewById(R.id.addLogo);


        Bundle bundle = getIntent().getExtras();
        uri = bundle.getString("ImageUri");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(uri)).copy(Bitmap.Config.ARGB_8888, true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setImageBitmap(bitmap);

        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = addLogoGreedy(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        addText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 bitmap = addTextWithBackground(bitmap);
                 image.setImageBitmap(bitmap);
             }
         });

         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 saveImage(bitmap);
             }


         });

         opacity.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (buttonPress == 0)
                 { buttonPress = 1;
                 bitmap = changeOpacity(bitmap);
                 image.setImageBitmap(bitmap);
                }
                else
                     Toast.makeText(ImageEditing.this,"OPACITY ALREADY 50%", Toast.LENGTH_LONG).show();
             }
         });

         flipHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = horizontal(bitmap);
                image.setImageBitmap(bitmap);
            }
        });

        flipVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = vertical(bitmap);
                image.setImageBitmap(bitmap);
            }
        });
    }


    public Bitmap horizontal(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);

    }

    public Bitmap vertical(Bitmap src){
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public Bitmap changeOpacity(Bitmap bitmap){
        int opacity = 255/2;
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }

    public Bitmap addTextWithBackground(Bitmap bitmap){

        String str = "GREEDY GAMES";
        Paint.FontMetrics fm = new Paint.FontMetrics();
        Canvas canvas = new Canvas(bitmap);
        Paint mTxtPaint = new Paint();
        mTxtPaint.setColor(Color.BLACK);
        mTxtPaint.setTextSize(200);
        mTxtPaint.setTextAlign(Paint.Align.CENTER);

        mTxtPaint.getFontMetrics(fm);

        int margin = 5;
        int x = canvas.getWidth()/2;
        int y = canvas.getHeight()/2;

        canvas.drawRect(x - margin - 800, y + fm.top - margin,
                x + mTxtPaint.measureText(str) - 650, y + fm.bottom
                        + margin, mTxtPaint);

        mTxtPaint.setColor(getResources().getColor(android.R.color.holo_green_light));

        canvas.drawText(str, x, y, mTxtPaint);

        return bitmap;
    }

    public Bitmap addLogoGreedy(Bitmap bitmap){
        Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.greedylogo).copy(Bitmap.Config.ARGB_8888, true);
        int x = logo.getWidth();
        int y = logo.getHeight();
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(logo, (bitmap.getWidth()) - x,(bitmap.getHeight()) - y, new Paint());

        return bitmap;
    }

    public void saveImage(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Toast.makeText(ImageEditing.this,
                "saved",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
