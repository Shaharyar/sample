package com.meetingmatch.main;



import java.util.Calendar;

import com.example.sample.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

 
public class MainActivity extends Activity {
	
	private RelativeLayout rl;
	private CalendarView cal;
	private Long selected_date;
	private int day,month,year,hour,minute,am_pm;
	

	@SuppressLint({ "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rl = (RelativeLayout) findViewById(R.id.rl);
        
        cal = new CalendarView(MainActivity.this);
   
        Calendar c = Calendar.getInstance(); 
        
        day = c.get(Calendar.DATE);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hour=c.get(Calendar.HOUR);
        minute=c.get(Calendar.MINUTE);
        am_pm=c.get(Calendar.AM_PM);
        



        
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
			month = m;
			year= y;
			
		
		}
	});
    }
    //called by menu item with id create_event
    public void showEventDialog(CalendarView view, int year, int month,
			int dayOfMonth,int hour, int minute, int am_pm)
	{
		Long selected_date=view.getDate();
		
		DialogFragment eventFragment = new CreateEventDialog();
		Bundle args=new Bundle();
		args.putLong("selected_date", selected_date);
		args.putInt("day", dayOfMonth);
		args.putInt("month", ++month);
		args.putInt("year", year);
		args.putInt("hour", hour);
		args.putInt("minute", minute);
		args.putInt("am_pm", am_pm);
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

    	  this.showEventDialog(cal,year,month,day,hour,minute,am_pm);
        break;
        
      //menu item with id action_calendars is selected  
      case R.id.action_calendars:
    	  Intent calendar_intent = new Intent(this, Calendars.class);
    	  startActivity(calendar_intent);
        break;
      case R.id.views_day:
    	  Intent day_intent = new Intent(this, DayView.class);
    	  day_intent.putExtra("day", day);
    	  day_intent.putExtra("month", month);
    	  day_intent.putExtra("year", year);
    	  
    	  startActivity(day_intent);
        break; 
      case R.id.views_month:
    	  Intent month_intent = new Intent(this, MonthView.class);
    	  month_intent.putExtra("day", day);
    	  month_intent.putExtra("month", month);
    	  month_intent.putExtra("year", year);
    	  
    	  startActivity(month_intent);
        break; 
      case R.id.views_year:
    	  Intent year_intent = new Intent(this, YearView.class);
    	  year_intent.putExtra("day", day);
    	  year_intent.putExtra("month", month);
    	  year_intent.putExtra("year", year);
    	  
    	  startActivity(year_intent);
        break; 
      case R.id.views_agenda:
    	  Intent agenda_intent = new Intent(this, Agenda.class);
    	  startActivity(agenda_intent);
        break; 
      default:
        break;
      }

      return true;
    } 
}