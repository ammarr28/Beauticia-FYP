package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetNowActivity extends AppCompatActivity {
    TextView goregister;
    TextInputEditText uemail,upass;
    Button btn_forget;
    StringRequest request;
    RequestQueue res;
    ProgressDialog loadingbar;
    String url="https://bigbox.com.pk/beauticia/change_password.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_now);
        goregister=findViewById(R.id.goregister);
        btn_forget=findViewById(R.id.btn_forget);
        uemail=findViewById(R.id.uemail);
        upass=findViewById(R.id.upass);
        loadingbar=new ProgressDialog(this);

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();





        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetNowActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingbar.setTitle("Login Now");
                loadingbar.setMessage("Please Wait..While Checking Your Crediential");
                loadingbar.show();
                final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
                final SharedPreferences.Editor edit=shared.edit();
                request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.contains("Email Does Not Exist"))
                        {

                            Toast.makeText(ForgetNowActivity.this, "Email Does Not Exist", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent=new Intent(ForgetNowActivity.this,ForgetNowActivity.class);
                            startActivity(intent);
                            finish();


                        }

                        else
                        {
                            Intent intent=new Intent(ForgetNowActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                            loadingbar.dismiss();
                            //  Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();





                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgetNowActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                })
                {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String ,String> map=new HashMap<>();
                        map.put("uemail",uemail.getText().toString());
                        map.put("upass",upass.getText().toString());



                        return map;
                    }
                };


                res= Volley.newRequestQueue(ForgetNowActivity.this);
                res.add(request);

            }
        });
    }
}