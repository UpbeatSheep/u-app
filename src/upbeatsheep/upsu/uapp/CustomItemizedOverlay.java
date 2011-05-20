package upbeatsheep.upsu.uapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay {

	private ArrayList<CustomOverlayItem> mOverlays = new ArrayList<CustomOverlayItem>();
	Context mContext;

	public CustomItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	@Override
	protected boolean onTap(int index) {
		final CustomOverlayItem item = mOverlays.get(index);

		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setItems(new String[] { "More Information..." },
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i;
						if (item.getType() == "building") {
							i = new Intent(mContext, Building.class);
						} else {
							i = new Intent(mContext, Venue.class);
						}
						i.putExtra("name", item.getTitle());
						i.putExtra("desc", item.getSnippet());
						i.putExtra("url", item.getUrl());

						mContext.startActivity(i);

					}
				});
		dialog.show();
		return true;
	}

	public CustomItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;

	}

	public void addOverlay(CustomOverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
