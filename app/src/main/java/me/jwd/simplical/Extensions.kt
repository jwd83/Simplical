package me.jwd.simplical

import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.Fragment

fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
    putLong(key, java.lang.Double.doubleToRawLongBits(double))

fun SharedPreferences.getDouble(key: String, default: Double) =
    java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))

fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}
