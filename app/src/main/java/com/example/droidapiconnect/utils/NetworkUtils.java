package com.example.droidapiconnect.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    final static String USA_DATA_BASE_URL="https://datausa.io/api/data?drilldowns=State&measures=Population&year=latest";

    public static URL buildUrl(){

        Uri urlStr = Uri.parse(USA_DATA_BASE_URL)
                .buildUpon().build();
        URL url= null;
        try {
            url = new URL(urlStr.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String makeHTTPRequest(URL url) throws IOException{
        HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
        InputStream inputStream=connection.getInputStream();

        try{
            Scanner scanner=new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput=scanner.hasNext();
            if(hasInput)
                return scanner.next();
            else
                return null;
        }
        finally {
            connection.disconnect();
        }
    }
}
