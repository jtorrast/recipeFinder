package com.example.recipefinder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefinder.R
import com.example.recipefinder.adapters.MealAdapter
import com.example.recipefinder.adapters.OnClickListener
import com.example.recipefinder.databinding.FragmentMealsBinding
import com.example.recipefinder.models.ModelsMeals
import com.example.recipefinder.pojos.MealPojo

private const val ARG_MEALS = "Meals"

class MealsFragment : Fragment(), OnClickListener {

    private lateinit var mealAdapter: MealAdapter
    private lateinit var linearLayoutManager: GridLayoutManager

    private lateinit var binding: FragmentMealsBinding
    private lateinit var listener: MealListener
    private lateinit var meals: MutableList<MealPojo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        meals = (arguments?.getSerializable(ARG_MEALS) as? Array<MealPojo>)?.toMutableList() ?: mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealsBinding.inflate(inflater, container, false)

        mealAdapter = MealAdapter(meals, this)
        linearLayoutManager = GridLayoutManager(context, 2)

        binding.rvMeals.apply {
            layoutManager = linearLayoutManager
            adapter = mealAdapter
        }
        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance(m: MutableList<MealPojo>) =
            MealsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MEALS, m.toTypedArray())
                }
            }
    }

    override fun onClick(meal: MealPojo) {
        if (listener != null) {
            listener.onmealSelected(meal)
        }
    }

    fun setListener(listener: MealListener){
        this.listener = listener
    }


}