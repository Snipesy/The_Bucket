//
// Created by hooge on 3/17/2017.
//
/********************************************************************
*** NAME : Justin Hoogestraat***
*** CLASS : CSc 300 ***
*** ASSIGNMENT : 5***
*** DUE DATE : 2/27/17***
*** INSTRUCTOR : HAMER ***
*********************************************************************
*** DESCRIPTION : This class implements a binary searchtree with counting,
*** and an integer element.
********************************************************************/

#include "justih5.h"
#include <new>
#include <iostream>

/********************************************************************
*** FUNCTION BST300 default constructor ***
*********************************************************************
*** DESCRIPTION : This constructor initilises the tree to null.
*** RETURN : BST300 ***
********************************************************************/
BST300::BST300()
    :tree(nullptr)
{
}

/********************************************************************
*** FUNCTION BST300 copy constructor ***
*********************************************************************
*** DESCRIPTION : This constructor will make a deep copy of a BST 300
*** INPUT ARGS : BST300 - the bst to copy              ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : BST300 ***
********************************************************************/
BST300::BST300(const BST300 &otherTree) : BST300()
{
    copy(otherTree.tree);
}


/********************************************************************
*** FUNCTION BST300 destructor ***
*********************************************************************
*** DESCRIPTION : destroys the tree, freeing all memory.
********************************************************************/
BST300::~BST300() {
    destroy(tree);
}

/********************************************************************
*** FUNCTION insert ***
*********************************************************************
*** DESCRIPTION : inserts an element into the bst
*** INPUT ARGS :  Element300 element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::insert(const Element300 element) {
    try {
        if (tree == nullptr)
            tree = new TNode300(nullptr, nullptr, element, 1);
        else
            insert(element, tree);
    }
    catch (std::bad_alloc &ba)
    {
        std::cout << "Ran out of memory." << std::endl;
    }
}

/********************************************************************
*** FUNCTION insert ***
*********************************************************************
*** DESCRIPTION : recursive implentation of insert
*** INPUT ARGS :  Element300 element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : TNode300Ptr currentPosition ***
*** RETURN : void ***
********************************************************************/
void BST300::insert(const Element300 element, TNode300Ptr &currentPosition) {

    TNode300Ptr right = currentPosition->getRight();
    TNode300Ptr left = currentPosition->getLeft();

    // Check if this is the right node, increment if true
    if (element == currentPosition->getElement())
        currentPosition->setCount(currentPosition->getCount() + 1);
    // If this is the case we want to go right.
    else if (element > currentPosition->getElement())
    {
        // Go right or create right if not possible
        if (right == nullptr)
            currentPosition->setRight(new TNode300(nullptr, nullptr, element, 1));
        else
            insert(element,right);
    }
        // Go Left
    else
    {
        if ( left == nullptr)
            currentPosition->setLeft(new TNode300(nullptr, nullptr, element, 1));
        else
            insert(element, left);
    }
}

/********************************************************************
*** FUNCTION remove ***
*********************************************************************
*** DESCRIPTION : removes an element or decrements its counter
*** INPUT ARGS :  Element300 element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::remove(const Element300 element) {
        remove(element,tree);
}

/********************************************************************
*** FUNCTION remove ***
*********************************************************************
*** DESCRIPTION : recursive implentation of remove
*** INPUT ARGS :  Element300 element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : TNode300ptr current Position ***
*** RETURN : void ***
********************************************************************/
void BST300::remove(const Element300 element, TNode300Ptr &cur) {

    if (cur == nullptr)
    {
        return; // Not in BST
    }
    else if (cur->getElement() == element)
    {
        cur->setCount(cur->getCount() - 1);
        if (cur->getCount() < 1)
        {
            // delete node
            removeNode(cur);

        }
    }
    else if (cur->getElement() > element) {
        TNode300Ptr left = cur->getLeft();
        remove(element, left);
        cur->setLeft(left);

    }
    else {
        TNode300Ptr right = cur->getRight();
        remove(element, right);
        cur->setRight(right);
    }
}

/********************************************************************
*** FUNCTION removeNode ***
*********************************************************************
*** DESCRIPTION : removes a node and maintains BST
*** INPUT ARGS :  none ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : TNode300ptr current Position ***
*** RETURN : void ***
********************************************************************/
void BST300::removeNode(TNode300Ptr &cur) {

    if (cur->getLeft() == nullptr && cur->getRight() == nullptr)
    {
        delete cur;
        cur = nullptr; // Since pass by reference this will take care of parent
    }
    else if (cur->getRight() == nullptr)
    {
        TNode300Ptr tmp = cur;
        cur = cur->getLeft();
        delete tmp;
    }
    else if (cur->getLeft() == nullptr)
    {
        TNode300Ptr tmp = cur;
        cur = cur->getRight();
        delete tmp;
    }
    else
    {
        TNode300Ptr tmp = nullptr;

        TNode300Ptr right = cur->getRight();

        findMinNode(right, tmp);

        cur->setRight(right);

        cur->setElement(tmp->getElement());
        cur->setCount(tmp->getCount());
        delete tmp;
    }
}

/********************************************************************
*** FUNCTION findMinNode ***
*********************************************************************
*** DESCRIPTION : used with delete node in event of left and right
*** INPUT ARGS :  Element300 element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : TNode300ptr tmp ***
*** RETURN : void ***
********************************************************************/
void BST300::findMinNode(TNode300Ptr &cur, TNode300Ptr &tmp) {
    if(cur->getLeft() == nullptr)
    {
        tmp = cur;
        cur = cur->getRight();
        tmp->setRight(nullptr);
    }
    else {
        TNode300Ptr left = cur->getLeft();
        findMinNode(left, tmp);
        cur->setLeft(left);
    }
}

