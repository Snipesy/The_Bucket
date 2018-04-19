/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty

import guru.surreal.szone.netty.client.RtpSession
import guru.surreal.szone.netty.client.RtpdSession
import guru.surreal.szone.netty.client.test
import guru.surreal.szone.netty.rtsp.TransportHeader
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelFutureListener
import io.netty.handler.codec.rtsp.RtspResponseStatuses
import io.netty.handler.codec.rtsp.RtspMethods
import io.netty.handler.codec.rtsp.RtspHeaderNames
import io.netty.util.CharsetUtil
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.http.*
import io.netty.handler.codec.rtsp.RtspHeaderValues
import io.netty.handler.codec.rtsp.RtspVersions
import kotlinx.coroutines.experimental.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.logging.Logger
import java.net.InetSocketAddress
import java.util.*


class RtspServerHandler: ChannelInboundHandlerAdapter()
{

    val log = Logger.getLogger("Netty")



    val sessionSockets = LinkedList<DatagramSocket>()


    private fun send(ctx: ChannelHandlerContext, req: DefaultHttpRequest, rep: FullHttpResponse) {
        val cseq = req.headers().get(RtspHeaderNames.CSEQ)
        if (cseq != null) {
            rep.headers().add(RtspHeaderNames.CSEQ, cseq)
        }
        val session = req.headers().get(RtspHeaderNames.SESSION)
        if (session != null) {
            rep.headers().add(RtspHeaderNames.SESSION, session)
        }
        if (!HttpUtil.isKeepAlive(req)) {
            ctx.writeAndFlush(rep).addListener(ChannelFutureListener.CLOSE)
        } else {
            rep.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE)
            ctx.writeAndFlush(rep)
        }

        println("Tx: $rep")
    }


    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {

        if (msg is DefaultHttpRequest && ctx != null) {



            println("HttpRx: $msg")

            val rep = DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.NOT_FOUND)

            when (msg.method()) {
                // Client is polling our RTSP options
                RtspMethods.OPTIONS -> {
                    log.info("${ctx.channel().remoteAddress()} requested OPTIONS")
                    rep.status = RtspResponseStatuses.OK
                    rep.headers().add(RtspHeaderValues.PUBLIC, "DESCRIBE, SETUP, PLAY, PAUSE, TEARDOWN")
                    send(ctx,msg,rep)
                }
                RtspMethods.DESCRIBE -> {
                    log.info("${ctx.channel().remoteAddress()} requested DESCRIBE")

                    rep.status = RtspResponseStatuses.OK
                    //@TODO this stuff

                    // @stub
                    val buf = Unpooled.copiedBuffer("c=IN IP4 10.5.110.117\r\nm=video 5006 RTP/AVP 96\r\na=rtpmap:96 H264/90000\r\n", CharsetUtil.UTF_8)
                    rep.headers().add(RtspHeaderNames.CONTENT_TYPE, "application/sdp")
                    rep.headers().add(RtspHeaderNames.CONTENT_LENGTH, if (test.contents == null) buf.writerIndex().toString() else test.contents!!.writerIndex().toString())
                    rep.content().writeBytes(if (test.contents == null) buf else test.contents)
                    send(ctx, msg, rep)
                }
                RtspMethods.SETUP -> {
                    log.info("${ctx.channel().remoteAddress()} requested SETUP")


                    // setup some sockets

                    val streamSocker = DatagramSocket()


                    val controlSocket = try {
                        DatagramSocket(streamSocker.localPort+1)
                    }
                    catch(e: Exception)
                    {
                        DatagramSocket()
                    }


                    rep.status = RtspResponseStatuses.OK

                    val transportHeader = TransportHeader(msg.headers().get(RtspHeaderNames.TRANSPORT))

                    transportHeader.clientPortRtp = transportHeader.clientPortRtp ?: 5004
                    transportHeader.clientPortRtcp = transportHeader.clientPortRtcp ?: 5005
                    transportHeader.serverPortRtp = streamSocker.localPort
                    transportHeader.serverPortRtcp = controlSocket.localPort
                    transportHeader.protocol = transportHeader.protocol ?: "RTP/AVP"
                    transportHeader.cast = transportHeader.cast ?: "unicast"
                    transportHeader.mode = "PLAY"



                    val session = String.format("%08x", (Math.random() * 65536).toInt())
                    rep.headers().add(RtspHeaderNames.SESSION, session)
                    rep.headers().add(RtspHeaderNames.TRANSPORT, transportHeader.make())

                    val socketAddress = ctx.channel().remoteAddress() as InetSocketAddress
                    val inetaddress = socketAddress.address



                    sessionSockets += streamSocker
                    sessionSockets += controlSocket

                    val reciever = RtpSession.Reciever(streamSocker, inetaddress, transportHeader.clientPortRtp!!)

                    RtpSession.recieverList.add(reciever)


                    launch {
                        val buf = ByteArray(2000)
                        while(true)
                        {
                            try {
                                val dp = DatagramPacket(buf, buf.size, inetaddress, transportHeader.clientPortRtcp!!)

                                controlSocket.receive(dp)
                                println("Recieved")

                                RtpdSession.cur?.send(buf, dp.length)
                            }
                            catch (e: Exception)
                            {
                                // do nothing
                            }
                        }
                    }



                    send(ctx, msg, rep)

                }
                RtspMethods.PLAY -> {
                    log.info("${ctx.channel().remoteAddress()} requested PLAY")

                    rep.status = RtspResponseStatuses.OK


                    // just sendRequest an ack
                    send(ctx, msg, rep)

                }
                RtspMethods.PAUSE -> {
                    log.info("${ctx.channel().remoteAddress()} requested PAUSE")

                    rep.status = RtspResponseStatuses.OK


                    // just sendRequest an ack
                    send(ctx, msg, rep)
                }
                RtspMethods.TEARDOWN -> {
                    log.info("${ctx.channel().remoteAddress()} requested TEARDOWN")


                    // Destroy context
                    rep.status = RtspResponseStatuses.OK
                    sessionSockets.forEach { it.close() }
                    ctx.write(rep).addListener(ChannelFutureListener.CLOSE)

                }
                else -> {
                    System.err.println("Unknown method :" + msg.method())
                    ctx.write(rep).addListener(ChannelFutureListener.CLOSE)

                }

            }

        }

    }
}
