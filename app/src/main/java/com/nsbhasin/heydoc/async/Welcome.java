package com.nsbhasin.heydoc.async;

import android.content.Context;
import android.os.AsyncTask;

import com.nsbhasin.heydoc.activity.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Welcome extends AsyncTask<String, Void, JSONObject> {
    private Context mContext;

    public Welcome(Context context) {
        this.mContext = context;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject jobj = null;
        try {
            String text = "";
            BufferedReader reader = null;
            URL url = new URL("https://young-retreat-26153.herokuapp.com/welcome");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }
            text = sb.toString();
            reader.close();
            jobj = new JSONObject(text);
            return jobj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        MainActivity context = (MainActivity) mContext;
        context.ServerWelcome(result);
    }
}
