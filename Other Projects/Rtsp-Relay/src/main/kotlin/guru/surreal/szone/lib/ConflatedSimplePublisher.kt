/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.lib

import org.reactivestreams.Subscriber

class ConflatedSimplePublisher<T>: SimplePublisher<T>() {


    var lastSent: T? = null
    override fun subscribe(s: Subscriber<in T>?) {

        super.subscribe(s)
        if (lastSent != null && s != null)
            s.onNext(lastSent)
    }

    override fun send(item: T) {
        lastSent = item
        super.send(item)
    }



}