package com.jady.feature.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "https://github.com/Jadyli/kotlin-multiplatform-starter"
        )

        ShadowMaterialCard()
    }
}

@Composable
fun ShadowMaterialCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    extraShadow: Dp = 6.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    // 外部 shadow：可以自定义更强或不同风格的阴影（ambient/spot 颜色也可传）
    Box(
        modifier = modifier
            .padding(16.dp)
            .shadow(extraShadow, shape = shape, clip = false) // 外层更显著的阴影
            .clip(shape)
            .clickable {}
    ) {
        // Material3 Card 自带 elevation（更符合 Material 视觉层级）
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = shape,
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 占位图片资源（替换为你的资源或Network Image）
                // Image(painter = painterResource(id = R.drawable.sample), contentDescription = null, ...)

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Compose Material3 卡片",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "这是一个带有自定义阴影和 Material3 elevation 的示例卡片。你可以调整 elevation 与 extraShadow 来改变视觉效果。",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
