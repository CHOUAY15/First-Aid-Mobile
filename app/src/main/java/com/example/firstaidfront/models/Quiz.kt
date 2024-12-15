package com.example.firstaidfront.models

import android.os.Parcel
import android.os.Parcelable

data class Quiz(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(question)
        parcel.writeStringList(options)
        parcel.writeInt(correctAnswerIndex)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz = Quiz(parcel)
        override fun newArray(size: Int): Array<Quiz?> = arrayOfNulls(size)
    }
}