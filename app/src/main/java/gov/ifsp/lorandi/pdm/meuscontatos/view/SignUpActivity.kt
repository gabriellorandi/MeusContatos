package gov.ifsp.lorandi.pdm.meuscontatos.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gov.ifsp.lorandi.pdm.meuscontatos.controller.ContatoController
import gov.ifsp.lorandi.pdm.meuscontatos.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var activitySignUpBinding: ActivitySignUpBinding

    private lateinit var contatoController: ContatoController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)
    }

    fun registrar(view: View) {

        val email = activitySignUpBinding.emailTexto.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show()
            return
        }

        val password = activitySignUpBinding.senhaTexto.text.toString()
        val repeatPassword = activitySignUpBinding.reSenhaTexto.text.toString()

        if (password.isEmpty() || password.length <= TAMANHA_MIN_SENHA || !password.equals(repeatPassword)) {
            Toast.makeText(this, "Senhas invalidas!", Toast.LENGTH_SHORT).show()
            return
        }

        contatoController.signUp(email,password)


    }


}