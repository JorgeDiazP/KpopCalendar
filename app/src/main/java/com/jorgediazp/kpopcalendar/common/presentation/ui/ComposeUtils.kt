package com.jorgediazp.kpopcalendar.common.presentation.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class ComposeUtils {

    companion object {

        @Composable
        fun LazyListState.isScrollingUp(): Boolean {
            var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
            var previousScrollOffset by remember(this) {
                mutableIntStateOf(
                    firstVisibleItemScrollOffset
                )
            }
            return remember(this) {
                derivedStateOf {
                    if (previousIndex != firstVisibleItemIndex) {
                        previousIndex > firstVisibleItemIndex
                    } else {
                        previousScrollOffset >= firstVisibleItemScrollOffset
                    }.also {
                        previousIndex = firstVisibleItemIndex
                        previousScrollOffset = firstVisibleItemScrollOffset
                    }
                }
            }.value
        }
    }
}