package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = findViewById<View>(android.R.id.content).rootView
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    // if more than 100 pixels, its probably a keyboard
    return rootView.rootView.height - rect.height() > 100
}

fun Activity.isKeyboardClosed() = !isKeyboardOpen()
