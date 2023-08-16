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

public class ServiceAdapter extends ArrayAdapter<Service> {
    Context context;
    List<Service> arrayapply;
    String url="https://bigbox.com.pk/beauticia/delete_myservice.php";
    StringRequest request;
    RequestQueue res;

    public ServiceAdapter(@NonNull Context context, List<Service> arrayapply ) {
        super(context,R.layout.custom_service,arrayapply);
        this.context=context;
        this.arrayapply=arrayapply;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_service,null,true);
        TextView uservice=view.findViewById(R.id.uservice);
        TextView ufee=view.findViewById(R.id.ufee);
        Button sremove=view.findViewById(R.id.sremove);




        uservice.setText("Service Name : "+arrayapply.get(position).getService_name());
        ufee.setText("Service Fee : "+arrayapply.get(position).getService_fee());

        sremove.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(getContext(), "Delete Un Successfully", Toast.LENGTH_SHORT).show();

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
                        map.put("sid", arrayapply.get(position).getService_id());

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
