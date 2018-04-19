/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.rtsp

/**
 * Parses and creates a transport header
 */
class TransportHeader() {


    var clientPortRtcp: Int? = null
    var clientPortRtp: Int? = null
    var protocol: String? = null
    var cast: String? = null
    var mode: String? = null
    var ssrc: String? = null
    var serverPortRtcp: Int? = null
    var serverPortRtp: Int? = null



    constructor(header: String): this()
    {

        val h1 = header.filterNot { it == ' ' }.split(';')

        protocol = h1.firstOrNull()
        cast = h1.getOrNull(1)



        val trans=h1.map { it.split('=') }
        val serverPorts = trans.find { it.firstOrNull() != null && it.first().toLowerCase() == "server_port" }

        if (serverPorts != null)
        {
            val ports =  serverPorts[1].split('-')
            serverPortRtp = ports.firstOrNull()?.toInt()
            serverPortRtcp = ports.getOrNull(1)?.toInt()
        }

        val clientPorts = trans.find { it.firstOrNull() != null && it.first().toLowerCase() == "client_port" }


        if (clientPorts != null)
        {
            val ports =  clientPorts.getOrNull(1)?.split('-')
            clientPortRtp = ports?.firstOrNull()?.toInt()
            clientPortRtcp = ports?.getOrNull(1)?.toInt()
        }

        println("TEST $clientPortRtp and $clientPortRtcp")


        val ssrc = trans.find {it.firstOrNull() != null && it.first().toLowerCase() == "ssrc"}

        if (ssrc != null)
        {
            this.ssrc = ssrc.getOrNull(1)
        }

        val mode = trans.find {it.firstOrNull() != null && it.first().toLowerCase() == "mode"}

        if (mode != null)
        {
            this.mode = mode.getOrNull(1)
        }

    }

    fun make(): String
    {
        val sb = StringBuilder()

        protocol?.let { sb.append("$it;") }
        cast?.let { sb.append("$it;") }
        clientPortRtp?.let { it1 -> clientPortRtcp?.let { sb.append("client_port=$it1-$it;") } }
        serverPortRtp?.let { it1 -> serverPortRtcp?.let { sb.append("server_port=$it1-$it;") } }
        mode?.let { sb.append("\"$it\";") }

        // remove last ;
        if (sb.isNotEmpty())
            sb.setLength(sb.length-1)

        return sb.toString()

    }


}