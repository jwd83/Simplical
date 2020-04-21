package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object Info {
    // shared pref's info

    // Filename
    const val spFilename: String = "SIMPLICAL"

    // Keys
    private const val spKeyWeight: String  = "WEIGHT"
    private const val spKeyHeight: String  = "HEIGHT"
    private const val spKeyActivityLevel: String  = "ACTIVITY_LEVEL"
    private const val spKeyMale: String  = "MALE"
    private const val spKeyBirthDate: String  = "BIRTH_DATE"
    private const val spKeyRate: String  = "RATE"
    private const val spKeyCaloriesConsumedDate: String  = "CALORIES_CONSUMED_DATE"
    private const val spKeyCaloriesConsumed: String  = "CALORIES_CONSUMED"
    private const val spKeyGoalWeight: String  = "GOAL_WEIGHT"

    // Check not set
    const val birthDateNotSet = "NOT_SET"

    // working data
    var height: Double = 0.0
    var weight: Double = 0.0
    var activityLevel: Double = 0.0
    var male: Boolean = false
    var birthDate: String? = ""
    var rate: Double = 0.0
    var caloriesConsumedDate: String? = ""
    var caloriesConsumed: Double = 0.0
    var goalWeight: Double = 0.0

    // this will need to be computed from birth date
    var age: Double = 37.0

//      Todo see if this works/how it works
//    lateinit var myAc: Activity
//    fun setActivity(ac: Activity) {
//        myAc = ac
//    }

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

    fun calculateRemainingDailyCalories(): Double {

        // this will reset the daily calories if needed
        addDailyCalories(0.0)

        // solve the remaining calories for today
        return calculateDailyCalories() - caloriesConsumed
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
        editPrefs.putDouble(spKeyCaloriesConsumed, caloriesConsumed)
        editPrefs.putString(spKeyCaloriesConsumedDate, caloriesConsumedDate)
        editPrefs.putDouble(spKeyGoalWeight, goalWeight)
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
        caloriesConsumed = prefs.getDouble(spKeyCaloriesConsumed, 0.0)
        caloriesConsumedDate = prefs.getString(spKeyCaloriesConsumedDate, "")
        goalWeight = prefs.getDouble(spKeyGoalWeight, 0.0)

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

    fun addDailyCalories(kCals: Double) {
        val today = getISODate()

        if(caloriesConsumedDate == today) {
            caloriesConsumed += kCals
        } else {
            caloriesConsumed = kCals
            caloriesConsumedDate = today
        }
    }

    fun getWeightAtBMI(bmi: Double): Double {
        return if (height > 0.0)
            (bmi / 703.0717 * height * height)
        else
            0.0
    }

    // sleek 1 liner
    private fun getISODate(): String = DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now())

//
//    fun getISODate(): String {
//
//        return DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now())
////
////        val current = LocalDateTime.now()
////
////        val formatter = DateTimeFormatter.BASIC_ISO_DATE
////        return current.format(formatter)
//    }
}
