package com.shopkeeperhelper;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Adapters.RecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;
    private ArrayList<String> list;
    private DataBaseSQL dataBaseSQL;
    private ProgressBar progress;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First get data from database, create bean and render data in listview
        //If no data then show message
        dataBaseSQL = new DataBaseSQL(this,"balDetails",null,2);
        /*dataBaseSQL.insertData("Prashant","7875600158","c/5-13",123);
        dataBaseSQL.insertData("Mohit","1234567890","c/4-11",154);
        dataBaseSQL.insertData("Akash","0253278163","c/3-45",345);*/

        progress = (ProgressBar) findViewById(R.id.progress);
        int count = dataBaseSQL.getTableCount();
        Log.d("Table size","size = "+count);

        list = dataBaseSQL.getRows();

        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.addButton);
        floatingButton.setOnClickListener(new FloatingButtonClicked());

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.removeAllViews();
        TextView noData = (TextView) findViewById(R.id.noData);
        if(count > 0)
        {
            adapter = new RecyclerAdapter(list);
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
        recyclerView.addOnItemTouchListener(new RecycleItemClick());

        EditText edtSearch = (EditText) findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                list.clear();
                list.addAll(dataBaseSQL.getSearchResult(editable.toString()));
                adapter.notifyDataSetChanged();
            }
        });
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                //IntentResult result = IntentIntegrtor
                String content = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                //String price = data.getDataString();
                Log.d("content: ",content);
                Log.d("format: ",format);
            }
        }
    }*/

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("MainActivity","Came back to postResume");
        list.clear();
        list.addAll(dataBaseSQL.getRows());
        if(adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
        progress.setVisibility(View.GONE);
    }

    private class RecycleItemClick implements RecyclerView.OnItemTouchListener
    {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("ShopKeeper","onInterceptTouch");
            Log.d("ShopKeeper","clicked");
            View child = rv.findChildViewUnder(e.getX(),e.getY());

            if(child!=null && e.getAction() == MotionEvent.ACTION_UP)
            {
                Log.d("ShopKeeper","postion= "+rv.getChildAdapterPosition(child));
                ItemDetailDataBase itemDataBase = new ItemDetailDataBase(MainActivity.this,"itemBalance",null,3);

                RelativeLayout mainParent = (RelativeLayout) child;

                //LinearLayout parent = (LinearLayout) mainParent.getChildAt(0);
                TextView childView = (TextView) mainParent.getChildAt(1);
                ArrayList<String> list = itemDataBase.getAllItems(childView.getText().toString());

                Intent intent = new Intent(MainActivity.this,AllItemsList.class);
                intent.putExtra("list",list);
                startActivity(intent);

                /*String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE","PRODUCT_MODE");
                startActivityForResult(intent,0);*/
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }



    private class FloatingButtonClicked implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {

            progress.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this,AddDataToTable.class);
            intent.putExtra("progress",progress.getId());
            startActivity(intent);
        }
    }
}
