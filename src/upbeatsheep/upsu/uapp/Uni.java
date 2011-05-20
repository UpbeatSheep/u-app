package upbeatsheep.upsu.uapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

public class Uni extends ListActivity {
	Button infoButton;
	Button myButton;
	Button mapButton;
	Context mContext;

	private RSSListAdapter adapter;
	private List<JSONObject> feeds;

	final static String SERVER_ADDRESS = "http://omondo.co.uk/json/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.uni);
		setProgressBarIndeterminateVisibility(true);
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
		infoButton = (Button) findViewById(R.id.btn_uni_info);
		myButton = (Button) findViewById(R.id.btn_my_uni);
	}

	private void setUpOnClickListeners() {

		mapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, Map.class);
				i.putExtra("uniMode", true);
				i.putExtra("upsuMode", false);
				i.putExtra("activity", "uni");
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_down_right_in,
						R.anim.slide_down_right_out);
			}
		});
		infoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, UniInfo.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
		});
		myButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, UniMy.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_down_in,
						R.anim.slide_down_out);
			}
		});
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

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.uni, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.mnu_upsu_site:
			i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse("http://www.plymouth.ac.uk"));
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
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
}
