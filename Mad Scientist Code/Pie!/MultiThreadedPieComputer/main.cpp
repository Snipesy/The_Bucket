#include <iostream>
#include <mutex>
#include <thread>
#include <cmath>
#include <iomanip>


using namespace std;

mutex pieLock;
bool stop = false;
long long iterations = 1;
double sum = 0;

long long factorial(long long n)
{
    if (n == 0)
        return 1;
    long long product = 1;
    for (int i = 1; i < n + 1; i++)
        product *= i;

    return product;
}
const long long k1 = 545140134;
const long long k2 = 13591409;
const long long k3 = 640320;
const long long k4 = 100100025;
const long long k5 = 327843840;
const long long k6 = 53360;
double pieThreadUpdate(long long n)
{


    long long topn6 = 6 * n;
    long long top2 = (k2 + n * k1);
    long long top = factorial(topn6)  * top2;

    double bottom = ((factorial(n),3));


    // Gegory Series
    //double piece = (double)1 / (n*2 - 1);

    // old method
    //double piece = 1 /( pow((double)n,2) );

    return piece;
}
void pieMutexUpdate(double piece, long long iter)
{
    std::lock_guard<std::mutex> guard(pieLock);
    sum += piece;
    iterations = iter;
}
void pieThread(long long someint)
{
    long long ouriterations = 1;
    while (ouriterations < someint - 10000)
    {
        double piece = 0;
        // since mutex is costly operate in batches of 10000
        for (int i = 0; i < 10000; i++)
        {
            piece += pieThreadUpdate(ouriterations);
            ouriterations++;

        }
        pieMutexUpdate(piece, ouriterations);
    }

    stop = true;
}


double mainThreadUpdate()
{
    std::lock_guard<std::mutex> guard(pieLock);
    return sum;
}
//   ---
//  -----
// -------

int main() {
    thread p(pieThread, 9223372036854775807ll);
    cout.precision(40);
    long long prev = 0;
    while (!stop)
    {
        std::this_thread::sleep_for(std::chrono::milliseconds(500));

#ifdef _WIN32
        system("cls");
#else
        system("clear");
#endif

        cout << mainThreadUpdate()  << endl;
        long long cur = iterations;
        cout << "Ops/Second: " << (cur - prev);
        prev = cur;
    }
    p.join();
    return 0;
}

