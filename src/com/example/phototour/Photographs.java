package com.example.phototour;



public class Photographs {
	
	int _id;
	String _name;
	String _Latitude;
	String _Longitude;
	String _timeStamp;
	
	//empty constructor
	public Photographs(){
		
	}
	
	//constructor
	public Photographs(int id, String name, String latitude, String longitude, String timeStamp){
		this._id = id;
		this._name = name;
		this._Latitude = latitude;
		this._Longitude = longitude;
		this._timeStamp = timeStamp;
	}
	
	//constructor
	public Photographs(String name, String latitude, String longitude, String timeStamp){
		this._name = name;
		this._Latitude = latitude;
		this._Longitude = longitude;
		this._timeStamp = timeStamp;		
	}
	
	//ACCESSORS
	public int getID(){
		return this._id;
	}
	
	public void setID(int id){
		this._id = id;
	}
	
	public String getName(){
		return this._name;
	}
	
	public void setName(String name){
		this._name = name;
	}
	
	public String getLatitude(){
		return this._Latitude;
	}
	
	public void setLatitude(String latitude){
		this._Latitude = latitude;
	}
	
	public String getLongitude(){
		return this._Longitude;
	}
	
	public void setLongitude(String longitude){
		this._Longitude = longitude;
	}
	
	public String getTimeStamp(){
		return this._timeStamp;
	}
	
	public void setTimeStamp(String timeStamp){
		this._timeStamp = timeStamp;
	}

}