/********************************************************************
*** FUNCTION inOrder ***
*********************************************************************
*** DESCRIPTION : shows BST in order
*** INPUT ARGS :  none **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::inOrder() const {
    std::cout << "Start -> ";
    inOrder(tree);
    std::cout << "Stop" << std::endl;
}

/********************************************************************
*** FUNCTION inOrder ***
*********************************************************************
*** DESCRIPTION : recursive imlentation of inOrder
*** INPUT ARGS :  TNode300Ptr currentPostion **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::inOrder(const TNode300Ptr cur) const {
    if (cur != nullptr)
    {
        inOrder(cur->getLeft());
        std::cout << cur->getElement() << "/" << cur->getCount() << " -> ";
        inOrder(cur->getRight());
    }
}

/********************************************************************
*** FUNCTION preOrder ***
*********************************************************************
*** DESCRIPTION : shows BST in preOrder
*** INPUT ARGS :  none **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::preOrder() const {

    std::cout << "Start -> ";
    preOrder(tree);
    std::cout << "Stop" << std::endl;
}

/********************************************************************
*** FUNCTION preorder ***
*********************************************************************
*** DESCRIPTION : recursive imlentation of preorder
*** INPUT ARGS :  TNode300Ptr currentPostion **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::preOrder(const TNode300Ptr cur) const {
    if (cur != nullptr)
    {
        std::cout << cur->getElement() << "/" << cur->getCount() << " -> ";
        preOrder(cur->getLeft());
        preOrder(cur->getRight());
    }
}

/********************************************************************
*** FUNCTION postOrder ***
*********************************************************************
*** DESCRIPTION : shows BST in postOrder
*** INPUT ARGS :  none **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::postOrder() const {
    std::cout << "Start -> ";
    postOrder(tree);
    std::cout << "Stop" << std::endl;
}


/********************************************************************
*** FUNCTION postOrder ***
*********************************************************************
*** DESCRIPTION : recursive imlentation of postOrder
*** INPUT ARGS :  TNode300Ptr currentPostion **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::postOrder(const TNode300Ptr cur) const {
    if (cur != nullptr)
    {
        postOrder(cur->getLeft());
        postOrder(cur->getRight());
        std::cout << cur->getElement() << "/" << cur->getCount() << " -> ";
    }
}

/********************************************************************
*** FUNCTION copy ***
*********************************************************************
*** DESCRIPTION : similiar to preOrder, copies list to our tree.
*** INPUT ARGS :  TNode300Ptr currentPosition **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::copy(const TNode300Ptr cur) {
    // Traverse in pre order
    if (cur != nullptr)
    {
        for (int i = 0; i < cur->getCount(); i++)
            insert(cur->getElement());
        copy(cur->getLeft());
        copy(cur->getRight());
    }
}

/********************************************************************
*** FUNCTION find ***
*********************************************************************
*** DESCRIPTION : returns a pointer to a node with element
*** INPUT ARGS :  Element300 element to find **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : TNode300Ptr ***
********************************************************************/
TNode300Ptr BST300::find(const Element300 element) const {
    return find(element, tree);
}

/********************************************************************
*** FUNCTION find ***
*********************************************************************
*** DESCRIPTION : recursive implentation of find
*** INPUT ARGS :  Element300 element to find **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : TNode300Ptr cur ***
*** RETURN : TNode300Ptr ***
********************************************************************/
TNode300Ptr BST300::find(const Element300 element, const TNode300Ptr cur) const {
    if (cur == nullptr)
        return nullptr;
    else if (cur->getElement() == element)
        return cur;
    else if (cur->getElement() > element)
        return find(element, cur->getLeft()); // consts, no reassignment needed
    else
        return find(element, cur->getRight());
}

/********************************************************************
*** FUNCTION destroy ***
*********************************************************************
*** DESCRIPTION : desttroys entire list
*** INPUT ARGS :  none **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void BST300::destroy(TNode300Ptr &) {
    while (tree != nullptr)
        removeNode(tree);
}


/*
 * TNode300 Implentation
 */


/********************************************************************
*** FUNCTION TNode300 Constructor ***
*********************************************************************
*** DESCRIPTION : initilizaes members wtih specified arguements
*** INPUT ARGS :  left,right,element,count **
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : TNode300 ***
********************************************************************/
TNode300::TNode300(const TNode300Ptr left, const TNode300Ptr right
        , Element300 element, int count)
        : left(left), right(right), element(element), count(count){}


/********************************************************************
*** FUNCTION TNode300 getter and setters ***
*********************************************************************
*** DESCRIPTION : getters and setters for TNode300 members
********************************************************************/
TNode300Ptr TNode300::getLeft() const
{
    return left;
}

void TNode300::setLeft(const TNode300Ptr left)
{
    TNode300::left = left;
}

TNode300Ptr TNode300::getRight() const
{
    return right;
}

void TNode300::setRight(const TNode300Ptr right)
{
    TNode300::right = right;
}

Element300 TNode300::getElement() const {
    return element;
}

void TNode300::setElement(Element300 element) {
    TNode300::element = element;
}

int TNode300::getCount() const {
    return count;
}

void TNode300::setCount(int count) {
    TNode300::count = count;
}

