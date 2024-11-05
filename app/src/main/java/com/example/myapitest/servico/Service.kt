package com.example.myapitest.servico

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {

    private val urlBase: String = "http://10.0.2.2:3000/"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(this.urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCarroServico(): CarServices {

        return this.getRetrofit().create(CarServices::class.java)
    }

}