package upbeatsheep.upsu.uapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UniInfo extends Activity {

	private Button buildingsButton;
	private TextView uniInfo;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uni_info);

		mContext = this;

		initialiseWidgets();

		uniInfo.setText("The University of Plymouth is the largest university in the South West of England, with over 30,000 students and is 9th largest in the United Kingdom by total number of students (including the Open University).[5] It has almost 3,000 staff (one of the largest employers in the southwest). The main campus is in the Devon city of Plymouth, but the university has campuses and affiliated colleges all over South West England.");
		// TODO: Some sort of dynamic loading of uni info

		setUpOnClickListeners();
	}

	private void initialiseWidgets() {
		buildingsButton = (Button) findViewById(R.id.button1);
		uniInfo = (TextView) findViewById(R.id.uni_info);
	}

	private void setUpOnClickListeners() {

		buildingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, Buildings.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.slide_down_in,
						R.anim.slide_down_out);
			}
		});
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
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
}
