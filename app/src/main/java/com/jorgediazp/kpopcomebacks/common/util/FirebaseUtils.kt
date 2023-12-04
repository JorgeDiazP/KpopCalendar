package com.jorgediazp.kpopcomebacks.common.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_LONG

class FirebaseUtils {

    companion object {

        fun getRemoteConfigLong(key: FirebaseRemoteConfigKey): Long {
            val value = FirebaseRemoteConfig.getInstance().getLong(key.name)
            return if (value == DEFAULT_VALUE_FOR_LONG) key.defaultValue as Long else value
        }

    }
}