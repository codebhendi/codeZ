#include <bits/stdc++.h>

using namespace std;

bool mem[100][100];
int m;
int n;
int q;
int mat[100][100];
int x2;
int y2;

float distance(int x1, int y1, int x2, int y2) {
	return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
}

int func(int x1, int y1) {
	int min_weight = INT_MAX;
	bool flag = false;
	mem[x1][y1] = true;

	for (int i = 0; i < 8; i++) {
		float dis;

		if (i == 0 && y1 + 1 < n && !mem[x1][y1 + 1] && mat[x1][y1 + 1] != 1) {// for the right move
			flag = true;
			dis = distance(x1, y1, x1, y1 + 1);
			cout << x1 << y1 << " ";
			cout << "right" << endl;

			if (!(x1 == x2 && y1 + 1 == y2)) {
                //cout << "sdfkndsf" << endl;
                dis = dis + func(x1, y1 + 1);
                //cout << dis << endl;
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}

		} else if (i == 1 && x1 + 1 < m && y1 + 1 < n && !mem[x1 + 1][y1 + 1] && mat[x1 + 1][y1 + 1] != 1) {// for the right down move
		    dis = distance(x1, y1, x1 + 1, y1 + 1);
		    flag = true;
			cout << "down right" << endl;

			if (!(x1 + 1 == x2 && y1 + 1 == y2)) {
                dis = dis + func(x1 + 1, y1 + 1);
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}

		} else if (i == 2 && x1 + 1 < m && !mem[x1 + 1][y1] && mat[x1 + 1][y1] != 1) {// for the down move
            dis = distance(x1, y1, x1 + 1, y1);
            cout << dis << endl;
			flag = true;
			cout << x1 << y1 << endl;
			cout << "down" << endl;

			if (!(x1 + 1 == x2 && y1 == y2)) {
                float temp;
                temp = func(x1 + 1, y1);
                dis = dis + temp;
                cout << dis << "temp";
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}
		} else if (i == 3 && x1 + 1 < m && y1 - 1 >= 0 && !mem[x1 + 1][y1 - 1] && mat[x1 + 1][y1 - 1] != 1) {// for the down left move
            flag = true;
            dis = distance(x1, y1, x1 + 1, y1 - 1);
			cout << "down left" << endl;

			if (!(x1 + 1 == x2 && y1 - 1== y2)) {
                dis = dis + func(x1 + 1, y1 - 1);
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}
		} else if (i == 4 && y1 - 1 >= 0 && !mem[x1][y1 - 1]  && mat[x1][y1 - 1] != 1) {// for the left move
            flag = true;
            dis = distance(x1, y1 - 1, x2, y2);
			cout << "left" << endl;

			if (!(x1 == x2 && y1 - 1 == y2)) {
                dis = dis + func(x1, y1 - 1);
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}
		} else if (i == 5 && x1 - 1 >= 0 && y1 - 1 >= 0 && !mem[x1 - 1][y1 - 1]  && mat[x1 - 1][y1 - 1] != 1) {// for the up left move
            flag = true;
            dis = distance(x1, y1, x1 - 1, y1 - 1);
			cout << "up left" << endl;

			if (!(x1 - 1 == x2 && y1 - 1== y2)) {
                dis = dis + func(x1 - 1, y1 - 1);
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}
		} else if (i == 6 && x1 - 1 >= 0 && !mem[x1 - 1][y1]  && mat[x1 - 1][y1] != 1) {// for the up move
            flag = true;
            dis = distance(x1, y1, x1 - 1, y1);
			cout << "up" << endl;

			if (!(x1 - 1 == x2 && y1 == y2)) {
                dis = dis + func(x1 - 1, y1);
			}
			dis = round(dis);
			if (min_weight > (int)dis) {
                min_weight = dis;
			}
		} else if (i == 7 && x1 - 1 >= 0 && y1 + 1 < n && !mem[x1 - 1][y1 + 1]  && mat[x1 - 1][y1 + 1] != 1) {// for the up right move
            flag = true;
            dis = distance(x1, y1, x1 - 1, y1 + 1);
			cout << "up right" << endl;

			if (!(x1 - 1 == x2 && y1 + 1 == y2)) {
                dis = dis + func(x1 - 1, y1 + 1);
			}
			int x;
			x = round(dis);
			if (min_weight > x) {
                min_weight = x;
			}
		}
    }

    if (!flag) {
            return INT_MAX;
    }

	return min_weight;
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
        memset(mem, false, sizeof(mem));
		int x1, y1;
		scanf("%d%d%d%d", &x1, &y1, &x2, &y2);
		cout << func(x1, y1);
	}

	return 0;
}
