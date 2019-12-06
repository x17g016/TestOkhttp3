package com.example.testserver;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostAsyncTask extends AsyncTask<Object, Void, Object> {

    //弱参照を使用
    private final WeakReference<Activity> w_Activity;

    //コンストラクタで、 呼び出し元Activityを弱参照で変数セット
    PostAsyncTask(Activity activity) {
        // USE WEEK REFERENCE
        this.w_Activity = new WeakReference<>(activity);
    }

    @Override
    protected Object doInBackground(Object[] data) {
        //Object配列でパラメータを持ってこれたか確認
        String url = (String) data[0];
        String queryString = (String) data[1];

        //HTTP処理用オプジェクト作成
        OkHttpClient client = new OkHttpClient();

        //送信用ポストデータを作成
        MediaType MIMEType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(MIMEType, "{}");
        //送信用リクエストを作成
        Request request = new Request.Builder().
                url(url)
                .post(requestBody)
                .build();

        //受信用オブジェクトを作成
        Call call = client.newCall(request);
        String result = "";
        //送信と受信
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //結果を返し、onPostExecute で受け取る
        return result;
    }

    //バックグラウンド完了処理
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Log.i("onPostExecute", (String) result);
        //⑩簡単にJSONレスポンスをパースしてみる
        String title = "no response";
        String title2 = "";
        //String description = "";
        //String publicTime = "";
        try {
            //キー値をもとにJSON検索
            JSONObject json = new JSONObject((String) result);
            title = json.getString("name");
            title2 = json.getString("age");
            /*JSONObject descriptionObj = (JSONObject) json.get("description");
            description = descriptionObj.getString("text");
            publicTime = descriptionObj.getString("publicTime");*/
        } catch (JSONException je) {
            je.getStackTrace();
        }

        //画面表示
        Activity activity = w_Activity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            // activity is no longer valid, don't do anything!
            return;
        }
        TextView tv = activity.findViewById(R.id.textview);
        String showText = "post.ver" + "\n" + title + "\n" + title2;
        tv.setText(showText);


    }
}