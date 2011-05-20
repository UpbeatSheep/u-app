package upbeatsheep.rss;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;


public class RssActivity extends ListActivity {

	private RSSListAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<JSONObject> jobs = new ArrayList<JSONObject>();
		try {
			jobs = RssReader.getLatestRssFeed(new String[]{"http://feeds.feedburner.com/UniversityOfPlymouthNewsFeed"});	//Add extra feeds via a string deliminated by spaces
		} catch (Exception e) {
			Log.e("RSS ERROR", "Error loading RSS Stream >> " + e.getMessage() + " //" + e.toString());
		}

		adapter = new RSSListAdapter(this,jobs);
		setListAdapter(adapter);
	}

}
