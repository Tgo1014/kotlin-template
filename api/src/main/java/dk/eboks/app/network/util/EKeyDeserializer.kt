package dk.eboks.app.network.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import dk.eboks.app.domain.models.channel.ekey.BaseEkey
import dk.eboks.app.domain.models.channel.ekey.Ekey
import dk.eboks.app.domain.models.channel.ekey.Login
import dk.eboks.app.domain.models.channel.ekey.Note
import dk.eboks.app.domain.models.channel.ekey.Pin
import java.lang.reflect.Type
import java.util.ArrayList
import java.util.TreeMap

class EKeyDeserializer : JsonDeserializer<List<BaseEkey>> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<BaseEkey> {

        val list = ArrayList<BaseEkey>()
        val ja = json.asJsonArray

        for (je in ja) {
            val type = je.asJsonObject.get("eKeyType").asString
            val c = map[type] ?: throw RuntimeException("Unknow class: $type")
            list.add(context.deserialize<BaseEkey>(je, c))
        }

        return list
    }

    companion object {

        private val map = TreeMap<String, Class<*>>()

        init {
            map["Login"] = Login::class.java
            map["Note"] = Note::class.java
            map["Pin"] = Pin::class.java
            map["Ekey"] = Ekey::class.java
        }
    }
}