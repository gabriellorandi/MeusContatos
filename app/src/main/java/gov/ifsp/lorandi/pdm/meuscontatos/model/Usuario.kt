package gov.ifsp.lorandi.pdm.meuscontatos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Usuario(
    var email: String = "",
    var senha: String = ""
): Parcelable
