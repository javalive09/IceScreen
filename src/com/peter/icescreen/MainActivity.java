package com.peter.icescreen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.peter.lockscreen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private boolean mUpdateTime = true;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        final TextView mTimeView = (TextView) findViewById(R.id.time);
        final TextView mDateView = (TextView) findViewById(R.id.date);
        
        new Thread(new Runnable() {

        	SimpleDateFormat hms = new SimpleDateFormat("HH:mm", Locale.getDefault());
        	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			
			@Override
			public void run() {
				while(mUpdateTime) {
					final String time = hms.format(new Date());
					final String date = ymd.format(new Date());
					
	                runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mTimeView.setText(time);
							mDateView.setText(date);
						}
	                });
	                
	                try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
        	
        }).start();
        startService(new Intent(MainActivity.this, LockService.class));
    }

    public void doFinish() {
    	runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mUpdateTime = false;
		    	finish();
			}
		});
    }
    
}
