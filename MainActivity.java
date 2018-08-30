package com.example.admin.feedtheneedy;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    TabHost tb;

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11;
    Spinner sp1,sp2;
    Button btn1,btn2;
    TextView tv;
    String values[],username,password,type;
    String url,url1;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb= (TabHost) findViewById(R.id.tabHost);
       e1= (EditText) findViewById(R.id.edittext);
        e2= (EditText) findViewById(R.id.edittext2);
        e3= (EditText) findViewById(R.id.edittext3);
        e4= (EditText) findViewById(R.id.edittext4);
        e5= (EditText) findViewById(R.id.edittext5);
        e6= (EditText) findViewById(R.id.edittext6);
        e7= (EditText) findViewById(R.id.edittext7);
        e8= (EditText) findViewById(R.id.edittext8);
        e9= (EditText) findViewById(R.id.edittext9);
        e10= (EditText) findViewById(R.id.edittext10);
        e11= (EditText) findViewById(R.id.textt);

        tv= (TextView) findViewById(R.id.textView);

        btn1= (Button) findViewById(R.id.button);
        btn2= (Button) findViewById(R.id.button2);
        sp1= (Spinner) findViewById(R.id.spinner1);
        sp2= (Spinner) findViewById(R.id.spinner2);



        tb.setup();

        TabHost.TabSpec tabSpec=tb.newTabSpec("");

        tabSpec.setIndicator("LOGIN");
        tabSpec.setContent(R.id.login);

        tb.addTab(tabSpec);

        tabSpec=tb.newTabSpec("");
        tabSpec.setIndicator("SIGNUP");
        tabSpec.setContent(R.id.signup);

        tb.addTab(tabSpec);


        requestQueue= Volley.newRequestQueue(MainActivity.this);

        url="https://nidhisingh0301.000webhostapp.com/signin.php";
        url1="https://nidhisingh0301.000webhostapp.com/login.php";
        values= getResources().getStringArray(R.array.usertype);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        sp1.setAdapter(adapter);


        sp2.setAdapter(adapter);

       btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(MainActivity.this, "Login successfully..", Toast.LENGTH_SHORT).show();

                sendrequest1();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendrequest();
                String name="";
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("name_k",name);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Sign in", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void sendrequest1()
    {
        StringRequest req1=new StringRequest(Request.Method.POST,url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String code=jsonObject.getString("code");


                    if(code.equals("No"))
                    {

                        Toast.makeText(MainActivity.this, "Invalid User name or password " ,Toast.LENGTH_SHORT).show();
                    }
                     else
                    {
                        String name=jsonObject.getString("name");
                        Toast.makeText(MainActivity.this, "Welcome"+name, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                        intent.putExtra("name_k",e1.getText().toString());
                        startActivity(intent);


                    }
                }
                catch (Exception e)
                {

                }
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> list=new HashMap<>();
                username=e1.getText().toString();
                password=e2.getText().toString();
                type=sp1.getSelectedItem().toString();
                list.put("username",username);
                list.put("password",password);
                list.put("type",type);
                return list;
            }
        } ;

        requestQueue.add(req1);
    }

    public void sendrequest()
    {
        StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {


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
                list.put("name",e3.getText().toString());
                list.put("phone",e4.getText().toString());
                list.put("email",e5.getText().toString());
                list.put("address",e6.getText().toString());
                list.put("city",e7.getText().toString());
                list.put("state",e8.getText().toString());
                list.put("pincode",e9.getText().toString());
                list.put("username",e10.getText().toString());
                list.put("password",e11.getText().toString());
                list.put("type",sp2.getSelectedItem().toString());
                return list;
            }
        };
        requestQueue.add(req);
    }
}
