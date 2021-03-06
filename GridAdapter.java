package com.example.demo;
import android.content.Context;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<EventObjects> allEvents;
    Date color_date;
    Context cos;
    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<EventObjects> allEvents,Date color) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        mInflater = LayoutInflater.from(context);
        cos=context;
        color_date=color;
    }
    @NonNull
    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        Calendar today_date=Calendar.getInstance();
        int toYear=today_date.get(Calendar.YEAR);
        int toMonth=today_date.get(Calendar.MONTH)+1;
        int toDay=today_date.get(Calendar.DATE);
        int colorday=color_date.getDate();
        int colorMonth=color_date.getMonth()+1;
        int coloryear=color_date.getYear();
        View view = convertView;




        if(view == null){
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);

            view.setBackgroundColor(Color.parseColor("#ffffff"));

        }
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        if(displayMonth == currentMonth && displayYear == currentYear){
            cellNumber.setTextColor(Color.BLACK);
            cellNumber.setTag(0);



        }else{

            if(displayMonth > currentMonth && displayYear == currentYear || (displayMonth < currentMonth && displayYear > currentYear))
            {
                cellNumber.setTag(1);
            }
            else{
                cellNumber.setTag(2);
            }
            cellNumber.setTextColor(Color.LTGRAY);

        }
        //Add day to calendar
        //TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));


        if(displayMonth == toMonth && displayYear == toYear&&dayValue==toDay){
            view.setBackgroundResource(R.drawable.today);
            cellNumber.setTag(-1);
        }
        if(displayMonth == colorMonth&&colorday==dayValue)
        {
            view.setBackgroundResource(R.drawable.select_date);
            cellNumber.setTextColor(Color.WHITE);
        }

            view.setTag(position);


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });



        //Add events to the calendar
        TextView eventIndicator = (TextView)view.findViewById(R.id.event_id);
        Calendar eventCalendar = Calendar.getInstance();
        for(int i = 0; i < allEvents.size(); i++){
            eventCalendar.setTime(allEvents.get(i).getDate());
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
            }
        }
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}
