package com.intellize.nb_food_delervery.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;


import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.view.activity.LoginActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mateware.snacky.Snacky;

public class Utils {

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static boolean isNotEmptyString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }


    public static void showMessage(Activity activity, AlertType alertType, String message) {
        // Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        switch (alertType) {

            case SUCCESS:
//               Snacky.builder()
//                       .setActivity(activity)
//                       .setText(message)
//                       .setDuration(3000)
//                       .success()
//                       .show();
//               break;
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            case ERROR:
//               Snacky.builder()
//                       .setActivity(activity)
//                       .setText(message)
//                       .setDuration(3000)
//                       .error()
//                       .show();
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                break;
            case TOAST:
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean validateField(EditText editText, String msg) {
        if (editText.getText().toString().length() == 0) {
            editText.setError(msg);
            editText.requestFocus();

            return false;
        }
        return true;
    }


    public static String getRealPathFromUri(Uri contentUri, Activity activity) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static String getDecimal(int value) {
        return decimalFormat.format(value);
    }

    public static String getDecimal(double value) {
        return decimalFormat.format(value);
    }

    public static String getDecimal(String value) {
        return decimalFormat.format(value);
    }


    public static Dialog getLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public static String generateId() {
        return System.currentTimeMillis() + "";
    }

    public static String getTime() {
        return dateFormat.format(new Date());
    }

}
