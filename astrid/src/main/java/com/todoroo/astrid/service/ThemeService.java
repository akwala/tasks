/**
 * Copyright (c) 2012 Todoroo Inc
 *
 * See the file "LICENSE" for the full license governing this code.
 */
package com.todoroo.astrid.service;

import android.app.Activity;
import android.graphics.PixelFormat;

import com.todoroo.andlib.utility.AndroidUtilities;
import com.todoroo.andlib.utility.Preferences;

import org.tasks.R;

public class ThemeService {

    public static final String THEME_WHITE = "white";
    public static final String THEME_BLACK = "black";

    public static void applyTheme(Activity activity) {
        int currentTheme = getTheme();
        activity.setTheme(currentTheme);
        activity.getWindow().setFormat(PixelFormat.RGBA_8888);
    }

    private static int getTheme() {
        String preference = Preferences.getBoolean(R.string.p_use_dark_theme, false) ? THEME_BLACK : THEME_WHITE;
        return getStyleForSetting(preference);
    }

    private static int getStyleForSetting(String setting) {
        if(THEME_BLACK.equals(setting)) {
            return R.style.Tasks;
        } else {
            return R.style.Tasks_Light;
        }
    }

    public static int getThemeColor() {
        int theme = getTheme();
        switch(theme) {
        case R.style.Tasks:
            return R.color.blue_theme_color;
        case R.style.Tasks_Light:
        default:
            return R.color.dark_blue_theme_color;
        }
    }

    public static int getEditDialogTheme() {
        boolean ics = AndroidUtilities.getSdkVersion() >= 14;
        int themeSetting = getTheme();
        int theme;
        if (themeSetting == R.style.Tasks) {
            if (ics) {
                theme = R.style.TEA_Dialog_ICS;
            } else {
                theme = R.style.TEA_Dialog;
            }
        } else {
            if (ics) {
                theme = R.style.TEA_Dialog_Light_ICS;
            } else {
                theme = R.style.TEA_Dialog_Light;
            }
        }
        return theme;
    }

    public static int getDialogTheme() {
        int themeSetting = getTheme();
        int theme;
        if (themeSetting == R.style.Tasks) {
            theme = R.style.Tasks_Dialog;
        } else {
            theme = R.style.Tasks_Dialog_Light;
        }
        return theme;
    }

    public static int getDarkVsLight(int resForLight, int resForDark) {
        int theme = getTheme();
        if (theme == R.style.Tasks) {
            return resForDark;
        } else {
            return resForLight;
        }
    }
    public static int getTaskEditDrawable(int regularDrawable, int lightBlueDrawable) {
        return getDarkVsLight(regularDrawable, lightBlueDrawable);
    }

    public static int getTaskEditThemeColor() {
        return getDarkVsLight(R.color.task_edit_selected, R.color.blue_theme_color);
    }
}
