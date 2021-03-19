package com.krishna.login_registratioin_validation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://10.0.2.2/android_php/fetchData.php";

    TextView fname,lname,address,mobile,email,password;
    EditText searchName;
    Button search;
    SharedPreferences sharedPreferences;
    public static final String fileName = "login";
    public static final String USERNAME = "t1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchName = findViewById(R.id.serchname);
        search = findViewById(R.id.search);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USERNAME)) {
//            textView.setText("Welcome To AsciiEducation");
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fetchData();
            }
        });
    }

    private void fetchData() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);

                    String First = object.getString("one");
                    String Last = object.getString("two");
                    String Address = object.getString("three");
                    String Email = object.getString("four");
                    String Mobile = object.getString("five");
                    String Password = object.getString("six");

                    fname.setText("First");
                    lname.setText("Last");
                    address.setText("Address");
                    email.setText("Email");
                    mobile.setText("Mobile");
                    password.setText("Password");

                    int success = object.getInt("sucess");
                    if (success == 0)
                    {
                        Toast.makeText(MainActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else if (success == 1)
                    {
                        Toast.makeText(MainActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    else if (success == 2)
                    {
                        Toast.makeText(MainActivity.this,object.getString("success"),Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();

                map.put("searchName",searchName.getText().toString().trim());
                Log.d("searchName", searchName.getText().toString().trim());


                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}