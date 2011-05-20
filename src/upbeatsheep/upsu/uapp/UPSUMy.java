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

public class UPSUMy extends Activity {

	private Button venuesButton;
	private Button eventsButton;
	private Button sportsButton;
	private Button socButton;
	
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upsu_my);
		
        mContext = this;
        
        initialiseWidgets();
        

//TODO: Some sort of dynamic loading of uni info
        
        setUpOnClickListeners();
	}
	
	 private void initialiseWidgets(){
		 venuesButton = (Button) findViewById(R.id.btn_ven);
		 		
		        sportsButton = (Button) findViewById(R.id.btn_spo);
		 		
		      socButton = (Button) findViewById(R.id.btn_soc);
		 	
		     eventsButton = (Button) findViewById(R.id.btn_eve);
	    	
	    }
	    
	    private void setUpOnClickListeners(){
	    	venuesButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(mContext, Venues.class);
					
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.slide_down_in,R.anim.slide_down_out); 
				}});
	    	
	    	eventsButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(mContext, UPSUE.class);
					
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out); 
				}});
	    	
	    	sportsButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://upsu.com/sports/content/122175/welcome_to_upsu_sports/"));
					startActivity(i);
				}});
	    	socButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://upsu.com/societies"));
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
		overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out); 
	}
}
