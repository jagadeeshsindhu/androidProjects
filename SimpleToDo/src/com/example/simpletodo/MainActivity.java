package com.example.simpletodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	ListView listView;
	ArrayList<String> listItems;
	ArrayAdapter<String> listItemsAdaptor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listItems);
		listItems = new ArrayList<String>();
		readFromFile();
		listItemsAdaptor = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(listItemsAdaptor);
		listViewListener();
		listViewEditListener();
		listItemsAdaptor.add("Do Android");
		listItemsAdaptor.add("Do Dance");
		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
		 */
	}

	public void addNewItem(View v) {
		EditText newItem = (EditText) findViewById(R.id.addText);
		listItemsAdaptor.add(newItem.getText().toString());
		newItem.setText("");
		writeToFile();
	}

	private void listViewListener() {
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				listItems.remove(position);
				listItemsAdaptor.notifyDataSetChanged();
				writeToFile();
				return true;
			}
		});
	}
	
	private void listViewEditListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String listItem = listItems.get(position);
				Intent intent = new Intent( MainActivity.this, EditActivity.class); 
				intent.putExtra("editText",listItem);
				intent.putExtra("position", position);
				startActivityForResult(intent,1);
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String updatedTxt=intent.getStringExtra("updatedTxt");
	            int position=intent.getIntExtra("postion",0);
	            listItems.set(position, updatedTxt);
	            listItemsAdaptor.notifyDataSetChanged();
	        }
	    }
	}

	public void readFromFile() {
		File f = new File(".//todo.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				listItems.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile() {
		File f = new File("/Users/sindhuj/Documents/androidQuote/SimpleToDo",
				"todo.txt");
		try {
			FileUtils.writeLines(f, listItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
