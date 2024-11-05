package com.example.myapitest.servico

import com.example.myapitest.model.Car
import com.example.myapitest.model.CarConsult
import com.example.myapitest.model.Response
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.ArrayList

interface CarServices {

    @GET("car")
    fun listarCarros(): Call<ArrayList<Car>>

    @GET("car/{idCarro}")
    fun buscarCarroPeloId(@Path("idCarro") idCarro: String): Call<CarConsult>

    @DELETE("car/{idCarroDeletar}")
    fun deletarCarro(@Path("idCarroDeletar") idCarroDeletar: String): Call<Response>

}