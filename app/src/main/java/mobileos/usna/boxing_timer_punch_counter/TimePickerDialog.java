package mobileos.usna.boxing_timer_punch_counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimePickerDialog extends DialogFragment {

    TimerListener settings;
    NumberPicker minWheel, secWheel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //initiate minute and second wheel
        setWheel();

        //initialize myListener with the parent activity
        Activity activity = getActivity();
        try {
            settings = (TimerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyDialogListener");
        }

        //create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //create Linear Layout Content and store 2 number picker Wheel in them
        Context context = getActivity();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.addView(minWheel);
        layout.addView(secWheel);
        builder.setView(layout);
        //builder
        builder.setTitle("Interval Time");
        builder.setMessage("please set the interval time:\nMinutes:    Seconds:");
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {

                //call methods from listener interface when appropriate
                int minutes = minWheel.getValue();
                int seconds = secWheel.getValue();
                settings.onTimeSet(minutes, seconds);

            }

        });

        return builder.create();



    }

    /**
     * this method is used to set the initialize the numberPicker minute and second Wheel with the
     * right configuration
     */
    public void setWheel(){
        minWheel = new NumberPicker(getActivity());
        minWheel.setMinValue(0);
        minWheel.setMaxValue(30);
        minWheel.setWrapSelectorWheel(true);
        minWheel.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //initiate second number wheel
        secWheel = new NumberPicker(getActivity());
        //this setts the seconds wheel to fit the dialog box
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        secWheel.setLayoutParams(param);
        secWheel.setMinValue(0);
        secWheel.setMaxValue(59);
        secWheel.setWrapSelectorWheel(true);
        secWheel.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);




    }


}
