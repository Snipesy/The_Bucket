/********************************************************************
*** NAME : Justin Hoogestraat***
*** CLASS : CSc 300 ***
*** ASSIGNMENT : 1  ***
*** DUE DATE : 2/1/17 ***
*** INSTRUCTOR : HAMER ***
*********************************************************************
*** DESCRIPTION : The division class can process a file and generate
*** . It can then generate statistics for those teams, and the
*** division as a whole.                                        ***
********************************************************************/

#ifndef ASSN1_DIVISION_H
#define ASSN1_DIVISION_H

#include <fstream>
#include "hoogej1oneteam.h"

const int MAX_NUMBER_OF_TEAMS = 6;

class division {
public:

private:
    bool promptForFileName(std::ifstream * &f);
    void readFile(std::ifstream *f);

    int numberOfTeams;
    hoogej1oneteam * ourTeams[MAX_NUMBER_OF_TEAMS];
    bool getWord(OneString line, int &currentPosition, OneString word);



public:

    /********************************************************************
    *** FUNCTION sort teams ***
    *********************************************************************
    *** DESCRIPTION : uses a bubble sort algorithim to sort the      ***
    ***             division's teams                                 ***
    ********************************************************************/
    void sortTeams();

    /********************************************************************
    *** FUNCTION sort teams ***
    *********************************************************************
    *** DESCRIPTION : uses standard io to show the statistics to the ***
    ***             user                                             ***
    ********************************************************************/
    void showTeams();

    /********************************************************************
    *** FUNCTION division constructor ***
    *********************************************************************
    *** DESCRIPTION : This constructor will prompt the user for a file
    *** and then read that file to generate teams. This could be moved to
    *** a seperate open method, but the constructor allows simplicity.
    ********************************************************************/
    division();
    /********************************************************************
    *** FUNCTION division constructor overload ***
    *********************************************************************
    *** DESCRIPTION : This constructor will use the specified file name
    *** instead of prompting the user.
    ********************************************************************/
    division(char * fileName[]);
    /********************************************************************
    *** FUNCTION division destructor ***
    *********************************************************************
    *** DESCRIPTION : cleans up teams ***
    ********************************************************************/
    ~division();


    /********************************************************************
   *** FUNCTION getters ***
   *********************************************************************
   *** DESCRIPTION : returns specified value for the entire divison***
   ********************************************************************/
    int getOurWins() const;
    int getOurLosses() const;
    int getOurOvertimeLosses() const;
    int getOurPoints() const;
    int getOurTotalGamesPlayed() const;
    int getNumberOfTeams() const;
    float getOurPointsPerGame() const;




};


#endif //ASSN1_DIVISION_H
