package com.meetingmatch.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.example.sample.R;
import com.meetingmatch.contact.Contacts;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateEventDialog extends DialogFragment  {
	private AlertDialog.Builder  builder;
	private Spinner cal_spinner;
	private Long selected_date;
	private int day,month,year,hour,minute, am_pm;
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
	private ArrayList<String> email_list=new ArrayList<String>();
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getAm_pm() {
		return am_pm;
	}

	public void setAm_pm(int am_pm) {
		this.am_pm =am_pm;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		selected_date=this.getArguments().getLong("selected_date");
		day=this.getArguments().getInt("day");
		month=this.getArguments().getInt("month");
		year=this.getArguments().getInt("year");
		hour=this.getArguments().getInt("hour");
		minute=this.getArguments().getInt("minute");
		am_pm=this.getArguments().getInt("am_pm");

		Calendar calendar=Calendar.getInstance();
		final Date date = calendar.getTime();
	
		// Use the Builder class for convenient dialog construction
		builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("New Event");

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view=(inflater.inflate(R.layout.event_form, null));
		builder.setView(view)    
		.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// FIRE ZE MISSILES!
				CreateEvent ce=new CreateEvent();
				ce.setData(getActivity(),view);
					
				ce.addCalendarEvent(email_list);
				
				Toast.makeText(getActivity(), "Event Created Successfully", Toast.LENGTH_LONG).show();

			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}

		});

		//Calendar spinner
		CalendarQuery cq=new CalendarQuery();
		String[] cal=(String[]) cq.getAllCalendar(getActivity().getContentResolver());

		cal_spinner = (Spinner)view.findViewById(R.id.calendar);

		ArrayAdapter cal_adapter = new ArrayAdapter(
				getActivity(), android.R.layout.simple_spinner_item, cal);

		cal_spinner.setAdapter(cal_adapter);
		//End of calendar spinner	

		//from date spinner
		Button from_date = (Button) view.findViewById(R.id.from_date);
		MonthConversion mc_from_date=new MonthConversion();
		from_date.setText(String.valueOf(mc_from_date.get_month_string(month))+", "+String.valueOf(day)+" "+String.valueOf(year));
		final EditText from_day=(EditText) view.findViewById(R.id.from_day);
		final EditText from_month=(EditText) view.findViewById(R.id.from_month);
		final EditText from_year=(EditText) view.findViewById(R.id.from_year);
		from_day.setText(Integer.toString(getDay()));
		from_month.setText(Integer.toString(getMonth()-1));
		from_year.setText(Integer.toString(getYear()));
		
		from_date.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialogFragment dpdf=new DatePickerDialogFragment();
				dpdf.setId("from_date");
				setDay(Integer.parseInt(from_day.getText().toString()));
				setMonth(Integer.parseInt(from_month.getText().toString()));
				setYear(Integer.parseInt(from_year.getText().toString()));
				dpdf.showDatePickerDialogFragment(getActivity(),day,month+1,year,view);
			}	    	
		});
		//End of from date spinner

		//from time spinner
		Button from_time = (Button) view.findViewById(R.id.from_time);
		final EditText from_hour=(EditText) view.findViewById(R.id.from_hour);
		final EditText from_minute=(EditText) view.findViewById(R.id.from_minute);
		final EditText from_am_pm=(EditText) view.findViewById(R.id.from_am_pm);

		if(am_pm==0){
			from_time.setText(hour+":"+minute+" AM");	
			from_hour.setText(Integer.toString(getHour()));
			from_minute.setText(Integer.toString(getMinute()));
			from_am_pm.setText("AM");
			//Toast.makeText(getActivity(),  "sample"+from_am_pm.getText().toString().trim(), Toast.LENGTH_SHORT).show();

		}
		else if(am_pm==1){
			from_time.setText((hour)+":"+minute+" PM");	
			from_hour.setText(Integer.toString(getHour()));
			from_minute.setText(Integer.toString(getMinute()));
			from_am_pm.setText("PM");
		}
		from_time.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialogFragment tpdf=new TimePickerDialogFragment();
				tpdf.setId("from_time");
				setMinute(Integer.parseInt(from_minute.getText().toString()));
				if(from_am_pm.getText().toString()=="AM"){
					setHour(Integer.parseInt(from_hour.getText().toString()));
					setAm_pm(0);
				}
				else if(from_am_pm.getText().toString()=="PM"){
					setHour(Integer.parseInt(from_hour.getText().toString()+12));
					setAm_pm(1);
				}
				tpdf.showTimePickerDialogFragment(getActivity(),hour,minute,am_pm,view);
			}	    	
		});
		//End of from time spinner

		//to date spinner
		Button to_date = (Button) view.findViewById(R.id.to_date);
		MonthConversion mc_to_date=new MonthConversion();
		to_date.setText(String.valueOf(mc_to_date.get_month_string(month))+", "+String.valueOf(day)+" "+String.valueOf(year));
		final EditText to_day=(EditText) view.findViewById(R.id.to_day);
		final EditText to_month=(EditText) view.findViewById(R.id.to_month);
		final EditText to_year=(EditText) view.findViewById(R.id.to_year);
		to_day.setText(Integer.toString(getDay()));
		to_month.setText(Integer.toString(getMonth()-1));
		to_year.setText(Integer.toString(getYear()));
		to_date.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialogFragment dpdf=new DatePickerDialogFragment();
				dpdf.setId("to_date");
				
				setDay(Integer.parseInt(to_day.getText().toString()));
				setMonth(Integer.parseInt(to_month.getText().toString()));

				setYear(Integer.parseInt(to_year.getText().toString()));

				dpdf.showDatePickerDialogFragment(getActivity(),day,month+1,year,view);
			}	    	
		});
		//End of to date spinner

		//to time spinner
		Button to_time = (Button) view.findViewById(R.id.to_time);
		final EditText to_hour=(EditText) view.findViewById(R.id.to_hour);
		final EditText to_minute=(EditText) view.findViewById(R.id.to_minute);
		final EditText to_am_pm=(EditText) view.findViewById(R.id.to_am_pm);
		
		
		if(am_pm==0){
			to_time.setText(hour+":"+minute+" AM");	
			to_hour.setText(Integer.toString(getHour()));
			to_minute.setText(Integer.toString(getMinute()));
			to_am_pm.setText("AM");
		}
		else if(am_pm==1){
			to_time.setText((hour)+":"+minute+" PM");	
			to_hour.setText(Integer.toString(getHour()));
			to_minute.setText(Integer.toString(getMinute()));
			from_am_pm.setText("PM");
		}
		to_time.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimePickerDialogFragment tpdf=new TimePickerDialogFragment();
				tpdf.setId("to_time");
				
				setMinute(Integer.parseInt(to_minute.getText().toString()));
				if(to_am_pm.getText().toString()=="AM"){
					setHour(Integer.parseInt(to_hour.getText().toString()));
					setAm_pm(0);
				}
				else if(to_am_pm.getText().toString()=="PM"){
					setHour(Integer.parseInt(to_hour.getText().toString()+12));
					setAm_pm(1);
				}
				tpdf.showTimePickerDialogFragment(getActivity(),hour,minute,am_pm,view);
			}	    	
		});
		//End of to time spinner
		
		//guests_listView
		final TextView guests_listView_txt=(TextView)view.findViewById(R.id.guests_listView);
		
		//end of guests_listView
		
		
		//guests
		final AutoCompleteTextView  guests_text=(AutoCompleteTextView )view.findViewById(R.id.guests);
		guests_text.addTextChangedListener(new TextWatcher() {

			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@SuppressLint("NewApi")
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(),  "on"+String.valueOf(s), Toast.LENGTH_SHORT).show();
				Contacts c=new Contacts();
				String[] contacts=new String[3];
				contacts=c.getContacts(view,String.valueOf(s));
				ArrayAdapter adapter = new ArrayAdapter(view.getContext().getApplicationContext(),android.R.layout.simple_list_item_1, contacts);	
				guests_text.setAdapter(adapter);
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			
			}
			
		});
		
		
		
		//end of guests

		
		//guests add_button
		Button guest_add_btn=(Button) view.findViewById(R.id.add_guests);
		guest_add_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(EMAIL_PATTERN.matcher(guests_text.getText().toString()).matches()){
					email_list.add(guests_text.getText().toString().trim());
					guests_listView_txt.append("\n"+guests_text.getText().toString().trim());
					guests_text.setText("");


				}
				else
					Toast.makeText(getActivity(),"Invalid email!",Toast.LENGTH_SHORT).show();

			}	
		});
		//end of guests add_button
		
		
		//show me as spinner
		Spinner show_me_as_spinner = (Spinner) view.findViewById(R.id.show_me_as); 
		// Create an ArrayAdapter using the string array and a default spinner layout 
		ArrayAdapter<CharSequence> show_me_as_adapter = ArrayAdapter.createFromResource(getActivity(),R.array.show_me_as, android.R.layout.simple_spinner_item); 
		// Specify the layout to use when the list of choices appears 
		show_me_as_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		// Apply the adapter to the spinner 

		show_me_as_spinner.setAdapter(show_me_as_adapter);
		//End of show me as spinner

		//privacy spinner
		Spinner privacy_spinner = (Spinner) view.findViewById(R.id.privacy); 
		// Create an ArrayAdapter using the string array and a default spinner layout 
		ArrayAdapter<CharSequence> privacy_adapter = ArrayAdapter.createFromResource(getActivity(),R.array.privacy, android.R.layout.simple_spinner_item); 
		// Specify the layout to use when the list of choices appears 
		privacy_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		// Apply the adapter to the spinner 

		privacy_spinner.setAdapter(privacy_adapter);
		//End of privacy spinner

		//reminder_time spinner
		Spinner reminder_time_spinner = (Spinner) view.findViewById(R.id.reminder_time); 
		// Create an ArrayAdapter using the string array and a default spinner layout 
		ArrayAdapter<CharSequence> reminder_time_adapter = ArrayAdapter.createFromResource(getActivity(),R.array.reminder_time, android.R.layout.simple_spinner_item); 
		// Specify the layout to use when the list of choices appears 
		reminder_time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		// Apply the adapter to the spinner 

		reminder_time_spinner.setAdapter(reminder_time_adapter);
		//End of reminder_time spinner

				
		//reminder_type spinner
		Spinner reminder_type_spinner = (Spinner) view.findViewById(R.id.reminder_type); 
		// Create an ArrayAdapter using the string array and a default spinner layout 
		ArrayAdapter<CharSequence> reminder_type_adapter = ArrayAdapter.createFromResource(getActivity(),R.array.reminder_type, android.R.layout.simple_spinner_item); 
		// Specify the layout to use when the list of choices appears 
		reminder_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		// Apply the adapter to the spinner 

		reminder_type_spinner.setAdapter(reminder_type_adapter);
		//End of reminder_type spinner
		
		

		// Create the AlertDialog object and return it
		return builder.create();
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


	}

}
