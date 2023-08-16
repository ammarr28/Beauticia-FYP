package com.example.beauticia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AllServiceAdapter extends ArrayAdapter<AllService> {
    Context context;
    List<AllService> arrayapply;


    public AllServiceAdapter(@NonNull Context context, List<AllService> arrayapply ) {
        super(context,R.layout.custom_allservice,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_allservice,null,true);
        TextView sname=view.findViewById(R.id.sname);
        TextView sprice=view.findViewById(R.id.sprice);
        TextView ssname=view.findViewById(R.id.ssname);
        TextView btn_book=view.findViewById(R.id.btn_book);




        sname.setText(arrayapply.get(position).getService_name());
        sprice.setText("Price : "+arrayapply.get(position).getService_fee());
        ssname.setText("Saloon : "+arrayapply.get(position).getSaloon_name());

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),BookNowActivity.class);
                intent.putExtra("serviceid",arrayapply.get(position).getService_id());
                intent.putExtra("saloonid",arrayapply.get(position).getSaloon_id());
                intent.putExtra("servicefee",arrayapply.get(position).getService_fee());
                intent.putExtra("servicename",arrayapply.get(position).getService_name());
                context.startActivity(intent);
            }
        });




        return view;
    }
}
