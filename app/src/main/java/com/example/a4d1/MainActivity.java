package com.example.a4d1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.img);
        Button btn = findViewById(R.id.btnShow);
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Dowload");
                progressDialog.setMessage("Dowloadingg");
                progressDialog.show();

                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        final Bitmap bitmap = loadImage("https://cdn.pixabay.com/photo/2023/05/03/12/40/bird-7967570_1280.jpg");
                        img.post(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(bitmap);
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        thread.start();
                    }
                },3000);
            }
        });



    }

    private Bitmap loadImage(String link){
        URL url ;
        Bitmap bitmap = null;

        try {
            url =  new URL(link);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }
}