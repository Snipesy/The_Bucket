/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import io.netty.handler.codec.http.HttpResponse
import io.netty.handler.codec.http.HttpResponseStatus

/**
 * Abstraction to make things simpler.
 */
abstract class AbstractAnticipator: Anticipator {
    override var onSuccess: ((code: HttpResponseStatus, msg: HttpResponse) -> Unit)? = null
    override var onHardFail: ((reason: Throwable) -> Unit)? = null
    override var onSoftFail: ((code: HttpResponseStatus, msg: HttpResponse) -> Unit)? = null
    override var context: AnticipatorContext? = null
    override var previous: Anticipator? = null

    override fun addAnticipator(anticipator: Anticipator): Anticipator {
        anticipator.context = this.context
        anticipator.previous = this
        // Pass thru defaults
        this.onSuccess = {a,b -> anticipator.onSuccess?.invoke(a,b)}
        this.onHardFail = {t -> anticipator.onHardFail?.invoke(t)}
        this.onSoftFail = {a,b -> anticipator.onSoftFail?.invoke(a,b)}

        anticipator.onAddedTo(this)


        return anticipator
    }


}