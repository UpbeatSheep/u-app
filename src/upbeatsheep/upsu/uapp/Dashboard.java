package upbeatsheep.upsu.uapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import upbeatsheep.rss.RSSListAdapter;
import upbeatsheep.rss.RssReader;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class Dashboard extends ListActivity {

	private ImageButton uniButton;
	private ImageButton upsuButton;
	private Button mapButton;
	private Context mContext;
	private RSSListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
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

	private void initialiseWidgets() {
		mapButton = (Button) findViewById(R.id.btn_campus_map);
		uniButton = (ImageButton) findViewById(R.id.btn_uni);
		upsuButton = (ImageButton) findViewById(R.id.btn_upsu);
	}

	public class GetRSSFeeds extends AsyncTask<URL, Integer, List<JSONObject>> {

		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		protected List<JSONObject> doInBackground(URL... urls) {
			List<JSONObject> jobs = new ArrayList<JSONObject>();

			try {
				jobs = RssReader
						.getLatestRssFeed(new String[] { "http://www.plymouth.ac.uk/feeds/news" }); // spaces
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

	private void setUpOnClickListeners() {

		mapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, Map.class);
				i.putExtra("uniMode", true);
				i.putExtra("upsuMode", true);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_down_in,
						R.anim.slide_down_out);
			}
		});
		uniButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, Uni.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

				startActivity(i);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
		});
		upsuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, UPSU.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
	}

	@Override
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
}