package com.example.testserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener { //①OnClickListener インタフェイスを継承

    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        //ActivityのonClickListenerを割り当てる
        findViewById(R.id.button_get).setOnClickListener(this);
        findViewById(R.id.button_post).setOnClickListener(this);

    }

    public void checkPermission(){
        // パーミッションの許可確認
        // 許可されていない
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可をリクエスト
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
        }
        // パーミッションが許可されている
        else {
            Toast.makeText(this, "権限が許可されています", Toast.LENGTH_SHORT).show();
            // 以下通常処理等に飛ばす・・・
        }
    }

    //onClickコールバックをここですべてキャッチして、viewのidで動作を決める
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                //GET リクエストURLを設定
                String url1 = "https://kinako.cf/json.json";
                String queryString = ""; //phpの?など用

                //GetAsyncTaskに渡すパラメータをObject配列に設定
                Object[] getParams = new Object[2];
                getParams[0] = url1;
                getParams[1] = queryString;

                //GetAsyncTaskを実行
                new GetAsyncTask(this).execute(getParams);
                break;

            //idがpostだったら
            case R.id.button_post:
                //POSTリクエストして、自前のファイル受信サイトへアクセス
                String url2 = "https://kinako.cf/json.json";
                String testStr = "test";   //配列が送れるかのテスト用
                //PostAsyncTaskに渡すパラメータをObject配列に設定
                Object[] postParams = new Object[2];
                postParams[0] = url2;
                postParams[1] = testStr;

                //PostAsyncTaskを実行
                new PostAsyncTask(this).execute(postParams);
                break;

            default:
                break;
        }
    }
}
