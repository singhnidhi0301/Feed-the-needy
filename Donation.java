package com.example.admin.feedtheneedy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.*;


public class Donation extends Fragment
{

    EditText e1,e2;
    TextView t1,t2;
    Button btn;
    int hour;
    int Minute,day,month,Year;
    String name;

    FragmentManager fm;
    FragmentTransaction ft;
    String url;
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.fragment_donation, container, false);

        e1= (EditText) v.findViewById(R.id.editText2);
        e2= (EditText) v.findViewById(R.id.editText3);
        t1= (TextView) v.findViewById(R.id.textView2);
        t2= (TextView) v.findViewById(R.id.textView3);
        btn= (Button) v.findViewById(R.id.button3);




        url="https://nidhisingh0301.000webhostapp.com/fooddetails.php";

        requestQueue= Volley.newRequestQueue(this.getActivity());




        Calendar ref = Calendar.getInstance();

        day = ref.get(Calendar.DAY_OF_MONTH);

        month = ref.get(Calendar.MONTH);
        Year = ref.get(Calendar.YEAR);
        hour=ref.get(Calendar.HOUR_OF_DAY);
        Minute=ref.get(Calendar.MINUTE);

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(),time,hour,Minute,true).show();
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(getActivity(),date,Year,month,day).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {



                Bundle bundle=getArguments();
                name=bundle.getString("name_key");
                sendrequest();

                Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();


            }
        });

        fm = getFragmentManager();
        ft = fm.beginTransaction();


        return v;


    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.option,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.item1:
                MyDonation myDonation=new MyDonation();
                ft.replace(R.id.second,myDonation);
                ft.addToBackStack("");
                ft.commit();

                break;
            case R.id.item2:
                Volunteer volunteer=new Volunteer();
                ft.replace(R.id.second,volunteer);
                ft.addToBackStack("");
                ft.commit();
                break;
            case R.id.item3:
                Charity charity=new Charity();
                ft.replace(R.id.second,charity);
                ft.addToBackStack("");
                ft.commit();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            t1.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            day=dayOfMonth;
            month=month+1;
            Year=year;
        }
    };

    TimePickerDialog.OnTimeSetListener time=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            t2.setText(hourOfDay+":"+minute);

            Minute=minute;
            hour=hourOfDay;
        }
    };


    public void sendrequest()
    {
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                e1.setText("");
                e2.setText("");
                t1.setText("");
                t2.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> list=new HashMap<>();

                list.put("description",e1.getText().toString());
                list.put("app_value",e2.getText().toString());
                list.put("date",t1.getText().toString());
                list.put("time",t2.getText().toString());
                list.put("username",name);

                return list;
            }
        };
        requestQueue.add(req);
    }


}




