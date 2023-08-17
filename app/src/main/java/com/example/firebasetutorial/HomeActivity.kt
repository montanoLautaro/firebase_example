package com.example.firebasetutorial

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetutorial.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")


        setup(email ?: "", provider ?: "")
        initListeners()


        //Guardar Datos del usuario logueado

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }


    private fun initListeners() {
        binding.btnLogout?.setOnClickListener {
            //borrado de datos del prefs
            val prefs =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            //cerrar sesion
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }


        binding.btnSave.setOnClickListener {
            db.collection("entertainments").document("title")
        }
    }

    private fun setup(email: String, provider: String) {
        binding.tvEmail?.text = email
        binding.tvProveedor?.text = provider

    }
}

enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}