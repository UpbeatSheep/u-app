package upbeatsheep.upsu.uapp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JSONListAdapter extends ArrayAdapter<JSONObject> {

	HTTPClient client;
	Context mContext;

	public JSONListAdapter(Context context, List<JSONObject> objects) {
		super(context, 0, objects);
		mContext = context;
		client = new HTTPClient(mContext);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		View rowView = inflater.inflate(R.layout.listview_item, null);
		JSONObject place = getItem(position);

		ImageView rowImage = (ImageView) rowView.findViewById(R.id.image);
		TextView rowText = (TextView) rowView.findViewById(R.id.text);

		try {
			String text = place.getString("building_name");
			rowText.setText(text);
			client.fetchBitmapOnThread(
					place.getJSONArray("img").getJSONObject(0).getString("url"),
					rowImage);

		} catch (JSONException e) {
			rowText.setText(e.getMessage());
		}

		return rowView;
	}

}
