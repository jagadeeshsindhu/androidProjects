package com.example.tipcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static Integer clickedButtonid = 0;
	int selectedColor = Color.CYAN;
	int unselectColor = Color.GRAY;
	EditText enteredAmt;
	EditText customAmt;
	TextView customText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enteredAmt = (EditText) findViewById(R.id.totalAmt);
		customAmt = (EditText) findViewById(R.id.customTip);
		customText=  (TextView) findViewById(R.id.customTipText);
		listenEditBillAmt();
		listenEditCustomAmt();

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
		setColors(selectedColor, unselectColor, unselectColor, unselectColor);
		setVisibilityForCustom(View.INVISIBLE);
		setTipAmount(0.1);
	}

	public void applyfifteenper(View v) {
		clickedButtonid = 2;
		setColors(unselectColor, selectedColor, unselectColor, unselectColor);
		setVisibilityForCustom(View.INVISIBLE);
		setTipAmount(0.15);
	}

	public void applytwentyper(View v) {
		clickedButtonid = 3;
		setColors(unselectColor, unselectColor, selectedColor, unselectColor);
		setVisibilityForCustom(View.INVISIBLE);
		setTipAmount(0.2);
	}

	public void customTip(View v) {
		clickedButtonid = 4;
		setColors(unselectColor, unselectColor, unselectColor, selectedColor);
		setVisibilityForCustom(View.VISIBLE);
		setCustomTipAmount();
	}

	private void setVisibilityForCustom(int visible) {
		customAmt.setVisibility(visible);
		customText.setVisibility(visible);
	}
	
	private void listenEditCustomAmt() {
		customAmt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				setCustomTipAmount();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {	}
		});
		
	}
	
	private void setCustomTipAmount() {
		if(customAmt.getText().toString().compareTo("") !=0 && isDouble(customAmt.getText().toString())) {
			Double customTipValue = Double.parseDouble(customAmt.getText().toString());
			setTipAmount(customTipValue/100);
		}
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
					applytenper(null);
					break;
				case 2:
					applyfifteenper(null);
					break;
				case 3:
					applytwentyper(null);
					break;
				case 4:
					customTip(null);
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

	private void setColors(int ten, int fifteen, int twenty, int custom) {
		Button tenper = (Button) findViewById(R.id.tenper);
		tenper.setBackgroundColor(ten);
		Button fifteenper = (Button) findViewById(R.id.fifteenper);
		fifteenper.setBackgroundColor(fifteen);
		Button twentyper = (Button) findViewById(R.id.twentyper);
		twentyper.setBackgroundColor(twenty);
		Button customper = (Button) findViewById(R.id.custom);
		customper.setBackgroundColor(custom);
	}

	private void setTipAmount(Double offset) {
		EditText enteredAmt = (EditText) findViewById(R.id.totalAmt);
		EditText tipAmt = (EditText) findViewById(R.id.tipValue);
		TextView tipText = (TextView) findViewById(R.id.displayText);
		if (enteredAmt.getText().toString().compareTo("") == 0
				|| !isDouble(enteredAmt.getText().toString())) {
			tipAmt.setText("0.0");
			Toast.makeText(MainActivity.this, "Enter a valid value", 5);
			// setColors(unselectColor, unselectColor, unselectColor);
			return;
		}
		Double doubleAmt = Double.parseDouble(enteredAmt.getText().toString());
		doubleAmt = doubleAmt * offset;
		tipAmt.setVisibility(View.VISIBLE);
		tipText.setVisibility(View.VISIBLE);
		tipAmt.setText(doubleAmt.toString());
	}

	private boolean isDouble(String string) {
		try {
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
