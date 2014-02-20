package com.example.myparty;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
 
public class MapActivity extends Activity implements LocationListener {

    private LocationManager locationManager;
    private GoogleMap gMap;
    private Marker markerLocation;
    private Marker markerAddress;
    private long minTime = 2000;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle b = getIntent().getExtras();
		String address = b.getString("address");
		double coord[] = getCoordonates(address);
        gMap =  ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        markerLocation = gMap.addMarker(new MarkerOptions().title("You are here").position(new LatLng(0, 0)));
        gMap.addMarker(new MarkerOptions().title(address).position(new LatLng(coord[0], coord[1])));
    }
    
    public double[] getCoordonates(String address){
    	double coordonates[] = new double[2];
    	Geocoder geocoder = new Geocoder(this);  
    	List<Address> addresses;
    	try {
			addresses = geocoder.getFromLocationName(address, 1);
			if(addresses.size() > 0) {
	    	    coordonates[0] = addresses.get(0).getLatitude();
	    	    coordonates[1] = addresses.get(0).getLongitude();
	    	}
		} catch (IOException e) {e.printStackTrace();}
    	return coordonates;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }
    }
 
    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(final Location location) {
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());    
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        markerLocation.setPosition(latLng);
    }
 
    @Override
    public void onProviderDisabled(final String provider) {
        if("gps".equals(provider)) {
        	locationManager.removeUpdates(this);
        }      
    }

    @Override
    public void onProviderEnabled(final String provider) {
        if("gps".equals(provider)) {
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, 10, this);
        }
    }
 
    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }

	//////////////////////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}  
	
}

