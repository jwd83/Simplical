package me.jwd.simplical

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_goal_weight.*

/**
 * A simple [Fragment] subclass.
 */
class GoalWeightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        goal_weight_info.text = "Based on your height the World Health Organization lists a healthy weight as between %.1f and %.1f.".format(
//            Info.getWeightAtBMI(18.5),
//            Info.getWeightAtBMI(25.0)
//        )

        goal_weight_info.text = getString(
            R.string.goal_text,
            Info.getWeightAtBMI(18.5),
            Info.getWeightAtBMI(25.0),
            Info.getWeightAtBMI(21.75),
            Info.getMillerIBW()
        )

        goal_weight_button.setOnClickListener {
            var success = false
            try {
                val newGoalWeight = edit_goal_weight_value.text.toString().toDouble()
                if(newGoalWeight in 50.0..600.0) {
                    Info.goalWeight = newGoalWeight

                    // check if this is part of the onboard process

                    if(Info.onboardComplete) {
                        Info.save(requireActivity())
                        success = true
                        findNavController().popBackStack()
                    } else {
                        // wrap up the onboarding process and launch the main activity
                        Info.onboardComplete = true
                        Info.save(requireActivity())
                        success = true
                        val ac = requireActivity()
                        val main = Intent(ac, MainActivity::class.java)
                        startActivity(main)
                        ac.finish()
                    }
                }
            } catch(e: Throwable) {

            }
            if(!success) {
                toast("Please enter a valid weight value.")
            }
        }
    }
}
