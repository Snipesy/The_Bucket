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

#ifndef UNTITLED2_JUSTIH5_H
#define UNTITLED2_JUSTIH5_H



typedef double Element300;

class TNode300;
typedef TNode300* TNode300Ptr;

// The TNOde300
class TNode300 {
public:
    /*********************************************************************
    *** DESCRIPTION : initilizaes members wtih specified arguements
    *** INPUT ARGS :  left,right,element,count **
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : TNode300 ***
    ********************************************************************/
    TNode300(const TNode300Ptr left, const TNode300Ptr right, Element300 element, int count);

    /********************************************************************
    *** FUNCTION TNode300 getter and setters ***
    *********************************************************************
    *** DESCRIPTION : getters and setters for TNode300 members
    ********************************************************************/
    TNode300Ptr getLeft() const;
    void setLeft(const TNode300Ptr left);
    TNode300Ptr getRight() const;
    void setRight(const TNode300Ptr right);
    Element300 getElement() const;
    void setElement(Element300 element);
    int getCount() const;
    void setCount(int count);
public:
    TNode300Ptr left;
    TNode300Ptr right;
    Element300 element;
    int count;
};


class BST300 {

public:								//  exportable
    /********************************************************************
    *** FUNCTION BST300 default constructor ***
    *********************************************************************
    *** DESCRIPTION : This constructor initilises the tree to null.
    *** RETURN : BST300 ***
    ********************************************************************/
    BST300( );

    /********************************************************************
    *** FUNCTION BST300 copy constructor ***
    *********************************************************************
    *** DESCRIPTION : This constructor will make a deep copy of a BST 300
    *** INPUT ARGS : BST300 - the bst to copy              ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : BST300 ***
    ********************************************************************/
    BST300( const BST300 & );

    /********************************************************************
    *** FUNCTION BST300 destructor ***
    *********************************************************************
    *** DESCRIPTION : destroys the tree, freeing all memory.
    ********************************************************************/
    ~BST300( );

    /********************************************************************
    *** FUNCTION insert ***
    *********************************************************************
    *** DESCRIPTION : inserts an element into the bst
    *** INPUT ARGS :  Element300 element ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : void ***
    ********************************************************************/
    void insert( const Element300 );

    /********************************************************************
    *** FUNCTION remove ***
    *********************************************************************
    *** DESCRIPTION : removes an element or decrements its counter
    *** INPUT ARGS :  Element300 element ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : void ***
    ********************************************************************/
    void remove( const Element300 );

    /********************************************************************
    *** FUNCTION find ***
    *********************************************************************
    *** DESCRIPTION : returns a pointer to a node with element
    *** INPUT ARGS :  Element300 element to find **
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : TNode300Ptr ***
    ********************************************************************/
    TNode300Ptr find( const Element300 ) const;

    /********************************************************************
    *** FUNCTION preOrder ***
    *********************************************************************
    *** DESCRIPTION : shows BST in preOrder
    *** INPUT ARGS :  none **
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : void ***
    ********************************************************************/
    void preOrder( ) const;

    /********************************************************************
    *** FUNCTION inOrder ***
    *********************************************************************
    *** DESCRIPTION : shows BST in order
    *** INPUT ARGS :  none **
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : void ***
    ********************************************************************/
    void inOrder( ) const;

    /********************************************************************
    *** FUNCTION postOrder ***
    *********************************************************************
    *** DESCRIPTION : shows BST in postOrder
    *** INPUT ARGS :  none **
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    *** RETURN : void ***
    ********************************************************************/
    void postOrder( ) const;


private:							//  non-exportable, hamer
    TNode300Ptr tree;
    void insert( const Element300, TNode300Ptr & );
    void remove( const Element300, TNode300Ptr & );
    TNode300Ptr find( const Element300, const TNode300Ptr ) const;
    void preOrder( const TNode300Ptr ) const;
    void inOrder( const TNode300Ptr ) const;
    void postOrder( const TNode300Ptr ) const;
    void copy( const TNode300Ptr );
    void destroy( TNode300Ptr & );
    void removeNode( TNode300Ptr & );
    void findMinNode( TNode300Ptr &, TNode300Ptr & );

};



#endif //UNTITLED2_JUSTIH5_H
