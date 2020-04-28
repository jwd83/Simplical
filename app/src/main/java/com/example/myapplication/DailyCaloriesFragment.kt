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

        val verticalList = daily_review_vertical_list

        Info.dailyFoods.forEach{
            val foodSwitch = Switch(requireActivity())
            foodSwitch.text = "${it.name}: ${it.qty} * ${it.calories} = ${it.qty * it.calories}"
            foodSwitch.id = View.generateViewId()
            it.id = foodSwitch.id
            verticalList.addView(foodSwitch)
        }

        addText("Calorie Budget:    %.1f".format(Info.calculateDailyCalories()), 30)
        addText("Calories Used:    %.1f".format(Info.dailyFoodsConsumedCalories()))
        addText("Calories Left:    %.1f".format(Info.dailyFoodsAvailableCalories()))

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
