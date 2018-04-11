/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

import cli.UserControlView

object main {

    /**
     * Entry point for program.
     */
    @JvmStatic
    fun main(args: Array<String>) {

        val uiv = UserControlView()

        uiv.start(args)

    }
}