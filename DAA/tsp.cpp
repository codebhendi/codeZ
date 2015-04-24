#include <bits/stdc++.h>

using namespace std;

int weight[1000][1000];
int added[1010];

void add(int index) {
    added[index] = 1;
    return ;
}

void rem(int index) {
    added[index] = 0;
    return;
}

int tsp(int index, int n) {
    bool flag = false;
    int min = INT_MAX;
    int temp_index;
    for (int i = 2; i <= n; i++) {
        if (added[i] == 0) {
            flag = true;
            add(i);
            int temp = weight[index][i] + tsp(i, n);
            if(min >temp) {
                //cout << index << " " << i <<  " " << added[3] << endl;
                min = temp;
            }
            rem(i);
        }
    }
    if (flag == false){
        //cout << index <<  "end" << 1 << endl;
        return weight[1][index];
    }
    return min;

}

int main()
{
    int t;
    scanf("%d", &t);

    while(t--) {
        int n;

        scanf("%d", &n);
        int wight[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( j!= i) {
                    printf("%d %d ", i, j);
                    scanf("%d",&wight[i][j] );
                    weight[i + 1][j + 1] = wight[i][j];
                }
            }
        }
        cout << endl;
        add(1);
        printf("%d\n", tsp(1, n));
    }

}
