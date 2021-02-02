package gov.ifsp.lorandi.pdm.meuscontatos.adapter

interface OnContatoClickListener {

    fun onContact(position: Int)

    fun onEditMenuItem(position: Int)
    fun onRemoveMenuItem(position: Int)

}