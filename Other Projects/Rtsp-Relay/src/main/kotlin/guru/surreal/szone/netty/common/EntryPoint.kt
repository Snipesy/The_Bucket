/*
 * Copyright (c) 2018. Surreal Development LLC
 */

package guru.surreal.szone.netty.common

import kotlinx.coroutines.experimental.Deferred

interface EntryPoint {

    fun go(): Deferred<Boolean>
}