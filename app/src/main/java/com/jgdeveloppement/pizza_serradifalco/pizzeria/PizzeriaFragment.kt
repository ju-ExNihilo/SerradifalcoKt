package com.jgdeveloppement.pizza_serradifalco.pizzeria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jgdeveloppement.pizza_serradifalco.R

/**
 * A simple [Fragment] subclass.
 */
class PizzeriaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pizzeria, container, false)
    }

}
