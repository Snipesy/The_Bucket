/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package bst

/**
 * open configuration for the BST.
 */
open class BstConfig<T>(val comparator: Comparator<T>) {

    /**
     * Manages what to do when an item is removed. (ex, decount)
     * return true removes the item
     * return false keeps the item
     *
     * by default the item is removed
     */
    open fun onItemRemoved(item: T): Boolean {
        return true
    }

    /**
     * Called when a duplicate is found
     * by default nothing is done
     */
    open fun onItemDuplicate(item: T) {

    }

    /**
     * Called when an item is added for the first time
     * by default nothing is done.
     */
    open fun onItemAdded(item: T)
    {

    }
}