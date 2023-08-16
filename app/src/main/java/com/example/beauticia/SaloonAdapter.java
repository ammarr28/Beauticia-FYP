package com.example.beauticia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

public class SaloonAdapter extends ArrayAdapter<AllSaloon> {
    Context context;
    List<AllSaloon> arrayapply;
    String url="https://bigbox.com.pk/beauticia/allservice.php";
    StringRequest request;
    RequestQueue res;

    public SaloonAdapter(@NonNull Context context, List<AllSaloon> arrayapply ) {
        super(context,R.layout.custom_allsaloon,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_allsaloon,null,true);
        TextView sname=view.findViewById(R.id.salon_name);
        TextView sprice=view.findViewById(R.id.salon_address);
        TextView btn_view=view.findViewById(R.id.btn_view);




        sname.setText(arrayapply.get(position).getUser_name());
        sprice.setText("Address : "+arrayapply.get(position).getUser_address());

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SaloonServiceActivity.class);
                intent.putExtra("sid",arrayapply.get(position).getUser_id());
                context.startActivity(intent);

            }
        });




        return view;
    }
}

