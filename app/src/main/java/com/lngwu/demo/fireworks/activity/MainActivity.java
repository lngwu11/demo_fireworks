package com.lngwu.demo.fireworks.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lngwu.demo.fireworks.R;
import com.lngwu.demo.fireworks.view.FireworksView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        FireworksView fireworksView = new FireworksView(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_main);
        frameLayout.addView(fireworksView);
    }
}
