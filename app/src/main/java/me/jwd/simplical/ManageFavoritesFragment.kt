package me.jwd.simplical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_manage_favorites.*

/**
 * A simple [Fragment] subclass.
 */
class ManageFavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_favorites, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val verticalList = favorites_vertical_list

        Info.favoriteFoods.forEach {
            val foodSwitch = Switch(requireActivity())
            foodSwitch.text = "(${it.calories}) ${it.name}"
            foodSwitch.id = View.generateViewId()
            it.id = foodSwitch.id
            verticalList.addView(foodSwitch)
        }

        button_remove_from_favorites.setOnClickListener {
            try {
                var foodsToRemove = mutableListOf<Int>()

                for((idx, food: Food) in Info.favoriteFoods.withIndex()) {
                    val favoriteFoodSwitch: Switch = view.findViewById(food.id)
                    if (favoriteFoodSwitch.isChecked) {
                        foodsToRemove.add(idx)
                    }
                }

                foodsToRemove.asReversed().forEach {
                    Info.favoriteFoods.removeAt(it)
                }

                Info.save(requireActivity())
                findNavController().popBackStack()
            } catch (e: Throwable) {
                toast("An error occurred:\n${e.message}")
            }
        }
    }
}
