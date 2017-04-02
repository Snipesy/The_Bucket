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

#include "hoogej1division.h"
#include <iostream>
#include <cstring>
#include <iomanip>

using namespace std;

/********************************************************************
*** FUNCTION division constructor ***
*********************************************************************
*** DESCRIPTION : This constructor will prompt the user for a file
*** and then read that file to generate teams. This could be moved to
*** a seperate open method, but the constructor allows simplicity.
********************************************************************/
division::division() : numberOfTeams(0) {
    ifstream *f = nullptr;
    bool suc = promptForFileName(f);
    if (!suc)
    {
        cout << "Could not open specified file." << endl;
    }
    else
    {
        readFile(f);
    }

    delete f;
}

/********************************************************************
*** FUNCTION division constructor overload ***
*********************************************************************
*** DESCRIPTION : This constructor will use the specified file name
*** instead of prompting the user.
********************************************************************/
division::division(char *fileName[]) : numberOfTeams(0) {
    ifstream *f = new ifstream(fileName[1]);
    if (f->is_open())
    {
        // Good
        readFile(f);
    }
    else
    {
        cout << "Could not open specified file." << endl;
    }

    delete f;
}

/********************************************************************
*** FUNCTION readFile ***
*********************************************************************
*** DESCRIPTION : Reads a filestream and parses its contents to
***  generate teams                                               ***
*** INPUT ARGS : ifstream f - the filestream to use               ***
*** OUTPUT ARGS : none ***
*** IN/OUT ARGS : none ***
*** RETURN : void ***
********************************************************************/
void division::readFile(std::ifstream *f) {
    OneString line;
    // Go over each line

    while (!(f->getline(line, MAX_ONE_STRING_SIZE).eof()) && numberOfTeams < MAX_NUMBER_OF_TEAMS)
    {

        int linePosition = 0;

        OneString teamName, wins, losses, overtime_losses;


        // verify valid and get words
        if (
        getWord(line, linePosition, teamName) &&
        getWord(line, linePosition, wins) &&
        getWord(line, linePosition, losses) &&
        getWord(line, linePosition, overtime_losses))
        {
            // Construct Team
            ourTeams[numberOfTeams] = new hoogej1oneteam(teamName,
                                                         atoi(wins), atoi(losses), atoi(overtime_losses));
            // Increment num of teams counter
            numberOfTeams++;
        }

        // else just skip


    }



}

/********************************************************************
*** FUNCTION readFile ***
*********************************************************************
*** DESCRIPTION : reads a word out of a character array by detecting
*** the first space or null terminator after eating leading spaces. ***
*** INPUT ARGS : char * line - character array to parse
*** OUTPUT ARGS : char * word - character array to store output word
*** IN/OUT ARGS : currentPosition - current position on line ***
*** RETURN : bool success ***
********************************************************************/
bool division::getWord(char *line, int &currentPosition, char *word) {

    int wordPosition = 0;
    bool suc = false;

    // Eat any leading spaces
    while (line[currentPosition] != '\0' && line[currentPosition] == ' '
           && currentPosition < MAX_ONE_STRING_SIZE)
        currentPosition++;

    // Go through the line at the position until a null terminator is hit,
    // a space is hit, or we've run out of space.
    while (line[currentPosition] != '\0' && line[currentPosition] != ' ')
    {
        suc = true;
        word[wordPosition] = line[currentPosition];
        currentPosition++;
        wordPosition++;
    }

    // increment current position
    currentPosition++;
    // add null terminator
    word[wordPosition] = '\0';

    return suc;
}






/********************************************************************
*** FUNCTION getNumberOfTeams ***
*********************************************************************
*** DESCRIPTION : getter for number of teams stored in division ***
*** RETURN : int numberofteams ***
********************************************************************/
int division::getNumberOfTeams() const {
    return numberOfTeams;
}

/********************************************************************
*** FUNCTION promptForFileName ***
*********************************************************************
*** DESCRIPTION : asks for a file name from user and then tries
***             to open the specified file stream.               ***
*** IN/OUT ARGS : ifstream * f, filestream to use                ***
*** RETURN : bool suc - result of file open
********************************************************************/
bool division::promptForFileName(std::ifstream * &f) {

    OneString buffer;
    cout << "Enter filename: ";
    cin.getline(buffer, MAX_ONE_STRING_SIZE);

    // Make new ifstream if nullptr
    if (f == nullptr)
        f = new ifstream(buffer);
    else
        f->open(buffer);

    // return true if file opened, else false
    if (f->is_open() == false)
    {
        return false;
    }
    return true;

}

/********************************************************************
*** FUNCTION division destructor ***
*********************************************************************
*** DESCRIPTION : cleans up teams ***
********************************************************************/
division::~division() {
    // Clean up our teams
    for (int i = 0; i < numberOfTeams; i++)
        delete ourTeams[i];
}

