package com.example.recipefinder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipefinder.R
import com.example.recipefinder.activities.MainActivity
import com.example.recipefinder.api.APIService
import com.example.recipefinder.databinding.FragmentMealDetailBinding
import com.example.recipefinder.databinding.FragmentMealsBinding
import com.example.recipefinder.models.ModelsMeals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MEAL_ID = "MealId"


/**
 * A simple [Fragment] subclass.
 * Use the [MealDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null


    private lateinit var binding: FragmentMealDetailBinding
    private var mActivity: MainActivity?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(ARG_MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealDetailBinding.inflate(inflater,container, false)
        mActivity = activity as? MainActivity

        getRecipe(mealId!!)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getRecipe(idMeal: String){

        //var recipe: ModelsMeals.MealDetail? = null

        /*println("METODO GETRECIPE")

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ModelsMeals.ApiResponseMealDetail> = getRetrofit()
                .create(APIService::class.java).getMealDetail("lookup.php?i=$idMeal")


            mActivity?.runOnUiThread {
                if (call.isSuccessful) {
                    println("CALLL->> ${call.isSuccessful}")
                    val apiResponseMealDetail: ModelsMeals.ApiResponseMealDetail? = call.body()

                    if (apiResponseMealDetail != null) {
                        //recipe = apiResponseMealDetail.mealDetail
                        println(recipe)
                        if (recipe != null) {
                            //binding.tvTitleMealDetail.text = recipe?.mealName
                        } else {
                            showError("Receta es nula")
                        }
                    }
                }
            }
        }*/
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showError(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
    }


    companion object {
        @JvmStatic
        fun newInstance(idMeal: String) =
            MealDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MEAL_ID, idMeal)

                }
            }
    }
}