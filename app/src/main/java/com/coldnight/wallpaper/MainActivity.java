package com.coldnight.wallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private WallpaperManager wm;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wm = WallpaperManager.getInstance(this);

        //选择静态壁纸
        ImageView wallPaper_btn = findViewById(R.id.wall_paper_btn);
        wallPaper_btn.setOnClickListener(v -> chooseFile(Consts.REQUEST_CHOOSEFILE_STATIC));

        //选择动态壁纸
        ImageView wallPaper_dynamic_btn = findViewById(R.id.wall_paper_dynamic_btn);
        wallPaper_dynamic_btn.setOnClickListener(v -> chooseFile(Consts.REQUEST_CHOOSEFILE_DYNAMIC));

        //清除壁纸
        TextView clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> clear_static());
    }

    private void clear_static() {
        try {
            wm.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    private void setWallPaper() {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            wm.setStream(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseFile(int type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType("*/*");//无类型限制
//        intent.setType("video/*;image/*");//同时选择视频和图片
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//选择文件返回
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode" + resultCode + "\nRESULT_OK" + RESULT_OK );
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Consts.REQUEST_CHOOSEFILE_STATIC:
                    Uri uri = data.getData();
                    String chooseFilePath = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
                    filePath = chooseFilePath;
                    Log.d("TAG", "选择文件返回：" + chooseFilePath);
                    setWallPaper();
                    //                  sendFileMessage(chooseFilePath);
                    break;
                case Consts.REQUEST_CHOOSEFILE_DYNAMIC:

                    break;
            }
        }
    }


}