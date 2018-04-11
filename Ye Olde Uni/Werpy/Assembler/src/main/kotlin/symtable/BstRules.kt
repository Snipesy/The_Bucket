/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package symtable

import bst.BstConfig

/**
 * Contains compare rules for symtable's bst.
 */
class BstRules: BstConfig<Symbol>(
        Comparator { source, target ->
            // Notice how we use the effective symbol not the full symbol.
            target.effectiveSymbol().compareTo(source.effectiveSymbol())

        })