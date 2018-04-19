/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import guru.surreal.szone.netty.client.ClientMarshall
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.HttpResponse

import io.netty.handler.codec.rtsp.RtspHeaderNames
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.reactive.openSubscription
import org.reactivestreams.Subscriber

open class EntryAnticipator(cm: ClientMarshall, msg: () -> FullHttpRequest): AbstractAnticipator(), EntryPoint
{

    override var context: AnticipatorContext? = AnticipatorContext(cm, msg, 0, this)

    // Timeout in milliseconds
    var timeout: Int = 5000
    var sequenceCode: Int
    get() = context!!.count
    set(value) {context!!.count = value}


    override fun onAddedTo(anticipator: Anticipator) {
        // Do nothing this is an entry.
        return
    }

    override fun go(): Deferred<Boolean>
    {
        val cma = context!!.cm.handler.reponsePublisher.openSubscription()
        return async {

            try {
                withTimeout(timeout)
                {


                     for (it in cma) {
                        val code = it.headers().getInt(RtspHeaderNames.CSEQ)

                        if (code != null)
                        {
                            if (code == sequenceCode)
                            {
                                hit(it)
                                this@EntryAnticipator.context!!.cm.handler.reponsePublisher.unsubscribe(cma as Subscriber<in HttpResponse>)

                            }
                        }
                    }
                }
                true
            }
            catch (e: TimeoutCancellationException)
            {

                onHardFail?.invoke(e)
                false
            }

        }


    }

    private fun hit(msg: HttpResponse)
    {
        val code = msg.status()

        if (code.code() in 200..299)  {
            onSuccess?.invoke(code, msg)
        }
        else
        {
            onSoftFail?.invoke(code,msg)
        }
    }
}