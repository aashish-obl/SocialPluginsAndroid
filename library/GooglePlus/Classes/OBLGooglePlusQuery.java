package com.example.googlepluslibrary;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnPeopleLoadedListener;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

public class OBLGooglePlusQuery implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnPeopleLoadedListener {
	OBLGooglePlusUser userprofile = new OBLGooglePlusUser();
	List<OBLGooglePlusFriend> arrayOfGPFriends = new ArrayList<OBLGooglePlusFriend>();
	OBLGooglePlusLogin oblpluslogin;
	OBLGooglePlusQueryInterface gpInterface;
	Person person;
	OBLGooglePlusFriend oblgpfriend;
	Activity activity;
	Context context;
	PlusClient plusClient;
	String clientId;
	String[] scopes = new String[] { Scopes.PLUS_ME, Scopes.PLUS_LOGIN };
	String[] actions = new String[] { "http://schemas.google.com/AddActivity",
			"http://schemas.google.com/BuyActivity" };
	String accountName = "";
	int gender_value;
	int temp = 0;

	public OBLGooglePlusQuery(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
		/*
		 * The object of OBLGoogleQueryInterface is used to communicate with
		 * MainActivity
		 */
		gpInterface = (OBLGooglePlusQueryInterface) activity;

	}

	public void createPlusClient() {
		if (plusClient == null) {
			plusClient = new PlusClient.Builder(context, this, this)
					.setActions(actions).setScopes(scopes).build();
		} else {
			plusClient.connect();
		}
	}

	// The method is called from onConnected method and displays the user
	// details
	public void fetchUserProfile() {
		{
			temp = 1;
			if (plusClient.isConnected()) {
				person = plusClient.getCurrentPerson();
				userprofile.setFirstName(person.getName().getGivenName());
				userprofile.setMiddlename(person.getName().getMiddleName());
				userprofile.setLastName(person.getName().getFamilyName());
				userprofile.setProfileName(person.getDisplayName());
				userprofile.setBirthdate(person.getBirthday());
				userprofile.setCurrentLocation(person.getCurrentLocation());
				gender_value = person.getGender();
				userprofile.setGender(gender_value == 0 ? "Male" : "Female");
				userprofile.setEmail(plusClient.getAccountName());
				accountName = userprofile.getFirstName();
				/*
				 * The userInfoReceived method belongs to
				 * OBLGooglePlusQueryInterface, this method contains the object
				 * of OBLGooglePlusUser class and the method is called from Main
				 * Activity which displays the user details
				 */
				gpInterface.userInfoReceived(userprofile);
			} else {
				plusClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnected(Bundle arg0) {
		// temp value is used to display either user details or friends details
		if (temp == 1) {
			fetchUserProfile();
			temp = 0;
		}
		if (temp == 2) {
			/*
			 * This Method belongs to PlusClient class which contains the
			 * OnPeopleLoadListener Object and pageToken
			 */
			plusClient.loadVisiblePeople(this, null);
			temp = 0;
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	// This is an abstract method of OnPeopleLoadedListener and it is called
	// from loadVisiblePeople method
	@Override
	public void onPeopleLoaded(ConnectionResult con, PersonBuffer buffer,
			String arg2) {
		int count = buffer.getCount();
		String gender = "";
		for (int i = 0; i < count; i++) {
			if (buffer.get(i).getGender() == 0) {
				gender = "Male";
			}
			if (buffer.get(i).getGender() == 1) {
				gender = "Female";
			}
			// ArrayList is used to add the object of OBLGooglePlusFriend class
			arrayOfGPFriends.add(new OBLGooglePlusFriend(buffer.get(i).getId(),
					buffer.get(i).getDisplayName(),
					buffer.get(i).getBirthday(), gender, buffer.get(i)
							.getImage().getUrl()));
		}
		buffer.close();
		/*
		 * The friendsInfoReceived method belongs to
		 * OBLGooglePlusQueryInterface, this method contains the list of
		 * OBLGooglePlusUser's class object and the method is called from Main
		 * Activity which displays the friends details
		 */
		gpInterface.friendsInfoReceived(arrayOfGPFriends);
	}

	// This method is called from mainActivity which displays the friends
	// details
	public void allFriends() {
		temp = 2;
		// It will directly call OnConnected Method
		plusClient.connect();
	}
}
