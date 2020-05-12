package me.jwd.simplical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class HomeFragment : Fragment() {

    var cal: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // draw the ui and then setup a periodic redraw
        redrawUI()

        button_update_details.setOnClickListener{
            findNavController().navigate(R.id.action_HomeFragment_to_SettingsFragment)
        }

        button_enter_calories.setOnClickListener{
            findNavController().navigate(R.id.action_HomeFragment_to_EnterCaloriesFragment)
        }

        button_home_update_weight.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_UpdateWeightFragment)
        }

        button_update_goal_weight.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_UpdateGoalWeight)
        }

        button_enter_favorite.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_EnterFavoriteFragment)
        }

        button_review_day.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_DailyCalories)
        }

        button_manage_favorites.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_ManageFavorites)
        }

        button_reset_data.setOnClickListener {
            Info.reset(requireActivity())
            requireActivity().finish()
        }

        button_reset_onboard.setOnClickListener {
            Info.onboardComplete = false
            Info.save(requireActivity())
            requireActivity().finish()
        }
    }

    private fun redrawUI() {

        var cal1: Double
        var cal2: Double

        if(Info.dailyFoodsAvailableCalories() < Info.calculateTDEEAvailableDailyCalories()){
            cal1 = Info.dailyFoodsAvailableCalories()
            cal2 = Info.calculateTDEEAvailableDailyCalories()
        } else {
            cal1 = Info.calculateTDEEAvailableDailyCalories()
            cal2 = Info.dailyFoodsAvailableCalories()
        }

        text_calories_consumed.text = "%.0f".format(Info.dailyFoodsConsumedCalories())
        text_calories_remaining.text = "%.0f - %.0f".format(cal1, cal2)
        text_current_weight.text = "%.1f".format(Info.weight)
        text_goal_weight_left.text = if(Info.weight > Info.goalWeight) {
            "%.1f".format(Info.goalWeight - Info.weight)
        } else {
            "+%.1f".format(Info.goalWeight - Info.weight)
        }
    }
}
