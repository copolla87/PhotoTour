package com.example.phototour;

import com.example.phototour.CameraActivity;

import com.example.phototour.MainMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends Activity {

	Context mContext;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        mContext = MainMenu.this;
        View.OnClickListener handler = new View.OnClickListener() {
        	public void onClick(View v) {
        		switch (v.getId()) {
        		case R.id.takePicture:
                    // go to camera activity
                    Intent nextActivityCamera = new Intent(mContext,
                            CameraActivity.class);
                    startActivity(nextActivityCamera);
                    break;
        		case R.id.gallery:
        			//go to gallery activity
        			Intent nextActivityGallery = new Intent(mContext,
        					Gallery.class);
        			startActivity(nextActivityGallery);
        			break;
        		case R.id.map:
        			//go to Google Maps activity
        			Intent nextActivityMap = new Intent(mContext,
        					MapActivity.class);
        			startActivity(nextActivityMap);
        			break;
        		}
        	}
        };
        findViewById(R.id.takePicture).setOnClickListener(handler);
        findViewById(R.id.gallery).setOnClickListener(handler);
        findViewById(R.id.map).setOnClickListener(handler);
    }
    
	//Back pressed on main menu returns you to Home screen 
    //without killing the process and without removing you from activity stack
	@Override
	public void onBackPressed(){
	    Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
	}
}
