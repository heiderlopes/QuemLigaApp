package br.com.heiderlopes.whocallclone.model

import android.os.Parcel
import android.os.Parcelable

data class Contato(val numero: String, val tipo: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(numero)
        parcel.writeString(tipo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contato> {
        override fun createFromParcel(parcel: Parcel): Contato {
            return Contato(parcel)
        }

        override fun newArray(size: Int): Array<Contato?> {
            return arrayOfNulls(size)
        }
    }
}