/********************************************************************
*** NAME : Justin Hoogestraat***
*** CLASS : CSc 300 ***
*** ASSIGNMENT : 2***
*** DUE DATE : 2/15/17***
*** INSTRUCTOR : HAMER ***
*********************************************************************
*** DESCRIPTION : This class implements a stack of 8 character long
*** arrays using a linked list implentation.
********************************************************************/

#ifndef ASSN2_HOOGEJ2_H
#define ASSN2_HOOGEJ2_H

const int MAX_ARR_SIZE = 8;
typedef char Element300[MAX_ARR_SIZE];

class Stack300 {
public:							//  exportable
    //  General description of each of the ADT operations/methods/functions – exportable operations only
    /********************************************************************
    *** FUNCTION Stack default constructor ***
    *********************************************************************
    *** DESCRIPTION : This constructor initilises the top to null.
    ********************************************************************/
    Stack300();
    /********************************************************************
    *** FUNCTION Stack copy constructor ***
    *********************************************************************
    *** DESCRIPTION : This constructor will make a deep copy of a STack 300
    *** INPUT ARGS : Stack300 - the stack to copy              ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none ***
    ********************************************************************/
    Stack300( Stack300 & );
    /********************************************************************
    *** FUNCTION Stack destructor ***
    *********************************************************************
    *** DESCRIPTION : This destructor will free any memory in its nodes
    ********************************************************************/
    ~Stack300();
    /********************************************************************
    *** FUNCTION pop ***
    *********************************************************************
    *** DESCRIPTION : Copys the top element into an array, and then deletes
    *** the top node from the stack.
    *** INPUT ARGS :  None ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : Element300 - Character pointer to store
    *** RETURN : void ***
    ********************************************************************/
    void pop( Element300 );			//  UPDATED
    /********************************************************************
    *** FUNCTION push ***
    *********************************************************************
    *** DESCRIPTION : adds a new node to the stack with the arguement as its element
    *** INPUT ARGS :  Element300 - character array to use as element ***
    *** OUTPUT ARGS : none ***
    *** IN/OUT ARGS : none
    *** RETURN : void ***
    ********************************************************************/
    void push( const Element300 );
    /********************************************************************
    *** FUNCTION view ***
    *********************************************************************
    *** DESCRIPTION : prints stack with top first
    ********************************************************************/
    void view();
    /********************************************************************
    *** FUNCTION view Reverse ***
    *********************************************************************
    *** DESCRIPTION : Prints stack with top last
    ********************************************************************/
    void viewReverse();
    // ~ hamer

private:						//  non-exportable

    void view(const char *);
    // - hamer
    struct Node300;
    typedef Node300 * Node300Ptr;
    struct Node300 {//  no private documentation in header file
        Node300();
        Node300(const Element300 elment, const Node300Ptr next);
        Element300 element;
        Node300Ptr next;
    };
    Node300Ptr top;
public:
    /********************************************************************
    *** FUNCTION getTop ***
    *********************************************************************
    *** DESCRIPTION : getter for top
    *** returns Node300 - the top of the stack
    ********************************************************************/
    const Node300 *getTop() const;

private:

    void copy(Node300Ptr);
    void purge(Node300Ptr);
    void reverse();

};



#endif //ASSN2_HOOGEJ2_H
