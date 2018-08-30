package com.example.admin.feedtheneedy;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        try {
            Donation donation = new Donation();


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Bundle b = getIntent().getExtras();

            String n = b.getString("name_k");

            Bundle bb = new Bundle();
            bb.putString("name_key", n);

            donation.setArguments(bb);

            ft.add(R.id.second, donation);
            ft.addToBackStack("");
            ft.commit();
        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
