/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package bst

/**
 *  This class implements a binary search tree.
 *  Unlike most traditonal bianry search trees this class does not implement any comparisions or hashing,
 *  and instead leaves the compares to a Comparator interface. This makes the BST adaptable for all uses.
 *
 *  An additional duplicate callback can be specified for handling duplicate
 *  conditions (such as adding, or throwing an exception)
 */
class BST<T>(val mConfig: BstConfig<T>) {

    /**
     * Variables for the BST
     */

    // Top of the mTree, start of the linked list.
    private var mTree: BstNode<T>? = null

    private val mComparator: Comparator<T> = mConfig.comparator

    fun insertAll(list: List<T>)
    {
        list.forEach { insert(it) }
    }

    /**
     * Inserts an element into the binary search tree.
     */
    fun insert(item: T) {
        val pos = mTree
        // Checks if there is a mTree at all
        when (pos) {
            null -> {
                mTree = BstNode(item, null, null)
                mConfig.onItemAdded(item)
            }
            else -> {

                insert(item, pos)
            }
        }
    }

    /**
     * Recursive insert implementation
     */
    private fun insert(item: T, position: BstNode<T>) {

        val left = position.left
        val right = position.right
        val posItem = position.item

        // Compare position and insertion item using comparator
        val cmp = mConfig.comparator.compare(posItem, item)

        // They are equal
        if (cmp == 0) {
            mConfig.onItemDuplicate(posItem)
        } else if (cmp > 0) {
            // Go right or create right if not possible
            if (right == null) {
                position.right = BstNode(item, null, null)
                mConfig.onItemAdded(item)
            } else {
                insert(item, right)
            }
        } else {
            // Go left or create left if not possible
            if (left == null) {
                position.left = BstNode(item, null, null)
                mConfig.onItemAdded(item)
            } else {
                insert(item, left)
            }
        }


    }

    /**
     * Simply returns true if the item exists in the tree.
     */
    fun contains(item: T): Boolean
    {
        return findNode(item) != null
    }

    /**
     * finds the item.
     * The usage of this is going to depend on the bst rules.
     */
    fun find(item: T): T?
    {
        val node = findNode(item) ?: return null

        return node.item
    }

    /**
     * Retrieves node from the item. Returns null if it does not exist.
     */
    private fun findNode(item: T): BstNode<T>? {
        return recursivefind(item, mTree)
    }


    /**
     * Recursive implementation for find.
     */
    private fun recursivefind(item: T, pos: BstNode<T>?): BstNode<T>? {
        if (pos == null)
            return null
        val cmp = mComparator.compare(pos.item, item)
        if (cmp == 0)
            return pos
        else if (cmp < 0)
            return recursivefind(item, pos.left)
        else
            return recursivefind(item, pos.right)
    }




    /**
     * Functional tree traversals.
     * Allows the BST to be iterated over without creating a new ADT.
     */
    fun traverseInOrder(callback: (T) -> Unit)
    {
        traverseInOrder(mTree, callback)
    }

    /**
     * Recursive implementation for in order traverse.
     */
    private fun traverseInOrder(cur: BstNode<T>?, callback: (T) -> Unit)
    {
        if (cur != null)
        {
            traverseInOrder(cur.left, callback)
            callback(cur.item)
            traverseInOrder(cur.right, callback)
        }
    }


    /**
     * The below methods allow the BST to be exported to an array list using the specified order.
     *
     */
    fun exportPreOrder(): List<T>
    {
        val a = ArrayList<T>()
        getPreOrder(mTree, a)
        return a
    }

    private fun getPreOrder(cur: BstNode<T>?, list: MutableList<T>)
    {
        if (cur != null)
        {
            list.add(cur.item)
            getPreOrder(cur.left, list)
            getPreOrder(cur.right, list)
        }
    }

    fun exportInOrder(): List<T>
    {
        val a = ArrayList<T>()
        getInOrder(mTree, a)
        return a
    }

    private fun getInOrder(cur: BstNode<T>?, list: MutableList<T>)
    {
        if (cur != null)
        {
            getInOrder(cur.left, list)
            list.add(cur.item)
            getInOrder(cur.right, list)
        }
    }

    fun exportPostOrder(): List<T>
    {
        val a = ArrayList<T>()
        getPostOrder(mTree, a)
        return a
    }

    private fun getPostOrder(cur: BstNode<T>?, list: MutableList<T>)
    {
        if (cur != null)
        {

            getPostOrder(cur.left, list)
            getPostOrder(cur.right, list)
            list.add(cur.item)
        }
    }





}