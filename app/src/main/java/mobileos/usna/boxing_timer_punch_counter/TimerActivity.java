package mobileos.usna.boxing_timer_punch_counter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Author: Oue, Hitoshi P.
 * Description: This Class is responsible for the core functionality of the timer
 */

public class TimerActivity extends AppCompatActivity implements TimerListener, View.OnClickListener  {

    TextView timer, interval, rest, rounds;
    Button start, end;
    CountDownTimer clock;
    long mil, restMil, interMil;
    int numRounds;
    boolean lastRound, breakTime;
    MediaPlayer mp, mp2, mp3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //set the views
        setViews();

        //run the timer Dialog
        TimePickerDialog interval = new TimePickerDialog();
        interval.show(getSupportFragmentManager(), "set timer");




    }

    /**
     * this method initiates the view set in the timer_activity_XML
     * and sets onclick listeners on buttons
     */
    public void setViews(){
        timer = findViewById(R.id.timer);
        interval = findViewById(R.id.interval_time);
        rest = findViewById(R.id.rest_time);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end_timer);
        rounds = findViewById(R.id.rounds_value);


        //enable onclick listener for buttons
        start.setOnClickListener(this);
        end.setOnClickListener(this);

        //set bool
        lastRound = false;
        breakTime = false;

        //make music
         mp2 = MediaPlayer.create(TimerActivity.this, R.raw.boxing_bell);
         mp = MediaPlayer.create(TimerActivity.this, R.raw.timer_tick);
         mp.setLooping(true);
        mp3 = MediaPlayer.create(TimerActivity.this, R.raw.alarm);


    }

    /**
     * this method will be used to set the timer on the layout when user finishes dialog
     * it is a method implemented from the TimerListener Interface
     * @param min is the
     * @param secs
     */
    @Override
    public void onTimeSet(int min, int secs) {
        setMil(min, secs);
        String format = String.format(Locale.getDefault(), "%02d:%02d", min, secs);
        timer.setText(format);
        interval.setText(format);

        //launch Interval Dialog
        RestTimePickerDialog rest = new RestTimePickerDialog();
        rest.show(getSupportFragmentManager(), "set rest time");


    }

    /**
     *this method is an implementation of the TimListener interface that will set the interval time View
     * @param min
     * @param secs
     */
    @Override
    public void onRestSet(int min, int secs) {
        restMil = (long) TimeUnit.MINUTES.toMillis(min) + TimeUnit.SECONDS.toMillis(secs+1);
        String format = String.format(Locale.getDefault(), "%02d:%02d", min, secs);
        //set Rest time on view
        rest.setText(format);

        //launch Rounds Dialog
        RoundsPickerDialog roundsDialog = new RoundsPickerDialog();
        roundsDialog.show(getSupportFragmentManager(), "set rounds");

    }

    /**
     * this method is an implementation of the TimerListener that will set the # of rounds from Dialog
     * @param rds
     */
    @Override
    public void onRoundsSet(int rds) {
        //sets rounds dialog
        numRounds = rds;
        rounds.setText(Integer.toString(rds));

    }

    /**
     * this method starts the timer and decides when the timer will run rest time and decrease rounds
     */
    private void startTimer(){


        if(breakTime == false) {
            mp2.start();
            mp.start();
        }

        //create Countdown obj
         clock = new CountDownTimer(mil, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mil = millisUntilFinished;
                updateTimerView(mil);
                if(mil < TimeUnit.SECONDS.toMillis(10)){
                    timer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

             /**
              * method override when time if finished. set the next interval and play sounds
              */
            @Override
            public void onFinish() {
                //Toast.makeText(getBaseContext(),  "Timer finished", Toast.LENGTH_SHORT).show();
                if(lastRound == false){
                    if(breakTime == true){
                        //added 1000 mil so that a second can be added to display
                        mp2.start();
                        mil = interMil + 1000;
                        breakTime = false;



                    }else{
                        mp3.start();
                        mp.pause();
                        mil = restMil;
                        breakTime = true;
                    }
                    decreaseRounds();
                    startTimer();

                }else{
                    mp.pause();
                    updateTimerView(interMil);

                }
            }
        }.start();
    }

    /**
     * this override method will start the timer async task to run and change the start button to 'pause'
     * additionally, it will enable the end button.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if( v == start) {
            Toast.makeText(getBaseContext(), "start button pressed", Toast.LENGTH_SHORT).show();
            if(start.getText().equals("start")) {

                startTimer();
                start.setText("pause");
            }else{
                clock.cancel();
                mp.pause();
                start.setText("start");

            }

        }

        if(v == end){
            Toast.makeText(getBaseContext(),  "end button pressed", Toast.LENGTH_SHORT).show();
            mp2.stop();
            mp3.stop();
            mp.stop();
            finish();
        }


    }

    /**
     * this method is used to update the timer TextView
     * @param timeLeft
     */
    public void updateTimerView(long timeLeft){
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String format = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timer.setText(format);

    }

    /**
     * this method sets the long mil variable for interval and rest time in milliseconds
     * @param min
     * @param secs
     */
    public void setMil(int min, int secs){
        mil = (long) TimeUnit.MINUTES.toMillis(min) + TimeUnit.SECONDS.toMillis(secs);
        interMil = mil;
    }

    /**
     * this method is used to decrease the number of rounds and updates TextView
     */
    private void decreaseRounds(){
        numRounds--;
        if(numRounds >= 0) {
            rounds.setText(Integer.toString(numRounds));
        }

        if(numRounds < 0){
            lastRound = true;
        }
    }
}
