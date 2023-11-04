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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author jady
 * @since 2023/11/4 16:45
 * email: 1257984872@qq.com
 */
@Composable
fun CounterScreen() {
    val scope = MainScope()
    val eventsFlow = MutableSharedFlow<CounterEvent>(extraBufferCapacity = 20)
    val models = scope.launchMolecule(mode = RecompositionMode.ContextClock) {
        CounterPresenter(eventsFlow, apiService)
    }
    CounterLayout(models) {
        eventsFlow.tryEmit(it)
    }
}

@Composable
fun CounterLayout(models: StateFlow<CounterModel>, onEventClick: (CounterEvent) -> Unit) {
    val model = models.collectAsState()
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
