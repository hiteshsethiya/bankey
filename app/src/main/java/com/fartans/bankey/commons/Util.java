package com.fartans.bankey.commons;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.Locale;

/**
 * Created by hitesh on 14/03/16.
 * @author hitesh.sethiya
 */
public class Util {

    private static final String TAG = Util.class.getName();
    public static String rupeeConverter(Double amount) {
        String inRupees = String.valueOf(amount);
        DecimalFormat decimalFormat;
        try {
            if(amount >= 100000000) {
                //10 cr
                amount = 245600000.00;
                amount /= 10000000;
                decimalFormat = new DecimalFormat("##.00");
                decimalFormat.setMaximumFractionDigits(2);
                decimalFormat.setMaximumIntegerDigits(2);
                inRupees = decimalFormat.format(amount);
                inRupees = inRupees.concat(" Cr");
            } else if(amount >= 10000000) {
                //1 Cr
                amount /= 10000000;
                decimalFormat = new DecimalFormat("#.00");
                decimalFormat.setMaximumFractionDigits(2);
                decimalFormat.setMaximumIntegerDigits(2);
                inRupees = decimalFormat.format(amount);
                inRupees = inRupees.concat(" Cr");
            } else {
                decimalFormat = new DecimalFormat("##,##,##0.00");
                decimalFormat.setCurrency(Currency.getInstance(new Locale("EN", "IN")));
                decimalFormat.setMaximumFractionDigits(2);
                inRupees = decimalFormat.format(amount);
            } } catch(InputMismatchException e) {
            Log.e(TAG, "InputMismatchException e", e);
        }

        return inRupees;
    }
}