/********************************************************************
*** FUNCTION sort teams ***
*********************************************************************
*** DESCRIPTION : uses a bubble sort algorithim to sort the      ***
***             division's teams                                 ***
********************************************************************/
void division::sortTeams() {

    // Create array for our names
    OneString names[MAX_NUMBER_OF_TEAMS];
    // Copy names into array
    for (int i = 0; i < numberOfTeams; i++)
        strcpy(names[i], ourTeams[i]->getOurName());



    // standard Strcmp bubble sort
    bool done = false;
    while (!done)	{
        done = true;
        for (int i = 0; i < numberOfTeams - 1; i++)
        {
            // str case cmp is a posix compliant stricmp
            int cmp = strcasecmp(names[i], names[i + 1]);

            if (cmp > 0)
            {
                done = false;
                OneString tmp;
                // Swap inner names
                // copy into tmp
                strcpy(tmp, names[i]);

                // move other name
                strcpy(names[i], names[i + 1]);

                //move temp into old
                strcpy(names[i + 1], tmp);

                // Swap pointers
                hoogej1oneteam * tmpptr = ourTeams[i];
                ourTeams[i] = ourTeams[i+1];
                ourTeams[i + 1] = tmpptr;
            }

        }
    }

}

/********************************************************************
*** FUNCTION sort teams ***
*********************************************************************
*** DESCRIPTION : uses standard io to show the statistics to the ***
***             user                                             ***
********************************************************************/
void division::showTeams() {

    cout << fixed << showpoint << setprecision(2) << endl;


    // show individual team stats.

    cout << "Individual Statistics" << endl;
    cout << left << setw(15) << setfill(' ') << "Team Name";
    cout << left << setw(10) << setfill(' ') << "Wins";
    cout << left << setw(10) << setfill(' ') << "Losses";
    cout << left << setw(10) << setfill(' ') << "OT Losses";
    cout << left << setw(10) << setfill(' ') << "Points";
    cout << left << setw(10) << setfill(' ') << "Points/Game";


    cout << endl;

    for (int i = 0; i < numberOfTeams; i++)
    {
        cout << left << setw(15) << setfill(' ') << ourTeams[i]->getOurName();
        cout << left << setw(10) << setfill(' ') << ourTeams[i]->getOurWins();
        cout << left << setw(10) << setfill(' ') << ourTeams[i]->getOurLosses();
        cout << left << setw(10) << setfill(' ') << ourTeams[i]->getOurOvertimeLosses();
        cout << left << setw(10) << setfill(' ') << ourTeams[i]->getOurPoints();
        cout << left << setw(10) << setfill(' ') << ourTeams[i]->getOurPointsPerGame();


        cout << endl;
    }

    cout << endl;
    // Show totals
    cout << left << setw(15) << setfill(' ') << "Totals";
    cout << left << setw(10) << setfill(' ') << getOurWins();
    cout << left << setw(10) << setfill(' ') << getOurLosses();
    cout << left << setw(10) << setfill(' ') << getOurOvertimeLosses();
    cout << left << setw(10) << setfill(' ') << getOurPoints();
    cout << left << setw(10) << setfill(' ') << getOurPointsPerGame();
    cout << endl;




}

/********************************************************************
*** FUNCTION getOurWins ***
*********************************************************************
*** DESCRIPTION : counts all wins of our teams and returns sum    ***
*** RETURN : int wins - number of wins
********************************************************************/
int division::getOurWins() const {
    int wins = 0;
    for(int i = 0; i < numberOfTeams; i++)
        wins += ourTeams[i]->getOurWins();
    return wins;
}

/********************************************************************
*** FUNCTION getOurWins ***
*********************************************************************
*** DESCRIPTION : counts all losses of our teams and returns sum  ***
*** RETURN : int losses - number of losses
********************************************************************/
int division::getOurLosses() const {
    int losses = 0;
    for(int i = 0; i < numberOfTeams; i++)
        losses += ourTeams[i]->getOurLosses();
    return losses;
}

/********************************************************************
*** FUNCTION getOurOvertimeLosses ***
*********************************************************************
*** DESCRIPTION : counts all ot losses of our teams and returns sum***
*** RETURN : int - number of overtime losses
********************************************************************/
int division::getOurOvertimeLosses() const {
    int losses = 0;
    for(int i = 0; i < numberOfTeams; i++)
        losses += ourTeams[i]->getOurOvertimeLosses();
    return losses;
}

/********************************************************************
*** FUNCTION getOurPoints ***
*********************************************************************
*** DESCRIPTION : counts all or points of our teams and returns sum***
*** RETURN : int - number of points
********************************************************************/
int division::getOurPoints() const {
    int points = 0;
    for(int i = 0; i < numberOfTeams; i++)
        points += ourTeams[i]->getOurPoints();
    return points;
}

/********************************************************************
*** FUNCTION getOurTotalGamesPlayed ***
*********************************************************************
*** DESCRIPTION : counts all values of our teams and returns sum***
*** RETURN : int - number of games played
********************************************************************/
int division::getOurTotalGamesPlayed() const
{
    return getOurWins() + getOurOvertimeLosses() + getOurLosses();
}

/********************************************************************
*** FUNCTION getOurPointsPerGame ***
*********************************************************************
*** DESCRIPTION : gets our points and divides by total games played***
*** RETURN : float - points per game
********************************************************************/
float division::getOurPointsPerGame() const {
    float ppp = getOurPoints() / (float) getOurTotalGamesPlayed();
    return ppp;
}

