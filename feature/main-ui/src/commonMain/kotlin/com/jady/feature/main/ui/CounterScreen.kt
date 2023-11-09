package com.jady.feature.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author jady
 * @since 2023/11/4 16:45
 * email: 1257984872@qq.com
 */
@Composable
fun CounterScreen() {
    val scope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    val eventsFlow = remember { MutableSharedFlow<CounterEvent>(extraBufferCapacity = 20) }
    val models = remember {
        scope.launchMolecule(mode = RecompositionMode.Immediate) {
            CounterPresenter(eventsFlow, apiService)
        }
    }
    println("CounterScreen")
    CounterLayout(models) {
        eventsFlow.tryEmit(it)
    }
}

@Composable
fun CounterLayout(models: StateFlow<CounterModel>, onEventClick: (CounterEvent) -> Unit) {
    println("CounterLayout")
    val model by models.collectAsState()
    println(model)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { onEventClick(Change(-10)) }) {
                Text(text = "-10")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onEventClick(Change(-1)) }) {
                Text(text = "-1")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = model.value.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onEventClick(Change(1)) }) {
                Text(text = "+1")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onEventClick(Change(10)) }) {
                Text(text = "+10")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onEventClick(Randomize) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Randomize")
        }
    }
}
