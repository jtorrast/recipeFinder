package com.example.recipefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.recipefinder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<Models.ApiResponse> = getRetrofit()
                    .create(APIService::class.java).getCategories("categories.php")

                runOnUiThread {
                    if (call.isSuccessful) {
                        val apiResponse: Models.ApiResponse? = call.body()
                        if (apiResponse != null) {
                            val categories: List<Models.Category>? = apiResponse.categories
                            if (categories != null && categories.isNotEmpty()) {
                                fillSpinner(categories)
                            } else {
                                // Handle the case where categories is empty or null
                                showError("No se encontraron categorías válidas")
                            }
                        } else {
                            showError("Respuesta del servidor nula")
                        }
                    } else {
                        showError("Error en la solicitud al servidor")
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions, e.g., network errors
                showError("Error al obtener las categorías: ${e.message}")
            }
        }




    }

    private fun fillSpinner(categories: List<Models.Category>) {

        Log.e("Tamaño array categories", categories.size.toString())



        val categoryNames = categories
            .mapNotNull { it.categoria?.takeIf { it.isNotBlank() } }


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


}