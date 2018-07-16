package com.example.caucse.calendar;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class MainActivity extends Activity implements OnTimeChangedListener {

    CalendarView monthView;
    CalendarAdapter monthViewAdapter;

    TextView monthText;
    private final int DIALOG_CUSTOM_ID = 1;

    ListView lv;
    ArrayList<DayData> dayData;

    int curYear;
    int curMonth;
    int curDay;
    String txt ="";
    int curHour;
    int curMin;

    EditText et;
    Button save;

    ArrayAdapter<String> adapter;
    ArrayList<String> as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayData = new ArrayList<DayData>();

        ArrayList<String> ts = new ArrayList<String>();
        ts.add("안녕");
        ts.add("안녕");

        lv = (ListView)findViewById(R.id.listView);

        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        monthView.setOnDataSelectionListener(new OnDataSelectionListener(){
            public void onDataSelected(AdapterView parent, View v, int position, long id){
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                curDay = curItem.getDay();
                as = new ArrayList<String>();
                for(int i =0; i< dayData.size();i++){
                    if(dayData.get(i).getDay() ==curDay){
                        as.add(dayData.get(i).getScadule());
                    }
                }
                updateLv();
            }
        });

        monthText = (TextView)findViewById(R.id.monthText);
        setMonthText();

        Button monthPrevious = (Button)findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        Button monthNext = (Button)findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

    }

    public void updateLv(){
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,as);
        lv.setAdapter(adapter);
    }

    private void setMonthText(){
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년" + (curMonth+1) + "월");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int curId = item.getItemId();

        if(curId == R.id.action_settings){
            final DayData dd = new DayData(curYear, curMonth, curDay);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dia = View.inflate(this, R.layout.schedule_layout, null);
            builder.setTitle("일정추가");
            builder.setView(dia);
            save = (Button)dia.findViewById(R.id.button1);
            et = (EditText)dia.findViewById(R.id.editText1);
            final TimePicker tp = (TimePicker)dia.findViewById(R.id.timePicker1);
            tp.setOnTimeChangedListener(this);
            View.OnClickListener saveListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    txt = et.getText().toString();

                    dd.setTime(tp.getCurrentHour(), tp.getCurrentMinute());
                    dd.setString(txt);
                    dayData.add(dd);
                }
            };
            save.setOnClickListener(saveListener);
            builder.show();
        }
        return true;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        curHour = hourOfDay;
        curMin = minute;
    }
}
