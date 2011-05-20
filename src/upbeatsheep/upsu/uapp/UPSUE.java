package upbeatsheep.upsu.uapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import upbeatsheep.rss.RSSListAdapter;
import upbeatsheep.rss.RssReader;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class UPSUE extends ListActivity {

	
	private Context mContext;
	private RSSListAdapter adapter;
	private List<JSONObject> feeds;
	

    
    private Button mon;
    private Button tue;
    private Button wed;
    private Button thur;
    private Button fri;
    private Button sat;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.events);
        
        mContext = this;
        
        initialiseWidgets();
        
        setUpOnClickListeners();
    
	
    try {
		new GetRSSFeeds()
				.execute(new URL(
						"http://feeds.feedburner.com/UniversityOfPlymouthNewsFeed"));
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
}
    
    private void initialiseWidgets(){
    	mon = (Button) findViewById(R.id.eventmon);
    	tue = (Button) findViewById(R.id.eventtue);
    	wed = (Button) findViewById(R.id.eventwed);
    	thur = (Button) findViewById(R.id.eventthur);
    	fri = (Button) findViewById(R.id.eventfri);
    	sat = (Button) findViewById(R.id.eventsat);
    }
    
	public class GetRSSFeeds extends AsyncTask<URL, Integer, List<JSONObject>> {

		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		protected List<JSONObject> doInBackground(URL... urls) {
			List<JSONObject> jobs = new ArrayList<JSONObject>();

			try {
				jobs = RssReader
						.getLatestRssFeed(new String[] { "http://www.upsu.com/rss/eventfeed.php" }); 																			// spaces
			} catch (Exception e) {
				Log.e("RSS ERROR",
						"Error loading RSS Stream >> " + e.getMessage() + " //"
								+ e.toString());
			}
			return jobs;
		}
	
	
	protected void onPostExecute(List<JSONObject> result) {
		setProgressBarIndeterminateVisibility(false);
		adapter = new RSSListAdapter(mContext, result);
		adapter.notifyDataSetChanged();
		setListAdapter(adapter);
	}
}
	
    private void setUpOnClickListeners(){
    	mon.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585033/regular_nights_/monday_-_fuzzed_up/"));
				startActivity(i);
			}});
    	tue.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585053/regular_nights_/tuesday_-_the_tuesday_club/"));
				startActivity(i);
			}});
    	wed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585063/regular_nights_/wednesday_-_supporters_wednesday/"));
				startActivity(i);
			}});
    	thur.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585073/regular_nights_/thursday_-_brassic/"));
				startActivity(i);
			}});
    	fri.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585103/regular_nights_/friday_-_the_big_cheese/"));
				startActivity(i);
			}});
    	sat.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://upsu.com/suents/content/585113/regular_nights_/saturday_-_ultimate_saturday/"));
				startActivity(i);
			}});
    
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try {
			Log.i("UPSU Android",
					"Help: " + position + " "
							+ adapter.getItem(position).getString("link"));
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(adapter.getItem(position).getString("link")));
			startActivity(i);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    @Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id, args);
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.upsu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.mnu_upsu_site:
			i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse("http://www.UPSU.com"));
			startActivity(i);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
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
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out); 
	}

}
