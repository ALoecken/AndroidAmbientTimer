package com.example.android.basicimmersivemode;

import android.graphics.Color;

public class ColorStatus {

    private static ColorStatus status;

    private int startColor = 0 ;
    private int endColor = 0;

    private long starttime;
    private long endtime;

    public static ColorStatus getInstance() {
        if (status == null) status = new ColorStatus();
        return status;
    }

    public void startChange(int startColor, int endColor, float seconds) {
        starttime = System.currentTimeMillis();
        endtime = starttime + (long)(seconds * 1000f);
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public int getCurrentColor() {
        long time = System.currentTimeMillis();
        if (time < starttime) return startColor; // todo this should never happen
        if (time > endtime) return Color.BLACK;

        long duration = endtime - starttime;
        long passedtime = time - starttime;
        float ratio = (float) passedtime/(float) duration;

        return interpolateColor(startColor, endColor, ratio);
    }



    /** Returns an interpoloated color, between <code>a</code> and <code>b</code>
     * from https://stackoverflow.com/a/7871291*/
    private int interpolateColor(int a, int b, float proportion) {
        float[] hsva = new float[3];
        float[] hsvb = new float[3];
        Color.colorToHSV(a, hsva);
        Color.colorToHSV(b, hsvb);
        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSVToColor(hsvb);
    }

    private float interpolate(float a, float b, float proportion) {
        return (a + ((b - a) * proportion));
    }
}
