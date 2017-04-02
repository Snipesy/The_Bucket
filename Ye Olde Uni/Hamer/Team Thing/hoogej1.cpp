/********************************************************************
*** NAME : Justin Hoogestraat***
*** CLASS : CSc 300 ***
*** ASSIGNMENT : 1***
*** DUE DATE : 2/1/17***
*** INSTRUCTOR : HAMER ***
*********************************************************************
*** DESCRIPTION : This program procceses division statistics for the
*** North American hockey league. It works by inputting a file,
*** , specified during runtime or as a command arguement, and then
*** parses the file and displays useful statistics to the user.***
********************************************************************/


#include <iostream>
#include "hoogej1division.h"

void brief();

/********************************************************************
*** FUNCTION main ***
*********************************************************************
*** DESCRIPTION : The entry point for the program. ***
*** INPUT ARGS : argc - number of command arguements
***              arg - pointer to character arrays of arguements ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : returns an integer passed to the system ***
********************************************************************/
int main(int argc, char * argv[]) {

    brief();

    division * ourDivision = nullptr;

    // construct division object based on command line inputs
    if (argc > 1)
    {
        ourDivision = new division(argv);
    }
    else
    {
        ourDivision = new division();
    }

    // If we got some teams then display the results, else give error
    if (ourDivision->getNumberOfTeams() > 0)
    {
        ourDivision->sortTeams();
        ourDivision->showTeams();
    }
    else
    {
        std::cout << "Nothing to show." << std::endl;
    }

    // cleanup division

    delete ourDivision;


    return 0;
}

/********************************************************************
*** FUNCTION brief ***
*********************************************************************
*** DESCRIPTION : provides a brief introduction to the program ***
********************************************************************/
void brief()
{
    std::cout << "This program will produce statistics for the North American Hockey League division by parsing"
            " a specified file.\n By Justin Hoogestraat" << std::endl << std::endl;
}
