package gov.ifsp.lorandi.pdm.meuscontatos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import gov.ifsp.lorandi.pdm.meuscontatos.databinding.ActivityContatoBinding
import gov.ifsp.lorandi.pdm.meuscontatos.model.Contato

class ContatoActivity : AppCompatActivity() {

    private lateinit var activityContactBinding: ActivityContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityContactBinding = ActivityContatoBinding.inflate(layoutInflater)
        setContentView(activityContactBinding.root)

        val contato: Contato? = intent.getParcelableExtra(MainActivity.Extras.EXTRA_CONTACT)
        if (contato != null) {
            activityContactBinding.etContactName.setText(contato.nome)
            activityContactBinding.etContactName.isEnabled = false
            activityContactBinding.telefoneTexto.setText(contato.telefone)
            activityContactBinding.emailTexto.setText(contato.email)
            if (intent.action == MainActivity.Extras.VIEW_CONTACT_ACTION) {
                activityContactBinding.telefoneTexto.isEnabled = false
                activityContactBinding.emailTexto.isEnabled = false
                activityContactBinding.btSave.visibility = View.GONE
            }
        }

        activityContactBinding.btSave.setOnClickListener {
            val newContact = Contato(
                activityContactBinding.etContactName.text.toString(),
                activityContactBinding.telefoneTexto.text.toString(),
                activityContactBinding.emailTexto.text.toString()
            )

            val returnIntent = Intent()
            returnIntent.putExtra(MainActivity.Extras.EXTRA_CONTACT, newContact)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

}