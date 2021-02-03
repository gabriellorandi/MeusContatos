package gov.ifsp.lorandi.pdm.meuscontatos.dao

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import gov.ifsp.lorandi.pdm.meuscontatos.controller.UsuarioController
import gov.ifsp.lorandi.pdm.meuscontatos.model.Usuario

class UsuarioFirebase: UsuarioController() {
    private val USUARIO_LIST_REALTIME_DATABASE = "usuariosList"

    private val usuariosListRtDb = Firebase.database.getReference(USUARIO_LIST_REALTIME_DATABASE)

    private val usuariosList: MutableList<Usuario> = mutableListOf()
    init {
        usuariosListRtDb.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novoContato: Usuario = snapshot.getValue<Usuario>() ?: Usuario()

                if (usuariosList.indexOfFirst { it.email.equals(novoContato.email) } == -1) {
                    usuariosList.add(novoContato)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val usuarioEditado: Usuario = snapshot.getValue<Usuario>() ?: Usuario()

                val posicao = usuariosList.indexOfFirst { it.email.equals(usuarioEditado.email) }
                usuariosList[posicao] = usuarioEditado
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val usuarioRemovido: Usuario = snapshot.getValue<Usuario>() ?: Usuario()
                usuariosList.remove(usuarioRemovido)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
    }

    fun get(email: String): Usuario = usuariosList[usuariosList.indexOfFirst { it.email.equals(email) }]

    fun registrar(email: String, senha:String) = createOrUpdateUsuario(email,senha)

    fun logar(email: String, senha:String): Usuario = usuariosList[usuariosList.indexOfFirst { it.email.equals(email) && it.senha.equals(senha)}]

    private fun createOrUpdateUsuario(email: String, senha:String) {
        usuariosListRtDb.child(email).setValue(email)
        usuariosListRtDb.child(senha).setValue(senha)
    }
}