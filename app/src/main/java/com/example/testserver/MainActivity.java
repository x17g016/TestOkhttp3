package com.example.testserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener { //①OnClickListener インタフェイスを継承

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActivityのonClickListenerを割り当てる
        findViewById(R.id.button_get).setOnClickListener(this);
        findViewById(R.id.button_post).setOnClickListener(this);
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
