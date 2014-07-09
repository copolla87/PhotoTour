package com.example.phototour;

import com.example.phototour.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	final boolean setMyLocationEnabled = true;
	GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		try{
			//initialize GPS
			GPSTracker gps = new GPSTracker(this);
			gps = new GPSTracker(MapActivity.this);
			//check if GPS is available
			if(gps.canGetLocation()){
				//get Latitude and Longitude from gps
				double lat = gps.getLatitude();
				double lon = gps.getLongitude();
				//get a map fragment
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
				googleMap.setMyLocationEnabled(true);
				//create LatLng variable pass lat and lon from above and move Google map camera to this location
				LatLng location = new LatLng(lat, lon);
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
				//set zoom level to 12 and map centers there 
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
			} else {
				gps.showSettingsAlert();
			}
		} finally {
			//TODO get last known position from service
		}
	}
	
	//back pressed returns you to main menu
	@Override
	public void onBackPressed(){
		Intent backIntent = new Intent(this, MainMenu.class);
		startActivity(backIntent);
	}
}
