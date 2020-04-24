package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_calories_remaining.text = "%.0f".format(Info.calculateRemainingDailyCalories())
        text_current_weight.text = "%.1f".format(Info.weight)
        text_goal_weight_left.text = if(Info.weight > Info.goalWeight) {
            "%.1f".format(Info.goalWeight - Info.weight)
        } else {
            "+%.1f".format(Info.goalWeight - Info.weight)
        }

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

//        button_debug_message.setOnClickListener {
//            toast(
//                ("%.1f\n%.1f\n${Info.caloriesConsumedDate}\n%.1f").format(
//                    Info.calculateTDEE(),
//                    Info.rate,
//                    Info.caloriesConsumed
//                )
//            )
//        }
//
//        button_reset_data.setOnClickListener {
//            Info.reset(requireActivity())
//            requireActivity().finish()
//        }
    }
}
