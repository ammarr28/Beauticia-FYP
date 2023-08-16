package com.example.beauticia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaloonServiceActivity extends AppCompatActivity {

    ListView listView;
    StringRequest request;
    RequestQueue res;
    String url="https://bigbox.com.pk/beauticia/fetch_saloonservice.php";
    AllService service;
    AllServiceAdapter serviceAdapter;
    public  static ArrayList<AllService> arrayappliedlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_service);
        Intent intent = getIntent();
        String sid = intent.getStringExtra("sid");

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();




        listView=findViewById(R.id.allservicelist);
        serviceAdapter=new AllServiceAdapter(this,arrayappliedlist);
        listView.setAdapter(serviceAdapter);

arrayappliedlist.clear();
        request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);

                        String service_id=obj.getString("service_id");
                        String service_name=obj.getString("service_name");
                        String service_fee=obj.getString("service_fee");
                        String saloon_id=obj.getString("saloon_id");
                        String saloon_name=obj.getString("user_name");


                        service=new AllService(service_id,service_name,service_fee,saloon_id,saloon_name);
                        arrayappliedlist.add(service);
                        serviceAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SaloonServiceActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("sid", sid);

                return map;
            }
        };

        res= Volley.newRequestQueue(SaloonServiceActivity.this);
        res.add(request);




    }
}