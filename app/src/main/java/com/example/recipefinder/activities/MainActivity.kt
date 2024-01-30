package com.example.recipefinder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.recipefinder.R
import com.example.recipefinder.api.APIService
import com.example.recipefinder.databinding.ActivityMainBinding
import com.example.recipefinder.fragments.MealDetailFragment
import com.example.recipefinder.fragments.MealListener
import com.example.recipefinder.fragments.MealsFragment
import com.example.recipefinder.models.ModelsCategories
import com.example.recipefinder.models.ModelsMeals
import com.example.recipefinder.pojos.MealPojo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), MealListener {

    private lateinit var binding: ActivityMainBinding
    private val mealsList = mutableListOf<MealPojo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (mealsList.isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<ModelsCategories.ApiResponseCategories> = getRetrofit()
                    .create(APIService::class.java).getCategories("categories.php")

                runOnUiThread {
                    if (call.isSuccessful) {
                        val apiResponseCategories: ModelsCategories.ApiResponseCategories? = call.body()

                        if (apiResponseCategories != null) {
                            val categories: List<ModelsCategories.Category>? = apiResponseCategories.categories

                            if (categories != null && categories.isNotEmpty()) {

                                    fillSpinner(categories)

                            } else {
                                showError("No se encontraron categorías válidas")
                            }
                        } else {
                            showError("Respuesta del servidor nula")
                        }
                    } else {
                        showError("Error en la solicitud al servidor")
                    }
                }
            }
        }


        /*Evento que para cuando seleccionemos un item del spiner envie el string*/
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                //ahora falta enviar el string para que complete la url, tendremos que hacer otra consulta a la api para que muestre las recetas

                val tipo = parent?.getItemAtPosition(position) as? String
                mealsList.clear()

                getMeals(tipo)

                //showError(tipo!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //Control del bottom navigation

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked =true
            when(it.itemId){
                R.id.navigation_home->{
                    showError("HOME")
                }
                R.id.navigation_fav-> {
                    showError("FAV")
                }
            }
            false
        }
        
    }

    private fun getMeals(category: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ModelsMeals.ApiResponseMeals> = getRetrofit()
                .create(APIService::class.java).getMealsByCategory("filter.php?c=$category")

            runOnUiThread {
                if (call.isSuccessful){
                    val apiResponseMeals: ModelsMeals.ApiResponseMeals? = call.body()

                    if (apiResponseMeals != null) {
                        val meals: List<ModelsMeals.Meal>? = apiResponseMeals.meals

                        if (meals != null && meals.isNotEmpty()){
                            getRecipies(meals)
                        }else{
                            showError("No hay recetas")
                        }
                    }else{
                        showError("Respuesta del servidor nula")
                    }
                }else{
                    showError("Error en la solicitud al servidor")
                }
            }
        }
    }

    private fun getRecipies(meals: List<ModelsMeals.Meal>) {

        meals.map { it.mealName }

        meals.forEach { meals->
            val nameMeal = meals.mealName
            val img = meals.image
            val id = meals.idMeal

            mealsList.add(MealPojo(nameMeal, img, id))

            val frgMeals = MealsFragment.newInstance(mealsList)

            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, frgMeals).commit()
            frgMeals.setListener(this)

        }

        /*if (mealsList.isNotEmpty()) {
            mealsList.forEach { meal ->
                println(meal)
                //enviar
            }
        }*/
    }


    private fun fillSpinner(categories: List<ModelsCategories.Category>) {

        Log.e("Tamaño array categories", categories.size.toString())

        val categoryNames = categories.map { it.categoria }


        if (categoryNames.isNotEmpty()) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val spinner = binding.spinner
            spinner.adapter = adapter
        } else {
            // Handle the case where there are no valid category names
            // You can show an error message or take appropriate action
            showError("categoria name vacia")
        }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun showError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onmealSelected(meal: MealPojo) {
        //Toast.makeText(this, meal.getIdMeal().toString(), Toast.LENGTH_SHORT).show()

        /*val idMeal = meal.getIdMeal().toString()

        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ModelsMeals.ApiResponseMealDetail> = getRetrofit()
                .create(APIService::class.java).getMealDetail("lookup.php?i=$idMeal")

            val meal: ModelsMeals.ApiResponseMealDetail? = call.body()

            runOnUiThread {
                if (call.isSuccessful) {

                   val mealDetail: List<ModelsMeals.MealDetail>? = meal?.mealDetail

                    mealDetail?.forEach { detail->
                        println(detail.mealName)
                    }
                }
            }

        }*/


        val frgMealDetail = MealDetailFragment.newInstance(meal.getIdMeal().toString())

        supportFragmentManager.beginTransaction()
            .add(R.id.containerMain, frgMealDetail)
            .addToBackStack(null)
            .commit()
    }


}