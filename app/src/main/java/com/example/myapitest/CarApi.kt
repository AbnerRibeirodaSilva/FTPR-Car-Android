package com.example.myapitest

import android.util.Log
import com.example.myapitest.model.Car
import com.example.myapitest.model.CarConsult
import com.example.myapitest.servico.CarServices
import com.example.myapitest.servico.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

class CarApi(private val service: Service = Service()) {

    private val carService: CarServices = service.getCarroServico()

    fun listCars(
        processingRequest: () -> Unit,
        onError: (mensagemErro: String) -> Unit,
        onSuccess: (ArrayList<Car>) -> Unit
    ) {
        val servico: Service = Service()
        val carroServico: CarServices = servico.getCarroServico()


        processingRequest()

        carroServico.listarCarros()
            .enqueue(object : Callback<java.util.ArrayList<Car>> {

                override fun onResponse(call: Call<java.util.ArrayList<Car>>, response: Response<java.util.ArrayList<Car>>) {

                    if (response.isSuccessful) {
                        val cars = response.body()
                        Log.d("my ball", "Response bem-sucedido: $cars")
                        cars?.forEach { car ->
                            Log.d("car", "Carro: ${car.name}")
                        }
                        onSuccess(cars ?: arrayListOf())
                    } else {
                        Log.e("CarApi", "Erro ao tentar consultar os carros: ${response.code()} - ${response.errorBody()?.string()}")
                        onError("Erro ao tentar-se consultar os carros.")
                    }

                }

                override fun onFailure(call: Call<java.util.ArrayList<Car>>, t: Throwable) {
                    Log.e("CarApi", "Falha na requisição: ${t.message}", t)
                    onError("Erro NA CONSULTA: ${ t.message }")
                }

            })

    }

    fun findById(
        carId: String,
        processingRequest: () -> Unit,
        onResult: (Result<Car>) -> Unit
    ) {
        processingRequest()

        carService.buscarCarroPeloId(carId).enqueue(object : Callback<CarConsult> {
            override fun onResponse(call: Call<CarConsult>, response: Response<CarConsult>) {
                if (response.isSuccessful) {
                    response.body()?.car?.let { car ->
                        onResult(Result.Success(car))
                    } ?: onResult(Result.Error("Carro não encontrado."))
                } else {
                    onResult(Result.Error("Erro na busca"))
                }
            }

            override fun onFailure(call: Call<CarConsult>, t: Throwable) {
                Log.e("CarApi", "Falha na requisição: ${t.message}", t)
                onResult(Result.Error("Erro: ${t.message}"))
            }
        })
    }

    fun deleteCar(
        carId: String,
        processingRequest: () -> Unit,
        onResult: (Result<String>) -> Unit
    ) {
        processingRequest()

        carService.deletarCarro(carId).enqueue(object : Callback<com.example.myapitest.model.Response> {
            override fun onResponse(call: Call<com.example.myapitest.model.Response>, response: Response<com.example.myapitest.model.Response>) {
                if (response.isSuccessful) {
                    onResult(Result.Success("Carro excluído!"))
                } else {
                    onResult(Result.Error("Erro ao excluir carro!"))
                }
            }

            override fun onFailure(call: Call<com.example.myapitest.model.Response>, t: Throwable) {
              Log.e("CarApi", "Falha na requisição: ${t.message}", t)
                onResult(Result.Error("Erro ao excluir: ${t.message}"))
            }
        })
    }
}
