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

#include "hoogej1oneteam.h"
#include <cstring>

/********************************************************************
*** FUNCTION oneteam constructor ***
*********************************************************************
*** DESCRIPTION : sets the private variables to args ***
*** ARGS : string, int, int, int , the names, wins, losses and OT losses
********************************************************************/
hoogej1oneteam::hoogej1oneteam(OneString name, int wins, int losses, int overtimeLosses) {
    strcpy(ourName, name);
    ourWins = wins;
    ourLosses = losses;
    ourOvertimeLosses = overtimeLosses;

}

/********************************************************************
*** FUNCTION oneteam constructor ***
*********************************************************************
*** DESCRIPTION : sets the private variables to placeholders ***
********************************************************************/
hoogej1oneteam::hoogej1oneteam()
{
    std::strcpy(ourName, "N/A");
    ourWins = 0;
    ourLosses = 0;
    ourOvertimeLosses = 0;
}

/********************************************************************
*** FUNCTION getOurName ***
*********************************************************************
*** DESCRIPTION : getter for the team name ***
*** RETURNS : char * - pointer to name
********************************************************************/
const char *hoogej1oneteam::getOurName() const {
    return ourName;
}

/********************************************************************
*** FUNCTION getOurWins ***
*********************************************************************
*** DESCRIPTION : getter for the team wins ***
*** RETURNS : int - wins
********************************************************************/
int hoogej1oneteam::getOurWins() const {
    return ourWins;
}

/********************************************************************
*** FUNCTION getOurLosses ***
*********************************************************************
*** DESCRIPTION : getter for the team losses ***
*** RETURNS : int - losses
********************************************************************/
int hoogej1oneteam::getOurLosses() const {
    return ourLosses;
}

/********************************************************************
*** FUNCTION getOurOvertimeLosses ***
*********************************************************************
*** DESCRIPTION : getter for the team ot losses ***
*** RETURNS : int - ot losses
********************************************************************/
int hoogej1oneteam::getOurOvertimeLosses() const {
    return ourOvertimeLosses;
}

/********************************************************************
*** FUNCTION getOurPointsPerGame ***
*********************************************************************
*** DESCRIPTION : calculates points per game ***
*** RETURNS : float - points per game
********************************************************************/
float hoogej1oneteam::getOurPointsPerGame() const {
    if (getTotalGamesPlayed() == 0) return 0;
    return getOurPoints() / (float) getTotalGamesPlayed();
}

/********************************************************************
*** FUNCTION getOurPoints ***
*********************************************************************
*** DESCRIPTION : calculates total points ***
*** RETURNS : int - total points
********************************************************************/
int hoogej1oneteam::getOurPoints() const {
    return (getOurWins() * 2) + getOurOvertimeLosses();
}

/********************************************************************
*** FUNCTION getOurPoints ***
*********************************************************************
*** DESCRIPTION : calculates total games ***
*** RETURNS : int - total games played
********************************************************************/
int hoogej1oneteam::getTotalGamesPlayed() const {
    return (getOurLosses() + getOurWins() + getOurOvertimeLosses());
}
