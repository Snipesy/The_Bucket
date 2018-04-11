/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package symtable

/**
 * Manages the symbol table itself, hiding the underlying BST.
 */
class SymbolTable {

    // Private tree with our set of rules
    private val tree = bst.BST(BstRules())


    /**
     * Adds a symbol. Duplicates will be skipped (they dont throw errors)
     */
    fun addSymbol(symbol: Symbol)
    {
        tree.insert(symbol)
    }

    /**
     * Checks if a symbol exists in the table.
     * true if and only if the symbol exists.
     */
    fun existsInTable(symbol: Symbol): Boolean
    {
        return tree.contains(symbol)
    }

    /**
     * Retrieves a symbol using its symbol/label.
     * Null means the symbol was not found.
     */
    fun lookupSymbolFromName(name: String): Symbol?
    {
        return tree.find(Symbol(name))
    }

    /**
     * Returns a list of the bst in order.
     * Arguably negates the entire point of the BST, but it is an easy way of doing it.
     */
    fun getInOrderExport(): List<Symbol>
    {
        return tree.exportInOrder()
    }

    /**
     * Instead of exporting the bst can be traversed like so. A callback is used for doing any needed
     * operations.
     */
    fun traverseInOrder(callback: (Symbol) -> Unit)
    {
        tree.traverseInOrder(callback)
    }
}