package gov.ifsp.lorandi.pdm.meuscontatos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import gov.ifsp.lorandi.pdm.meuscontatos.controller.ContatoController
import gov.ifsp.lorandi.pdm.meuscontatos.controller.UsuarioController
import gov.ifsp.lorandi.pdm.meuscontatos.databinding.ActivityLoginBinding

const val TAMANHA_MIN_SENHA = 8

class SingInActivity : AppCompatActivity() {


    private lateinit var activitySingInBinding: ActivityLoginBinding

    private lateinit var usuarioController: UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySingInBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activitySingInBinding.root)
    }

    fun login(view: View) {
        val email = activitySingInBinding.emailTexto.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val password = activitySingInBinding.senhaTexto.text.toString()

        if (password.isEmpty() || password.length <= TAMANHA_MIN_SENHA) {
            Toast.makeText(this, "Senha inválida", Toast.LENGTH_SHORT).show()
            return
        }


        usuarioController.signIn(email,password)

    }

    fun recuperar(view: View) {
        startActivity(Intent(this, RecuperaSenhaActivity::class.java))
    }

    fun registrar(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}