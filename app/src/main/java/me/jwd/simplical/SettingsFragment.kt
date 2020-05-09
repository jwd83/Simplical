package me.jwd.simplical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.DecimalFormat

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

        if(Info.onboardComplete) {

            val df: DecimalFormat = DecimalFormat("#.#")


            // load sex
            if (Info.male) {
                settings_radio_male.isChecked = true
            } else {
                settings_radio_female.isChecked = true
            }

            // load height, weight and age
            settings_edit_height.setText(df.format(Info.height))
            settings_edit_weight.setText(df.format(Info.weight))
            settings_edit_birth.setText(df.format(Info.age))

            // load activity level
            // todo
            // look at doing a when statement here. comparing double is generally a no-no but they
            // should never be modified once set. may consider storing the string and evaluating
            // it to a double on load
            if (Info.activityLevel == 1.1) settings_radio_al_110.isChecked = true
            if (Info.activityLevel == 1.2) settings_radio_al_120 .isChecked = true
            if (Info.activityLevel == 1.37) settings_radio_al_137.isChecked = true
            if (Info.activityLevel == 1.55) settings_radio_al_155.isChecked = true
            if (Info.activityLevel == 1.725) settings_radio_al_1725.isChecked = true
            if (Info.activityLevel == 1.9) settings_radio_al_190.isChecked = true
        }



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
                    R.id.settings_rate_00 -> 0.0
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

                    // generate a toast message
                    toast(
                        ("BMI = %.2f\nBMR = %.1f\nTDEE = %.1f\nspFile = " + Info.spFilename)
                            .format(
                                Info.calculateBMI(),
                                Info.calculateBMR(),
                                Info.calculateTDEE()
                            )
                    )

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
