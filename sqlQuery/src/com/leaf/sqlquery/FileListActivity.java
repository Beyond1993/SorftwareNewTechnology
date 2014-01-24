package com.leaf.sqlquery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.leaf.sqlquery.util.HttpData;
import com.leaf.sqlquery.util.Settings;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FileListActivity extends Activity {

	ArrayList<HashMap<String, String>> listItem;
	int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filelist);
		listAllFile();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayOptions(0, actionBar.DISPLAY_SHOW_HOME);
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
			FileListActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void listAllFile() {
		new Thread() {
			public void run() {
				HttpData httpData = new HttpData();
				listItem = new ArrayList<HashMap<String, String>>();
				listItem = httpData.ListAllfile(Settings.getListFileUrl());
				listViewHandler.sendEmptyMessage(0);
			}

		}.start();
	}

	private Handler listViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			ListView listView = (ListView) findViewById(R.id.fileListView);

			SimpleAdapter listItemAdapter = new SimpleAdapter(
					FileListActivity.this, listItem, R.layout.file_item,
					new String[] { "fileName" },
					new int[] { R.id.fileItemTextView });

			listView.setAdapter(listItemAdapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					index = arg2;
					new Thread() {
						public void run() {
							HttpData httpData = new HttpData();
							String path=httpData.downloadTemp(
									Settings.getDownloadFileUrl(),
									((HashMap<String, String>) listItem
											.get(index)).get("fileName"));
							
							Intent intent=getWordIntent(path);
							startActivity(intent);
						}
					}.start();
				}
			});

		}
	};
	
	public Intent getWordIntent(String path){
		
		Intent intent=new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}
}
