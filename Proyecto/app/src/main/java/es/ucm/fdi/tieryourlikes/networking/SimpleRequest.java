package es.ucm.fdi.tieryourlikes.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import es.ucm.fdi.tieryourlikes.AppConstants;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SimpleRequest {

    private static final String DIR_PROTOCOL = "http";
    private static final String IP_ADDRESS = "10.0.2.2";
    private static final int PORT = 5000;
    private static final int TTL_SECONDS = 40;
    private static final int TTL_MSECONDS = 3000;
    private static final String SERVER_URL = DIR_PROTOCOL + "://" + IP_ADDRESS + ":" + PORT;
    private static final String DIRECTORY = "_templateImages";
    private static final String IMAGE_DIRECTORY = SERVER_URL + "/" + DIRECTORY;

    private volatile String response;

    public SimpleRequest(){
        response = null;
    }

    public Call createCall(Request request){
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(TTL_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TTL_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TTL_SECONDS, TimeUnit.SECONDS)
                .build();
        Call call = client.newCall(request);

        return  call;
    }


    public Request buildRequest(String postBodyString, String method, String route) {

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(postBodyString, mediaType);

        Request request = null;

        if(method.equals(AppConstants.METHOD_GET)){
            request = new Request.Builder()
                    .get()
                    .url(SERVER_URL + route)//Ej http://10.0.0.2:5000/login
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Connection", "close")
                    .build();
        }else{
            request = new Request.Builder()
                    .method(method, body)
                    .url(SERVER_URL + route)//Ej http://10.0.0.2:5000/login
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Connection", "close")
                    .build();
        }

        return request;
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static String getImageDirectory() {
        return IMAGE_DIRECTORY;
    }

    public String getResponse(){
        return this.response;
    }


    //https://stackoverflow.com/questions/25805580/how-to-quickly-check-if-url-server-is-available
    public static boolean isServerReachable(Context context) {
        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL urlServer = new URL(SERVER_URL);
                HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                urlConn.setConnectTimeout(3000); //<- 3Seconds Timeout
                urlConn.connect();
                if (urlConn.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isHostReachable(){
        boolean exists = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress(IP_ADDRESS, PORT);

            // Create an unbound socket
            Socket sock = new Socket();

            // This method will block no more than timeoutMs.
            // If the timeout occurs, SocketTimeoutException is thrown.
            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            exists = true;
        } catch(IOException e) {
            // Handle exception
        }
        return exists;
    }

}