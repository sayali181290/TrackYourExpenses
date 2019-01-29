package com.sayali.trackyourexpenses.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sayali.trackyourexpenses.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * Added By : Sayali Deshpande
     * @return : returns boolean value based on whether the currently running
     * OS is Marshmallow or above it.
     */
    public static boolean isMarshmallowAndAbove(){
        if (Build.VERSION.SDK_INT >= 23)
            return true;
        return false;
    }

    /**
     * Added by : Sayali Deshpande
     * @param context : Current context
     * @param title : Title string for an alert dialog
     * @param msg : Message needs to be displayed in an alert dialog.
     */
    public static void showAlertDialog(Context context, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param context : Current context
     * @param title : Title string for an alert dialog
     * @param msg : Message needs to be displayed in an alert dialog
     * @param isCancellable : Sets boolean value based on whether back button click
     *                      dissmisses the current dialog or not.
     */
    public static void showAlertDialog(Context context, String title, String msg, boolean isCancellable){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(isCancellable);
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Added By : Sayali Deshpande
     * @param context : Current context
     * @param title : Title string for an alert dialog
     * @param msg : Message to be diaplayed on an alert dialog
     * @param positiveBtnTitle : String that needs to be displayed on the button
     * @param okListener : Action that needs to be done when clicking on the positive
     *                   button
     */
    public static void showAlertDialog(Context context, String title, String msg, String positiveBtnTitle, DialogInterface.OnClickListener okListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveBtnTitle, okListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param context : Current context
     * @param title : Title string for an alert dialog
     * @param msg : Message that needs to be displayed in an alert dialog
     * @param positiveBtnTitle : Button title for positive button
     * @param okListener : Action that needs to be performed when clicking on positive
     *                   button
     * @param negativeBtnTitle : Button title for negative button
     * @param cancelListener : Action that needs to be performed when clicking on
     *                       negative button
     */
    public static void showAlertDialog(Context context, String title, String msg, String positiveBtnTitle, DialogInterface.OnClickListener okListener, String negativeBtnTitle, DialogInterface.OnClickListener cancelListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveBtnTitle, okListener);
        builder.setNegativeButton(negativeBtnTitle, cancelListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param context : Current context
     * @param title : Title string for an alert dialog
     * @param msg : Message that needs to be displayed in an alert dialog
     * @param positiveBtnTitle : Button title for positive button
     * @param okListener : Action that needs to be performed when clicking on positive
     *                   button
     * @param negativeBtnTitle : Button title for negative button
     * @param cancelListener : Action that needs to be performed when clicking on
     *                       negative button
     */
    public static void showAlertDialog(Context context, String title, String msg, String positiveBtnTitle, DialogInterface.OnClickListener okListener, String negativeBtnTitle, DialogInterface.OnClickListener cancelListener, String neutalBtnTitle, DialogInterface.OnClickListener neutralListener ){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveBtnTitle, okListener);
        builder.setNegativeButton(negativeBtnTitle, cancelListener);
        builder.setNeutralButton(neutalBtnTitle,neutralListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Added By : Sayali Deshpande
     * @param context : An activity from which it is being called
     * @param message : Message needs to be displayed in the alert
     * Description : This is used when displaying Alternate address &
     *                Important news.
     */
    public static void showAlertWithCustomUI(Context context, String heading, String message){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        View dialogView = inflater.inflate(R.layout.alert_ui_alt_addr, null);
//        dialogBuilder.setView(dialogView);
//
//        AppCompatTextView mHeading = (AppCompatTextView) dialogView.findViewById(R.id.heading);
//        mHeading.setText(heading);
//
//        AppCompatTextView mTxtContent= (AppCompatTextView) dialogView.findViewById(R.id.txtContent);
//        mTxtContent.setText(message);
//
//        AppCompatButton mBtnOk = (AppCompatButton) dialogView.findViewById(R.id.btnOk);
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//        mBtnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
    }

    /**
     * Added By : Sayali Deshpande
     * @param context : Current context
     * @param msg : Meesage to be displayed on toast for short duration.
     */
    public static void showShortLengthToastMessage(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param context : Current context
     * @param msg : Message to be displayed on toast for long duration.
     */
    public static void showLongLengthToastMessage(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param activity : From which activity keyboard needs to get dismissed.
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus()!=null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Added by : Sayali Deshpande
     * @param mContext : context of an app
     * @param msg : message needs to be displayed
     * Description : Displays the short length toast message.
     */
    public static void displayShortLengthToast(Context mContext, String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Added by : Sayali Deshpande
     * @param mContext : context of an app
     * @param msg : message needs to be displayed
     * Description : Displays the long length toast message.
     */
    public static void displayLongLengthToast(Context mContext, String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
