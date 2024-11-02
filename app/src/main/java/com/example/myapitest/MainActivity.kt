package com.example.myapitest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapitest.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermission()
        setupView()

        // 1- Criar tela de Login com algum provedor do Firebase (Telefone, Google)
        //      Cadastrar o Seguinte celular para login de test: +5511912345678
        //      Código de verificação: 101010

        // 2- Criar Opção de Logout no aplicativo

        // 3- Integrar API REST /car no aplicativo
        //      API será disponibilida no Github
        //      JSON Necessário para salvar e exibir no aplicativo
        //      O Image Url deve ser uma foto armazenada no Firebase Storage
        //      { "id": "001", "imageUrl":"https://image", "year":"2020/2020", "name":"Gaspar", "licence":"ABC-1234", "place": {"lat": 0, "long": 0} }

        // Opcionalmente trabalhar com o Google Maps ara enviar o place
    }

    override fun onResume() {
        super.onResume()
        fetchItems()
    }

    private fun setupView() {
        binding.logoutButton.setOnClickListener{
            signOut()
        }
    }

    private fun requestLocationPermission() {
        // TODO
    }

    private fun fetchItems() {
        RetrofitInstance.api.getCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    val cars = response.body() ?: emptyList()
                    // Atualize o RecyclerView com a lista de carros
                    setupRecyclerView(cars)
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {

            }
        })
    }

    companion object{
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }


    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


}
