package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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

        Info.favorites.forEach{
            val rb = RadioButton(requireActivity())
            rb.text = it.name               // set the text of the button
            rb.id = View.generateViewId()   // create an ID on the fly
            rgf.addView(rb)                 // add button to our favorites group
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
