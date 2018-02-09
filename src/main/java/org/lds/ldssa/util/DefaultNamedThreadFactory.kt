package org.lds.ldssa.util

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

// todo move to commons
class DefaultNamedThreadFactory(name: String) : ThreadFactory {
    private val group: ThreadGroup
    private val threadNumber = AtomicInteger(1)
    private val namePrefix: String

    init {
        val s = System.getSecurityManager()
        group = if (s != null) {
            s.threadGroup
        } else {
            Thread.currentThread().threadGroup
        }
        namePrefix = "$name-${poolNumber.getAndIncrement()}-thread-"
    }

    override fun newThread(r: Runnable): Thread {
        val thread = Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0)
        if (thread.isDaemon) {
            thread.isDaemon = false
        }

        if (thread.priority != Thread.NORM_PRIORITY) {
            thread.priority = Thread.NORM_PRIORITY
        }

        return thread
    }

    companion object {
        private val poolNumber = AtomicInteger(1)
    }
}