package com.example.tipcalculator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	public static Integer clickedButtonid = 0;
	int selectedColor = Color.CYAN;
	int unselectColor = Color.GRAY;
	EditText enteredAmt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enteredAmt = (EditText) findViewById(R.id.totalAmt);
		listenEditBillAmt();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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

	public void applytenper(View v) {
		clickedButtonid = 1;
		setColors(selectedColor, unselectColor, unselectColor);
		setTipAmount(0.1);
	}

	public void applyfifteenper(View v) {
		clickedButtonid = 2;
		setColors(unselectColor, selectedColor, unselectColor);
		setTipAmount(0.15);
	}

	public void applytwentyper(View v) {
		clickedButtonid = 3;
		setColors(unselectColor, unselectColor, selectedColor);
		setTipAmount(0.2);
	}

	private void listenEditBillAmt() {
		enteredAmt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (clickedButtonid == 0) {
					return;
				}
				switch (clickedButtonid) {
				case 1:
					setColors(selectedColor, unselectColor, unselectColor);
					setTipAmount(0.1);
					break;
				case 2:
					setColors(unselectColor, selectedColor, unselectColor);
					setTipAmount(0.15);
					break;
				case 3:
					setColors(unselectColor, unselectColor, selectedColor);
					setTipAmount(0.2);
					break;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void setColors(int ten, int fifteen, int twenty) {
		Button tenper = (Button) findViewById(R.id.tenper);
		tenper.setBackgroundColor(ten);
		Button fifteenper = (Button) findViewById(R.id.fifteenper);
		fifteenper.setBackgroundColor(fifteen);
		Button twentyper = (Button) findViewById(R.id.twentyper);
		twentyper.setBackgroundColor(twenty);
	}

	private void setTipAmount(Double offset) {
		EditText enteredAmt = (EditText) findViewById(R.id.totalAmt);
		EditText tipAmt = (EditText) findViewById(R.id.tipValue);
		TextView tipText = (TextView) findViewById(R.id.displayText);
		if (enteredAmt.getText().toString().compareTo("")==0 || !isDouble(enteredAmt.getText().toString())) {
			tipAmt.setText("0.0");
			Toast.makeText(MainActivity.this, "Enter a valid value", 5);
			//setColors(unselectColor, unselectColor, unselectColor);
			return;
		}	
		Double doubleAmt = Double.parseDouble(enteredAmt.getText().toString());
		doubleAmt = doubleAmt * offset;
		tipAmt.setVisibility(View.VISIBLE);	
		tipText.setVisibility(View.VISIBLE);
		tipAmt.setText(doubleAmt.toString());
	}

	private boolean isDouble(String string) {
		try{
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
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
