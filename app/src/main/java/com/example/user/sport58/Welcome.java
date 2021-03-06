package com.example.user.sport58;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;
import me.wangyuwei.particleview.ParticleView;


public class Welcome extends Activity implements Runnable {
    private ProgressBar pb1;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String appVersion;
    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自適屏布局始量化
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        setContentView(R.layout.activity_welcome);

        PackageManager manager = this.getPackageManager();
        try { PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            appVersion = info.versionName; //版本名
        } catch (PackageManager.NameNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }


       version=(TextView)findViewById(R.id.version);
        version.setText("V."+appVersion);

        Activity welcomeclose = this;

        SharedPreferences pref=getSharedPreferences("user",MODE_PRIVATE);
        String use = getSharedPreferences("user", MODE_PRIVATE).getString("user", "");
        String pass = getSharedPreferences("user", MODE_PRIVATE).getString("pass", "");


        ParticleView sport58 = findViewById(R.id.sport58);

        sport58.startAnim();

        sport58.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {

                    finish();
                    startActivity(new Intent(Welcome.this, menu.class));

            }
        });



    }


    @Override
    public void run() {

    }


}
