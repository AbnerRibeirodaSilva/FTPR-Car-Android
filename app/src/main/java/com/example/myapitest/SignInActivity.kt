package com.example.myapitest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapitest.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGoogleSignIn()
        setupView()
    }

    private fun setupGoogleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("549502219-o3nu64vhc16tjlumpn984cq29kldg8e5.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { idToken->
                    firebaseAuthGoogle(idToken)
                }

            }catch (e: ApiException){
                Log.e("LoginActivity", "Google Sign in Error")
            }
        }

    }

    private fun firebaseAuthGoogle(idToken: String){
        val crtedencial = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(crtedencial)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    Log.d("LoginActivity","User: ${user?.uid}")
                    startActivity(MainActivity.newIntent(this))
                }else{
                    Toast.makeText(
                    this,
                    "Deu ruim no login",
                    Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun setupView(){
        binding.googleSignInButtom.setOnClickListener{
            signIn()
        }
    }
    private fun signIn(){
  //  googleSignInLauncher.launch(googleSignInClient.signInIntent)
        startActivity(MainActivity.newIntent(this))
    }

    companion object{
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}