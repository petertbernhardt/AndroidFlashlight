package com.peter.flashlight;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Flashlight extends Activity {

	public static Camera cam = null; // if it's not static, onDestroy() destroys it
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flashlight);
		addListenerOnButton();
	}
	
	// Toggle Button Event Listener
	public void addListenerOnButton() {
		// TODO Auto-generated method stub
		final ToggleButton tb = (ToggleButton)findViewById(R.id.toggleButton1) ;
	    tb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// If the ToggleButton is set to off, fire flashLightOn(View v)
				// Else, fire flashLightOff(View v)
				if (!tb.isChecked()) {
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
		try {
			// If the phone has flash capabilities
			if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
				cam = Camera.open();
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
			if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
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
	
}
