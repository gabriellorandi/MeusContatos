package gov.ifsp.lorandi.pdm.meuscontatos.view

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import gov.ifsp.lorandi.pdm.meuscontatos.R
import gov.ifsp.lorandi.pdm.meuscontatos.adapter.ContatoAdapter
import gov.ifsp.lorandi.pdm.meuscontatos.adapter.OnContatoClickListener
import gov.ifsp.lorandi.pdm.meuscontatos.controller.ContatoController
import gov.ifsp.lorandi.pdm.meuscontatos.databinding.ActivityMainBinding
import gov.ifsp.lorandi.pdm.meuscontatos.model.Contato
import gov.ifsp.lorandi.pdm.meuscontatos.view.MainActivity.Extras.EXTRA_CONTACT
import gov.ifsp.lorandi.pdm.meuscontatos.view.MainActivity.Extras.VIEW_CONTACT_ACTION
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), OnContatoClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var contatoController: ContatoController
    private lateinit var contatoList: MutableList<Contato>

    private lateinit var contatoAdapter: ContatoAdapter
    private lateinit var contactsLayoutManager: LinearLayoutManager

    private val NEW_CONTACT_REQUEST_CODE = 0
    private val EDIT_CONTACT_REQUEST_CODE = 1
    object Extras {
        val EXTRA_CONTACT = "EXTRA_CONTACT"
        val VIEW_CONTACT_ACTION = "VIEW_CONTACT_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        contatoController = ContatoController()

        contatoList = mutableListOf()

        contactsLayoutManager = LinearLayoutManager(this)

        contatoAdapter = ContatoAdapter(contatoList, this)

        activityMainBinding.rvContacts.adapter = contatoAdapter
        activityMainBinding.rvContacts.layoutManager = contactsLayoutManager

        GetContactsTask(this).execute()
    }

    private class GetContactsTask(context: MainActivity): AsyncTask<Void, Void, List<Contato>>() {

        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            super.onPreExecute()

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            activity.pbLoadingContacts.visibility = View.VISIBLE
            activity.rvContacts.visibility = View.GONE
        }

        override fun doInBackground(vararg p0: Void?): List<Contato> {
            Thread.sleep(5000)

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return mutableListOf()

            return activity.contatoController.buscaContatos()
        }

        override fun onPostExecute(result: List<Contato>?) {
            super.onPostExecute(result)

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            activity.pbLoadingContacts.visibility = View.GONE
            activity.rvContacts.visibility = View.VISIBLE

            if (result != null) {
                activity.contatoList.clear()
                activity.contatoList.addAll(result)
                activity.contatoAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miNewContact) {
            val newContactIntent = Intent(this, ContatoActivity::class.java)
            startActivityForResult(newContactIntent, NEW_CONTACT_REQUEST_CODE)
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_CONTACT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val novoContato = data.getParcelableExtra<Contato>(EXTRA_CONTACT)
            if (novoContato != null) {
                contatoController.insereContato(novoContato)

                contatoList.add(novoContato)
                contatoAdapter.notifyDataSetChanged()
            }
        } else {
            if (requestCode == EDIT_CONTACT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                val atualizarContato: Contato? = data.getParcelableExtra(EXTRA_CONTACT)
                if (atualizarContato != null) {
                    contatoController.atualizaContato(atualizarContato)

                    contatoList[contatoList.indexOfFirst { it.nome.equals(atualizarContato.nome) }] = atualizarContato
                    contatoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onContact(position: Int) {
        val contato: Contato = contatoList[position]

        val viewContactIntent = Intent(this, ContatoActivity::class.java)
        viewContactIntent.putExtra(EXTRA_CONTACT, contato)
        viewContactIntent.action = VIEW_CONTACT_ACTION

        startActivity(viewContactIntent)
    }

    override fun onEditMenuItem(position: Int) {
        val contato: Contato = contatoList[position]

        val editContactIntent = Intent(this, ContatoActivity::class.java)
        editContactIntent.putExtra(EXTRA_CONTACT, contato)
        startActivityForResult(editContactIntent, EDIT_CONTACT_REQUEST_CODE)
    }

    override fun onRemoveMenuItem(position: Int) {
        val contato: Contato = contatoList[position]

        if (position != -1) {
            contatoController.removeContato(contato.nome)
            contatoList.removeAt(position)
            contatoAdapter.notifyDataSetChanged()
        }
    }

}