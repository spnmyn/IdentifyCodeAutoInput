package com.example.administrator.identifycodeautoinput;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tech.michaelx.authcode.AuthCode;
import tech.michaelx.authcode.CodeConfig;

public class MainActivity extends AppCompatActivity {
    /*声明权限标识符*/
    private static final int REQUEST_PERMISSION_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 动态获取权限
        handlePermission();

        findViewById(R.id.get_code_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeConfig config = new CodeConfig.Builder()
                        .codeLength(4) // 设置验证码长度
                        .smsFromStart(133) // 设置验证码发送号码前几位数字
                        //.smsFrom(1690123456789) // 如果验证码发送号码固定，则可以设置验证码发送完整号码
                        .smsBodyStartWith("百度科技") // 设置验证码短信开头文字
                        .smsBodyContains("验证码") // 设置验证码短信内容包含文字
                        .build();
                AuthCode.getInstance().with(MainActivity.this).config(config).into((EditText) findViewById(R.id.code_et));
            }
        });

    }

    /**
     * 简单处理了短信权限
     */
    private void handlePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length != 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您阻止了app读取您的短信，您可以自己手动输入验证码", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AuthCode.getInstance().onDestroy();
    }
}
