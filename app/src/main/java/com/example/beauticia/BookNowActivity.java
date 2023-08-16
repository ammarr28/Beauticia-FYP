package com.example.beauticia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookNowActivity extends AppCompatActivity {

    TextInputEditText uname,uphone,sfee,cardno,cvccode,cardexpire,sbookingdate;
    Button btn_book;
    String url="https://bigbox.com.pk/beauticia/book_service.php";
    StringRequest request;
    RequestQueue res;
    ProgressDialog loadingbar;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        uname=findViewById(R.id.uname);
        uphone=findViewById(R.id.uphone);
        sfee=findViewById(R.id.sfee);
        cardno=findViewById(R.id.cardno);
        cvccode=findViewById(R.id.cvccode);
        cardexpire=findViewById(R.id.cardexpire);
        sbookingdate=findViewById(R.id.sbookingdate);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
        sbookingdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BookNowActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_book=findViewById(R.id.btn_book);
        loadingbar=new ProgressDialog(this);
        final SharedPreferences shared=getSharedPreferences("credientials2",MODE_PRIVATE);
        final SharedPreferences.Editor edit=shared.edit();
        uname.setText(shared.getString("user_name",""));
        uphone.setText(shared.getString("user_phone",""));
        Intent intent = getIntent();
        String serviceid = intent.getStringExtra("serviceid");
        String saloonid = intent.getStringExtra("saloonid");
        String servicefee = intent.getStringExtra("servicefee");
        String servicename = intent.getStringExtra("servicename");
        sfee.setText(servicefee);


        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingbar.setTitle("Booking Service");
                loadingbar.setMessage("Please Wait..While Adding The Record");
                loadingbar.show();


                request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Add Successfully")) {

                            Toast.makeText(BookNowActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();



                            Intent intent = new Intent(BookNowActivity.this, MainActivity.class);

                            startActivity(intent);

                        }

                        if (response.contains("Add Un Successfully")) {
                            Toast.makeText(BookNowActivity.this, "Add Un Successfully", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookNowActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }) {


                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("serviceid", serviceid);
                        map.put("saloonid", saloonid);
                        map.put("servicefee", servicefee);
                        map.put("servicename", servicename);
                        map.put("userid", shared.getString("user_id",""));
                        map.put("cardno", cardno.getText().toString());
                        map.put("cvccode", cvccode.getText().toString());
                        map.put("cardexpire", cardexpire.getText().toString());
                        map.put("sbookingdate", sbookingdate.getText().toString());


                        return map;
                    }
                };


                res = Volley.newRequestQueue(BookNowActivity.this);
                res.add(request);

            }
        });


    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat(myFormat, Locale.US);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sbookingdate.setText(sdf.format(myCalendar.getTime()));
        }

    }
}