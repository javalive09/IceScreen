package com.peter.icescreen;

import android.view.animation.Interpolator;

public class EaseOutExpoInterpolator implements Interpolator{

    @Override
    public float getInterpolation(float input) {
        if (input == 1.0f) {
            return 1.0f;
        }
        return (float) (1 - Math.pow(2.0, -10.0 * input));
    }

}
