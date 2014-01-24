package com.leaf.sqlquery;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.leaf.sqlquery.util.HttpData;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UserInfoActivity extends Activity {

	private String UserId;
	Map map;
	ArrayList<HashMap<String, String>> listItem;

	EditText searchEdit;
	Button searchBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subsidy_list);
		
		searchEdit=(EditText)findViewById(R.id.searchEdit);
		searchBtn=(Button) findViewById(R.id.searchBtn);
		
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UserId = searchEdit.getText().toString();
				searchEdit.setVisibility(View.GONE);
				searchBtn.setVisibility(View.GONE);
				searchUserInfo();
			}
		});
		
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayOptions(0, actionBar.DISPLAY_SHOW_HOME);
		// actionBar.setIcon(R.drawable.ic_ab_back_holo_dark);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				UserId = query;
				searchUserInfo();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in Action Bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			UserInfoActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void searchUserInfo() {
		new Thread() {
			public void run() {
				HttpData httpData = new HttpData();
				listItem = new ArrayList<HashMap<String, String>>();
				listItem = httpData.searchId(UserId,
						com.leaf.sqlquery.util.Settings.getUserInfoUrl());
				com.leaf.sqlquery.util.Settings.setSubsidys(listItem);
				searchHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	private Handler searchHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

			if (listItem != null) {

				nameTextView
						.setText(((HashMap<String, String>) listItem.get(0))
								.get("userName"));
				nameTextView.setVisibility(View.VISIBLE);

				ListView listView = (ListView) findViewById(R.id.subsidyList);
				SimpleAdapter listItemAdapter = new SimpleAdapter(
						UserInfoActivity.this, listItem, R.layout.subsidy_item,
						new String[] { "subsidyName", "subsidyDate" },
						new int[] { R.id.subsidyName, R.id.subsidyDate });

				listView.setAdapter(listItemAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						Intent intent = new Intent();
						intent.setClass(UserInfoActivity.this,
								SubsidyInfoActivity.class);
						intent.putExtra("index", arg2);
						startActivity(intent);
					}
				});
			} else {
				nameTextView.setVisibility(View.VISIBLE);
				nameTextView.setText("没有找到该用户");
			}

		}
	};

}
