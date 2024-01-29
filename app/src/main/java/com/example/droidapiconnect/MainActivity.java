package com.example.droidapiconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.droidapiconnect.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cityEd;
    TextView resultTv;
    Button btnFetch;
    String cityName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEd = findViewById(R.id.city_et);
        resultTv = findViewById(R.id.result_tv);
        btnFetch = findViewById(R.id.btn_fetch);

        btnFetch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_fetch) {
            cityName = cityEd.getText().toString();
            try {
                queryData(cityName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void queryData(String city) throws IOException {
        URL url= NetworkUtils.buildUrl();
        new DataTask().execute(url);


    }
    public class DataTask extends AsyncTask<URL,Void,String> {


        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data= null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            setcityData(s);
        }
        public void setcityData(String data){
            JSONObject myObject=null;
            try {
                myObject = new JSONObject(data);
                JSONArray citya = myObject.getJSONArray("data");
                for (int i=0; i<citya.length();i++) {
                    JSONObject cityo = citya.getJSONObject(i);
                    String cityn = cityo.get("State").toString();
                    Log.d("adApi",cityn);
                    Log.d("TextCityName","cityName");
                    if (cityn.equals(cityName)) {
                        String cityp = cityo.get("Population").toString();
                        resultTv.setText(cityp);
                        break;
                    }
                    else
                    {
                        resultTv.setText(cityName + "Not Found");

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        }
}