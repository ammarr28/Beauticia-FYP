package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewServiceListActivity extends AppCompatActivity {

    ListView listView;
    StringRequest request;
    RequestQueue res;
    String url="https://bigbox.com.pk/beauticia/fetch_myservice.php";
    Service service;
    ServiceAdapter serviceAdapter;
    public  static ArrayList<Service> arrayappliedlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_list);

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();



        listView=findViewById(R.id.servicelist);
        serviceAdapter=new ServiceAdapter(this,arrayappliedlist);
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


                        String service_id=obj.getString("service_id");
                        String service_name=obj.getString("service_name");
                        String service_fee=obj.getString("service_fee");
                        String saloon_id=obj.getString("saloon_id");




                        service=new Service(service_id,service_name,service_fee,saloon_id);
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
                Toast.makeText(ViewServiceListActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("sid", shared.getString("user_id",""));

                return map;
            }
        };

        res= Volley.newRequestQueue(ViewServiceListActivity.this);
        res.add(request);
    }
}