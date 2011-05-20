package upbeatsheep.upsu.uapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class Venues extends ListActivity {
	
	JSONListAdapter adapter;
	List<JSONObject> places;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.venues);
		
		mContext = this;
		
		try {
			new getBuildings().execute(new URL("http://www.omondo.co.uk/JSON/venues.txt"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private class getBuildings extends AsyncTask<URL, Integer, List<JSONObject>> {
	     
		protected void onPreExecute(){
			setProgressBarIndeterminateVisibility(true);
		}
		
		protected List<JSONObject> doInBackground(URL... urls) {
			HTTPClient client = new HTTPClient(mContext);
			
			try {
				JSONObject momo = client.getJSON(urls[0].toString());
	   		 	JSONArray markers = momo.getJSONArray("building_names");
	   		 	places = new ArrayList<JSONObject>();
	   		 	
	   		 	for (int i = 0; i<markers.length(); i++){
	   		 		Log.i("UPSU Android", markers.getJSONObject(i).toString());
	   		 		places.add(markers.getJSONObject(i));
	   		 	}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return places;
	     }

	     protected void onPostExecute(List<JSONObject> result) {
	    	 adapter = new JSONListAdapter(mContext,result);
				setListAdapter(adapter);
				setProgressBarIndeterminateVisibility(false);
	     }
	 }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent i = new Intent(this, Venue.class);
		  try {
			i.putExtra("name", places.get(position).getString("building_name"));
			i.putExtra("desc", places.get(position).getString("building_does"));
			i.putExtra("url", places.get(position).getJSONArray("img").getJSONObject(0).getString("url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out); 
		  
		  super.onListItemClick(l, v, position, id);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}


public void onBackPressed() {
	super.onBackPressed();
	this.finish();
	overridePendingTransition(R.anim.slide_up_in,	R.anim.slide_up_out);
			}
	}
