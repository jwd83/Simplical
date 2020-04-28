package com.example.myapplication

import com.google.gson.Gson
import android.app.Activity
import android.content.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// SQLite with Room
// https://developer.android.com/training/data-storage/room
// https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0

// General Data storage
// https://developer.android.com/training/data-storage
// https://kotlinlang.org/docs/reference/collections-overview.html
// https://kotlinlang.org/docs/reference/iterators.html


object Info {
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
    private const val spKeyFavorites: String  = "FAVORITES"
    private const val spKeyDailyFoods: String = "DAILY_FOODS"
    private const val spKeyDailyFoodsDate: String ="DAILY_FOODS_DATE"

    // Check not set
    const val birthDateNotSet = "NOT_SET"

    // List of favorites
    var favoriteFoods = mutableListOf<Food>()
    var dailyFoods = mutableListOf<Food>()

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
    var dailyFoodsDate: String? = ""

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

    fun save(activity: Activity) {
        // create preferences editor
        // val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefs = activity.getSharedPreferences(spFilename, Context.MODE_PRIVATE)
        val editPrefs = prefs.edit()

        // create Gson object to save favorites list
        val gson: Gson = Gson()
        val favoritesJSON: String = gson.toJson(favoriteFoods)
        val dailyJSON: String = gson.toJson(dailyFoods)

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
        editPrefs.putString(spKeyFavorites, favoritesJSON)
        editPrefs.putString(spKeyDailyFoods, dailyJSON)
        editPrefs.putString(spKeyDailyFoodsDate, dailyFoodsDate)
        editPrefs.apply()
    }

    fun load(activity: Activity) {
        // create preferences editor
        // val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        val prefs = activity.getSharedPreferences(spFilename, Context.MODE_PRIVATE)
        // create Gson object to import our favorite and daily food lists
        val gson: Gson = Gson()

        // load basic data
        birthDate = prefs.getString(spKeyBirthDate, birthDateNotSet)
        male = prefs.getBoolean(spKeyMale, false)
        height = prefs.getDouble(spKeyHeight, 0.0)
        weight = prefs.getDouble(spKeyWeight, 0.0)
        activityLevel = prefs.getDouble(spKeyActivityLevel, 0.0)
        rate = prefs.getDouble(spKeyRate, 0.0)
        caloriesConsumed = prefs.getDouble(spKeyCaloriesConsumed, 0.0)
        caloriesConsumedDate = prefs.getString(spKeyCaloriesConsumedDate, "")
        goalWeight = prefs.getDouble(spKeyGoalWeight, 0.0)
        dailyFoodsDate = prefs.getString(spKeyDailyFoodsDate, "")

        // load Json lists
        val favoritesJson = prefs.getString(spKeyFavorites, "")
        val dailyJson =  prefs.getString(spKeyDailyFoods, "")

        try {
            favoriteFoods = gson.fromJson(favoritesJson, Array<Food>::class.java).toMutableList()
        }
        catch (e: Throwable) {

        }
        try {
            dailyFoods = gson.fromJson(dailyJson, Array<Food>::class.java).toMutableList()
        }
        catch (e: Throwable) {

        }
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

    /**
     * Return how many calories we have left for today
     */
    fun dailyFoodsAvailableCalories(): Double {
        return calculateDailyCalories() - dailyFoodsConsumedCalories()
    }

    /**
     * Add an entry to our daily foods list
     */
    fun dailyFoodsAdd(calories: Double, qty: Double, name: String) {
        dailyFoodsReset()                                       // check for date change
        dailyFoods.add(Food(0, name, calories, qty))        // add new food
    }

    /**
     * return the total number of calories consumed today
     */
    fun dailyFoodsConsumedCalories(): Double {
        var totalCalories = 0.0

        // check for date change
        dailyFoodsReset()

        dailyFoods.forEach{
            totalCalories += it.calories * it.qty
        }

        return totalCalories
    }

    /**
     * Will reset the daily food intake total if the date has changed OR if forceReset is true
     */
    private fun dailyFoodsReset(forceReset: Boolean = false) {
        val today = getISODate()

        if(dailyFoodsDate != today || forceReset) {
            dailyFoodsDate = today
            dailyFoods = mutableListOf<Food>()
        }
    }

    fun getWeightAtBMI(bmi: Double): Double {
        return if (height > 0.0)
            (bmi / 703.0717 * height * height)
        else
            0.0
    }

    fun getMillerIBW(): Double {
        /*
        D. R. Miller Formula (1983)

        Male:	56.2 kg + 1.41 kg per inch over 5 feet
        Female:	53.1 kg + 1.36 kg per inch over 5 feet
         */

        val kgToLbs = 2.20462

        return if (male)
            56.2 * kgToLbs + 1.41 * kgToLbs * (height - 60)
        else
            53.1 * kgToLbs + 1.36 * kgToLbs * (height - 60)
    }

    // sleek 1 liner
    private fun getISODate(): String = DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now())

    fun addFavorite(name: String, calories: Double) {
        // todo prevent adding duplicate item name
        favoriteFoods.add(Food(0, name, calories))
    }
}
