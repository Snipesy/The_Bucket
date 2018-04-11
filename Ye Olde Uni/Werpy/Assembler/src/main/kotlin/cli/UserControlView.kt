/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package cli

import symtable.Symbol
import symtable.SymbolTable
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * Serves as the sort of view and view model of the program.
 * THe user control view is responsible for all the IO, and tossing data to the sym table.
 */
class UserControlView {


    val table = SymbolTable()




    /**
     * You could call this the real entry point to the program.
     * It checks for the symtable name and prompts for input.
     */
    fun start(args: Array<String>)
    {

        // First try symtable.dat name. If that fails asks the user for a name.
        if (symtableIteration("symtable.dat"))
        {
            println("Symtable data file not found. Falling back to user input.")
            while (inputtedSymIteration());
        }

        // Wait for user
        print("Press enter to continue")
        readLine()

        // Next up see if the args contain a search file name. Which means the args will be at least 1

        if (args.size > 0 && searchFile(args[0]))
        {
            // This means the args worked, excellent
        }
        else
        {
            // If they don't work, ask the user for them.
            println("No command arguments for search file.")
            do {
                print("Enter search filename (q to skip): ")
                val input = readLine()
            } while (input != "q" && !searchFile(input!!))
        }


        // Wait for user
        print("Press enter to continue")
        readLine()


        // Finally view the table

        viewTable()

        // Done!

    }


    /**
     * Table viewing. Works similiarly to search file style
     */
    private fun viewTable()
    {
        //val list = table.getInOrderExport()

        var lineCount = 4

        println("\nSYMBOL TABLE (ALL)\n")
        System.out.format("%-17s%-8s%-8s%-8s%-8s", "Label/Symbol", "Value","RFlag", "IFlag", "MFlag").println("\n")

        table.traverseInOrder {
            System.out.format("%-17s%-8s%-8s%-8s%-8s",
                    it.effectiveSymbol(), it.value,it.rFlag, it.iFlag, it.mFlag)
                    .println()
            lineCount++

            if (lineCount == 18)
            {
                println("\nPress enter for next page...")
                readLine()
                lineCount = 0
            }
        }

    }

    /**
     * tests the search file. Returns false if its invalid.
     */
    private fun searchFile(filename: String): Boolean
    {
        val file = File(filename)

        // The search file shares a similiar format with the symtable file
        // so we can reuse some code.
        val reader = tryFile(file) ?: return false

        val list = parseToList(reader)

        // Create header
        // Header needs 4 for line num
        // 17 for label
        // 8 for value, r flag, i flag, m flag, etc

        println("\nSEARCH RESULTS\n")
        System.out.format("%-4s%-17s%-8s%-8s%-8s%-8s", "#", "Label/Symbol", "Value","RFlag", "IFlag", "MFlag").println("\n")

        var count = 1
        list.forEach {
            // First wil lbe symbol
            val label = it[0]

            // Parse label
            val symParse = Symbol.checksymbol(label)


            if (symParse == Symbol.Companion.SymbolResult.OKAY)
            {
                // Lookup
                val lookup = table.lookupSymbolFromName(it[0])

                // Not found
                if (lookup == null)
                {
                    System.out.format("%-4s%-17s%-20s", count, it[0], "DOES NOT EXIST")
                }
                // Found
                else {
                    System.out.format("%-4s%-17s%-8s%-8s%-8s%-8s", count,
                            lookup.effectiveSymbol(), lookup.value,lookup.rFlag, lookup.iFlag, lookup.mFlag)
                }
            }
            else
            {
                // Error with symbol name
                System.out.format("%-4s%-17s%-32s", count, it[0], "ERROR - " + symParse.toString())
            }

            println()

            count++


        }

        println()

        return true





    }

    /**
     * Asks for user input for sym table file name.
     */
    private fun inputtedSymIteration(): Boolean
    {
        print("Enter filename (q to skip): ")
        val input = readLine()

        if (input.equals("q"))
            return false
        else
            return symtableIteration(input)
    }


    /**
     * Tries to do a round of openning and parsing the sym table file using the specified filename.
     */
    private fun symtableIteration(filename: String?): Boolean
    {

        // The best way of doing file names. Works relatively and absolutely.
        val file = File(filename)

        val reader = tryFile(file) ?: return true




        val list = parseToList(reader)

        println("Parsing file with " + list.size + " lines...")

        reader.close()

        listToSymbolTable(list)




        return false

    }

    /**
     * Tries to open a file and returns it in the form of a buffered reader.
     * A null buffered reader mens the file failed to open.
     */
    fun tryFile(file: File): BufferedReader?
    {
        try {
            println("Openning " + file.absolutePath)
            val bufferedReader = BufferedReader(FileReader(file))
            return bufferedReader
        }
        catch(e: Exception)
        {
            println(e.localizedMessage)
            return null
        }

    }

    /**
     * Takes a file in the form of a bfufered reader and
     * splits it up based on white spaces.
     */
    fun parseToList(reader: BufferedReader): List<List<String>>
    {
        val list = ArrayList<List<String>>()
        var line = reader.readLine()


        while (line != null)
        {

            val split = line.split("\\s+".toRegex()).dropWhile { it.isEmpty() }
            // This will split up with white space delimiters (any number of spaces)
            list.add(split)
            line = reader.readLine()


        }


        return list.toList()

    }

    /**
     * Takes the list found from the file and tries to parse
     * it. Valid entires and placed into the symbol table.
     */
    fun listToSymbolTable(list: List<List<String>>)
    {

        var counter = 0
        list.forEach {
            counter += 1
            // Check if it has enough args... Maybe throw error if theres not enough? Not specified in specs.
            if (it.size >= 3)
            {
                lineToSymbolTable(it,counter)
            }
            else
            {
                print("$counter. Not enough args (3 required)")
            }


        }
    }

    /**
     * Takes a list of strings and converts it to a symbol.
     * Verbosely outputs to console.
     * As a precondition the list is assumed to be of at least size 3
     */
    fun lineToSymbolTable(line: List<String>, num: Int)
    {
        // First wil lbe symbol
        val label = line[0]

        // Parse label
        val symParse = Symbol.checksymbol(label)



        if (symParse != Symbol.Companion.SymbolResult.OKAY)
        {
            println("$num. ERROR - " + symParse.toString() + ": $label")
            return
        }
        val symbol = Symbol(label)

        // Parse rflag

        val flag = Symbol.parseRFlag(line[1])

        symbol.rFlag = when (flag)
        {
            Symbol.Companion.RFlagResult.OKAY_FALSE -> false
            Symbol.Companion.RFlagResult.OKAY_TRUE -> true
            else -> {
                println("$num. ERROR - " + flag.toString() + ": " + line[1])
                return
            }
        }

        // Parse value

        try {
            symbol.value = Symbol.Companion.parseValue(line[2])
        }
        catch (e: Exception)
        {
            println("$num. ERROR - invalid value: " + line[2])
        }


        // Check if symbol exists in table
        if (table.existsInTable(symbol))
        {
            println("$num. ERROR - symbol previously defined: " + line[0])
            // Set m flag of that symbol
            table.lookupSymbolFromName(symbol.symbol)!!.mFlag = true
            return
        }

        // Otherwise add it to the table
        table.addSymbol(symbol)





    }
}