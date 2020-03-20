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

/**
 * Description: this class is for the initializing the rounds dialog
 */
public class RoundsPickerDialog extends DialogFragment {
    TimerListener settings;
    NumberPicker rdWheel;

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
        layout.addView(rdWheel);
       // layout.addView(secWheel);
        builder.setView(layout);
        //builder
        builder.setTitle("Rounds");
        builder.setMessage("please set the # of Rounds:");
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {

                //call methods from listener interface when appropriate
                int rd = rdWheel.getValue();
                settings.onRoundsSet(rd);


            }

        });

        return builder.create();



    }

    /**
     * this method is used to set the initialize the numberPicker minute and second Wheel with the
     * right configuration
     */
    public void setWheel(){
        rdWheel = new NumberPicker(getActivity());
        rdWheel.setMinValue(0);
        rdWheel.setMaxValue(15);
        rdWheel.setWrapSelectorWheel(true);
        rdWheel.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rdWheel.setLayoutParams(param);



    }


}
