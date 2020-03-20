package mobileos.usna.boxing_timer_punch_counter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Author: Oue, Hitoshi P.
 * Description: this class is the main portion of the app that will display the main menu
 * for the counter puncher or the timer.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button timer, puncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

    }

    public void setViews(){
        timer = findViewById(R.id.timer);
        puncher = findViewById(R.id.puncher);
        timer.setOnClickListener(this);
        puncher.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == timer){
            //small dialog
            Toast.makeText(getBaseContext(),  "timer button", Toast.LENGTH_SHORT).show();
            Log.i("punch app", "timer button pressed");
            //start the timer Activity
            startActivity(new Intent(MainActivity.this, TimerActivity.class));

        }
        if(v == puncher){
            Toast.makeText(getBaseContext(),  "puncher button", Toast.LENGTH_SHORT).show();
            Log.i("punch app", "punch button pressed");
            //start puncher counter Activity
            startActivity(new Intent(MainActivity.this, Counter.class));


        }
    }
}
