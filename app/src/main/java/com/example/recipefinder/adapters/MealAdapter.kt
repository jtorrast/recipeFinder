package com.example.recipefinder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recipefinder.R
import com.example.recipefinder.databinding.ItemMealBinding
import com.example.recipefinder.models.ModelsMeals
import com.example.recipefinder.pojos.MealPojo
import com.squareup.picasso.Picasso

class MealAdapter(private val meals: MutableList<MealPojo>, private val listener: OnClickListener):
    RecyclerView.Adapter<MealAdapter.ViewHolder>() {

        private lateinit var context: Context
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemMealBinding.bind(view)

        fun setListener(meal: MealPojo){
            binding.root.setOnClickListener {
                listener.onClick(meal)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals.get(position)

        holder.apply{
            setListener(meal)
            binding.tvName.text= meal.getMealName()
            Picasso.get().load(meal.getImage()).into(binding.imgPhoto)//usando picaso

            //Glide
           /* Glide.with(context)
                .load(meal.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)*/

        }
    }

}