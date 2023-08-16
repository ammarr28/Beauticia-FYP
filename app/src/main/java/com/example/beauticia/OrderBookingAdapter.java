package com.example.beauticia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBookingAdapter extends ArrayAdapter<OrderBooking> {
    Context context;
    List<OrderBooking> arrayapply;
    String url="https://bigbox.com.pk/beauticia/complete_booking.php";
    StringRequest request;
    RequestQueue res;


    public OrderBookingAdapter(@NonNull Context context, List<OrderBooking> arrayapply ) {
        super(context,R.layout.custom_mybboking,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mybboking,null,true);
        TextView sname=view.findViewById(R.id.sname);
        TextView uname=view.findViewById(R.id.uname);
        TextView uphone=view.findViewById(R.id.uphone);
        TextView bdate=view.findViewById(R.id.bdate);
        TextView sprice=view.findViewById(R.id.sprice);
        TextView btn_complete=view.findViewById(R.id.btn_complete);




        sname.setText(arrayapply.get(position).getService_name());
        sprice.setText("Price : "+arrayapply.get(position).getService_fee());
        uname.setText("User Name : "+arrayapply.get(position).getUser_name());
        uphone.setText("User Phone : "+arrayapply.get(position).getUser_phone());
        bdate.setText("Booking : "+arrayapply.get(position).getBooking_date());

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Deleted")) {

                            arrayapply.remove(position);
                            notifyDataSetChanged();
                        }

                        if (response.contains("NotDeleted")) {
                            Toast.makeText(getContext(), "Complete Un Successfully", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("sid", arrayapply.get(position).getBooking_id());

                        return map;
                    }
                };


                res = Volley.newRequestQueue(getContext());
                res.add(request);


            }
        });




        return view;
    }
}

