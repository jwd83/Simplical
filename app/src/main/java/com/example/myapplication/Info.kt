package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.navigation.ActivityNavigatorExtras

object Info {
    // shared pref's info

    // Filename
    const val spFilename: String = "SIMPLICAL"

    // Keys
    const val spKeyWeight: String  = "WEIGHT"
    const val spKeyHeight: String  = "HEIGHT"
    const val spKeyActivityLevel: String  = "ACTIVITY_LEVEL"
    const val spKeyMale: String  = "MALE"
    const val spKeyBirthDate: String  = "BIRTH_DATE"
    const val spKeyRate: String  = "RATE"

    // Check not set
    const val birthDateNotSet = "NOT_SET"

    // working data
    var height: Double = 0.0
    var weight: Double = 0.0
    var activityLevel: Double = 0.0
    var male: Boolean = false
    var birthDate: String? = ""
    var rate: Double = 0.0

    // this will need to be computed from birth date
    var age: Double = 0.0

    fun calculateBMI(): Double {
        return if (height > 0 && weight > 0) {
            703.0717 * (weight / (height*height))
        } else {
            0.0
        }
    }

    fun calculateBMR(): Double {
        return if(male) {
            (66 + (6.2 * weight) + (12.7 * height) - (6.76 * age))
        } else {
            (655.1 + 4.35 * weight + 4.7 * height - 4.7 * age)
        }
    }

    fun calculateTDEE(): Double {
        return calculateBMR() * activityLevel
    }

    fun calculateDailyCalories(): Double {
        return (calculateTDEE() * 7.0 - rate * 3500.0) / 7.0
    }

    fun save(activity: Activity) {
        // create preferences editor
        // val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefs = activity.getSharedPreferences(spFilename, Context.MODE_PRIVATE)

        val editPrefs = prefs.edit()

        // save data
        editPrefs.putString(spKeyBirthDate, birthDate)
        editPrefs.putBoolean(spKeyMale, male)
        editPrefs.putDouble(spKeyHeight, height)
        editPrefs.putDouble(spKeyWeight, weight)
        editPrefs.putDouble(spKeyActivityLevel, activityLevel)
        editPrefs.putDouble(spKeyRate, rate)
        editPrefs.apply()

        // announce we saved data
        Toast.makeText(activity.applicationContext, "Data saved", Toast.LENGTH_SHORT).show()
    }

    fun load(activity: Activity) {
        // create preferences editor
        // val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefs = activity.getSharedPreferences(spFilename, Context.MODE_PRIVATE)

        // load data
        birthDate = prefs.getString(spKeyBirthDate, birthDateNotSet)
        male = prefs.getBoolean(spKeyMale, false)
        height = prefs.getDouble(spKeyHeight, 0.0)
        weight = prefs.getDouble(spKeyWeight, 0.0)
        activityLevel = prefs.getDouble(spKeyActivityLevel, 0.0)
        rate = prefs.getDouble(spKeyRate, 0.0)

        // announce we loaded data
        Toast.makeText(activity.applicationContext, "Data loaded", Toast.LENGTH_SHORT).show()
    }

    fun reset(activity: Activity) {
        // create preferences editor
        // val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefs = activity.getSharedPreferences(spFilename, Context.MODE_PRIVATE)
        val editPrefs = prefs.edit()

        // clear stored data
        editPrefs.clear()

        // save cleared data
        editPrefs.apply()
    }
}
