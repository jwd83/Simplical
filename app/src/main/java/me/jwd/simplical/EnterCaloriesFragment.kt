package me.jwd.simplical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_enter_calories.*

class EnterCaloriesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_calories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redrawUI()
    }

    private fun redrawUI() {
        var success = false

        button_calorie_entry.setOnClickListener {
            try{

                val calories = text_calories.text.toString().toDouble()
                val quantity = text_quantity.text.toString().toDouble()
                var source = text_source.text.toString().trim()

                if (source == "") {
                    source = "Unlisted"
                }

                Info.dailyFoodsAdd(calories, quantity, source)

                if(switch_save_to_favorites.isChecked && source != "Unlisted") {
                    Info.addFavorite(source, calories)
                }

                Info.save(requireActivity())
                success = true

                findNavController().popBackStack()

            } catch(e: Throwable) {

            }
            if (!success) {
                toast( "Please respond to all questions above")
            }
        }
    }
}
