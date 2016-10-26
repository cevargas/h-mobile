package com.tn3.mobile.hermes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.tn3.mobile.hermes.models.Coletas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public Context context;
    private LocationManager locationManager;
    private String fileLocation = Environment.getExternalStorageDirectory() +"/TN3Hermes/";

    public Utils(Context context) {
        this.context = context;
    }

    public Utils() {
    }

    public Locale BRAZIL = new Locale("pt", "BR");

    public String convertToJson(String token, String imei) throws JSONException {

        //{"token":"85D47BP1SA53","imei":"123456789054321","acao":"autoriza"}

        JSONObject postDict = new JSONObject();
        postDict.put("token", token);
        postDict.put("imei", imei);
        postDict.put("content", null);

        String json = String.valueOf(postDict);
        byte[] data = new byte[0];
        try {
            data = json.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        Log.d("JSONAUTH", base64);

        return base64;
    }

    public static String convertJsonToB64(Object json) throws JSONException {

        String njson = String.valueOf(json);
        byte[] data = new byte[0];
        try {
            data = njson.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        //Log.d("postJson", base64);
        return base64;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String formatDate(Date param) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        //df = DateFormat.getDateInstance(DateFormat.FULL, BRAZIL);
        return df.format(param);
    }

    public String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS", BRAZIL);
        String strDate = sdf.format(new Date());
        return strDate;
    }

    public Date formatDate(String ndate){
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

        try {
            return formatter.parse(ndate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Date dbStringToDateForDb(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS", BRAZIL);
        Date d = new Date();
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public String dbFormatHour(Date date){
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", BRAZIL);
        return formatter.format(date);
    }


    public String dbFormatDate(Date date){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        return formatter.format(date);
    }

    public Date dbStringToDate(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        Date d = new Date();
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public String getMonth(String data) throws ParseException {

        DateFormat inputDF = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        Date date1 = inputDF.parse(data);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        //int month = cal.get(Calendar.MONTH);
        //int day = cal.get(Calendar.DAY_OF_MONTH);
        //int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH);
        return theMonth(month);

    }

    public String getDay(String data) throws ParseException {

        DateFormat inputDF = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        Date date1 = inputDF.parse(data);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }

    public static String theMonth(int month) {
        String[] monthNames = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
        return monthNames[month];
    }

    public void alert(String msg, int duraction) {
        Toast.makeText(context, msg, duraction).show();
    }

    public boolean isConnected() {
        ConnectionCheckUtils connectionCheckUtils = new ConnectionCheckUtils(context);
        return connectionCheckUtils.hasInternetConnection();
    }

    public String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String myAndroidDeviceId = "";

        if (telephonyManager.getDeviceId() != null){
            myAndroidDeviceId = telephonyManager.getDeviceId();
        }else {
            myAndroidDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return myAndroidDeviceId;
    }

    public boolean checkGPS() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean checkNetwork() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void writeToFile(String nomeDoArquivo, String data) {
        try {

            File root = new File(fileLocation);
            if (!root.exists()) {
                root.mkdirs();
            }

            File filepath = new File(root, nomeDoArquivo + ".txt");
            FileWriter writer = new FileWriter(filepath, true);
            writer.append(data);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            Log.e("UtilsException", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(String nomeDoArquivoSalvo) {

        String aBuffer = "";

        try {
            File myFile = new File(fileLocation + nomeDoArquivoSalvo + ".txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";

            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            Log.e("UtilsException", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("UtilsException", "Can not read file: " + e.toString());
        }

        return aBuffer;
    }

    public boolean fileExists(String file) {
        File arquivo = new File(fileLocation + file + ".txt");
        return arquivo.exists();
    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public boolean saveImageToInternalStorage(Bitmap image, String name) {

        OutputStream stream = null;
        File file;

        try {

            if(isExternalStorageWritable()) {
                file = new File(fileLocation, name);
                stream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.flush();
                stream.close();
            }
            else {
                Log.e("saveToInternalStorage()", "Sem permissao para salvar a imagem em disco interno");
                return false;
            }

        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }

        return true;
    }

    /*
    public Bitmap loadImageFromStorage(Coletas coletas) {

        Bitmap bitmap = null;

        try {
            File f = new File(fileLocation, coletas.getFotoCancelamentoColeta());
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    */

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
