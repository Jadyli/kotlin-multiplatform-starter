package com.jady.feature.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import io.ktor.client.call.*
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
    println("CounterPresenter")

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is Change -> {
                    count += event.delta
                }
                Randomize -> {
                    loading = true
                    launch {
                        count = apiService.get(RandomNumber.RandomInteger(min = -20, max = 20)).body<String>().trim().toInt()
                        loading = false
                    }
                }
            }
        }
    }

    println("count: $count")
    return CounterModel(count, loading)
}
