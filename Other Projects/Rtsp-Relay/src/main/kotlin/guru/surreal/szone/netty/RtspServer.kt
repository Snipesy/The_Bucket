/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.rtsp.RtspDecoder
import io.netty.handler.codec.rtsp.RtspEncoder
import io.netty.handler.codec.rtsp.RtspMethods
import java.util.logging.Logger

class RtspServer(port: Int) {


    val log = Logger.getLogger("Netty")


    init {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
            b.channel(NioServerSocketChannel::class.java)
            b.option(ChannelOption.SO_KEEPALIVE, true)

            b.childHandler(object : ChannelInitializer<SocketChannel>() {
                public override fun initChannel(ch: SocketChannel) {
                    log.info("New Connection from ${ch.remoteAddress()} to ${ch.localAddress()}")
                    val p = ch.pipeline()
                    p.addLast(RtspDecoder(), RtspEncoder())
                    p.addLast(RtspServerHandler())


                }
            })

            val ch = b.bind(57039).sync().channel()
            log.info("Server started on localhost on 57039")
            ch.closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }
}