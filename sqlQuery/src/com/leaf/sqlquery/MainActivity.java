package com.leaf.sqlquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainActivity extends Activity {
	
	Button userInfoBtn;
	Button fileListBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userInfoBtn=(Button) findViewById(R.id.button1);
		fileListBtn=(Button) findViewById(R.id.button2);
		
		userInfoBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, UserInfoActivity.class);
				startActivity(intent);
			}
		});
		
		fileListBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, FileListActivity.class);
				startActivity(intent);
//				MainActivity.this.finish();
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
//		SearchView searchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
		return true;
	}

}
