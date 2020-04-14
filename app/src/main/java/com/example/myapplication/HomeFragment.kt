package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.system.exitProcess

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

//        text_calories_remaining.text = "%.0f".format(Info.calculateDailyCalories())
        text_calories_remaining.text = "%.0f".format(Info.calculateRemainingDailyCalories())
        text_current_weight.text = "%.1f".format(Info.weight)

        button_update_details.setOnClickListener{
            findNavController().navigate(R.id.action_HomeFragment_to_SettingsFragment)
        }

        button_enter_calories.setOnClickListener{
            findNavController().navigate(R.id.action_HomeFragment_to_EnterCaloriesFragment)
        }

        button_reset_data.setOnClickListener {
            Info.reset(requireActivity())
            requireActivity().finish()
//            activity.finish()
//            toast("Data cleared, please restart app")
//            exitProcess(-1)
        }
    }
}
