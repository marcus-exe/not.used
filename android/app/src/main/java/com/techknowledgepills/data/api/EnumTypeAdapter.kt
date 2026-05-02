package com.techknowledgepills.data.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.techknowledgepills.domain.model.ContentType
import com.techknowledgepills.domain.model.StressLevel

class ContentTypeAdapter : TypeAdapter<ContentType>() {
    override fun write(out: JsonWriter, value: ContentType) {
        out.value(value.ordinal + 1) // Backend uses 1-based enum
    }

    override fun read(`in`: JsonReader): ContentType {
        val value = `in`.nextInt()
        return ContentType.values()[value - 1]
    }
}

class StressLevelAdapter : TypeAdapter<StressLevel>() {
    override fun write(out: JsonWriter, value: StressLevel) {
        out.value(value.ordinal + 1) // Backend uses 1-based enum
    }

    override fun read(`in`: JsonReader): StressLevel {
        val value = `in`.nextInt()
        return StressLevel.values()[value - 1]
    }
}

