package gov.ifsp.lorandi.pdm.meuscontatos.controller

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gov.ifsp.lorandi.pdm.meuscontatos.dao.ContatoFirebase
import gov.ifsp.lorandi.pdm.meuscontatos.model.Contato

import gov.ifsp.lorandi.pdm.meuscontatos.view.MainActivity
open class ContatoController : AppCompatActivity() {

    val contatoDao: ContatoFirebase
    init {
        contatoDao = ContatoFirebase()
    }


    fun insereContato(contato: Contato) = contatoDao.createContato(contato)
    fun buscaContato(nome: String) = contatoDao.readContato(nome)
    fun buscaContatos() = contatoDao.readContatos()
    fun atualizaContato(contato: Contato) = contatoDao.updateContato(contato)
    fun removeContato(nome: String) = contatoDao.deleteContato(nome)

}
