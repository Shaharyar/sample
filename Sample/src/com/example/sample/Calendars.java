package com.example.sample;



import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class Calendars extends ListActivity {
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    CalendarQuery cq=new CalendarQuery();
	String[] cal=(String[]) cq.getAllCalendar(getContentResolver());
    
	ListAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice, cal);
	 
	setListAdapter(adapter);
   
    
    
  }
} 