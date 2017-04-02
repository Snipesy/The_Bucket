#include <iostream>
#include "hoogej2.h"

using namespace std;

int main() {
    Stack300 a;
    Element300 string;

    a.push("1");
    a.push("2");
    a.push("3");
    a.push("4");
    a.view();
    a.viewReverse();
    a.pop(string);
    cout << string << endl;
    a.pop(string);
    cout << string << endl;
    a.pop(string);
    cout << string << endl;
    a.pop(string);
    cout << string << endl;
    a.pop(string);
    cout << string << endl;

    a.push("1");
    a.push("2");
    a.push("3");
    a.push("4");
    cout << " Copy Test" << endl;

    Stack300 * b = new Stack300(a);
    b->view();
    b->viewReverse();

    return 0;
}