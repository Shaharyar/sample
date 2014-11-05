package com.meetingmatch.main;

import com.example.sample.R;
import com.meetingmatch.Adapters.CalendarAdapter;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;


public class Calendars extends ListActivity {
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    Context context;

    CalendarQuery cq=new CalendarQuery();
	String[][] cal=(String[][]) cq.getAllCalendar(getContentResolver());
    
	setContentView(R.layout.list_view);
	 
    context=this;
    
    ListView lv=(ListView) findViewById(android.R.id.list);
    lv.setAdapter(new CalendarAdapter(this,cal,cal[0].length ));
  }
  

  
} 
