package com.tn3.mobile.hermes.utils;

import android.util.Log;
import com.google.gson.Gson;
import com.tn3.mobile.hermes.dto.ResultWSDTO;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    static URL url;
    static HttpURLConnection http;
    static InputStream is;
    static Reader reader;

    public static Reader get(String surl) {

        try {

            url = new URL(surl);
            http = (HttpURLConnection) url.openConnection();

            is = new BufferedInputStream(http.getInputStream());
            reader = new InputStreamReader(is);

            return reader;

        } catch (IOException e) {
            Log.e("HttpUtils", "error", e);
        }

        return null;
    }

    public static ResultWSDTO post(String surl, String jsonDATA, String method) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        if(method.isEmpty()) {
            method = "POST";
        }

        try {

            url = new URL(surl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(8000);
            urlConnection.setRequestMethod(method);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");

            //{"body":"eyJ0b2tlbiI6Ijg1RDQ3QlAxU0E1MyIsImltZWkiOiIxMjM0NTY3ODkwNTQzMjEiLCJhY2FvIjoiYXV0b3JpemEifQ=="}
            JSONObject postDict = new JSONObject();
            try {
                postDict.put("body", jsonDATA);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("POSTJSON", String.valueOf(postDict));

            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(String.valueOf(postDict));

            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            return result(reader, ResultWSDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("HttpUtils", "Error closing stream", e);
                }
            }
        }
        return null;
    }

    public static <T> T result(Reader read, Class<T> cc) {
        reader = read;
        T rs = new Gson().fromJson(reader, cc);
        return rs;
    }

    public static <T> T get(String surl, Class<T> cc) {
        reader = HttpUtils.get(surl);
        T rs = new Gson().fromJson(reader, cc);
        return rs;
    }
}
