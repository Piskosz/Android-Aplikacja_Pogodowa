package com.example.pogodav2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String yourText = editText.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline()==true){
                    openActivity2();
                }
                else{

                }


            }
        });


    }
    public void openActivity2(){


        EditText editText = findViewById(R.id.editTextTextPersonName);
        String yourText = editText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("message",yourText);
        editor.apply();

        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Nie ma połączenia z internetem!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return false;
        }
    }


}