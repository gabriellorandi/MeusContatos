package gov.ifsp.lorandi.pdm.meuscontatos.service

import gov.ifsp.lorandi.pdm.meuscontatos.dao.ContactDAO
import gov.ifsp.lorandi.pdm.meuscontatos.model.Contato
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

private val CONTACTS_LIST_REALTIME_DATABASE = "contatos"

class ContatoService: ContactDAO {

    private val contatoFireBase = Firebase.database.getReference(CONTACTS_LIST_REALTIME_DATABASE)
    private var contatos: MutableList<Contato> = mutableListOf()

    init {
        contatoFireBase.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newContato: Contato = snapshot.getValue<Contato>() ?: Contato()

                if (contatos.indexOfFirst { it.nome.equals(newContato.nome) } == -1) {
                    contatos.add(newContato)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val editedContato: Contato = snapshot.getValue<Contato>() ?: Contato()

                val index = contatos.indexOfFirst { it.nome.equals(editedContato.nome) }
                contatos[index] = editedContato
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val deletedContato: Contato = snapshot.getValue<Contato>() ?: Contato()
                contatos.remove(deletedContato)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
    }

    override fun save(contato: Contato) = createOrUpdateContact(contato)

    override fun get(name: String): Contato = contatos[contatos.indexOfFirst { it.nome == name }]

    override fun getAll(): MutableList<Contato> = contatos

    override fun update(contato: Contato) = createOrUpdateContact(contato)

    override fun delete(name: String) {
        contatoFireBase.child(name).removeValue()
    }

    private fun createOrUpdateContact(contato: Contato) {
        contatoFireBase.child(contato.nome).setValue(contato)
    }

}