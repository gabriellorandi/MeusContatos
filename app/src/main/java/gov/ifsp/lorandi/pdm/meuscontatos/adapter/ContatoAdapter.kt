package gov.ifsp.lorandi.pdm.meuscontatos.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gov.ifsp.lorandi.pdm.meuscontatos.R
import gov.ifsp.lorandi.pdm.meuscontatos.model.Contato

class ContatoAdapter(
    private val contatoList: MutableList<Contato>,
    private val onContatoClickListener: OnContatoClickListener,
): RecyclerView.Adapter<ContatoAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(layoutContactView: View): RecyclerView.ViewHolder(layoutContactView),
        View.OnCreateContextMenuListener {
        val tvName: TextView = layoutContactView.findViewById(R.id.tvName)
        val tvPhoneNumber: TextView = layoutContactView.findViewById(R.id.tvPhoneNumber)
        val tvEmail: TextView = layoutContactView.findViewById(R.id.tvEmail)

        init {
            layoutContactView.setOnCreateContextMenuListener(this)
        }

        private val INVALID_POSITION = -1
        var index: Int = INVALID_POSITION

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add("Editar")?.setOnMenuItemClickListener {
                if (index != INVALID_POSITION) {
                    onContatoClickListener.onEditMenuItem(index)
                    true
                }
                false
            }
            menu?.add("Remover")?.setOnMenuItemClickListener {
                if (index != INVALID_POSITION) {
                    onContatoClickListener.onRemoveMenuItem(index)
                    true
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutContactView: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_contato, parent, false)

        return ContactViewHolder(layoutContactView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contatoList[position]

        holder.tvName.text = contact.nome
        holder.tvPhoneNumber.text = contact.telefone
        holder.tvEmail.text = contact.email

        holder.itemView.setOnClickListener{
            onContatoClickListener.onContact(position)
        }
        holder.index = position
    }

    override fun getItemCount(): Int = contatoList.size

}