package com.example.administrator.mycalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mycalendar.bean.DateBean;
import com.example.administrator.mycalendar.listener.OnMultiChooseListener;
import com.example.administrator.mycalendar.listener.OnPagerChangeListener;
import com.example.administrator.mycalendar.utils.CalendarUtil;
import com.example.administrator.mycalendar.weiget.CalendarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView chooseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choose);

        final TextView title = (TextView) findViewById(R.id.title);

        final StringBuilder sb = new StringBuilder();

        chooseDate = (TextView) findViewById(R.id.choose_date);

        List<String> list = new ArrayList<>();
        list.add("2017.11.11");
        list.add("2017.11.12");
        list.add("2017.12.22");
        list.add("2017.12.25");

        calendarView = (CalendarView) findViewById(R.id.calendar);
        int[] currentDate = CalendarUtil.getCurrentDate();
        calendarView
                .setStartEndDate("2015.1", "2019.12")
                .setDisableStartEndDate("2017.10.7", "2019.10.7")
                .setInitDate(currentDate[0]+"."+currentDate[1])
                .setMultiDate(list)
                .init();

        title.setText(currentDate[0] + "年" + currentDate[1] + "月");

        for (String d : list) {
            sb.append("选中：" + d + "\n");
        }
        chooseDate.setText(sb.toString());

        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean date, boolean flag) {
                String d = date.getSolar()[0] + "." + date.getSolar()[1] + "." + date.getSolar()[2] + ".";
                if (flag) {//选中
                    sb.append("选中：" + d + "\n");
                } else {//取消选中
                    sb.append("取消：" + d + "\n");
                }
                chooseDate.setText(sb.toString());

                //test
                if (flag) {
                    for (DateBean db : calendarView.getMultiDate()) {
                        Log.e("date:", "" + db.getSolar()[0] + db.getSolar()[1] + db.getSolar()[2]);
                    }
                }
            }
        });

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
    }

    public void lastMonth(View view) {
        calendarView.lastMonth();
    }

    public void nextMonth(View view) {
//        calendarView.nextMonth();
//        q签到
        calendarView.addChooseDate(CalendarUtil.getStringCurrentDate());
    }

}
