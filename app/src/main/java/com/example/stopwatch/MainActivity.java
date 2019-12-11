package com.example.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    Button start, berhenti, reset, lap;

    long MilisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    Handler handler;

    int seconds, minutes, milliseconds;

    ListView listView;

    String[] ListElements = new String[]{ };

    List<String> ListElementsArrayList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        start = findViewById(R.id.button);
        berhenti = findViewById(R.id.button2);
        reset = findViewById(R.id.button3);
        lap = findViewById(R.id.button4);
        listView = findViewById(R.id.listview1);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        berhenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MilisecondTime;
                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MilisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                seconds = 0 ;
                minutes = 0 ;
                milliseconds = 0 ;

                textView.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();

            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            MilisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MilisecondTime;

            seconds = (int) (UpdateTime / 1000);

            minutes = seconds / 60;

            seconds = seconds % 60;

            milliseconds = (int) (UpdateTime % 1000);

            textView.setText("" + minutes + ":" + String.format("%02d", seconds) + ":" + String.format("%02d", milliseconds));

            handler.postDelayed(this, 0);

        }
    };
}

