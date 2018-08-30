package com.example.admin.feedtheneedy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyDonation extends Fragment
{
    String TAG_RESULT = "result";
    ListView listView;
    RequestQueue rq;
    String description,app_value,username;

    ArrayList<String> arrayList;

    ArrayAdapter<String> adapter;

    String url="https://nidhisingh0301.000webhostapp.com/mydonation.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_donation, container, false);

        listView= (ListView) v.findViewById(R.id.listview);

        arrayList=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);

        rq= Volley.newRequestQueue(this.getActivity());

        sendjsonrequest();



        return v;
    }


    public void sendjsonrequest(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray jsonArray=response.getJSONArray(TAG_RESULT);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj=jsonArray.getJSONObject(i);
                        description=obj.getString("description");
                        app_value=obj.getString("app_value");
                        username=obj.getString("username");

                        arrayList.add("Food Description:- "+description+"\nApproximate value:- "
                                +app_value+"\nusername:- "+username);
                        adapter.notifyDataSetChanged();

                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jsonObjectRequest);
    }


}