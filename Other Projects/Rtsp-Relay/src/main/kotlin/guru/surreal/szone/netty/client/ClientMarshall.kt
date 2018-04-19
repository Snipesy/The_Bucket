/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.client

import guru.surreal.szone.NAME
import guru.surreal.szone.VCODE
import guru.surreal.szone.netty.common.EntryAnticipator
import guru.surreal.szone.netty.common.SecurityAnticipator
import guru.surreal.szone.netty.rtsp.TransportHeader
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.DefaultFullHttpRequest
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.rtsp.RtspHeaderNames
import io.netty.handler.codec.rtsp.RtspMethods
import io.netty.handler.codec.rtsp.RtspVersions
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.yield
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.logging.Logger


class ClientMarshall(val ctx: ChannelHandlerContext, val handler: RtspClientHandler) {


    val clientInfo = ClientInformation()
    val serverProfile = ServerProfile()

    val log = Logger.getLogger("Netty")



    fun sendRequest(req: FullHttpRequest): Int
    {
        val count = clientInfo.ourCount++
        req.headers().add(RtspHeaderNames.CSEQ, count)
        req.headers().add(RtspHeaderNames.USER_AGENT, "${VCODE} ${NAME}")

        // Authroization
        if (clientInfo.auth != null) req.headers().add(HttpHeaderNames.AUTHORIZATION, clientInfo.auth)
        else if (clientInfo.basicAuth != null) req.headers().add(HttpHeaderNames.AUTHORIZATION, clientInfo.basicAuth)

        println("Tx: $req")

        ctx.writeAndFlush(req)

        return count
    }

    fun justSend(req: FullHttpRequest): Int
    {
        val count = clientInfo.ourCount++
        req.headers().remove(RtspHeaderNames.CSEQ)
        req.headers().add(RtspHeaderNames.CSEQ, count)
        ctx.writeAndFlush(req)

        println("Tx: $req")

        return count
    }

    fun notifyDiscconecnt(reason: String = "") {
        ctx.writeAndFlush(DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.TEARDOWN, handler.uri_noAuthority)).addListener(ChannelFutureListener.CLOSE)
    }

    var id: String = ""
    suspend fun corouInit() {
        options()
        describe()

        setup()

        play(id)


    }

    init{
        launch { corouInit() }

    }

    suspend fun options()
    {
        val msg = {
            val m = DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, handler.uri_noAuthority)
            m
        }
        val rab = EntryAnticipator(this, msg)
        rab.sequenceCode=clientInfo.ourCount

        val sa = SecurityAnticipator()
        rab.addAnticipator(sa)

        sa.onSuccess = { code, msga ->

            println("SUC OPTIONS: " + msga)
            val options = msga.headers().get(RtspHeaderNames.PUBLIC).filterNot { it == ' '}.split(',')

            println("CAM OPTIONS: $options+")
        }

        sa.onHardFail = { println("FailOptions")}
        sa.onSoftFail = { a,b -> println("SoftFailOptions: code $a")}



        val def = rab.go()

        sendRequest(msg())

        def.await()
    }

    suspend fun describe()
    {
        val msg = {
            val m = DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.DESCRIBE, handler.uri_noAuthority)
            m
        }
        val rab = EntryAnticipator(this, msg)
        rab.sequenceCode=clientInfo.ourCount

        val sa = SecurityAnticipator()
        rab.addAnticipator(sa)

        sa.onSuccess = { code, msga ->

            println("SUCDESCRIBE " + msga)
            if (msga is FullHttpResponse)
            {
                test.contents = msga.content()
            }
            else
            {
                log.warning("Description is not a full response!")
            }
        }

        sa.onHardFail = { println("FailDescribe")}
        sa.onSoftFail = { a,b -> println("SoftFailDescribe: code $a") }



        val  def = rab.go()

        sendRequest(msg())

        def.await()
    }

    suspend fun play(session: String)
    {
        val msg = {
            val m = DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.PLAY, handler.uri_noAuthority)
            m.headers().add(RtspHeaderNames.SESSION, session)
            m
        }
        val rab = EntryAnticipator(this, msg)
        rab.sequenceCode=clientInfo.ourCount

        val sa = SecurityAnticipator()
        rab.addAnticipator(sa)

        sa.onSuccess = { code, msga ->
            println("SUCPLAY " + msga)

        }

        sa.onHardFail = { println("FailPlay")}
        sa.onSoftFail = { a,b -> println("SoftFailPlay: code $a")}



        val  def = rab.go()

        sendRequest(msg())

        def.await()
    }

    suspend fun setup()
    {
        val msg = {
            val m = DefaultFullHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.SETUP, handler.uri_noAuthority)
            m.headers().add(RtspHeaderNames.TRANSPORT, "RTP/AVP;unicast;client_port=5004-5005")
            m
        }
        val rab = EntryAnticipator(this, msg)
        rab.sequenceCode=clientInfo.ourCount

        val sa = SecurityAnticipator()
        rab.addAnticipator(sa)

        sa.onSuccess = { code, msga ->
            println("SUC1 " + msga)
            val session = msga.headers().get(RtspHeaderNames.SESSION).filterNot{ it == ' '}.split(';')
            val id = session.first()

            this.id = id


            val transportHeader = TransportHeader(msga.headers().get(RtspHeaderNames.TRANSPORT))

            val socket = DatagramSocket(transportHeader.clientPortRtp ?: 5004)


            val socket2 = DatagramSocket(transportHeader.clientPortRtcp ?: 5005)



            val rtpSession = RtpSession(id, socket, InetAddress.getByName(handler.uri.host), transportHeader.serverPortRtp!!)
            val rtpdSession = RtpdSession(id, socket2, InetAddress.getByName(handler.uri.host), transportHeader.serverPortRtcp!!)

            RtpSession.curSrc = transportHeader.ssrc!!

        }

        val  def = rab.go()

        sendRequest(msg())

        def.await()


    }



}