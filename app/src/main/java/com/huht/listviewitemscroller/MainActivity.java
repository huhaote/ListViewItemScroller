package com.huht.listviewitemscroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	float lastX,lastY,mLastionMotionX;
	
	private ScrollAdapter adapter;
	
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		adapter = new ScrollAdapter(this);
		list.setAdapter(adapter);
	}

}
