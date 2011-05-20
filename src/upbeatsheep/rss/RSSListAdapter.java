package upbeatsheep.rss;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import upbeatsheep.upsu.uapp.R;
import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class RSSListAdapter extends ArrayAdapter<JSONObject> {

	public RSSListAdapter(Context context, List<JSONObject> jobs) {
		super(context, 0, jobs);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate the views from XML
		View rowView = inflater.inflate(R.layout.image_text_layout, null);
		JSONObject jsonImageText = getItem(position);
		
		//The next section we update the text at runtime (as provided by the JSON from our REST call)
		TextView textView = (TextView) rowView.findViewById(R.id.job_text);
		
		try {
			Spanned text = (Spanned)jsonImageText.get("text");
			textView.setText(text);

		} catch (JSONException e) {
			textView.setText("JSON Exception");
		}

		return rowView;

	} 
	
}
