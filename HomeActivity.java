package com.razin.login;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity 
{
	Button btnSignIn,btnSignUp;
	LoginDataBaseAdapter loginDataBaseAdapter;
	
	  private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

	    protected LocationManager locationManager;
	    protected Button retrieveLocationButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
	     retrieveLocationButton=(Button)findViewById(R.id.button1);
	     locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	     locationManager.requestLocationUpdates(
	      LocationManager.GPS_PROVIDER,
	     MINIMUM_TIME_BETWEEN_UPDATES,
	    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
	    new MyLocationListener()
	   	      );
	   	     retrieveLocationButton.setOnClickListener(new OnClickListener() {
	   	    	   @Override
	   	    	   public void onClick(View v) {
	   	    	  showCurrentLocation();
	   	    	  }
	   	     });  
	   	
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
		    dialog.setTitle("Login");
	
		    // get the Refferences of views
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					// fetch the Password form database for respective user name
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					
					// check if the Stored password matches with  Password entered by user
					if(password.equals(storedPassword))
					{
						Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			dialog.show();
	   }
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}

	     
			protected void showCurrentLocation() {
				// TODO Auto-generated method stub
				Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		        if (location != null) {
		            String message = String.format(
		                    "Current Location \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude() );
		 Toast.makeText(HomeActivity.this, message,
		 Toast.LENGTH_LONG).show(); }
			}
			
// set on ClickListner for GPS
private class MyLocationListener implements LocationListener {

    public void onLocationChanged(Location location) {
    	String message = String.format(  "New Location \n Longitude: %1$s \n Latitude: %2$s",location.getLongitude(), location.getLatitude() );
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_LONG).show();
}

    public void onStatusChanged(String s, int i, Bundle b) {

        Toast.makeText(HomeActivity.this, "Provider status changed", Toast.LENGTH_LONG).show();
    }



    public void onProviderDisabled(String s) {

        Toast.makeText(HomeActivity.this,"Provider disabled by the user. GPS turned off",Toast.LENGTH_LONG).show();

    }



    public void onProviderEnabled(String s) {

        Toast.makeText(HomeActivity.this,"Provider enabled by the user. GPS turned on",Toast.LENGTH_LONG).show();

  


}}}