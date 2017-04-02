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
#include "hoogej2.h"
#include <cstring>
#include <cstdlib>
#include <iostream>

using namespace std;

/********************************************************************
*** FUNCTION Stack default constructor ***
*********************************************************************
*** DESCRIPTION : This constructor initilises the top to null.
********************************************************************/
Stack300::Stack300() :
    top(NULL)
{
    // Do nothing
}

/********************************************************************
*** FUNCTION Stack copy constructor ***
*********************************************************************
*** DESCRIPTION : This constructor will make a deep copy of a STack 300
*** INPUT ARGS : Stack300 - the stack to copy              ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
********************************************************************/
Stack300::Stack300(Stack300 & otherStack) :
    top(NULL)
{
    // copy from other stacks top
    copy((Node300Ptr)otherStack.getTop());
}
/********************************************************************
*** FUNCTION Stack destructor ***
*********************************************************************
*** DESCRIPTION : This destructor will free any memory in its nodes
********************************************************************/
Stack300::~Stack300() {
    purge(top);
}

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
void Stack300::pop(Element300 toStore) {
    // store top into the character array
    if (top) {
        strcpy(toStore, top->element);

        Node300Ptr oldTop = top;
        if (top->next == NULL)
            top = NULL;
        else
            top = oldTop->next;
        delete oldTop;
    }

}

/********************************************************************
*** FUNCTION push ***
*********************************************************************
*** DESCRIPTION : adds a new node to the stack with the arguement as its element
*** INPUT ARGS :  Element300 - character array to use as element ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none
*** RETURN : void ***
********************************************************************/
void Stack300::push(const Element300 toStore) {


    Node300Ptr newNodePtr = new Node300(toStore, NULL);
    if (top == NULL)
    {
        // stack is empty
        top = newNodePtr;
    }
    else
    {
        // stack has something
        // make new element top and link old top
        newNodePtr->next = top;
        top = newNodePtr;
    }

}

/********************************************************************
*** FUNCTION view ***
*********************************************************************
*** DESCRIPTION : prints stack with top first
********************************************************************/
void Stack300::view() {
    view("->");
}

/********************************************************************
*** FUNCTION view Reverse ***
*********************************************************************
*** DESCRIPTION : Prints stack with top last
********************************************************************/
void Stack300::viewReverse() {

    reverse();
    view("<-");
    reverse();
}

/********************************************************************
*** FUNCTION view private overload ***
*********************************************************************
*** DESCRIPTION : This allows the characters between each elements to be
*** easilly changed.
*** INPUT ARGS :  char * - array to seperate with
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none
*** RETURN : void ***
********************************************************************/
void Stack300::view(const char * between) {
    // Copy Stack
    Stack300 *tmpStack = new Stack300(*this);
    Element300 string;

    if (top)
    {
        pop(string);
        cout << string;
        while (top) {
            pop(string);
            cout << between << string;
        }
        cout << endl;
        // Restore old stack
        copy(Node300Ptr(tmpStack->getTop()));
        delete tmpStack;
    }
    else
    {
        cout << "The stack is empty!" << endl;
    }
}


/********************************************************************
*** FUNCTION copy ***
*********************************************************************
*** DESCRIPTION : The copy method is used primarilly for the copy constructor,
*** but it can be used at any time to copy a new stack. This will not purge
*** the old stack.
*** INPUT ARGS :  Node300Ptr - All elements after this node will be copied
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none
*** RETURN : void ***
********************************************************************/
void Stack300::copy(Node300Ptr ptr) {
    Node300Ptr current = ptr;
    Node300Ptr newNode;
    Node300Ptr prevNewNode = NULL;


    // create deep copy
    while (current != NULL)
    {
        if (prevNewNode == NULL) {
            // This will be the new top top
            newNode = new Node300(current->element, NULL);
            prevNewNode = newNode;
            top = newNode;
        }
        else {
            newNode = new Node300(current->element, NULL);
            prevNewNode->next = newNode;
            prevNewNode = newNode;
        }
        current = current->next;



    }



}

/********************************************************************
*** FUNCTION copy ***
*********************************************************************
*** DESCRIPTION : Deletes all nodes from the specifeid node.
*** INPUT ARGS :  Node300Ptr - All nodes after this node will be removed
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none
*** RETURN : void ***
********************************************************************/
void Stack300::purge(Stack300::Node300Ptr ptr) {
    // Delete the node and all nodes after that node
    Node300Ptr tmpPtr = ptr;
    while (ptr)
    {
        tmpPtr = ptr;
        ptr = ptr->next;
        delete tmpPtr;
    }
}

/********************************************************************
*** FUNCTION Node300 Default Constructor ***
*********************************************************************
********************************************************************/
Stack300::Node300::Node300() {
    // Default constructure for node300 struct
}

/********************************************************************
*** FUNCTION Node300 Constructor ***
*********************************************************************
*** DESCRIPTION : Allows Node300 members to be popultated with constructor
********************************************************************/
Stack300::Node300::Node300(const Element300 element, const Stack300::Node300Ptr next) {
    // make new copy of string
    strcpy(this->element, element);
    this->next = next;
}


/********************************************************************
*** FUNCTION reverse ***
*********************************************************************
*** DESCRIPTION : reverse all nodes in the stack, so the top becomes
*** the bottom.
********************************************************************/
void Stack300::reverse() {
    Node300Ptr current;
    Node300Ptr previous;
    Node300Ptr next;

    current = top;
    previous = NULL;
    // Flips each node so that the next points to the prev
    while (current)
    {
        next = current->next;
        current->next = previous;
        previous = current;
        current = next;
    }
    // Since current is null, the previous pointer is the  new head.
    top = previous;
}

/********************************************************************
*** FUNCTION getTop ***
*********************************************************************
*** DESCRIPTION : getter for top
*** returns Node300 - the top of the stack
********************************************************************/
const Stack300::Node300 *Stack300::getTop() const {
    return top;
}

