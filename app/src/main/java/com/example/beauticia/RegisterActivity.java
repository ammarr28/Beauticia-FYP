package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView gologin;
    Spinner ucity,logintype;
    String[] usertype = { "User", "Saloon"};
    String[] cityname = { "Karachi", "Lahore","Islambad","Multan","Hyderabad","Faisalabad","Others"};
    Button btn_signup;
    TextInputEditText uname,uemail,upass,uphone,uaddress;
    String url="https://bigbox.com.pk/beauticia/user_register.php";
    StringRequest request;
    RequestQueue res;
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gologin=findViewById(R.id.gologin);
        ucity=findViewById(R.id.ucity);
        logintype=findViewById(R.id.logintype);
        btn_signup=findViewById(R.id.btn_signup);
        uname=findViewById(R.id.uname);
        uemail=findViewById(R.id.uemail);
        upass=findViewById(R.id.upass);
        uphone=findViewById(R.id.uphone);
        uaddress=findViewById(R.id.uaddress);
        loadingbar=new ProgressDialog(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item,usertype);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        logintype.setAdapter(ad);

        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,cityname);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ucity.setAdapter(ad2);

        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        //register code
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(upass.length() >= 8) {

                    if (uphone.length() >= 11) {
                        loadingbar.setTitle("Reister Now");
                        loadingbar.setMessage("Please Wait..While Adding The Record");
                        loadingbar.show();


                        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Register Successfully")) {

                                    Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();


                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                } else if (response.contains("Register Un Successfully")) {
                                    Toast.makeText(RegisterActivity.this, "Register Un Successfully", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                } else if (response.contains("Email Already Register")) {
                                    Toast.makeText(RegisterActivity.this, "Email Already Register", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }) {


                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> map = new HashMap<>();
                                map.put("uname", uname.getText().toString());
                                map.put("uemail", uemail.getText().toString());
                                map.put("upass", upass.getText().toString());
                                map.put("uphone", uphone.getText().toString());
                                map.put("uaddress", uaddress.getText().toString());
                                map.put("ucity", ucity.getSelectedItem().toString());
                                map.put("logintype", logintype.getSelectedItem().toString());


                                return map;
                            }
                        };


                        res = Volley.newRequestQueue(RegisterActivity.this);
                        res.add(request);

                    } else {
                        Toast.makeText(RegisterActivity.this, "Phone Length Min 11", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Password Length Min 8", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}