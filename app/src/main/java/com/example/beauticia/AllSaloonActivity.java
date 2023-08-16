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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class AllSaloonActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar mtoolbar;
    ActionBarDrawerToggle toggle;
    TextView mname,memail,mphone;
    ListView listView;
    StringRequest request;
    RequestQueue res;
    String url="https://bigbox.com.pk/beauticia/fetch_allsaloons.php";
    AllSaloon service;
    SaloonAdapter serviceAdapter;
    public  static ArrayList<AllSaloon> arrayappliedlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_saloon);
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

        listView=findViewById(R.id.allsaloonlist);
        serviceAdapter=new SaloonAdapter(this,arrayappliedlist);
        listView.setAdapter(serviceAdapter);


        request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayappliedlist.clear();

                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);


                        String user_id=obj.getString("user_id");
                        String user_name=obj.getString("user_name");
                        String user_email=obj.getString("user_email");
                        String user_address=obj.getString("user_address");


                        service=new AllSaloon(user_id,user_name,user_email,user_address);
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
                Toast.makeText(AllSaloonActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        });

        res= Volley.newRequestQueue(AllSaloonActivity.this);
        res.add(request);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {




                switch (menuItem.getItemId()) {
                    case R.id.service_menu:


                        Intent intent7 = new Intent(AllSaloonActivity.this, MainActivity.class);
                        startActivity(intent7);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.saloon_menu:


                        Intent intent8 = new Intent(AllSaloonActivity.this, AllSaloonActivity.class);
                        startActivity(intent8);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.order_history:


                        Intent intent10 = new Intent(AllSaloonActivity.this, OrderHistoryActivity.class);
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
                        Intent intent9 = new Intent(AllSaloonActivity.this, LoginActivity.class);
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