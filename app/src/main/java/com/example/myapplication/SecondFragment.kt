package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

            val weight_empty = edit_weight.text.toString().isEmpty()
            val height_empty = edit_height.text.toString().isEmpty()

            if(weight_empty || height_empty) {
                Toast.makeText(context, "Please respond to all questions above", Toast.LENGTH_LONG).show()
            } else {

                val weight = edit_weight.text.toString().toDouble()
                val height = edit_height.text.toString().toDouble()
                val age = 37

                if(height > 24 && height < 108 && weight > 50 && weight < 3000) {
                    val bmi = 703.0717 * (weight / (height*height))
                    val maleBmr = (66 + (6.2 * weight) + (12.7 * height) - (6.76 * age))
                    val femaleBmr = (655.1 + 4.35 * weight + 4.7 * height - 4.7 * age)
                    Toast.makeText(context, ("BMI = %.2f\nmBMR = %.2f\n" +
                            "fBMR = %.2f").format(bmi, maleBmr, femaleBmr), Toast.LENGTH_LONG).show()
                }
            }



        }

//
//        view.findViewById<Button>(R.id.button_looks_good).setOnClickListener{
//            Toast.makeText(context, "Please review your information", Toast.LENGTH_LONG).show()
//
//        }
//
//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }


    }
}