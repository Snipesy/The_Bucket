/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.client

import guru.surreal.szone.netty.common.RTPPacket
import kotlinx.coroutines.experimental.channels.ProducerScope
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.reactive.asPublisher
import org.reactivestreams.Publisher
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

class RtpSession(val id: String, val socket: DatagramSocket, val remoteAddress: InetAddress, val remotePort: Int) {

    val buf = ByteArray(20000) // .02 megabyte byte array.
    init {
        onNewFrame = produce {
            while (true)
            {
                val dp = DatagramPacket(buf, buf.size, remoteAddress, remotePort)
                socket.receive(dp)

                val packet = RTPPacket(dp.data, dp.length)

                send(packet)


            }
        }.asPublisher()

    }

    class Reciever(val socket: DatagramSocket, val addr: InetAddress, val port: Int)


    companion object {
        var onNewFrame: Publisher<RTPPacket>? = null
        var curSrc: String = ""
        val recieverList = ArrayList<Reciever>()
    }

}