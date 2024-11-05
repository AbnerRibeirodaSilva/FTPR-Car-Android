package com.example.myapitest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapitest.adapter.CarAdapter
import com.example.myapitest.databinding.ActivityMainBinding
import com.example.myapitest.model.Car

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carAdapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermission()
        setupView()
    }

    override fun onResume() {
        fetchItems()
        super.onResume()

    }

    private fun setupView() {
        this.binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.logoutButton.setOnClickListener{
            signOut()
        }
        this.carAdapter = CarAdapter(this) { idCarro ->
            val intentDetalhesCarro = Intent(this, CarDetailsActivity::class.java)
            intentDetalhesCarro.putExtra("id_carro", idCarro)
            startActivity(intentDetalhesCarro)
            finish()
        }

        this.binding.recyclerView.adapter = this.carAdapter

    }

    private fun signOut(){
      //  FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }

    private fun requestLocationPermission() {

    }

    private fun notification(sucesso: Boolean, mensagem: String) {

    }

    private fun fetchItems() {

        try {
            val carApi: CarApi = CarApi()


            val onProcessandoRequisicaoConsultarCarros: () -> Unit = {

            }


            val onError: (String) -> Unit = { mensagemErro ->
                this.notification(false, mensagemErro)
            }


            val onSuccess: (ArrayList<Car>) -> Unit = { carros ->
                val msgAlertaSucesso: String = if (carros.size == 0) "Existe um total de ${ carros.size } cadastrados no sistema."
                else "Não existem carros cadastrados no sistema."

                this.notification(true, msgAlertaSucesso)


                this.apresentarCarros(carros)
            }

            carApi.listCars(
                processingRequest = onProcessandoRequisicaoConsultarCarros,
                onError = onError,
                onSuccess = onSuccess

            )

        } catch (e: Exception) {
            Toast.makeText(this, "Erro: ${ e.message }", Toast.LENGTH_LONG)
                .show()
        }

    }

    private fun apresentarCarros(carros: ArrayList<Car>) {
        Log.d("MainActivity", "Carros recebidos: ${carros.size}")
        this.carAdapter.cars = carros
        this.carAdapter.notifyDataSetChanged()
    }

    companion object{
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

}
