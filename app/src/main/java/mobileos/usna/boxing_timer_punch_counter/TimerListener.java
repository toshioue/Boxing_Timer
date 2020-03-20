package mobileos.usna.boxing_timer_punch_counter;

/**
 * this interface is used to retrieved information from the dialog boxes: ResttimerDialog, Roundspicker, and Timer
 */
public interface TimerListener {

     void onTimeSet(int min, int secs);
     void onRestSet(int min, int secs);
     void onRoundsSet (int rds);

}
