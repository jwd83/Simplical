package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.Switch
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_daily_calories.*
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 */
class DailyCaloriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_calories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val df0: DecimalFormat = DecimalFormat("#,###")
        val df1: DecimalFormat = DecimalFormat  ("#.#")
        val df2: DecimalFormat = DecimalFormat  ("#.##")
        val df3: DecimalFormat = DecimalFormat("#.###")

        val verticalList = daily_review_vertical_list

        var entries = 1;

        Info.dailyFoods.forEach{
            val foodSwitch = Switch(requireActivity())
//            foodSwitch.text = "${entries}. \"${it.name}\" at ${df2.format(it.calories)} CPP and ${df2.format(it.qty)} portions = ${df2.format(it.qty * it.calories)} total calories."
            foodSwitch.text = "${df2.format(it.qty)} of \"${it.name}\": ${df2.format(it.calories)}cal/each = ${df2.format(it.qty * it.calories)}cal/tot"
            foodSwitch.id = View.generateViewId()
            it.id = foodSwitch.id
            entries++
            verticalList.addView(foodSwitch)
        }

        addText("Calorie Budget:    %.1f".format(Info.calculateDailyCalories()), 30)
        addText("Calories Used:    %.1f".format(Info.dailyFoodsConsumedCalories()))
        addText("Calories Left:    %.1f".format(Info.dailyFoodsAvailableCalories()))

        day_in_review_value_sex.text = if(Info.male) { "Male" } else { "Female" }
        day_in_review_value_age.text = df0.format(Info.age)
        day_in_review_value_bmi.text = df1.format(Info.calculateBMI())
        day_in_review_value_bmr.text = df0.format(Info.calculateBMR())
        day_in_review_value_height.text = df1.format(Info.height)
        day_in_review_value_weight.text = df1.format(Info.weight)
        day_in_review_value_alm.text = df3.format(Info.activityLevel)
        day_in_review_value_tdee.text = df0.format(Info.calculateTDEE())
        day_in_review_value_goal_tdee.text = df0.format(Info.calculateTDEE())
        day_in_review_value_weekly_burn.text = df0.format(Info.calculateTDEE() * 7.0)
        day_in_review_value_daily_goal.text = df0.format(Info.calculateDailyCalories())
        day_in_review_value_weekly_pace.text = df1.format(Info.rate)
        day_in_review_value_weekly_adjustment.text = df0.format(Info.caloriesPerPound * Info.rate)
        // todo add the weekly target value

        var weekly_cals = Info.calculateTDEE() * 7.0
        if (Info.weight > Info.goalWeight) {
            weekly_cals -= Info.rate * Info.caloriesPerPound
        } else {
            weekly_cals += Info.rate * Info.caloriesPerPound
        }
        day_in_review_value_weekly_supply.text = df0.format(weekly_cals)


        button_remove_from_day.setOnClickListener {
            try {
                var foodsToRemove = mutableListOf<Int>()

                for((idx, food: Food) in Info.dailyFoods.withIndex()) {
                    val dailyFoodSwitch: Switch = view.findViewById(food.id)
                    if (dailyFoodSwitch.isChecked) {
                        foodsToRemove.add(idx)
                    }
                }

                foodsToRemove.asReversed().forEach {
                    Info.dailyFoods.removeAt(it)
                }

                Info.save(requireActivity())
                findNavController().popBackStack()
            } catch (e: Throwable) {
                toast("An error occurred:\n${e.message}")
            }
        }
    }

    private fun addText(str: CharSequence, topPadding: Int = 0) {
        var txt = TextView(requireActivity())
        txt.setTextColor(Color.BLACK)
        txt.setPadding(0,topPadding,0,0)
        txt.gravity = Gravity.RIGHT
        txt.text = str
        txt.textSize = 20f
        daily_review_vertical_list.addView(txt)
    }
}
