package com.example.admin.feedtheneedy;


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


public class Charity extends Fragment {

    String TAG_RESULT = "result";
    ListView listView;
    RequestQueue rq;
    String name,phone,address,city,state,pincode;

    ArrayList<String> arrayList;

    ArrayAdapter<String> adapter;

    String url="https://nidhisingh0301.000webhostapp.com/charity.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_charity, container, false);
        listView= (ListView) v.findViewById(R.id.listview1);

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
                        name=obj.getString("name");
                        phone=obj.getString("phone");
                        address=obj.getString("address");
                        city=obj.getString("city");
                        state=obj.getString("state");
                        pincode=obj.getString("pincode");



                        arrayList.add("Name-:- "+name+"\nPhone No.:- "+phone+"\nAddress:- " +address+","+city+","+state+","+pincode);
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
