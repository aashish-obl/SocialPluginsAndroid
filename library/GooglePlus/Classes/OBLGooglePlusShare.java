package com.example.googlepluslibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.plus.PlusShare;

public class OBLGooglePlusShare extends OBLPost {

	Context context;
	Activity activity;
	PlusShare plusshare;
	OBLGooglePlusShareInterface gpshareinterface;
	Intent shareIntent;
	Intent shareImageIntent;
	Intent photopicker;
	Uri selectedImage;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
	public OBLGooglePlusShare(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
		/*The object of OBLGoogleShareInterface is used to communicate with MainActivity*/
		gpshareinterface = (OBLGooglePlusShareInterface) activity;
	}

	@Override
	public void post(String status) {
		shareIntent = new PlusShare.Builder(activity).setType("text/plain")
				.setText(status).getIntent();
	}

	//This method is called from MainActivity and the imageUri is given by the user
	public void postsStatusWithDetailsDescription(String status, String title,
			String description, String imageUri) {
		Uri uri = Uri.parse(imageUri);
		shareImageIntent = new PlusShare.Builder(activity)
				.setText(status)
				.setType("text/plain")
				.setContentDeepLinkId("FantasyCricket", title, description, uri)
				.getIntent();
		activity.startActivityForResult(shareImageIntent, 9000);
	}

	public void onActivityResult(int requestCode, int responseCode, Intent data) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == Activity.RESULT_OK) {
			/*
			 * The sharingCompleted method belongs to
			 * OBLGooglePlusShareInterface, this method contains the boolean
			 * value and the method is called from Main
			 * Activity, if the value is true then it shows that the post is successfully shared
			 */
			gpshareinterface.sharingCompleted(true);
		}
	}
}
