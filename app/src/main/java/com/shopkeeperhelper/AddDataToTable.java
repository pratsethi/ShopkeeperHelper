package com.shopkeeperhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by PSethi on 28-Dec-16.
 */

public class AddDataToTable extends FragmentActivity {

    private boolean comingBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //init();
    }

    private void init()
    {
        comingBack = true;

        PersonDetailFragmant fragment = new PersonDetailFragmant();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentAddRow,fragment,"first");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(comingBack)
        {
            Log.d("shopkeeper","AddDataToTable-onResume");
            onBackPressed();
        }
        else {
            init();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
