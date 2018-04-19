/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.client

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


class RtpdSession(val id: String, val socket: DatagramSocket, val remoteAddress: InetAddress, val remotePort: Int) {


    fun send(bytes: ByteArray, count: Int)
    {

        val dp = DatagramPacket(bytes, count, remoteAddress, remotePort)
        println("sending")
        socket.send(dp)
    }
    init {
        cur = this
    }

    companion object {
        var cur: RtpdSession? = null
    }

}