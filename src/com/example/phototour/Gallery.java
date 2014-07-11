package com.example.phototour;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class Gallery extends Activity {
	public static final int RESULT_GALLERY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		//File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PHOTO MAP");
		//Uri fileUri = Uri.fromFile(file);
		Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent , RESULT_GALLERY );
		finish();
	}
	
	@Override
	public void onBackPressed(){
		Intent backIntent = new Intent(this, MainMenu.class);
		startActivity(backIntent);
		finish();
	}
}
