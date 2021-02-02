package gov.ifsp.lorandi.pdm.meuscontatos.dao

import com.google.firebase.auth.FirebaseAuth

object Auth {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
}