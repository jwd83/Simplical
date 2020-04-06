package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

                val weight = edit_weight.text.toString().toDouble()
                val height = edit_height.text.toString().toDouble()
                val birthDate = edit_birth.text.toString()

                val age = 37
                if(height >= 48 && height < 108 && weight > 50 && weight < 3000) {
                    // solve bmi/bmr values
                    val bmi = 703.0717 * (weight / (height*height))
                    val bmr = if(sexIsMale) {
                        (66 + (6.2 * weight) + (12.7 * height) - (6.76 * age))
                    } else {
                        (655.1 + 4.35 * weight + 4.7 * height - 4.7 * age)
                    }

                    // generate a toast message
                    Toast.makeText(
                        context,
                        ("BMI = %.2f\nBMR = %.2f").format(bmi, bmr),
                        Toast.LENGTH_LONG
                    ).show()

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