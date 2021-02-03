package gov.ifsp.lorandi.pdm.meuscontatos.controller

import androidx.appcompat.app.AppCompatActivity
import gov.ifsp.lorandi.pdm.meuscontatos.dao.UsuarioFirebase

open class UsuarioController : AppCompatActivity() {

    val usuarioDao: UsuarioFirebase
    init {
        usuarioDao = UsuarioFirebase()
    }

    fun signIn(email: String, senha: String) = usuarioDao.logar(email,senha)
    fun signUp(email: String, senha: String) = usuarioDao.registrar(email,senha)
    fun recuperar(email: String) = usuarioDao.get(email)

}
