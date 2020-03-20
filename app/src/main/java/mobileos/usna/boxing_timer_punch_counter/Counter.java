package mobileos.usna.boxing_timer_punch_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Author: Oue, Hitoshi P.
 * Description: this class is responsible for initializing the counter layout and incrementing the #
 * of punches thrown
 */
public class Counter extends AppCompatActivity implements View.OnClickListener {

    int total;
    Button add, end;
    TextView totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        setViews();
        //Toast.makeText(getBaseContext(),  "counter initialized", Toast.LENGTH_SHORT).show();

    }


    public void setViews(){
        total = 0;
        add = findViewById(R.id.add_one);
        end = findViewById(R.id.end_counter);
        totalView = findViewById(R.id.total);
        add.setOnClickListener(this);
        end.setOnClickListener(this);
    }
    public void add(){
        total++;
    }

    /**
     * this method will be called when user's clicks on Add or end button
     */
    @Override
    public void onClick(View v) {
        if(v == add){
            //Toast.makeText(getBaseContext(),  "add button pressed", Toast.LENGTH_SHORT).show();
            add();
            totalView.setText(Integer.toString(total));
        }
        if(v == end){
            Toast.makeText(getBaseContext(),  "end button pressed", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}

