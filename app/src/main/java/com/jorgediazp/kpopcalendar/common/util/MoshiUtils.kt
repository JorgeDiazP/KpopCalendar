package com.jorgediazp.kpopcalendar.common.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class MoshiUtils {

    companion object {

        @Throws(Exception::class)
        fun <T> String.getObjectFromJson(
            objectClass: Class<T>,
            moshiAdapters: List<Any>? = null
        ): T {
            val moshiBuilder = Moshi.Builder()
            moshiAdapters?.forEach() {
                moshiBuilder.add(it)
            }
            val moshi = moshiBuilder.build()
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(objectClass)
            return jsonAdapter.fromJson(this)!!
        }

        @Throws(Exception::class)
        fun <T> String.getObjectFromJson(
            type: Type,
            moshiAdapters: List<Any>? = null
        ): T {
            val moshiBuilder = Moshi.Builder()
            moshiAdapters?.forEach() {
                moshiBuilder.add(it)
            }
            val moshi = moshiBuilder.build()
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
            return jsonAdapter.fromJson(this)!!
        }

        @Throws(Exception::class)
        fun <T> T.getJsonFromObject(
            objectClass: Class<T>,
            moshiAdapters: List<Any>? = null
        ): String {
            val moshiBuilder = Moshi.Builder()
            moshiAdapters?.forEach() {
                moshiBuilder.add(it)
            }
            val moshi = moshiBuilder.build()
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(objectClass)
            return jsonAdapter.toJson(this)
        }

        @Throws(Exception::class)
        fun <T> T.getJsonFromObject(
            type: Type,
            moshiAdapters: List<Any>? = null
        ): String {
            val moshiBuilder = Moshi.Builder()
            moshiAdapters?.forEach() {
                moshiBuilder.add(it)
            }
            val moshi = moshiBuilder.build()
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
            return jsonAdapter.toJson(this)
        }
    }
}