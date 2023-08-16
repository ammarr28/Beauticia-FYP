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

public class LoginActivity extends AppCompatActivity {

    TextView goregister,goforget;
    Spinner logintype;
    TextInputEditText uemail,upass;
    Button btn_signin;
    String[] usertype = { "User", "Saloon"};
    StringRequest request;
    RequestQueue res;
    ProgressDialog loadingbar;
    String url="https://bigbox.com.pk/beauticia/user_login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        goregister=findViewById(R.id.goregister);
        logintype=findViewById(R.id.logintype);
        btn_signin=findViewById(R.id.btn_signin);
        uemail=findViewById(R.id.uemail);
        upass=findViewById(R.id.upass);
        loadingbar=new ProgressDialog(this);
        goforget=findViewById(R.id.goforget);

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();



        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item,usertype);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        logintype.setAdapter(ad);


        if(!shared.getString("user_email","").equals(""))
        {
            if(shared.getString("user_type","").equals("Saloon"))
            {
                Intent intent=new Intent(LoginActivity.this,SaloonDashboardActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }


        }


        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        goforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgetNowActivity.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
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


                        if(response.contains("Email And Password Does Not Match"))
                        {

                            Toast.makeText(LoginActivity.this, "Email & Password Does Not Match", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else if(response.contains("Account Block"))
                        {

                            Toast.makeText(LoginActivity.this, "Account Was Block", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            try {
                                JSONArray array=new JSONArray(response);
                                for (int i=0;i<array.length();i++)
                                {
                                    JSONObject obj=array.getJSONObject(i);


                                    String user_id=obj.getString("user_id");
                                    String user_name=obj.getString("user_name");
                                    String user_email=obj.getString("user_email");
                                    String user_phone=obj.getString("user_phone");
                                    String user_type=obj.getString("user_type");
                                    Log.d("usertype",user_type);
                                    Toast.makeText(LoginActivity.this, ""+user_type, Toast.LENGTH_SHORT).show();
                                    edit.putString("user_id",user_id);
                                    edit.putString("user_name",user_name);
                                    edit.putString("user_email",user_email);
                                    edit.putString("user_phone",user_phone);
                                    edit.putString("user_type",user_type);
                                    edit.commit();
                                    if(user_type.equals("Saloon"))
                                    {
                                        Intent intent=new Intent(LoginActivity.this,SaloonDashboardActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else{
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                            loadingbar.dismiss();
                            //  Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();





                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                })
                {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String ,String> map=new HashMap<>();
                        map.put("uemail",uemail.getText().toString());
                        map.put("upass",upass.getText().toString());
                        map.put("utype",logintype.getSelectedItem().toString());



                        return map;
                    }
                };


                res= Volley.newRequestQueue(LoginActivity.this);
                res.add(request);

            }
        });
    }
}