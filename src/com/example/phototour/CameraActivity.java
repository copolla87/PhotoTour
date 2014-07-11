package com.example.phototour;
import com.example.phototour.GPSTracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final String IMAGE_DIRECTORY_NAME = "PHOTO MAP"; 
	private Uri fileUri;
	File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PHOTO MAP");

	private ImageView imgPreview;
	
	GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		try {
			//opening GPSTracker
			GPSTracker gps = new GPSTracker(this);
			gps = new GPSTracker(CameraActivity.this);
			if(gps.canGetLocation()){
				double latitude = gps.getLatitude();
				double longitude = gps.getLongitude();
			} else {
				gps.showSettingsAlert();
			} 	
		} finally{
			//TODO
		}
		
	     imgPreview = (ImageView) findViewById(R.id.imgPreview);
	    
	     captureImage();
	    	
	     
	}
	
	//icture captured or user cancelled
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
	    	galleryAddPic();
	    	previewCapturedImage();
	    	newPhotograph(imgPreview);
	    } else if (resultCode == RESULT_CANCELED){
	    	Toast.makeText(getApplicationContext(), "user cancelled", Toast.LENGTH_SHORT).show();
	    } else {
	    	Toast.makeText(getApplicationContext(), "failed to capture image", Toast.LENGTH_SHORT).show();
	    }
	}
	
	/*
	@Override
	public void onResume(){
		super.onResume();
		captureImage();
	}
	*/
	
	//Null pointer exception if not added 
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
		
		
	}
	
	//Null pointer exception if not added 
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		fileUri = savedInstanceState.getParcelable("file_uri");
		
	}
	
	
	//Back pressed returns you to main menu
	@Override
	public void onBackPressed(){
		Intent backIntent = new Intent(this, MainMenu.class);
		startActivity(backIntent);
		finish();
	}
	
	
	/*----------------------------------------------------------------------------------------------------------------
	 * 				HELPER METHODS
	 ----------------------------------------------------------------------------------------------------------------*/
	
	private void captureImage(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		finish();
	}
	
	public Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}
	
	private static File getOutputMediaFile(int type){
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);
		
		if(!mediaStorageDir.exists()){
			
			if(!mediaStorageDir.mkdirs()){
				Log.d(IMAGE_DIRECTORY_NAME,"Failed create" + IMAGE_DIRECTORY_NAME + "directory");
				return null;
			}
		}
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		File mediaFile;
		if(type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}
	
	private void previewCapturedImage(){
		try{
			imgPreview.setVisibility(View.VISIBLE);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),options);
			imgPreview.setImageBitmap(bitmap);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	//make photo available to android default gallery
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(file.getPath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	//add photograph to database(with slightly different name though - need fix)
	private void newPhotograph(View view){
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		DatabaseHandler dbHandler = new DatabaseHandler(this);
		Photographs photograph = new Photographs(timeStamp.toString(), String.valueOf(gps.getLatitude()), String.valueOf(gps.getLongitude()), timeStamp.toString());
		dbHandler.addPhotograph(photograph);
	}
}
