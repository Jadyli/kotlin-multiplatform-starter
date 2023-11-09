package com.jady.kmp.demo

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.internal.MainDispatcherFactory
import java.awt.event.ActionListener
import javax.swing.SwingUtilities
import javax.swing.Timer
import kotlin.coroutines.CoroutineContext

/**
 * @author jady
 * @since 2023/11/08 11:19
 * email: 1257984872@qq.com
 */
/**
 * Dispatches execution onto Swing event dispatching thread and provides native [delay] support.
 */
@Suppress("unused")
public val Dispatchers.Swing : SwingDispatcher
    get() = com.jady.kmp.demo.Swing

/**
 * Dispatcher for Swing event dispatching thread.
 *
 * This class provides type-safety and a point for future extensions.
 */
@OptIn(InternalCoroutinesApi::class)
public sealed class SwingDispatcher : MainCoroutineDispatcher(), Delay {
    /** @suppress */
    override fun dispatch(context: CoroutineContext, block: Runnable): Unit = SwingUtilities.invokeLater(block)

    /** @suppress */
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val timer = schedule(timeMillis) {
            with(continuation) { resumeUndispatched(Unit) }
        }
        continuation.invokeOnCancellation { timer.stop() }
    }

    /** @suppress */
    override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
        val timer = schedule(timeMillis) {
            block.run()
        }
        return DisposableHandle { timer.stop() }
    }

    private fun schedule(timeMillis: Long, action: ActionListener): Timer =
        Timer(timeMillis.coerceAtMost(Int.MAX_VALUE.toLong()).toInt(), action).apply {
            isRepeats = false
            start()
        }
}

@OptIn(InternalCoroutinesApi::class)
internal class SwingDispatcherFactory : MainDispatcherFactory {
    override val loadPriority: Int
        get() = 0

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher = Swing
}

private object ImmediateSwingDispatcher : SwingDispatcher() {
    override val immediate: MainCoroutineDispatcher
        get() = this

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = !SwingUtilities.isEventDispatchThread()

    @OptIn(InternalCoroutinesApi::class)
    override fun toString() = toStringInternalImpl() ?: "Swing.immediate"
}

/**
 * Dispatches execution onto Swing event dispatching thread and provides native [delay] support.
 */
@OptIn(InternalCoroutinesApi::class)
internal object Swing : SwingDispatcher() {

    /* A workaround so that the dispatcher's initialization crashes with an exception if running in a headless
    environment. This is needed so that this broken dispatcher is not used as the source of delays. */
    init {
        Timer(1) { }.apply {
            isRepeats = false
            start()
        }
    }

    override val immediate: MainCoroutineDispatcher
        get() = ImmediateSwingDispatcher

    override fun toString() = toStringInternalImpl() ?: "Swing"
}
