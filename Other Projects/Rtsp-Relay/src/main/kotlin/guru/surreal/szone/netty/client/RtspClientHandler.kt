/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.client


import guru.surreal.szone.lib.SimplePublisher
import guru.surreal.szone.netty.stripUriAuthority
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.*

import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import java.net.URI
import java.util.logging.Logger


class RtspClientHandler( val uri: URI): ChannelInboundHandlerAdapter() {



    private val log = Logger.getLogger("NettyClient")
     val uri_noAuthority = stripUriAuthority(uri).toASCIIString()


    private var ourCount = 1
    private var basicAuth: String? = null

    val reponsePublisher = SimplePublisher<FullHttpResponse>()
    val requestPublisher = SimplePublisher<HttpRequest>()
    val activityPublisher = ConflatedBroadcastChannel<Boolean>()

    var marshall: ClientMarshall? = null


    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
        activityPublisher.offer(true)

        marshall = ClientMarshall(ctx!!, this)
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?) {
        super.channelUnregistered(ctx)
        activityPublisher.offer(false)
    }


    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {


        if (ctx == null) return

        if (msg is FullHttpResponse)
        {
            reponsePublisher.send(msg)
        }
        else if (msg is HttpRequest)
        {
            requestPublisher.send(msg)
        }
    }


}