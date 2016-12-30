package com.shopkeeperhelper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First get data from database, create bean and render data in listview
        //If no data then show message
        DataBaseSQL dataBaseSQL = new DataBaseSQL(this,"balDetails",null,1);
        dataBaseSQL.insertData("Prashant","7875600158","c/5-13",123);
        dataBaseSQL.insertData("Mohit","1234567890","c/4-11",154);
        dataBaseSQL.insertData("Akash","0253278163","c/3-45",345);

        int count = dataBaseSQL.getTableCount();
        Log.d("Table size","size = "+count);

        ArrayList<String> list = dataBaseSQL.getRows();

        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.addButton);
        floatingButton.setOnClickListener(new FloatingButtonClicked());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        TextView noData = (TextView) findViewById(R.id.noData);
        if(count > 0)
        {
            RecyclerAdapter adapter = new RecyclerAdapter(list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private class FloatingButtonClicked implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this,AddDataToTable.class);
            startActivity(intent);
        }
    }
}
