package gov.ifsp.lorandi.pdm.meuscontatos.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gov.ifsp.lorandi.pdm.meuscontatos.controller.ContatoController
import gov.ifsp.lorandi.pdm.meuscontatos.databinding.ActivityRecuperarSenhaBinding

class RecuperaSenhaActivity : AppCompatActivity() {

    private lateinit var activityForgetPasswordBinding: ActivityRecuperarSenhaBinding

    private lateinit var contatoController: ContatoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityForgetPasswordBinding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityForgetPasswordBinding.root)
    }

    fun recuperar(view: View) {
        val email = activityForgetPasswordBinding.emailTexto.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email Inválido", Toast.LENGTH_SHORT).show()
            return
        }


        contatoController.recuperar(email)

    }


}