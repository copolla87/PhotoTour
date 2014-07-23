package com.example.phototour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.phototour.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	final boolean setMyLocationEnabled = true;
	GPSTracker gps;
	//contains all markers added to map
	private HashMap<Marker, MyMarker> mMarkersHashMap;
	//it carries all MyMarker objects
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    public PhotographsDataSource dataSource;
    public List<Photographs> photos = new ArrayList<Photographs>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//initilization 
		mMarkersHashMap = new HashMap<Marker, MyMarker>();
		//myMarkerObjects(mMyMarkersArray);
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
				
				// Initialize the HashMap for Markers and MyMarker object
		        mMarkersHashMap = new HashMap<Marker, MyMarker>();

		        //new datasource within this activuty context
		    	dataSource = new PhotographsDataSource(this);
		    	//open connection with datasource
		    	dataSource.open();
		    	photos = dataSource.getPhotographs();
		    	for(Photographs photo : photos){
		    		mMyMarkersArray.add(new MyMarker(photo._name, Double.parseDouble(photo.getLatitude()),  Double.parseDouble(photo.getLongitude())));
		    		
		    	}
				plotMarkers(mMyMarkersArray);
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
		finish();
	}
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * 				HELPER METHODS
	 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/ 
	
	private void myMarkerObjects(ArrayList<MyMarker> myMarkersArray){
		PhotographsDataSource dbHandler = new PhotographsDataSource(this);
		dbHandler.open();
		List<Photographs> list = dbHandler.getPhotographs();
		for(Photographs photograph : list){
			myMarkersArray.add(new MyMarker(photograph.getName(), Double.valueOf(photograph.getLongitude()), Double.valueOf(photograph.getLatitude())));
		}
		
	}
	
	
	private void plotMarkers(ArrayList<MyMarker> markers){
		if(markers.size() >= 0){
			for(MyMarker myMarker : markers){
				//create marker with options
				 Marker currentMarker = googleMap.addMarker(new MarkerOptions()
	             .position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()))
	             .title("Hello world")
	             .icon(null));
	             mMarkersHashMap.put(currentMarker, myMarker);

			}
		}
	}
	
	
	
	
	
	
	/*SCRAP SCRAP SCRAP SCRAP SCRAP
	//create markers
	private ArrayList<Marker> markerFactory(){
		DatabaseHandler dbHandler = new DatabaseHandler(null);
		ArrayList<Photographs> list = dbHandler.getPhotographs();
		ArrayList<Marker> markerList = null;
		for(Photographs photograph : list){
			LatLng location = new LatLng(Double.valueOf(photograph.getLongitude()), Double.valueOf(photograph.getLatitude()));
			 Marker marker = googleMap.addMarker(new MarkerOptions()
		     .position(location));
			 markerList.add(marker);
		}
		return markerList;
	}
	
	
	private void ploatMarkers(ArrayList<MyMarker> markers)
	{
		DatabaseHandler dbHandler = new DatabaseHandler(null);
		ArrayList<Photographs> list = dbHandler.getPhotographs();
		
	    if(markers.size() > 0)
	    {
	        for (MyMarker myMarker : markers)
	        {

	            // Create user marker with custom icon and other options
	            MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
	            markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

	            Marker currentMarker = mMap.addMarker(markerOption);
	            mMarkersHashMap.put(currentMarker, myMarker);

	            mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
	        }
	    }
	}
	
	private void plotMarkers(ArrayList<MyMarker> markers){
		
	}
	SCRAP SCRAP SCRAP SCRAP SCRAP*/
}
