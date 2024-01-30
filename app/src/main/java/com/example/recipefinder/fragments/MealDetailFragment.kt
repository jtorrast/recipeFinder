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
import com.squareup.picasso.Picasso
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

        var recipe: ModelsMeals.MealDetail? = null


        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ModelsMeals.ApiResponseMealDetail> = getRetrofit()
                .create(APIService::class.java).getMealDetail("lookup.php?i=$idMeal")

            val meal: ModelsMeals.ApiResponseMealDetail? = call.body()

            mActivity?.runOnUiThread {
                if (call.isSuccessful) {

                    val mealDetail: List<ModelsMeals.MealDetail>? = meal?.mealDetail

                    mealDetail?.forEach { detail->
                        binding.tvTitleMealDetail.text = detail.mealName
                        Picasso.get().load(detail.image).fit().into(binding.imgMealDetail)
                        binding.tvYoutube.text = detail.youtubeLink
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        /*
        * Comprobar si isChecked{
        *   guardamos la receta en la bd
        * }
        * */
        if (binding.checkRecipe.isChecked) {
            showError("I like")
        } else {
            showError("No check")
        }
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