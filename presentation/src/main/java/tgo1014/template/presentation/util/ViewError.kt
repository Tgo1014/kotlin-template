package tgo1014.template.presentation.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewError(
    var title: String,
    var message: String,
    var code: Int = 400
) : Parcelable
