/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.rtsp.*
import java.net.URI
import java.util.logging.Logger

class RtspClient(uri: URI) {

    val log = Logger.getLogger("NettyClient")
    init {
        val workerGroup = NioEventLoopGroup()

        try {
            val b = Bootstrap()
            b.group(workerGroup)
            b.channel(NioSocketChannel::class.java)
            b.option(ChannelOption.SO_KEEPALIVE, true)
            b.handler(object : ChannelInitializer<SocketChannel>()
            {
                override fun initChannel(ch: SocketChannel?) {
                    val p = ch!!.pipeline()
                    p.addLast(RtspDecoder(), HttpObjectAggregator(5000), RtspEncoder())
                    val rch = RtspClientHandler(uri)
                    p.addLast(rch)
                }
            }
            )

            val host = uri.host
            val port = if (uri.port == -1) 554 else uri.port

            log.info("Connecting to $host on $port")
            val future = b.connect(uri.host,port).sync()

            // Wait until the connection is closed
            future.channel().closeFuture().sync()
        }
        finally {
            log.info("ERROR")
            workerGroup.shutdownGracefully()
        }
    }


}