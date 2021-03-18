package com.krishna.login_registratioin_validation;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button login,signup;
    SharedPreferences sharedPreferences;
    String uname,pass;
    private final String url = "http://10.0.2.2/android_php/Login_Registration.php";
//    private final String url = "https://glossological-skies.000webhostapp.com/Login_Registration.php";


    public static final String fileName = "login";
    public static final String USERNAME = "t1";
    public static final String PASSWORD = "t2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.submit);
        signup = findViewById(R.id.signup);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(USERNAME)){
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkData();
            }
        });

    }

    private void checkData() {
        username = findViewById(R.id.t1);
        password = findViewById(R.id.t2);

        uname = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);

                    int success = object.getInt("sucess");
                    if (success == 1)
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USERNAME,uname);
                        editor.putString(PASSWORD,pass);
                        editor.commit();
                        username.setText("");
                        password.setText("");

                        Toast.makeText(getApplicationContext(),"User Login Successfull..",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter Valid Username & Password",Toast.LENGTH_LONG).show();
                        username.setText("");
                        password.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Exception Occured",Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                username.setText("");
                username.requestFocus();
                password.setText("");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> map = new HashMap<String, String>();
                map.put(USERNAME,uname);
                map.put(PASSWORD,pass);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}