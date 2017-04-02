#include <iostream>
#include "justih5.h"

using namespace std;


int main() {


    BST300 a;
    a.insert(6);
    a.insert(2);
    a.insert(1);
    a.insert(1);
    a.insert(4);
    a.insert(4);
    a.insert(3);
    a.insert(5);
    a.insert(7);
    a.insert(9);
    a.insert(8);

    a.preOrder();
    a.inOrder();
    a.postOrder();

    BST300 b(a);

    b.preOrder();

    a.remove(8);


    a.preOrder();
    a.inOrder();
    a.postOrder();


    cout << "------ The Dak ------" << endl;
    BST300 bst;

    bst.insert(6);

    for(double i = 4; i < 10; i++)
        bst.insert(i);
    for(double i = 2; i < 5; i++)
        bst.insert(i);

    for(double i = 2; i < 4; i++)
        bst.remove(i);

    bst.remove(6);
    bst.remove(6);
    bst.remove(6);

    bst.inOrder();
    bst.preOrder();
    bst.postOrder();


    TNode300Ptr lol = bst.find(6);
    cout << lol << endl;
    lol = bst.find(9);
    cout << lol->getElement();

    lol = nullptr;
    cout << endl << lol << endl;

}