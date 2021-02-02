package gov.ifsp.lorandi.pdm.meuscontatos.controller

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import gov.ifsp.lorandi.pdm.meuscontatos.dao.Auth
import gov.ifsp.lorandi.pdm.meuscontatos.view.MainActivity
class ContatoController : AppCompatActivity() {
    fun signIn(email: String, password: String) {

        Auth.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao logar!", Toast.LENGTH_SHORT).show()
            }

        finish()
    }

    fun signUp(email: String, password: String) {
        Auth.firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao registrar", Toast.LENGTH_SHORT).show()
                }
            }
        
         finish()
    }

    fun recuperar(email: String) {
        Auth.firebaseAuth.sendPasswordResetEmail(email)
        Toast.makeText(this, "Email enviado!", Toast.LENGTH_SHORT).show()

        finish()
    }
}
