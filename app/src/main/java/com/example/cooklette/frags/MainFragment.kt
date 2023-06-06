package com.example.cooklette.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cooklette.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonA).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_ingredientFragment)
        }

        view.findViewById<Button>(R.id.buttonB).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_savedFragment)
        }
    }

}