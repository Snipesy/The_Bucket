/********************************************************************
*** NAME : Justin Hoogestraat***
*** CLASS : CSc 300 ***
*** ASSIGNMENT : 1  ***
*** DUE DATE : 2/1/17 ***
*** INSTRUCTOR : HAMER ***
*********************************************************************
*** DESCRIPTION : The One Team class mainly serves to be the team in
*** for a division object. It doubles as storing statistics for the
*** team as well as generating others.
********************************************************************/

#ifndef ASSN1_ONETEAM_H
#define ASSN1_ONETEAM_H

/********************************************************************
*** ONE STRING ***
*********************************************************************
*** DESCRIPTION : The one string type is a character array with
*** MAX_ONE_STRING_SIZE elements.
********************************************************************/
const int MAX_ONE_STRING_SIZE = 80;
typedef char OneString[MAX_ONE_STRING_SIZE];

class hoogej1oneteam {
private:
    OneString ourName;
public:

    /********************************************************************
    *** FUNCTION getters ***
    *********************************************************************
    *** DESCRIPTION : retrieves specified value from object ***
    ********************************************************************/
    const char *getOurName() const;
    int getOurWins() const;
    int getOurLosses() const;
    int getOurOvertimeLosses() const;
    int getOurPoints() const;
    int getTotalGamesPlayed() const;
    float getOurPointsPerGame() const;

private:
    int ourWins;
    int ourLosses;
    int ourOvertimeLosses;
public:
    /********************************************************************
    *** FUNCTION oneteam constructor ***
    *********************************************************************
    *** DESCRIPTION : sets the private variables to args ***
    *** ARGS : string, int, int, int , the names, wins, losses and OT losses
    ********************************************************************/
    hoogej1oneteam(OneString name, int wins, int losses, int overtimeLosses);

    /********************************************************************
    *** FUNCTION oneteam constructor ***
    *********************************************************************
    *** DESCRIPTION : sets the private variables to placeholders ***
    ********************************************************************/
    hoogej1oneteam();
};


#endif //ASSN1_ONETEAM_H
