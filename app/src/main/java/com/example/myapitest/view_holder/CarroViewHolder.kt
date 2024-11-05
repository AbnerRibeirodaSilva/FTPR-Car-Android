package com.example.myapitest.view_holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapitest.R

class CarroViewHolder(view: View): ViewHolder(view) {

    val txtCarName: TextView = view.findViewById(R.id.txt_nome_carro)
    val txtCarYear: TextView = view.findViewById(R.id.txt_ano_carro)
    val txtCarLicence: TextView = view.findViewById(R.id.license)

}