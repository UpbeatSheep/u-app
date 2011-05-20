package upbeatsheep.upsu.uapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Venue extends Activity {
	
	private TextView buildingName;
	private TextView buildingDesc;
	private ImageView buildingImage;
	private String name = "Building Name";
	private String desc = "Building Description";
	private String url;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.building);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			name = extras.getString("name");
			desc = extras.getString("desc");
			url = extras.getString("url");
		}
		
        initialiseWidgets();
        
        setUpOnClickListeners();
    }
    
    private void initialiseWidgets(){
    	buildingName = (TextView) findViewById(R.id.txt_building_name);
    	buildingName.setText(name);
    	buildingDesc = (TextView) findViewById(R.id.txt_building_desc);
    	buildingDesc.setText(desc);
    	buildingImage = (ImageView) findViewById(R.id.img_building_image);
    	HTTPClient client = new HTTPClient(this);
    	client.fetchBitmapOnThread(url, buildingImage);
    }
    
    private void setUpOnClickListeners(){
    	
	}
    
    @Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	
			overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
		}
	}


