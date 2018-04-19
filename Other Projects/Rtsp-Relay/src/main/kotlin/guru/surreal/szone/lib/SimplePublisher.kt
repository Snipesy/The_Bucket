/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.lib

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

open class SimplePublisher<T> : Publisher<T> {


    private val subs = ArrayList<Subscriber<in T>>()


    /**
     * Remove this subscriber from the list and call On Complete.
     */
    fun unsubscribe(s: Subscriber<in T>)
    {
        if (subs.contains(s))
        {
            subs.remove(s)
            s.onComplete()
        }
    }

    override fun subscribe(s: Subscriber<in T>?) {
        if (s == null) return
        subs.add(s)
    }

    open fun send(item: T)
    {
        subs.forEach { it.onNext(item) }
    }

    open fun error(throwable: Throwable)
    {
        subs.forEach { it.onError(throwable) }
        subs.clear()
    }

    open fun complete(throwable: Throwable)
    {
        subs.forEach { it.onComplete() }
        subs.clear()
    }
}