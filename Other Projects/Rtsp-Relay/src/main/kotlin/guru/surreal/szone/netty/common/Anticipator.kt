/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import io.netty.handler.codec.http.HttpResponse
import io.netty.handler.codec.http.HttpResponseStatus

interface Anticipator {
    var onSoftFail: ((code: HttpResponseStatus, msg: HttpResponse) -> Unit)?
    var onHardFail: ((reason: Throwable) -> Unit)?
    var onSuccess: ((code: HttpResponseStatus, msg: HttpResponse) -> Unit)?
    var context: AnticipatorContext?
    var previous: Anticipator?

    fun addAnticipator(anticipator: Anticipator): Anticipator
    fun onAddedTo(anticipator: Anticipator)
}