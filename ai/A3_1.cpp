#include <bits/stdc++.h>

using namespace std;

int m, n, q, mat[100][100];

float distance(int x1, int y1, int x2, int y2) {
	return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
}

void func(int x1, int y1, int x2, int y2) {
	for (int i = 0;i < 8; i++) {
		float dis;

		if (i == 0 && y1 + 1 < n && mat[x1][y1 + 1] == 1) {
			dis = distance(x1, y1, x1, y1 + 1);
			if (x1 == x2 && y1 + 1 == y2) {
				return dis;
			}
			dis = dis + func(x1, y1 + 1, x2, y2);
		} else if (i == 1 && x1 + 1 < m && y1 + 1 < n && mat[x1 + 1][y1 + 1] != 1) {

		} else if (i == 2 && x1 + 1 < m && mat[x1 + 1][y1] != 1) {

		} else if (i == 3 && y1 - 1 >= 0 && x1 + 1 < m && mat[x1 + 1][y1 - 1] != 1) {

		} else if (i == 4 && y1 - 1 >= 0 && mat[x1][y1 - 1] != 1) {

		} else if (i == 5 && x1 - 1 >= 0 && y1 - 1 >= 0 && mat[x1 - 1][y1 - 1] != 1) {

		} else if (i == 6 && x1 - 1 >= 0 && mat[x1 - 1][y1] != 1) {

		} else if (i == 7 && y1 + 1 < n && x1 - 1 >= 0 && mat[x1 - 1][y1 + 1] == 1) {

		} else {
			return 0;
		}
	}
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
		int x1, x2, y1, y2;
		scanf("%d%d%d%d", &x1, &y1, &x2, &y2);
		func(x1,y1,x2,y2);
	}

	return 0;
}