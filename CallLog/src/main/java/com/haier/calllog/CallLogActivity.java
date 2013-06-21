package com.haier.calllog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CallLogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calllog_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call_log, menu);
        return true;
    }
    
}
