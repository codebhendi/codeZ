#include <bits/stdc++.h>

using namespace std;

class Environment{
	public :
		posx;
		posy;
		struct tree {
			struct TREE * child[100];
			int n;
			int puzz[100][100];
		};
		tree * node;

	Environment() {
		tree * node = newnode();
		int temp;
		scanf("%d", &temp);
		node->n = temp;

		for (int i = 0; i < node->n; i++) {
			for (int j = 0; j < node->n; j++) {
				scanf("%d", &node->puzz[i][j]);
				if (node->puzz[i][j] == -1) {
					posx = i;
					posy = j;
				}
			}
		}

		int solution[node->n][node->n];
		for (int i = 0; i < node->n; i++) {
			for (int j = 0; j < node->n; j++) {
				solution[i][j] = 3 * i + j + 1;
			}
		}
	}

	tree * newnode() {
		tree * temp = NULL;
		temp = (tree *)malloc(sizeof(tree));
		memset(temp->child, NULL, sizeof(temp->child));
		return temp;
	}
};

class Agent {
	
};

int main()
{
	int t;
	
	scanf("%d", &t);

	for (int i = 0; i < t; i++) {
		Environment e1;
	} 
}
