package com.example.sample;



import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class Calendars extends ListActivity {
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
	
	String[] projection = new String[] { CalendarContract.Calendars._ID,CalendarContract.Calendars.NAME,CalendarContract.Calendars.VISIBLE};
	     
	Cursor managedCursor =
	  managedQuery(CalendarContract.Calendars.CONTENT_URI, projection, null , null, null);

	String[] cal=new String[managedCursor.getCount()];
	if (managedCursor.moveToFirst()) {
		 String calName; 
		 String calId;
		 String calvisible;
		 int nameColumn = managedCursor.getColumnIndex("NAME"); 
		 int idColumn = managedCursor.getColumnIndex("_ID");
		 int visibleColumn = managedCursor.getColumnIndex("VISIBLE");
		 int i=0;
		 do {
		    calName = managedCursor.getString(nameColumn);
		    calId = managedCursor.getString(idColumn);
		    calvisible = managedCursor.getString(visibleColumn);
		    
		    cal[i]=calName;
		   
		   i++;
		 } while (managedCursor.moveToNext());
		
		}
	 ListAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice, cal);
	 
	 setListAdapter(adapter);
   
    
    
  }
} 