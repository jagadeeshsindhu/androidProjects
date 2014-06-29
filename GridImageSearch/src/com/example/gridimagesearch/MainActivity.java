package com.example.gridimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gridimagesearch.adaptor.ImageUrlArrayAdaptor;
import com.example.gridimagesearch.data.ImageUrls;
import com.example.gridimagesearch.util.JsonUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends ActionBarActivity {

	EditText searchtext;
	Button searchButton;
	GridView resultGrid;
	ArrayList<ImageUrls> imageUrls = new ArrayList<ImageUrls>();
	ImageUrlArrayAdaptor imageAdaptor;
	StringBuffer settings  = new StringBuffer();
	Integer sortSpinnerPos = -1;
	Integer geoSpinnerPos = -1;
	
	String key = "23395801dc09c1d1132c0e35b7b140ae";
	//String key ="fb8466cdcfdd8f2899a3cb2401e7aba8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
		imageAdaptor = new ImageUrlArrayAdaptor(this, imageUrls);
		resultGrid.setAdapter(imageAdaptor);
		startListernerOnGrid();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	private void startListernerOnGrid() {
		resultGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageUrls imageselected = (ImageUrls) resultGrid.getItemAtPosition(position);
				Intent intent = new Intent( MainActivity.this, FullScreenDisplay.class); 
				intent.putExtra("imageUrl",imageselected.getFullscreen());
				intent.putExtra("title",imageselected.getTitle());
				startActivity(intent);
			}
		});
		
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
	
	
	
	public void onImageSearch(View v) {
		String enteredString = searchtext.getText().toString();
		if( enteredString == null || enteredString.compareTo("") == 0) {
		Toast.makeText(this,"Please enter the search string", Toast.LENGTH_LONG).show();
		return;
		}
		Toast.makeText(this,"Searching for " + enteredString, Toast.LENGTH_LONG).show();
		
		AsyncHttpClient asyncClient =  new AsyncHttpClient();
		String callUrlString = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="
				+ key + "&text=" + Uri.encode(enteredString) + "&per_page=18&format=json&nojsoncallback=1"+settings;
		Log.i("INFO",callUrlString);
		asyncClient.get(callUrlString, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				List<ImageUrls> imageURLSet = new JsonUtil().getPhotoSet(response.toString());
				imageAdaptor.clear();
				imageAdaptor.addAll(imageURLSet);
			}
		});
	//	new WebUtil().execute("empty");
	}

	// private methods

	private void setUpViews() {
		searchtext = (EditText) findViewById(R.id.textSearch);
		searchButton = (Button) findViewById(R.id.btmsearch);
		resultGrid = (GridView) findViewById(R.id.gdresults);
	}
	
	public void  changeSettings(View v) {
		Intent intent = new Intent( MainActivity.this, EditSettings.class); 
		if(sortSpinnerPos != -1)
			intent.putExtra("sortSpinnerPos", sortSpinnerPos);
		if(geoSpinnerPos != -1)
			intent.putExtra("geoSpinnerPos", geoSpinnerPos);
			
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String sortSetting=intent.getStringExtra("sort");
	            String geoSetting=intent.getStringExtra("geo");
	          //  &sort=relevance&geo_context=2
	            		settings.setLength(0);
	            if(sortSetting.compareTo("none") !=0){
	            	settings.append("&sort="+sortSetting);
	            }
	            if(geoSetting.compareTo("-1") !=0){
	            	settings.append("&geo_context="+geoSetting);
	            }
	            
	            sortSpinnerPos=intent.getIntExtra("sortPos", 0);
	            geoSpinnerPos=intent.getIntExtra("geoPos", 0);
	           
	        }
	    }
	}

}
