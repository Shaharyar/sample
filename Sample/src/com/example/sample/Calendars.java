package com.example.sample;



import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

public class Calendars extends ListActivity {
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
	
	String visibleID;
	String[] projection = new String[] { "_id", "name","visible"};
	Uri calendars = Uri.parse("content://com.android.calendar/calendars");
	     
	Cursor managedCursor =
	  managedQuery(calendars, projection, null, null, null);
	String[] cal=new String[managedCursor.getCount()];
	if (managedCursor.moveToFirst()) {
		 String calName; 
		 String calId;
		 String calvisible;
		 int nameColumn = managedCursor.getColumnIndex("name"); 
		 int idColumn = managedCursor.getColumnIndex("_id");
		 int visibleColumn = managedCursor.getColumnIndex("visible");
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