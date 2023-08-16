package com.example.beauticia;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar mtoolbar;
    ActionBarDrawerToggle toggle;
    TextView mname,memail,mphone;
    ListView listView;
    StringRequest request;
    RequestQueue res;
    String url="https://bigbox.com.pk/beauticia/allservice.php";
    AllService service;
    AllServiceAdapter serviceAdapter;
    public  static ArrayList<AllService> arrayappliedlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, mtoolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mname=findViewById(R.id.mname);
        memail=findViewById(R.id.memail);
        mphone=findViewById(R.id.mphone);

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();

        mname.setText("Name : "+shared.getString("user_name",""));
        memail.setText("Email : "+shared.getString("user_email",""));
        mphone.setText("Phone : "+shared.getString("user_phone",""));

        listView=findViewById(R.id.allservicelist);
        serviceAdapter=new AllServiceAdapter(this,arrayappliedlist);
        listView.setAdapter(serviceAdapter);


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
                Toast.makeText(MainActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });

        res= Volley.newRequestQueue(MainActivity.this);
        res.add(request);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {




                switch (menuItem.getItemId()) {
                    case R.id.service_menu:


                        Intent intent7 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent7);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.saloon_menu:


                        Intent intent8 = new Intent(MainActivity.this, AllSaloonActivity.class);
                        startActivity(intent8);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.order_history:


                        Intent intent10 = new Intent(MainActivity.this, OrderHistoryActivity.class);
                        startActivity(intent10);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout:

                        edit.putString("user_id","");
                        edit.putString("user_name","");
                        edit.putString("user_email","");
                        edit.putString("user_phone","");
                        edit.putString("user_type","");
                        edit.commit();
                        Intent intent9 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent9);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;



                }


                return true;
            }
        });

    }
}