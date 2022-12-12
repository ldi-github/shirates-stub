package shirates.stub.commons.extensions

import com.google.gson.GsonBuilder

fun Any?.toJson(): String {

    return GsonBuilder().setPrettyPrinting().create().toJson(this)
}