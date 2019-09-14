package com.example.ggupt.htn2019;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Node;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnCaptureImage;
    LinearLayout layout;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImage = (Button) findViewById(R.id.btn_captureImage);
        layout = (LinearLayout)findViewById(R.id.linearLayout);
        test = (TextView)findViewById(R.id.textView);

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

        if(bitmapArray.size() == 2){
            sendImageToNode(bitmapArray);
        }
//        test();
    }

    public void sendImageToNode(ArrayList<Bitmap> bitmapArray){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.33.141.252:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NodeServerApi nodeServerApi = retrofit.create(NodeServerApi.class);

        ArrayList<String> imageString = new ArrayList<>();
        for(int i = 0; i < bitmapArray.size(); i++){
            imageString.add(convertToBase64(bitmapArray.get(i)));
        }

        Call<NodeResponse> call = nodeServerApi.sendImage(imageString);

        call.enqueue(new Callback<NodeResponse>() {


            @Override
            public void onResponse(Call<NodeResponse> call, Response<NodeResponse> response) {
                if(!response.isSuccessful()){
                    test.setText("FAILED");
                }

                NodeResponse nodeResponse = response.body();
                if(nodeResponse.isResult()){
                    test.setText(nodeResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NodeResponse> call, Throwable t) {
                test.setText("FAILED" + t.getMessage());
            }
        });
    }

    private String convertToBase64(Bitmap bm)

    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }

//    public void test(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.33.141.252:3000")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        NodeServerApi nodeServerApi = retrofit.create(NodeServerApi.class);
//
//        Call<List<AutodeskResponse>> call = nodeServerApi.getTest();
//
//        call.enqueue(new Callback<List<AutodeskResponse>>() {
//
//
//            @Override
//            public void onResponse(Call<List<AutodeskResponse>> call, Response<List<AutodeskResponse>> response) {
//                if(!response.isSuccessful()){
//                    test.setText("FAIELD");
//                    return;
//                }
//
//                List<AutodeskResponse> result = response.body();
//                for(AutodeskResponse resultObj : result){
//                    test.setText(resultObj.getInfo());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AutodeskResponse>> call, Throwable t) {
//                test.setText("FAILED" + t.getMessage());
//            }
//        });
//    }
}
