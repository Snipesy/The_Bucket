/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import io.netty.handler.codec.rtsp.RtspHeaderNames
import io.netty.handler.codec.rtsp.RtspResponseStatuses
import java.util.*
import java.util.logging.Logger

class SecurityAnticipator : AbstractAnticipator() {

    private var attempts = 0
    var maxAttempts = 1

    override fun onAddedTo(anticipator: Anticipator) {
        anticipator.onSoftFail = { status, response ->


            if (status == RtspResponseStatuses.UNAUTHORIZED)
            {

                if (attempts >= maxAttempts)
                {
                    log.warning("Max password attempts exceeded.")
                    onHardFail?.invoke(Exception("Max auth attempts exceeded."))
                }
                attempts++

                /**
                 * Look for www
                 */
                val wwwA = response.headers().getAll(RtspHeaderNames.WWW_AUTHENTICATE)

                val basic = wwwA.find { it.startsWith("Basic") }

                val digest = wwwA.find { it.startsWith("Digest")}

                if (digest != null)
                {
                    addAuthroizationMd5(digest)
                }
                else if (basic != null)
                {
                    addAuthroizationBasic()
                }
                else
                {
                    onHardFail?.invoke(UnsupportedOperationException("Recieved unauthorized code but was not supplied with any header."))
                }


                // send again
                this.context!!.ep.go()
                this.context!!.count = context!!.cm.sendRequest(context!!.ctx())



            }

            onSoftFail?.invoke(status,response)
        }
    }

    fun addAuthroizationBasic()
    {
        if (context!!.cm.handler.uri.userInfo == null) return
        context!!.cm.clientInfo.basicAuth = "Basic " + Base64.getEncoder().encodeToString(context!!.cm.handler.uri.rawUserInfo.toByteArray(Charsets.UTF_8))
    }

    fun addAuthroizationMd5(header: String)
    {
        // Not worth it. Just do basic
        addAuthroizationBasic()

    }
    companion object {
        val log = Logger.getLogger("NettySecurity")
    }
}