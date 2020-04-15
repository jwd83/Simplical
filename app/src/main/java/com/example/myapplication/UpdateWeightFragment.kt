package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_update_weight.*
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 */
class UpdateWeightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_update_weight.setOnClickListener{
            var success = false
            try {
                val newWeight = edit_weight_value.text.toString().toDouble()
                if(newWeight in 50.0..600.0) {
                    Info.weight = newWeight
                    Info.save(requireActivity())
                    success = true
                    findNavController().popBackStack()
                }
            } catch(e: Throwable) {


            }
            if(!success) {
                toast("Please enter a valid weight value.")
            }
        }
    }
}
