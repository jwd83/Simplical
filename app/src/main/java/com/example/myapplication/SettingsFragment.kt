package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_button_looks_good.setOnClickListener {

            var success = false

            try {
                val sexIsMale = when (settings_radio_sex.checkedRadioButtonId) {
                    R.id.settings_radio_male -> true
                    R.id.settings_radio_female -> false
                    else -> throw IllegalArgumentException("Sex required")
                }

                val activityLevel = when(settings_radio_activity_level.checkedRadioButtonId) {
                    R.id.settings_radio_al_110 -> 1.1
                    R.id.settings_radio_al_120 -> 1.2
                    R.id.settings_radio_al_137 -> 1.37
                    R.id.settings_radio_al_155 -> 1.55
                    R.id.settings_radio_al_1725 -> 1.725
                    R.id.settings_radio_al_190 -> 1.9
                    else -> throw IllegalArgumentException("Activity level required")
                }

                val weight = settings_edit_weight.text.toString().toDouble()
                val height = settings_edit_height.text.toString().toDouble()
                val age = settings_edit_birth.text.toString().toDouble()
                val rate = when(settings_radio_rate.checkedRadioButtonId) {
                    R.id.settings_rate_05 -> 0.5
                    R.id.settings_rate_10 -> 1.0
                    R.id.settings_rate_15 -> 1.5
                    R.id.settings_rate_20 -> 2.0
                    else -> throw IllegalArgumentException("Rate of change required")
                }

                if(height in 48.0..108.0 && weight in 50.0..600.0) {

                    Info.weight = weight
                    Info.height = height
                    Info.male = sexIsMale
                    Info.age = age
                    Info.activityLevel = activityLevel
                    Info.rate = rate
                    Info.birthDate = Info.birthDateNotSet

                    // solve bmi/bmr values
                    val bmi = Info.calculateBMI()
                    val bmr = Info.calculateBMR()
                    val tdee = Info.calculateTDEE()

                    // generate a toast message
                    toast(("BMI = %.2f\nBMR = %.1f\nTDEE = %.1f\nspFile = " + Info.spFilename).format(bmi, bmr, tdee))
//                    Toast.makeText(
//                        context,
//                        ("BMI = %.2f\nBMR = %.1f\nTDEE = %.1f\nspFile = " + Info.spFilename).format(bmi, bmr, tdee),
//                        Toast.LENGTH_LONG
//                    ).show()

                    // store these values in shared preferences
                    Info.save(requireActivity())

                    findNavController().popBackStack()

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
