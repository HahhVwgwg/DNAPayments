package com.dnapayments.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity

object Earthquake {

    const val TAG = "Earthquake"

    fun onShake(context: Context?, view: View?) {
        context?.let {
            if (Build.VERSION.SDK_INT >= 26) {
                (it.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator).vibrate(
                        VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                (it.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator).vibrate(150)
            }
            animatedViewBound(view)
        }
    }

    private fun animatedViewBound(view: View?) {
        view?.let { v ->
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f, 1f)
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleX, scaleY)
            objectAnimator.interpolator = BounceInterpolator()
            objectAnimator.repeatMode = ValueAnimator.REVERSE
            objectAnimator.start()
        }
    }
}