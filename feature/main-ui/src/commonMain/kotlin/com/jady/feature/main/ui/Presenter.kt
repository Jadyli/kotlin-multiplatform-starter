package com.jady.feature.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @author jady
 * @since 2023/11/4 18:35
 * email: 1257984872@qq.com
 */
sealed interface CounterEvent
data class Change(val delta: Int) : CounterEvent
object Randomize : CounterEvent

data class CounterModel(
    val value: Int,
    val loading: Boolean,
)

@Composable
fun CounterPresenter(
    events: Flow<CounterEvent>,
    apiService: ApiService,
): CounterModel {
    var count by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is Change -> {
                    count += event.delta
                }
                Randomize -> {
                    loading = true
                    launch {
                        // We want to observe these two state changes atomically.
                        Snapshot.withMutableSnapshot {
                            count = apiService.getRandomInteger(-20, 20)
                            loading = false
                        }
                    }
                }
            }
        }
    }

    return CounterModel(count, loading)
}
