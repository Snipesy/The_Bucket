/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone

import guru.surreal.szone.netty.RtspServer
import guru.surreal.szone.netty.client.RtspClient
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.net.URI

/**
 * Szone tries to be a functional rtsp relay server.
 */
fun main(args: Array<String>) {

    runBlocking {
        async { val rtsp = RtspServer(args.getOrNull(1)?.toInt() ?: 57039) }
        val client = RtspClient(URI(args.getOrNull(0) ?: "axrtsphttp://root:pass@10.0.11.4/axis-media/media.amp"))
        //val client = RtspClient(URI("rtsp://root:pass@localhost:57039"))
    }

}
