package com.example.beauticia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrderhistoryAdapter extends ArrayAdapter<OrderHistory> {
    Context context;
    List<OrderHistory> arrayapply;


    public OrderhistoryAdapter(@NonNull Context context, List<OrderHistory> arrayapply ) {
        super(context,R.layout.custom_orderhistory,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_orderhistory,null,true);
        TextView sname=view.findViewById(R.id.sname);
        TextView sprice=view.findViewById(R.id.sprice);
        TextView bookdate=view.findViewById(R.id.bookdate);




        sname.setText(arrayapply.get(position).getService_name());
        sprice.setText("Price : "+arrayapply.get(position).getService_fee());
        bookdate.setText("Book Date : "+arrayapply.get(position).getBooking_date());





        return view;
    }
}

