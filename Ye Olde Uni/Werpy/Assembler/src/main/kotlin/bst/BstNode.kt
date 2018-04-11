/********************************************************************
 *** NAME : Justin Hoogestraat          ***
 *** CLASS : CSc 354                    ***
 *** ASSIGNMENT : 1                     ***
 *** DUE DATE : 9/12/17                 ***
 *** INSTRUCTOR : Jason Werpy            ***
 ********************************************************************/

package bst

/**
 * Node for the BST. Contains the item in question and left right nullable nodes.
*/
internal class BstNode<T>(
        val item: T,
        var left: BstNode<T>?,
        var right: BstNode<T>?
)