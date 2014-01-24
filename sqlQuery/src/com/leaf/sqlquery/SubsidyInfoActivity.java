package com.leaf.sqlquery;

import java.util.ArrayList;
import java.util.HashMap;

import com.leaf.sqlquery.util.Settings;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SubsidyInfoActivity extends Activity {

	public SubsidyInfoActivity() {
		// TODO Auto-generated constructor stub
	}
	TextView nameTextView;
	TextView idTextView;
	TextView itemTextView;
	TextView moneyTextView;
	TextView dateTextView;
	
	HashMap<String, String> subsidyInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		
		Bundle extras=getIntent().getExtras();
		int index=extras.getInt("index");
		
		subsidyInfo=Settings.getSubsidys().get(index);
		
		nameTextView=(TextView) findViewById(R.id.info1);
		idTextView=(TextView) findViewById(R.id.info2);
		itemTextView=(TextView) findViewById(R.id.info3);
		moneyTextView=(TextView) findViewById(R.id.info4);
		dateTextView=(TextView) findViewById(R.id.info5);
		
		nameTextView.setText(subsidyInfo.get("userName"));
		idTextView.setText(subsidyInfo.get("userId"));
		itemTextView.setText(subsidyInfo.get("subsidyName"));
		moneyTextView.setText(subsidyInfo.get("money"));
		dateTextView.setText(subsidyInfo.get("subsidyDate"));

	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar=this.getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayOptions(0, actionBar.DISPLAY_SHOW_HOME);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in Action Bar clicked; go home
	            Intent intent = new Intent(this, UserInfoActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            SubsidyInfoActivity.this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}


