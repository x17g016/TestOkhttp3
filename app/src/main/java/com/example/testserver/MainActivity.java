package com.example.testserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener { //①OnClickListener インタフェイスを継承

    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ActivityのonClickListenerを割り当てる
        findViewById(R.id.button_get).setOnClickListener(this);
        findViewById(R.id.button_post).setOnClickListener(this);

        String permissions[] = new String[]{Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE};
        checkPermission(permissions,REQUEST_CODE);

    }

    public void checkPermission(final String permissions[],final int request_code){
        // 同時に複数渡すが、許可されていないものだけダイアログが表示される
        ActivityCompat.requestPermissions(this, permissions, request_code);
    }

    // requestPermissionsのコールバック
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_CODE:
                for(int i = 0; i < permissions.length; i++ ){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "パーミッション追加しました", Toast.LENGTH_SHORT).show();
                        Log.w( "DEBUG_DATA", "パーミッション追加しました " + permissions[i] + " " + grantResults[i]);
                    } else {
                        Toast.makeText(this, "パーミッション追加できませんでした", Toast.LENGTH_SHORT).show();
                        Log.w( "DEBUG_DATA", "パーミッション追加できませんでした。 " + permissions[i] + " " + grantResults[i]);
                    }
                }
                break;
            default:
                break;
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
