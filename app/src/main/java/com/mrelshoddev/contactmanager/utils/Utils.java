package com.mrelshoddev.contactmanager.utils;

import android.content.Context;

public class Utils {
    public static int toPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
