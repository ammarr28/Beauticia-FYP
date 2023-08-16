package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class AddServiceActivity extends AppCompatActivity {

    TextInputEditText sname,sfee;
    Button btn_add,btn_view;
    String url="https://bigbox.com.pk/beauticia/add_service.php";
    StringRequest request;
    RequestQueue res;
    ProgressDialog loadingbar;
    ImageView backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        sname=findViewById(R.id.sname);
        sfee=findViewById(R.id.sfee);
        backs=findViewById(R.id.backs);

        btn_add=findViewById(R.id.btn_add);
        btn_view=findViewById(R.id.btn_view);
        loadingbar=new ProgressDialog(this);
        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingbar.setTitle("Adding Service");
                loadingbar.setMessage("Please Wait..While Adding The Record");
                loadingbar.show();


                request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Add Successfully")) {

                            Toast.makeText(AddServiceActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();



                            Intent intent = new Intent(AddServiceActivity.this, ViewServiceListActivity.class);

                            startActivity(intent);

                        }

                        if (response.contains("Add Un Successfully")) {
                            Toast.makeText(AddServiceActivity.this, "Add Un Successfully", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddServiceActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("sname", sname.getText().toString());
                        map.put("sfee", sfee.getText().toString());
                        map.put("sid", shared.getString("user_id",""));

                        return map;
                    }
                };


                res = Volley.newRequestQueue(AddServiceActivity.this);
                res.add(request);

            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddServiceActivity.this,ViewServiceListActivity.class);
                intent.putExtra("sid",shared.getString("user_email",""));
                startActivity(intent);
            }
        });
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddServiceActivity.this,SaloonDashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}