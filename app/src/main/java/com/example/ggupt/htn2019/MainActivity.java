package com.example.ggupt.htn2019;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCaptureImage;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImage = (Button) findViewById(R.id.btn_captureImage);
        layout = (LinearLayout)findViewById(R.id.linearLayout);

        btnCaptureImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        setImage(bitmap);
    }

    public void setImage(Bitmap bitmap){
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
        bitmapArray.add(bitmap);

        ImageView image = new ImageView(this);
        image.setLayoutParams(new android.view.ViewGroup.LayoutParams(1000,1500));
        image.setMaxHeight(150);
        image.setMaxWidth(75);
        image.setImageBitmap(bitmap);
        // Adds the view to the layout
        layout.addView(image);
    }
}
