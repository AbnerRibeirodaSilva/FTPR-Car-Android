package com.example.myapitest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.myapitest.R
import com.example.myapitest.model.Car
import com.example.myapitest.view_holder.CarroViewHolder
import java.util.ArrayList

class CarAdapter(private val context: Context, private val onViewCar: (String) -> Unit): Adapter<CarroViewHolder>() {

    var cars: ArrayList<Car> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarroViewHolder {
        val view: View = LayoutInflater.from(this.context).inflate(R.layout.item_car_layout, parent, false)

        return CarroViewHolder(view)
    }

    override fun getItemCount(): Int {

        return this.cars.size
    }

    override fun onBindViewHolder(holder: CarroViewHolder, position: Int) {
        val car: Car = this.cars[ position ]
        holder.txtCarName.text = car.name.uppercase()
        holder.txtCarYear.text = car.year
        holder.txtCarLicence.text = car.licence
        holder.itemView.setOnClickListener {
            this.onViewCar(car.id)
        }
    }
}