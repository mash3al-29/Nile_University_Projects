package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Variables
    private Stopwatch mainStopwatch = new Stopwatch();
    private Stopwatch lapStopwatch = new Stopwatch();
    private boolean isPlay = false;
    private ArrayList<String> laps = new ArrayList<>();

    // UI Components
    private TextView timerLabel;
    private TextView lapTimerLabel;
    private Button playPauseButton;
    private Button lapResetButton;
    private ListView lapsListView;
    private LapsAdapter lapsAdapter;

    // Handler for updating the timer
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        timerLabel = findViewById(R.id.timerLabel);
        lapTimerLabel = findViewById(R.id.lapTimerLabel);
        playPauseButton = findViewById(R.id.playPauseButton);
        lapResetButton = findViewById(R.id.lapResetButton);
        lapsListView = findViewById(R.id.lapsListView);

        lapsAdapter = new LapsAdapter(this, laps);
        lapsListView.setAdapter(lapsAdapter);

        lapResetButton.setEnabled(false);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseTimer();
            }
        });

        lapResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lapResetTimer();
            }
        });
    }

    private void playPauseTimer() {
        lapResetButton.setEnabled(true);

        changeButton(lapResetButton, "Lap", android.graphics.Color.BLACK);

        if (!isPlay) {
            handler.postDelayed(updateMainTimerRunnable, 35);
            handler.postDelayed(updateLapTimerRunnable, 35);
            isPlay = true;
            changeButton(playPauseButton, "Stop", android.graphics.Color.RED);
        } else {
            handler.removeCallbacks(updateMainTimerRunnable);
            handler.removeCallbacks(updateLapTimerRunnable);
            isPlay = false;
            changeButton(playPauseButton, "Start", android.graphics.Color.GREEN);
            changeButton(lapResetButton, "Reset", android.graphics.Color.BLACK);
        }
    }

    private void lapResetTimer() {
        if (!isPlay) {
            resetMainTimer();
            resetLapTimer();
            changeButton(lapResetButton, "Lap", android.graphics.Color.LTGRAY);
            lapResetButton.setEnabled(false);
        } else {
            laps.add(timerLabel.getText().toString());
            lapsAdapter.notifyDataSetChanged();
            resetLapTimer();
            handler.postDelayed(updateLapTimerRunnable, 35);
        }
    }

    private void changeButton(Button button, String title, int color) {
        button.setText(title);
        button.setTextColor(color);
    }

    private void resetMainTimer() {
        resetTimer(mainStopwatch, timerLabel);
        laps.clear();
        lapsAdapter.notifyDataSetChanged();
    }

    private void resetLapTimer() {
        resetTimer(lapStopwatch, lapTimerLabel);
    }

    private void resetTimer(Stopwatch stopwatch, TextView label) {
        handler.removeCallbacks(updateMainTimerRunnable);
        handler.removeCallbacks(updateLapTimerRunnable);
        stopwatch.setCounter(0.0);
        label.setText("00:00:00");
    }

    private Runnable updateMainTimerRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimer(mainStopwatch, timerLabel);
            handler.postDelayed(this, 35);
        }
    };

    private Runnable updateLapTimerRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimer(lapStopwatch, lapTimerLabel);
            handler.postDelayed(this, 35);
        }
    };

    private void updateTimer(Stopwatch stopwatch, TextView label) {
        stopwatch.setCounter(stopwatch.getCounter() + 0.035);

        int minutes = (int) (stopwatch.getCounter() / 60);
        String minutesStr = minutes < 10 ? "0" + minutes : String.valueOf(minutes);

        double seconds = stopwatch.getCounter() % 60;
        String secondsStr = seconds < 10 ? "0" + String.format("%.2f", seconds) : String.format("%.2f", seconds);

        label.setText(minutesStr + ":" + secondsStr);
    }
}