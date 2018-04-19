/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty

import java.net.URI

fun stripUriAuthority(uri: URI): URI
{
    return URI(uri.scheme, null, uri.host, uri.port, uri.path, uri.query, uri.fragment)
}