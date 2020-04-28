package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_enter_favorite.*

// info on recycler views
// https://developer.android.com/guide/topics/ui/layout/recyclerview

/**
 * A simple [Fragment] subclass.
 */
class EnterFavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rgf = radio_group_favorite

        Info.favoriteFoods.forEach{
            val rb = RadioButton(requireActivity())
            rb.text = "(${it.calories}) ${it.name}"               // set the text of the button
            rb.id = View.generateViewId()   // create an ID on the fly
            it.id = rb.id
            rgf.addView(rb)                 // add button to our favorites group
        }

        button_choose_favorite.setOnClickListener {
            var success = false
            try {
                val selectedId = radio_group_favorite.checkedRadioButtonId
                val qty = edit_favorite_quantity.text.toString().toDouble()
                Info.favoriteFoods.forEach {
                    if (selectedId == it.id) {
                        Info.dailyFoodsAdd(it.calories, qty, it.name)
                        Info.save(requireActivity())
                        success = true
                        findNavController().popBackStack()
                    }
                }
            }
            catch (e: Throwable) {
            }
            if(!success) {
                toast("Please make a valid entry")
            }
        }

//
//        var favList: String = ""
//
//        Info.favorites.forEach{
//            favList += "(${it.calories}) ${it.name}\n"
//        }
//        text_favorites.text = favList
    }
}
