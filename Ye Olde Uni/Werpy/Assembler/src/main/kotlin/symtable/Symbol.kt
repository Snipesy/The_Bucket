/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package symtable

/**
    SYMBOL (also referred to as a label in assembly language programing)
    • starts with a letter (A..Z, a..z).
    • followed by letters (A..Z, a..z) and digits (0..9).
    • maximum of 16 characters in length in the source program
    o only the first 6 characters are significant.
    o only the first 6 characters are stored in the symbol table.
    • case sensitive (CSc354, CSc354S1 – same symbol – stored as CSc354).
    • case sensitive (CSc354, CSC354 – not same symbol – stored as CSc354 & CSC354 respectively).
    RFLAG (Boolean) // not case sensitive
    • 0, false
    • 1, true
    VALUE
    • signed integer value (–, 0..9).
    IFLAG (Boolean)
    • indicates whether or not a symbol has been defined within the current control section (true for now).
    MFLAG (Boolean)
    • indicates whether or not a symbol has been defined more than one time in the same control section.
    • each valid symbol is inserted into the symbol table exactly one time (invalid symbols are never inserted).

 The class contains a companion object which holds common parse functions. The symbol object itself does not verify
 its arguements.

 */
class Symbol(val symbol: String)
{
    var iFlag = true
    var mFlag = false
    var rFlag = false
    var value: Int = 0

    // The effective symbol is the symbol used for the BST comparisions.
    // It consists of up to the first 6 characters.
    val effectiveSymbol = {
        if (symbol.length > 6)
            symbol.substring(0..5)
        else
            symbol.substring(0..(symbol.lastIndex))
    }


    /**
     * Companion object contains useful parsing functions for Symbols.
     */
    companion object {
        enum class SymbolResult
        {
            OKAY,
            INVALID_FIRST {
                override fun toString(): String {
                    return "symbol must start with a letter"
                }
            },
            DEFINED {
                override fun toString(): String {
                    return "symbol previously defined"
                }
            },
            MAX_LENGTH_EXCEEDED
            {
                override fun toString(): String {
                    return "symbol maximum length 16"
                }
            },
            NOT_ALPHANUMERIC
            {
                override fun toString(): String {
                    return "symbol must be alphanumeric"
                }
            }
        }

        fun checksymbol(symbol: String): SymbolResult
        {
            // Length must be at least 1
            if (symbol.isEmpty())
                return SymbolResult.INVALID_FIRST
            // First letter must be a letter
            if (!symbol[0].isLetter())
                return SymbolResult.INVALID_FIRST

            // String must be at most 16 letters

            if (symbol.length > 16)
                return SymbolResult.MAX_LENGTH_EXCEEDED

            // String must be composed of letters and numbers
            symbol.forEach { if (!it.isLetterOrDigit()) return SymbolResult.NOT_ALPHANUMERIC }

            return SymbolResult.OKAY
        }

        enum class RFlagResult {
            OKAY_FALSE,
            OKAY_TRUE,
            INVALID {
                override fun toString(): String {
                    return "invalid R flag format (0,1,false,true)"
                }
            }

        }

        fun parseRFlag(rflag: String): RFlagResult
        {
            if (rflag == "0")
                return RFlagResult.OKAY_FALSE
            else if (rflag == "1")
                return RFlagResult.OKAY_TRUE
            else if (rflag.equals("true", true))
                return RFlagResult.OKAY_TRUE
            else if (rflag.equals("false", true))
                return RFlagResult.OKAY_FALSE
            else
                return RFlagResult.INVALID

        }

        fun parseValue(value: String): Int
        {
            return Integer.parseInt(value)
        }


    }
}