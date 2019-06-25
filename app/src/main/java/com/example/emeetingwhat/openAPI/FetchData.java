package com.example.emeetingwhat.openAPI;

import android.os.AsyncTask;

import com.example.emeetingwhat.MainPageActivity;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL  url = new URL("https://api.myjson.com/bins/y6mbp");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

                JSONObject JO = new JSONObject(data);
                dataParsed = "합계 : " + JO.get("available_amt") + "\n" +
                             "출금가능합계 :" + JO.get("balance_amt") + "\n" +
                             "계좌명 : " + JO.get("product_name");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainPageActivity.data.setText(this.dataParsed);
    }
}
