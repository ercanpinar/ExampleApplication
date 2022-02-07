package com.ercanpinar.exampleapplication

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun View.clickWithDebounce(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun NavController.navigateWithAnim(@IdRes destId: Int, args: Bundle? = null) {
    try {
        this.navigate(
            destId, args, NavOptions.Builder()
                .setEnterAnim(R.anim.slide_anim_in)
                .setExitAnim(R.anim.slide_anim_out)
                .setPopEnterAnim(R.anim.slide_pop_anim_in)
                .setPopExitAnim(R.anim.slide_pop_anim_out).build()
        )
    } catch (e: Exception) {
        Log.e(this::class.java.simpleName, "Error navigating to: $destId \nError: ${e.message}")
    }
}