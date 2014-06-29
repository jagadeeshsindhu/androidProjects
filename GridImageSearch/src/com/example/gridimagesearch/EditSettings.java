package com.example.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditSettings extends ActionBarActivity {

	private String sortSettings[] = { "none", "relevance", "date-taken-asc", "date-taken-desc", "date-posted-asc",
			"date-posted-desc" };
	private String geoLocSettings[] = { "none", "any", "indoors", "outdoors" };
	Spinner sortSpinner;
	Spinner geoSpinner;

	String selectedSortSetting;
	String selectedGeoSetting;
	Integer sortSpinPos;
	Integer geoSpinPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_settings);
		setUpViews();
		Integer sortSpinnerPos = getIntent().getIntExtra("sortSpinnerPos", 0);
		if(sortSpinnerPos != 0)
			sortSpinner.setSelection(sortSpinnerPos);
		Integer geoSpinnerPos = getIntent().getIntExtra("geoSpinnerPos", 0);
		if(geoSpinnerPos != 0)
			geoSpinner.setSelection(geoSpinnerPos);
		
		listenerSortSpinnerSelection();
		listenerGeoSpinnerSelection();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	private void setUpViews() {
		sortSpinner = (Spinner) findViewById(R.id.sortspinner);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				sortSettings);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortSpinner.setAdapter(spinnerArrayAdapter);
		geoSpinner = (Spinner) findViewById(R.id.geospinner);
		ArrayAdapter<String> geoLocArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				geoLocSettings);
		geoLocArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		geoSpinner.setAdapter(geoLocArrayAdapter);

	}

	public void listenerSortSpinnerSelection() {
		sortSpinner.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedSortSetting = sortSettings[position];
				sortSpinner.setSelection(position);
				sortSpinPos=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void listenerGeoSpinnerSelection() {
		geoSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedGeoSetting = Integer.toString(position-1);
				geoSpinner.setSelection(position);
				geoSpinPos=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void saveSettings(View v) {
		Intent intent = new Intent(EditSettings.this, MainActivity.class);
		intent.putExtra("sort", selectedSortSetting);
		intent.putExtra("geo", selectedGeoSetting);
		intent.putExtra("sortPos", sortSpinPos);
		intent.putExtra("geoPos", geoSpinPos);
		
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_settings, menu);
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_edit_settings, container, false);
			return rootView;
		}
	}

}
