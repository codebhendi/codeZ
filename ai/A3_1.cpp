#include <bits/stdc++.h>

using namespace std;

int m;
int n;
int q;
int mat[100][100];
int x2;
int y2;

int distance(int x1, int y1, int x2, int y2) {
    return (sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
}

int func(int x, int y, int d) {
    if (d >= 4) {
        cout << INT_MAX;
        return INT_MAX;
    }
    if (x == x2 && y == y2) {
        return 0;
    }

    int min_dis = INT_MAX;
//    cout << min_dis;

    for (int i = 0; i < 8; i++) {
        int dis;
        if (i == 0 && y + 1 < n && mat[x][y + 1] != 1) {
            cout << x << y << "right" << endl;

            dis = distance(x, y, x, y + 1);
            int temp2 = func(x, y + 1, d + 1);
            cout << "end" << endl;
            dis += temp2;
            cout << temp2;
            if (dis < 0) {
                //cout << dis;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 1 && x + 1 < m && y + 1 < n && mat[x + 1][y + 1] != 1) {
            cout << x << y << "down right"<< endl;
            dis = distance(x, y, x + 1, y + 1);
            int temp2 = func(x + 1, y + 1, d + 1);
            cout << temp2;
            dis = dis + temp2;
            cout << "end" << endl;
            if (dis < 0) {
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 2 && x + 1 < m && mat[x + 1][y] != 1) {
            dis = distance(x, y, x + 1, y);
            cout << dis;
            cout << "down" << endl;
            int temp2 = func(x + 1, y + 1, d + 1);
            cout << temp2 << "end" << endl ;
            dis = dis + temp2;
            if (dis < 0) {
                cout << x << y << endl;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 3 && x + 1 < m && y - 1 >= 0 && mat[x + 1][y - 1] != 1) {
            dis = distance(x, y, x + 1, y - 1);

                cout << x << y << "down left" << endl;

            dis = dis + func(x + 1, y - 1, d + 1);
            cout << "end" << endl;
            if (dis < 0) {
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 4 && y - 1 >= 0 && mat[x][y - 1] != 1) {
            cout << "left" << endl;
            dis = distance(x, y, x, y - 1);
            dis = dis + func(x, y - 1, d + 1);
            cout << "end" << endl;
            if (dis < 0) {
                cout << x << y << endl;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 5 && x - 1 >= 0 && y - 1 >= 0 && mat[x - 1][y - 1] != 1) {
            dis = distance(x, y, x, y + 1);
    cout << "up left" << endl;
            dis = dis + func(x, y + 1, d + 1);
            cout << "end" << endl;
            if (dis < 0) {
                cout << x << y << endl;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                min_dis = temp;
            }
        } else if (i == 6 && x - 1 >= 0 && mat[x - 1][y - 1] != 1) {
            cout << "up " << endl;
            dis = distance(x, y, x - 1, y);
            dis = dis + func(x - 1, y, d + 1);
            cout << "end" << endl;
            if (dis < 0) {
                cout << x << y << endl;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                cout << temp;
                min_dis = temp;
            }
        } else if (i == 7 && x - 1 >= 0 && y + 1 < n && mat[x - 1][y + 1] != 1) {
            dis = distance(x, y, x - 1, y + 1);
            cout << "up right" << endl;
            dis = dis + func(x - 1, y + 1, d + 1);
            cout << "end" << endl;
            if (dis < 0) {
                cout << x << y << endl;
                exit(1);
            }
            int temp = dis;
            if (min_dis > temp) {
                min_dis = temp;
            }
        }
        cout << min_dis << "fbklneflbn" << endl;

    }

    cout << min_dis << endl;
    return min_dis;
}

int main()
{
	scanf("%d%d", &m, &n);

	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			scanf("%d", &mat[i][j]);
		}
	}

	scanf("%d", &q);

	for (int i = 0; i < q; i++) {
        memset(mat, 1, sizeof(mat));
        int x1, y1;
		scanf("%d%d%d%d", &x1, &y1, &x2, &y2);
		cout << func(x1, y1, 0);
	}

	return 0;
}
