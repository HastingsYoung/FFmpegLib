package com.module.ffmpeglib.samples;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.module.ffmpeglib.R;
import com.module.ffmpeglib.operators.VideoOperator;
import com.module.ffmpeglib.utils.CmdBuilder;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView tvStart;
    private TextView tvStart2;
    private TextView tvStart3;
    private ImageView ivGif;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStart = findViewById(R.id.tv_start);
        tvStart2 = findViewById(R.id.tv_start2);
        tvStart3 = findViewById(R.id.tv_start3);
        ivGif = findViewById(R.id.iv_gif);
        tvStart.setOnClickListener(this);
        tvStart2.setOnClickListener(this);
        tvStart3.setOnClickListener(this);

        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d("Perm", "Permission already granted.");
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    for (String pm : permission) {
                        Log.d("Perm", "Permission granted: " + pm);
                    }
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {

                }
            }, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            });
        }
    }

    @Override
    public void onClick(View view) {
        // resource ids cannot be used in a switch statement in android library modules
        if (view.getId() == R.id.tv_start) {
            test1();
        } else if (view.getId() == R.id.tv_start2) {

        } else if (view.getId() == R.id.tv_start3) {
            test3();
        }
    }


    private void test1() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d("HELLOJNI", VideoOperator.getInstance().getCompiledABI());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                });
            }
        }.start();
    }

    private void test3() {
        Glide.with(MainActivity.this).clear(ivGif);
        final CmdBuilder.FFmpegCmd cmd = CmdBuilder.newCmd()
                .addInputPath(PATH + "/DCIM/Camera/tudou.mp4")
                .setVideoOutputFrames(50)
                .setOverwriteOutput(true)
                .setOutputPath(PATH + "/DCIM/Camera/tudou_100.gif");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                int resultCode = VideoOperator.getInstance().runCmd(cmd);
                Log.d("FFMPEG", "result code: " + resultCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        progressDialog = null;
                        Glide.with(MainActivity.this)
                                .load(new File(PATH + "/DCIM/Camera/tudou_100.gif"))
                                .into(ivGif);
                    }
                });
            }
        }.start();
    }

}
