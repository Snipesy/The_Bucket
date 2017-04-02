// At some point the list becomes sorted. Fact.
// Well okay... There is the possibility that
// The list never becomes sorted. But given an
// infinite time frame it should become sorted.

#include <iostream>

using namespace std;
const int max_items = 13;

// N^3 + 2n^2 + 2
int maxSubSum(int arr[], int n)
{
    int maxSum = 0;
    for (int i = 0; i < n; i++)
    {
        for (int j = i; j < n; j++)
        {
            int thisSum = 0;
            for (int k = i; k <= j; k++)
            {
                thisSum = thisSum + arr[k];
            }
            if (thisSum > maxSum)
                maxSum = thisSum;
        }
    }
    return maxSum;
}


bool isSorted(int arr[], int n)
{
    bool sorted = true;
    for (int i = 1; i < n; i++)
    {
        if (arr[i-1] < arr[i])
        {}
            // cool
        else
        {
            sorted = false;
            break;
        }
    }

    return sorted;
}

void swap(int pos1, int pos2, int arr[])
{
    int tmp = arr[pos1];
    arr[pos1] = arr[pos2];
    arr[pos2] = tmp;
}

void hogSort(int arr[], int n) {
    long long counter = 0;
    while (!isSorted(arr, n))
    {
        // Scramble the array
        for (int i = 0; i < n; i++)
        {
            int random = (rand() % (n - i)) + i;

            swap(i, random, arr);
        }
        counter++;
    }
    std::cout << "Sorted in " << counter << std::endl;

}

int main() {
    int arr[max_items] = {};
    for (int i = 0; i < max_items; i++)
    {
        arr[i] = std::rand();
    }
hogSort(arr, max_items);
    system("pause");
    return 0;
}
