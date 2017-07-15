package xyz.somniumproject.mongofinal

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 * Created by frang on 7/15/2017.
 */
class TramaMovil {
    var error : Boolean  = false
    var mensaje : String = " "

    class Deserializer : ResponseDeserializable<TramaMovil> {
        override fun deserialize(content: String) = Gson().fromJson(content, TramaMovil::class.java)
    }
}