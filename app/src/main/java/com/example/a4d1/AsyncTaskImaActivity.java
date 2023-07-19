package com.example.a4d1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a4d1.Interface.MListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;



public class AsyncTaskImaActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Button btn;
    ImageView img;

    MListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_task_ima);
         img = findViewById(R.id.imgAs);
         btn = findViewById(R.id.btnShowAs);
         progressDialog = new ProgressDialog(AsyncTaskImaActivity.this);

         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new LoadImageTask().execute("https://cdn.pixabay.com/photo/2023/05/03/12/40/bird-7967570_1280.jpg");
             }
         });

    }
    public class LoadImageTask extends AsyncTask<String, Void , Bitmap>{

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("doadloading...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
            }  catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if (bitmap != null){
                mListener.onImageLoaded(bitmap);
            }else {
                mListener.enError();
            }
        }
    }

}