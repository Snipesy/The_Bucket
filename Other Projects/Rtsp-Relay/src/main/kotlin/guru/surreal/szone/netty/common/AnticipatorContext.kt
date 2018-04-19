/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import guru.surreal.szone.netty.client.ClientMarshall
import io.netty.handler.codec.http.FullHttpRequest

final class AnticipatorContext(val cm: ClientMarshall, var ctx: () -> FullHttpRequest, var count: Int = 0, val ep: EntryPoint)