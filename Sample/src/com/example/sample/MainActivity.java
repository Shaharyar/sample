package com.example.sample;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
 
public class MainActivity extends Activity {
	
	RelativeLayout rl;
	CalendarView cal;
	Long selected_date;
	int day;
	int month;
	int year;
    @SuppressLint({ "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rl = (RelativeLayout) findViewById(R.id.rl);
        
        cal = new CalendarView(MainActivity.this);
   
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
        		((int) LayoutParams.MATCH_PARENT, (int) LayoutParams.MATCH_PARENT);
        
        params.topMargin = 20;
        cal.setLayoutParams(params);
        
        rl.addView(cal);
        
        
      //on dateChange listener
      cal.setOnDateChangeListener(new OnDateChangeListener() {
			
		@Override
		public void onSelectedDayChange(CalendarView view, int y, int m,
				int dayOfMonth) {
			selected_date=view.getDate();
			day= dayOfMonth;
			month = ++m;
			year= y;
		
		}
	});
    }
    //called by menu item with id create_event
    public void showEventDialog(CalendarView view, int year, int month,
			int dayOfMonth)
	{
		Long selected_date=view.getDate();
		
		DialogFragment eventFragment = new CreateEventDialog();
		Bundle args=new Bundle();
		args.putLong("selected_date", selected_date);
		args.putInt("day", dayOfMonth);
		args.putInt("month", ++month);
		args.putInt("year", year);
		eventFragment.setArguments(args);
	    eventFragment.show(getFragmentManager(), null);
	}
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      //menu item with id create_event is selected
      case R.id.create_event:

    	  this.showEventDialog(cal,year,month,day);
        break;
        
      //menu item with id action_calendars is selected  
      case R.id.action_calendars:
    	  Intent i = new Intent(this, Calendars.class);
    	  startActivity(i);
        break;
        
      default:
        break;
      }

      return true;
    } 
}