package com.example.googlepluslibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

public class OBLGooglePlusLogin extends OBLLogin implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	PlusClient plusClient;
	OBLGooglePlusUser plusUser;
	ConnectionResult mConnectionResult;
	String clientId;
	String[] scopes = new String[] { Scopes.PLUS_ME, Scopes.PLUS_LOGIN };
	String[] actions = new String[] { "http://schemas.google.com/AddActivity",
			"http://schemas.google.com/BuyActivity" };
	String accountName;
	static Context context;
	Activity activity;
	OBLGooglePlusLoginInterface gplogin;
	Person person;

	public OBLGooglePlusLogin(Context context, Activity activity) {
		OBLGooglePlusLogin.context = context;
		this.activity = activity;
		/*
		 * The object of OBLGooglePlusLoginInterface is used to communicate with
		 * MainActivity
		 */
		gplogin = (OBLGooglePlusLoginInterface) activity;
	}

	@Override
	public void login() {
		// Creating the PlusClient Object and trying to connect
		if (plusClient == null) {
			plusClient = new PlusClient.Builder(context, this, this)
					.setActions(actions).setScopes(scopes).build();
			plusClient.connect();
		} else {
			plusClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		accountName = plusClient.getAccountName();
		person = plusClient.getCurrentPerson();
		Toast.makeText(context, accountName + " " + "Connected",
				Toast.LENGTH_SHORT).show();
		/*
		 * The googlePlusLoginCompleted method belongs to
		 * OBLGooglePlusLoginInterface, this method contains the boolean value
		 * and the method is called from Main Activity, if the value is true
		 * then it is successfully logged in
		 */
		gplogin.googlePlusLoginCompleted(true);
		// clientId=person.getId();
	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connection) {
		if (connection.hasResolution()) {
			try {
				connection.startResolutionForResult(activity,
						REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				plusClient.connect();
			}
		}
		mConnectionResult = connection;
	}

	public void onActivityResult(int requestCode, int responseCode, Intent data) {
		/*
		 * When the responseCode is Result_Ok then the connect method will be
		 * called or if the response code is Result_CANCELLED then the connect
		 * method will not called
		 */
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == Activity.RESULT_OK) {
			mConnectionResult = null;
			plusClient.connect();
		} else if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == Activity.RESULT_CANCELED) {
			mConnectionResult = null;
			Log.i("Login", "Cancelled");
		}
	}

	@Override
	public boolean logout() {

		if (plusClient.isConnected()) {
			plusClient.clearDefaultAccount();
			plusClient.disconnect();
			/*
			 * The googlePlusLogoutCompleted method belongs to
			 * OBLGooglePlusLoginInterface, this method contains the boolean
			 * value and the method is called from Main Activity, if the value
			 * is true then it is successfully logged out
			 */
			gplogin.googlePlusLogoutCompleted(true);
			return true;
		}
		return false;
	}
}