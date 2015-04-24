#include <bits/stdc++.h>

int weight[100];
int value[100];

bool possible(int index, int wt) {
    if (wt - weight[index] > 0) {
        return true;
    } else {
        return false;
    }
}

int knapsack(int index, int wt, int n) {
    if (index <= n) {
        return 0;
    }

    int max = INT_MIN;
    if (possible(index, wt)) {
        max = knapsack(index + 1, wt - weight[index], n) + value[index];
    }

    int max2;

    max2 = knapsack(index + 1, wt, n);

    if (max < max2) {
        max = max2;
    }

    return max;
}


int main()
{

    int n;
    int wt;
    scanf("%d", &wt);

    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        scanf("%d %d", &weight[i], &value[i]);
    }
    knapsack(0, wt, n);
}
