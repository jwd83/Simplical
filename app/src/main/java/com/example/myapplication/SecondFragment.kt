package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        button_looks_good.setOnClickListener {

            var success = false

            try {
                val sexIsMale = when (radio_sex.checkedRadioButtonId) {
                    R.id.radio_male -> true
                    R.id.radio_female -> false
                    else -> throw IllegalArgumentException("Sex required")
                }

                val activityLevel = when(radio_activity_level.checkedRadioButtonId) {
                    R.id.radio_al_110 -> 1.1
                    R.id.radio_al_120 -> 1.2
                    R.id.radio_al_137 -> 1.37
                    R.id.radio_al_155 -> 1.55
                    R.id.radio_al_1725 -> 1.725
                    R.id.radio_al_190 -> 1.9
                    else -> throw IllegalArgumentException("Activity level required")
                }

                val weight = edit_weight.text.toString().toDouble()
                val height = edit_height.text.toString().toDouble()
                val birthDate = edit_birth.text.toString()
                val age = 37.0
                val rate = when(radio_rate.checkedRadioButtonId) {
                    R.id.rate_05 -> 0.5
                    R.id.rate_10 -> 1.0
                    R.id.rate_15 -> 1.5
                    R.id.rate_20 -> 2.0
                    else -> throw IllegalArgumentException("Rate of change required")
                }

                if(height in 48.0..108.0 && weight in 50.0..600.0) {

                    Info.weight = weight
                    Info.height = height
                    Info.male = sexIsMale
                    Info.age = age
                    Info.activityLevel = activityLevel
                    Info.rate = rate
                    Info.birthDate = birthDate

                    // solve bmi/bmr values
                    val bmi = Info.calculateBMI()
                    val bmr = Info.calculateBMR()
                    val tdee = Info.calculateTDEE()

                    // generate a toast message
                    Toast.makeText(
                        context,
                        ("BMI = %.2f\nBMR = %.1f\nTDEE = %.1f\nspFile = " + Info.spFilename).format(bmi, bmr, tdee),
                        Toast.LENGTH_LONG
                    ).show()

                    // store these values in shared preferences
                    Info.save(requireActivity())

                    findNavController().navigate(R.id.action_SecondFragment_to_MainFragment)

                    success = true
                }
            } catch(e: Throwable) {

            }
            if (!success) {
                Toast.makeText(context, "Please respond to all questions above", Toast.LENGTH_LONG).show()
            }
        }
    }
}