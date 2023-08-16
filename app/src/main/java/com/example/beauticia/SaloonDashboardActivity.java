package com.example.beauticia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SaloonDashboardActivity extends AppCompatActivity {
    TextView mname,memail,mphone;
    LinearLayout logout,services,mybooking,complete_booking,my_wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_dashboard);
        mname=findViewById(R.id.mname);
        memail=findViewById(R.id.memail);
        mphone=findViewById(R.id.mphone);
        logout=findViewById(R.id.logout);
        services=findViewById(R.id.services);
        mybooking=findViewById(R.id.mybooking);
        complete_booking=findViewById(R.id.complete_booking);
        my_wallet=findViewById(R.id.my_wallet);

        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();

        mname.setText("Name : "+shared.getString("user_name",""));
        memail.setText("Email : "+shared.getString("user_email",""));
        mphone.setText("Phone : "+shared.getString("user_phone",""));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putString("user_id","");
                edit.putString("user_name","");
                edit.putString("user_email","");
                edit.putString("user_phone","");
                edit.putString("user_type","");

                edit.commit();
                Intent intent=new Intent(SaloonDashboardActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SaloonDashboardActivity.this,AddServiceActivity.class);
                startActivity(intent);
            }
        });
        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SaloonDashboardActivity.this,MyBookingActivity.class);
                startActivity(intent);
            }
        });
        complete_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SaloonDashboardActivity.this,MyCompleteBookingActivity.class);
                startActivity(intent);
            }
        });

        my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SaloonDashboardActivity.this,MyWalletActivity.class);
                startActivity(intent);
            }
        });

    }
}