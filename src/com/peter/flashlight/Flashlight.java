package com.peter.flashlight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

/*
 * TODO:
 * Fix leaving and re-opening the app.
 * Find a better way of handling when the camera is null when trying to turn on the light
 */

public class Flashlight extends Activity {

	public static Camera cam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashlight);
		cam = Camera.open();
		addListenerOnButton();
	}
	
	// Toggle Button Event Listener
	public void addListenerOnButton() {
		final ToggleButton tb = (ToggleButton)findViewById(R.id.toggleButton1) ;
	    tb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tb.isChecked()) {
					flashLightOn(v);
				} else {
					flashLightOff(v);
				}
			}
	    	
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flashlight, menu);
		return true;
	}
	
	// Method that, if the device has flash capabilities, turns on the flash to be used as a light.
	// If there is no flash, then it displays that to the user.
	public void flashLightOn(View view) {
		if (cam == null) {
			cam = Camera.open();
		}
		try {
			// If the phone has flash capabilities
			if (hasCameraFlash()) {
				Parameters p = cam.getParameters();
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				cam.setParameters(p);
				cam.startPreview();
			} else {
				// Phone has no flash, bring up an error message
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "Exception flashLightOn()", 
					       Toast.LENGTH_SHORT).show();
		}
	}
	
	// Method that, if the device has flash capabilities, it turns them off.
	// If there is no flash, then it displays that to the user.
	public void flashLightOff(View view) {
		try {
			if (hasCameraFlash()) {
				cam.stopPreview();
				cam.release();
				cam = null;
			} else {
				// Phone has no flash, bring up an error message
			}
		} catch (Exception e) {
			e.printStackTrace();
	        Toast.makeText(getBaseContext(), "Exception flashLightOff",
	                	   Toast.LENGTH_SHORT).show();
		}
	}
	
	// Checks if the device has a camera flash
	public static boolean hasCameraFlash() {
	    Camera.Parameters p = cam.getParameters();
	    return p.getFlashMode() == null ? false : true;
	}	
}
