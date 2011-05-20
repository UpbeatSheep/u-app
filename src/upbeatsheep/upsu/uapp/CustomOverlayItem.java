package upbeatsheep.upsu.uapp;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CustomOverlayItem extends OverlayItem {

	private String url;
	private String type;

	public CustomOverlayItem(GeoPoint point, String title, String snippet,
			String url, String type) {
		super(point, title, snippet);
		this.setUrl(url);
		this.setType(type);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
