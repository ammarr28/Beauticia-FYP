package com.example.beauticia;

import android.content.Context;
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

public class OrderBookingAdapter2 extends ArrayAdapter<OrderBooking> {
    Context context;
    List<OrderBooking> arrayapply;



    public OrderBookingAdapter2(@NonNull Context context, List<OrderBooking> arrayapply ) {
        super(context,R.layout.custom_mybboking2,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mybboking2,null,true);
        TextView sname=view.findViewById(R.id.sname);
        TextView uname=view.findViewById(R.id.uname);
        TextView uphone=view.findViewById(R.id.uphone);
        TextView bdate=view.findViewById(R.id.bdate);
        TextView sprice=view.findViewById(R.id.sprice);




        sname.setText(arrayapply.get(position).getService_name());
        sprice.setText("Price : "+arrayapply.get(position).getService_fee());
        uname.setText("User Name : "+arrayapply.get(position).getUser_name());
        uphone.setText("User Phone : "+arrayapply.get(position).getUser_phone());
        bdate.setText("Booking : "+arrayapply.get(position).getBooking_date());




        return view;
    }
}

