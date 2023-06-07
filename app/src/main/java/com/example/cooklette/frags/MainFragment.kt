package com.example.cooklette.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cooklette.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Inflate the custom toolbar layout
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        inflater.inflate(R.layout.toolbar_add, toolbar)

        // Set the custom toolbar as the activity's toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Customize the toolbar buttons or views based on the fragment

        return view
    }


}