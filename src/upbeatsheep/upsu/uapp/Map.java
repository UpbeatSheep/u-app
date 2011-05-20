package upbeatsheep.upsu.uapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Map extends MapActivity {

	private Boolean uniMode = false;
	private Boolean upsuMode = false;
	private List<Overlay> mapOverlays;
	private Context mContext;
	private ImageButton uniToggle;
	private ImageButton upsuToggle;
	private MapView mapView;
	private MapController mapController;
	private LocationManager locationManager;
	Drawable drawable;
	CustomItemizedOverlay uniOverlays;
	CustomItemizedOverlay upsuOverlays;
	private String callingActivity;
	final GeoPoint plymouthUni = new GeoPoint((int) (50.375255 * 1e6),
			(int) (-4.138927 * 1e6));
	final GeoPoint plymouth = new GeoPoint((int) (50.376288 * 1e6),
			(int) (-4.140902 * 1e6));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.map);

		drawable = getResources().getDrawable(R.drawable.marker);
		uniOverlays = new CustomItemizedOverlay(drawable, this);
		upsuOverlays = new CustomItemizedOverlay(drawable, this);

		mContext = this;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			uniMode = extras.getBoolean("uniMode");
			upsuMode = extras.getBoolean("upsuMode");
			callingActivity = extras.getString("activity");
			Log.i("UoP", "Calling Activity: " + callingActivity);
		}

		initialiseWidgets();

		setUpOnClickListeners();

		setUpMap();

		setDisplayItems();
	}

	private void initialiseWidgets() {
		mapView = (MapView) findViewById(R.id.mapview);
		uniToggle = (ImageButton) findViewById(R.id.btn_uni_toggle);
		uniToggle.setImageResource(R.drawable.btn_uni_logo);
		upsuToggle = (ImageButton) findViewById(R.id.btn_upsu_toggle);
	}

	private void setUpOnClickListeners() {
		uniToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!uniMode) {
					mapController.setZoom(17);
					mapController.animateTo(plymouthUni);
				}
				uniMode = !uniMode;
				setDisplayItems();
			}

		});
		upsuToggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!upsuMode) {
					mapController.setZoom(15);
					mapController.animateTo(plymouth);
				}
				upsuMode = !upsuMode;
				setDisplayItems();
			}

		});
	}

	private void setDisplayItems() {
		if ((uniMode) && (!mapOverlays.contains(uniOverlays))) {
			mapOverlays.add(uniOverlays);
		} else if ((!uniMode) && (mapOverlays.contains(uniOverlays))) {
			mapOverlays.remove(uniOverlays);
		}
		if ((upsuMode) && (!mapOverlays.contains(upsuOverlays))) {
			mapOverlays.add(upsuOverlays);
		} else if ((!upsuMode) && (mapOverlays.contains(upsuOverlays))) {
			mapOverlays.remove(upsuOverlays);
		}

		if ((uniMode) && (upsuMode)) {
			uniToggle.setImageResource(R.drawable.btn_uni_logo);
			upsuToggle.setImageResource(R.drawable.btn_upsu_logo);

		} else if ((uniMode) && (!upsuMode)) {
			uniToggle.setImageResource(R.drawable.btn_uni_logo);
			upsuToggle.setImageResource(R.drawable.btn_upsu_logo_faded);
			mapController.setZoom(17);
			mapController.animateTo(plymouthUni);
		} else if ((!uniMode) && (upsuMode)) {
			uniToggle.setImageResource(R.drawable.btn_uni_logo_faded);
			upsuToggle.setImageResource(R.drawable.btn_upsu_logo);
			mapController.setZoom(15);
			mapController.animateTo(plymouth);
		} else {
			uniToggle.setImageResource(R.drawable.btn_uni_logo_faded);
			upsuToggle.setImageResource(R.drawable.btn_upsu_logo_faded);

		}

		mapView.invalidate();
	}

	private void setUpMap() {
		mapView.setBuiltInZoomControls(true);
		mapView.setClickable(false);
		mapController = mapView.getController();
		mapController.setZoom(15);
		mapController.animateTo(plymouth);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());

		mapOverlays = mapView.getOverlays();

		try {
			new getBuildingOverlays().execute(new URL(Uni.SERVER_ADDRESS
					+ "buildings.txt"));
			new getVenuesOverlays().execute(new URL(Uni.SERVER_ADDRESS
					+ "venues.txt"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private class getBuildingOverlays extends AsyncTask<URL, Integer, Boolean> {

		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		protected Boolean doInBackground(URL... urls) {
			HTTPClient client = new HTTPClient(mContext);

			try {
				JSONObject momo = client.getJSON(urls[0].toString());

				JSONArray markers = momo.getJSONArray("building_names");

				for (int i = 0; i < markers.length(); i++) {
					JSONObject marker = markers.getJSONObject(i);
					GeoPoint point = new GeoPoint(
							(int) (marker.getDouble("lat") * 1e6),
							(int) (marker.getDouble("long") * 1e6));
					CustomOverlayItem overlayitem = new CustomOverlayItem(
							point, marker.getString("building_name"),
							marker.getString("building_does"), marker
									.getJSONArray("img").getJSONObject(0)
									.getString("url"), "building");
					uniOverlays.addOverlay(overlayitem);
				}

				return true;

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}

		protected void onPostExecute(Boolean successful) {
			if (successful) {
				mapView.invalidate();
				mapView.setClickable(true);
				setProgressBarIndeterminateVisibility(false);
			} else {
				Log.e("UoP", "ERROR :(");
				// TODO: Create an error message when it fails
			}
		}
	}

	private class getVenuesOverlays extends AsyncTask<URL, Integer, Boolean> {

		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}

		protected Boolean doInBackground(URL... urls) {
			HTTPClient client = new HTTPClient(mContext);

			try {
				JSONObject momo = client.getJSON(urls[0].toString());

				JSONArray markers = momo.getJSONArray("building_names");

				for (int i = 0; i < markers.length(); i++) {
					JSONObject marker = markers.getJSONObject(i);
					GeoPoint point = new GeoPoint(
							(int) (marker.getDouble("lat") * 1e6),
							(int) (marker.getDouble("long") * 1e6));
					CustomOverlayItem overlayitem = new CustomOverlayItem(
							point, marker.getString("building_name"),
							marker.getString("building_does"), marker
									.getJSONArray("img").getJSONObject(0)
									.getString("url"), "venue");
					upsuOverlays.addOverlay(overlayitem);
				}

				return true;

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
		}

		protected void onPostExecute(Boolean successful) {
			if (successful) {
				mapView.invalidate();
				mapView.setClickable(true);
				setProgressBarIndeterminateVisibility(false);
			} else {
				Log.e("UoP", "ERROR :(");
				// TODO: Create an error message when it fails
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.go_buildings:
			i = new Intent(mContext, Buildings.class);
			i.putExtra("uniMode", true);
			i.putExtra("upsuMode", true);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in_twice,
					R.anim.slide_left_out_twice);
			return true;
		case R.id.go_uni:
			i = new Intent(mContext, Uni.class);
			i.putExtra("uniMode", true);
			i.putExtra("upsuMode", true);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.slide_up_left_in,
					R.anim.slide_up_left_out);
			return true;
		case R.id.go_home:
			i = new Intent(mContext, Dashboard.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
			return true;
		case R.id.go_upsu:
			i = new Intent(mContext, UPSU.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.slide_up_right_in,
					R.anim.slide_up_right_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return super.onContextItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		if (callingActivity != null) {
			if (callingActivity.contentEquals("uni")) {
				overridePendingTransition(R.anim.slide_up_left_in,
						R.anim.slide_up_left_out);
			} else if (callingActivity.contentEquals("upsu")) {
				overridePendingTransition(R.anim.slide_up_right_in,
						R.anim.slide_up_right_out);
			}
		} else {
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
		}
	}

	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
