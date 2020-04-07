package com.example.myapplication

import androidx.navigation.ActivityNavigatorExtras

object Info {
    // shared pref's info
    val spFilename: String = "SIMPLICAL"
    val spKeyWeight = "WEIGHT"
    val spKeyHeight = "HEIGHT"
    val spKeyActivityLevel = "ACTIVITY_LEVEL"
    val spKeyMale = "MALE"
    val spKeyBirthDate = "BIRTH_DATE"
    val spKeyRate = "RATE"

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
}
