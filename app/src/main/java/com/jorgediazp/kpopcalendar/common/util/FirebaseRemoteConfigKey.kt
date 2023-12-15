package com.jorgediazp.kpopcalendar.common.util

enum class FirebaseRemoteConfigKey(val defaultValue: Any) {
    CALENDAR_MIN_TIME(defaultValue = 1577833200000L), //UTC
    CALENDAR_MAX_TIME(defaultValue = 1735689599000L) //UTC
}