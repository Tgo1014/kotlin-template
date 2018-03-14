package dk.eboks.app.domain.models.protocol

import com.google.gson.annotations.SerializedName

/**
 * Created by bison on 09-02-2018.
 */
enum class ErrorType(val type : String) {
    @SerializedName("error") ERROR("error"),
    @SerializedName("warning") WARNING("warning"),
    @SerializedName("information") INFORMATION("information");

    override fun toString(): String {
        when(this)
        {
            ERROR -> return "error"
            WARNING -> return "warning"
            INFORMATION -> return "information"
            else -> return "error"
        }
    }
}