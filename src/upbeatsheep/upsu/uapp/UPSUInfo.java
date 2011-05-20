package upbeatsheep.upsu.uapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UPSUInfo extends Activity {

	private Button upsuButton;
	private TextView uniInfo;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upsu_info);
		
        mContext = this;
        
        initialiseWidgets();
        
uniInfo.setText("The University of Plymouth's Student Union (UPSU) is located in the Drake Circus area of Plymouth, within the University of Plymouth campus.It is one of the largest student unions in the UK (third largest in the UK, according to some reports) hosting a wide array of events and offering services to all students. The University of Plymouth Students' Union is run by students for students. The Executive Officers or 'Sabbatical Officers' are elected in February and are the leaders of the UPSU, deciding on how UPSU runs day to day. The team consists of 5 Full Time officers that have recently graduated as students from the University, and collectively make up the Union Executive Committee (UEC). Each of them have their own remits, for example Campaigns, Media, Partner Colleges and Sports. They also provide academic and national representation for Plymouth students. The main building of the union has many different areas. The building is designed to be 'underground' and has many different rooms and services available to students. Inside, there is a small bar known as 'The Snug' with sofas and 3D televisions, becoming one of the few venues in Plymouth offering 3D sports coverage. Across from the bar is a 'the Lounge', where students can either sit on tables provided or sofas; also a fast-food bar, known as 'the snack shack', can be found in this area for students and can offer eat in as well as take away food. Across from the small bar area is a room with a dance floor and pool tables, sometimes used for private events. The UPSU have entitled this room 'Illusion'. Down from Illusion, the main bar can be found, known as 'Sub:Lime', this is where most of the union's events take place. In this area is a dance floor equipped with DJ booth and projectors.Attached to the main building on the 1st floor is the advice centre. The advice centre offers free, confidential advice to UPSU's members and can offer a broad range of advice, including financial, as well as offering free pregnancy test kits and academic advice. The advice centre is open weekdays between 10am and 3pm.");
//TODO: Some sort of dynamic loading of uni info
        
        setUpOnClickListeners();
	}
	
	 private void initialiseWidgets(){
	    	upsuButton = (Button) findViewById(R.id.button1);
	    	uniInfo = (TextView) findViewById(R.id.uni_info);
	    }
	    
	    private void setUpOnClickListeners(){
	    	
	    	upsuButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://www.upsu.com"));
					startActivity(i);
				}});
	    }
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id, args);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_down_in,R.anim.slide_down_out); 
	}
}
