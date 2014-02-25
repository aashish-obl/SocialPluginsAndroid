package com.example.facebookdemo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.facebooklibrary.OBLError;
import com.example.facebooklibrary.OBLFacebookFriend;
import com.example.facebooklibrary.OBLFacebookLogin;
import com.example.facebooklibrary.OBLFacebookLoginInterface;
import com.example.facebooklibrary.OBLFacebookPermission;
import com.example.facebooklibrary.OBLFacebookPost;
import com.example.facebooklibrary.OBLFacebookPostInterface;
import com.example.facebooklibrary.OBLFacebookQueryInterface;
import com.example.facebooklibrary.OBLFacebookQuey;
import com.example.facebooklibrary.OBLFacebookUser;
import com.example.facebooklibrary.OBLLog;

public class DemoActivity extends Activity implements
		OBLFacebookLoginInterface, OnClickListener, OBLFacebookPostInterface,
		OBLFacebookQueryInterface {

	Button login, logout, post, user, friend;
	OBLFacebookLogin objlogin;
	OBLLog objlog;
	OBLFacebookPost objpost;
	OBLFacebookQuey objquery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		login = (Button) findViewById(R.id.btn_login);
		logout = (Button) findViewById(R.id.btn_logout);
		post = (Button) findViewById(R.id.btn_post);
		user = (Button) findViewById(R.id.btn_user);
		friend = (Button) findViewById(R.id.btn_friend);
		login.setOnClickListener(this);
		logout.setOnClickListener(this);
		post.setOnClickListener(this);
		user.setOnClickListener(this);
		friend.setOnClickListener(this);

		objlog = new OBLLog();
		objlog.setDebuggingON(true);

		objlogin = new OBLFacebookLogin(this, this);
		objlogin.initSession(savedInstanceState);
		objlogin.setLoginBehaviour(OBLFacebookLogin.NATIVE_WEBVIEW);

		objpost = new OBLFacebookPost(this, this);
		objquery = new OBLFacebookQuey(this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		objlogin.ActivtyResult(requestCode, resultCode, data);
	}

	@Override
	public void loginResult(boolean result,OBLError error) {
		// TODO Auto-generated method stub
		if (result == true) {
			Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
			login.setVisibility(View.GONE);
			logout.setVisibility(View.VISIBLE);
		} else if (result == false) {
			Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
			login.setVisibility(View.VISIBLE);
			logout.setVisibility(View.GONE);
		}
		if (error!=null)
		{
			Log.i("Error","Name: "+error.getName()+" Message: "+error.getMessage()+" Description: "+error.getDescription());
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.btn_login) {
			objlogin.loginWithPermission(new
			String[]{OBLFacebookPermission.PUBLISH_ACTIONS,OBLFacebookPermission.USER_ABOUT_ME});
		} else if (arg0.getId() == R.id.btn_logout) {
			objlogin.logout();
		} else if (arg0.getId() == R.id.btn_post) {
			objpost.postsStatusWithDetailsDescription("Share Your Ride",
					"RideShare Buddy",
					"Share YOur Ride and decrease pollution",
					"http://ridesharebuddy.com/ride_images/icon200.png",
					"http://www.objectlounge.com");
		} else if (arg0.getId() == R.id.btn_user) {
			objquery.fetchUserProfile();
		} else if (arg0.getId() == R.id.btn_friend) {
			objquery.allFriends();
		}

	}

	@Override
	public void postingCompleted(boolean posted,OBLError error) {
		// TODO Auto-generated method stub
		if (posted) {
			Toast.makeText(this, "Posted Successfully", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(this, "Posting Failed", Toast.LENGTH_LONG).show();
		}
		if (error!=null)
		{
			Log.i("Error","Name: "+error.getName()+" Message: "+error.getMessage()+" Description: "+error.getDescription());
		}
	}

	@Override
	public void userInfoReceived(OBLFacebookUser user) {
		// TODO Auto-generated method stub
		Log.i("First Name:", user.getFirstName());
		Log.i("Middle Name:", user.getMiddlename());
		Log.i("Last Name:", user.getLastName());
		Log.i("Social Id:", user.getsocialMediaId());
		Log.i("Birthdate:", user.getBirthdate());
		Log.i("Gender:", user.getGender());
		Log.i("Location", user.getCurrentLocation());
		Log.i("Email:", user.getEmail());
		Log.i("Profile Name:", user.getProfileName());
		Log.i("HomeTown:", user.getHomeTown());
	}

	@Override
	public void friendsInfoReceived(List<OBLFacebookFriend> friends) {
		// TODO Auto-generated method stub
		for (int i = 0; i < friends.size(); i++) {
			Log.i("Friends Details", "ID: " + friends.get(i).getsocialMediaId()
					+ " Name: " + friends.get(i).getname() + " Gender: "
					+ friends.get(i).getGender());
		}
	}

}
