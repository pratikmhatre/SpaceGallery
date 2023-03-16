package com.cypher.spacegallery.core.helpers

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//Snippet taken from :- https://gist.github.com/gmk57/330a7d214f5d710811c6b5ce27ceedaa

inline fun <reified T> Flow<T>.collectWhileStarted(
    owner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) {
    object : DefaultLifecycleObserver {
        private var job: Job? = null

        override fun onStart(owner: LifecycleOwner) {
            job = owner.lifecycleScope.launch {
                this@collectWhileStarted.collect {
                    collector(it)
                }
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            job?.cancel()
            job = null
        }

        init {
            owner.lifecycle.addObserver(this)
        }
    }
}