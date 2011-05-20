package upbeatsheep.rss;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
import android.util.Log;

public class RssReader {
	private final static String BOLD_OPEN = "<B>";
	private final static String BOLD_CLOSE = "</B>";
	private final static String BREAK = "<BR>";
	private final static String ITALIC_OPEN = "<I>";
	private final static String ITALIC_CLOSE = "</I>";
	private final static String SMALL_OPEN = "<SMALL>";
	private final static String SMALL_CLOSE = "</SMALL>";
	private final static String HREF_OPEN = "<a href=\"";
	private final static String HREF_MID = "\"/>";
	private final static String HREF_CLOSE = "</a>";

	/**
	 * This method defines a feed URL and then calls our SAX Handler to read the article list
	 * from the stream
	 * 
	 * @return List<JSONObject> - suitable for the List View activity
	 */
	public static List<JSONObject> getLatestRssFeed(String[] addURLs){
		
		//Grab the default feeds
		RSSHandler rh = new RSSHandler();
		List<Article> articles = rh.getLatestArticles(addURLs[0]);
		Log.e("RSS ERROR", "Number of articles " + articles.size());
		
		//Now go through all the additional RSS URLs and import their content	
		for(int i=1; i<addURLs.length; i++)
		{
			articles.addAll(rh.getLatestArticles(addURLs[i]));
		}
		
		//output all data
		return fillData(articles);
	}
	
	
	/**
	 * This method takes a list of Article objects and converts them in to the 
	 * correct JSON format so the info can be processed by our list view
	 * 
	 * @param articles - list<Article>
	 * @return List<JSONObject> - suitable for the List View activity
	 */
	private static List<JSONObject> fillData(List<Article> articles) {

        List<JSONObject> items = new ArrayList<JSONObject>();
        for (Article article : articles) {
            JSONObject current = new JSONObject();
            try {
            	buildJsonObject(article, current);
			} catch (JSONException e) {
				Log.e("RSS ERROR", "Error creating JSON Object from RSS feed");
			}
			items.add(current);
        }
        Log.i("UPSU Android","Help: " + items);
        return items;
	}


	/**
	 * This method takes a single Article Object and converts it in to a single JSON object
	 * including some additional HTML formating so they can be displayed nicely
	 * 
	 * @param article
	 * @param current
	 * @throws JSONException
	 */
	private static void buildJsonObject(Article article, JSONObject current) throws JSONException {
		String title = article.getTitle();
		String description = article.getDescription();
		String date = article.getPubDate();
		String link = article.getUrl().toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append(HREF_OPEN).append(link).append(HREF_MID).append(BOLD_OPEN).append(title).append(BOLD_CLOSE).append(HREF_CLOSE);
		if (description != ""){
		sb.append(BREAK);
		sb.append(description);
		sb.append(BREAK);
		sb.append(SMALL_OPEN).append(ITALIC_OPEN).append(date).append(ITALIC_CLOSE).append(SMALL_CLOSE);
		}
		
		current.put("text", Html.fromHtml(sb.toString()));
		current.put("title", title);
		current.put("desc", description);
		current.put("date", date);
		current.put("link", link);
	}
}
