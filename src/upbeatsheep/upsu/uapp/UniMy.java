package upbeatsheep.upsu.uapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UniMy extends Activity {

	private Button tulipBtn;
	private Button webmailBtn;
	private Button timetableBtn;
	private Button lecturerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_uni);

		initialiseWidgets();

		setUpOnClickListeners();
	}

	private void initialiseWidgets() {
		tulipBtn = (Button) findViewById(R.id.btn_tulip);
		webmailBtn = (Button) findViewById(R.id.btn_webmail);
		timetableBtn = (Button) findViewById(R.id.btn_timetables);
		lecturerBtn = (Button) findViewById(R.id.btn_lecturers);
	}

	private void setUpOnClickListeners() {
		tulipBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://tulip.plymouth.ac.uk"));
				startActivity(i);
			}
		});
		webmailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://exchange.plymouth.ac.uk"));
				startActivity(i);
			}
		});
		timetableBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://timetables.plymouth.ac.uk"));
				startActivity(i);
			}
		});
		lecturerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse("http://mysite.plymouth.ac.uk"));
				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
		overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);
	}

}
