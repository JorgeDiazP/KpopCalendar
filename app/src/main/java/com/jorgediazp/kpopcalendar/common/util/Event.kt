package com.jorgediazp.kpopcalendar.common.util

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun getContent(): T = content

    /**
     * Get if it is handled and mark it handled to true
     */
    fun isNecessaryToHandle(): Boolean {
        val previous = hasBeenHandled
        hasBeenHandled = true
        return !previous
    }
}