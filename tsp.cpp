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
    int max = 0;
    int temp_index;
    for (int i = 2; i < n; i++) {
        if (added[i] == 0) {
            flag = true;
            add(i);
            int temp = weight[index][i] + tsp(i, n);
            if(max < temp) {
                cout << index << " " << i << endl;
                max = temp;
                rem(i);
            }
        }
    }
    if (flag == false){
        cout << index <<  " " << 1 << endl;
        return weight[1][index];
    }
    return max;

}

int main()
{
    int t;
    scanf("%d", &t);

    while(t--) {
        int n;

        n = 4;

        int wight[4][4] = {{0, 10, 15, 20}, { 10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}};
        /*scanf("%d", &n);

        */for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( j!= i) {
                    printf("%d %d", i, j);
                    //scanf("%d",&weight[i][j] );
                    weight[i][j] = wight[i][j];
                }
            }
        }

        add(1);
        printf("%d", tsp(1, n));
    }

}
