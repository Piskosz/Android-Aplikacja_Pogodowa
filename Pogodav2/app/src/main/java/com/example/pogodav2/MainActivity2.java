package com.example.pogodav2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {


    SwipeRefreshLayout swipe;
    ImageView image;
    TextView temp_yt,pressur,hum_yt,editText,min2,max2,czas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TextView message = findViewById(R.id.JD);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        String value = sharedPreferences.getString("message","");

        message.setText(value);

        image = findViewById(R.id.imageView2);
        editText = findViewById(R.id.JD);
        temp_yt = findViewById(R.id.Temp2);
        hum_yt = findViewById(R.id.Humidity2);
        pressur = findViewById(R.id.Pressure2);
        min2 = findViewById(R.id.Min2);
        max2 = findViewById(R.id.Max2);
        czas = findViewById(R.id.czas);
        swipe = findViewById(R.id.swipeToRefresh);
        updateTextView();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateTextView();

                swipe.setRefreshing(false);


            }
        });

        String city = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=e3d7873db4537bbeb2abd5161168df65&units=metric";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String city_find = jsonObject.getString("name");
                    editText.setText(city_find);

                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String icon = jsonObject1.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(image);

                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String temp_find = object2.getString("temp");
                    temp_yt.setText(temp_find + "°C");

                    JSONObject object3 = jsonObject.getJSONObject("main");
                    int Hum_find = object3.getInt("humidity");
                    hum_yt.setText(Hum_find + " %");


                    JSONObject object5 = jsonObject.getJSONObject("main");
                    double temp_min = object5.getDouble("temp_min");
                    min2.setText(temp_min+"°C");

                    JSONObject object6 = jsonObject.getJSONObject("main");
                    double temp_max = object6.getDouble("temp_max");
                    max2.setText(temp_max+"°C");

                    JSONObject object4 = jsonObject.getJSONObject("main");
                    String pres_find = object4.getString("pressure");
                    pressur.setText(pres_find+" hPa");


                }catch (JSONException e ){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        requestQueue.add(stringRequest);



    }
    private void updateTextView() {
        Date noteTS = Calendar.getInstance().getTime();
        czas.setText(DateFormat.getTimeInstance().format(noteTS));
    }


}