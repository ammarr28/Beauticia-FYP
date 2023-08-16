package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class MyBookingActivity extends AppCompatActivity {

    ListView listView;
    StringRequest request;
    RequestQueue res;
    String url="https://bigbox.com.pk/beauticia/mybooking.php";
    OrderBooking orderBooking;
    OrderBookingAdapter orderBookingAdapter;
    public  static ArrayList<OrderBooking> arrayappliedlist=new ArrayList<>();
    ImageView backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();
        backs=findViewById(R.id.backs);

        listView=findViewById(R.id.mybookinglist);
        orderBookingAdapter=new OrderBookingAdapter(this,arrayappliedlist);
        listView.setAdapter(orderBookingAdapter);

        request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);

                        String booking_id=obj.getString("booking_id");
                        String booking_date=obj.getString("booking_date");
                        String service_name=obj.getString("service_name");
                        String service_fee=obj.getString("service_fee");
                        String user_name=obj.getString("user_name");
                        String user_phone=obj.getString("user_phone");
                        orderBooking=new OrderBooking(booking_id,booking_date,service_name,service_fee,user_name,user_phone);
                        arrayappliedlist.add(orderBooking);
                        orderBookingAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyBookingActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("sid", shared.getString("user_id",""));


                return map;
            }
        };

        res= Volley.newRequestQueue(MyBookingActivity.this);
        res.add(request);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyBookingActivity.this,SaloonDashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}