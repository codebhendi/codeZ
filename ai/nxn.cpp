#include <bits/stdc++.h>

using namespace std;

class Environment{
	public :
		int posx;
		int posy;
		int n;
		int puzzle[100][100];

	Environment() {
		scanf("%d", &n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				scanf("%d", &puzzle[i][j]);
				if (puzzle[i][j] == -1) {
					posx = i;
					posy = j;
				}
			}
		}

	}

	void agent() {
		Agent a1;
		a1.n = n;
		a1.createSolution();
		a1.puzzle = puzzle;
		a1.posx = posx;
		a1.posy = posy;
		a1.move();
	}
};

class Agent {
	public :
		int n;
		int posx;
		int posy;
		int puzzle[100][100];
		int finalAnswer[100][100];
		int solution[100][100];
		struct tree {
			tree * parent;
			bool visited;
			int puzz[100][100];
			int posx;
			int posy;
		};

		tree * newnode() {
			tree * temp = NULL;
			temp = (tree *)malloc(sizeof(tree));
			temp->parent = NULL;
			return temp;
		}

		void createSolution() {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (node->posx != i && node->posy != j) {
						finalAnswer[i][j] = n * i + j + 1;
					} else {
						finalAnswer[i][j] = -1;
					}
				}
			}
		}

		void checksolution(tree * node) {
			bool flag = true;

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (solution[i][j] != finalAnswer[i][j]) {
						flag = false;
					}
 				}
			}

			return flag;
		}

		void moveup(tree * root, int x, int y) {
			int newx = x - 1;
			int temp = root->puzz[newx][y];
			root->puzz[newx][y] = root->puzz[x][y];
			root->puzz[x][y] = temp;
			root->posy = y;
			root->posx = newx;
		}

		void moveleft(tree * root, int x, int y) {
			int newy = y - 1;
			int temp = root->puzz[x][newy];
			root->puzz[x][newy] = root->puzz[x][y];
			root->puzz[x][y] = temp;
			root->posy = newy;
			root->posx = x;
		}

		void movedown(tree * root, int x, int y) {
			int newx = x + 1;
			int temp = root->puzz[x][newy];
			root->puzz[x][newy] = root->puzz[x][y];
			root->puzz[x][y] = temp;
			root->posy = y;
			root->posx = newx;
		}

		void moveright(tree * root, int x, int y) {
			int newy = y + 1;
			int temp = root->puzz[x][newy];
			root->puzz[x][newy] = root->puzz[x][y];
			root->puzz[x][y] = temp;
			root->posy = newy;
			root->posx = x;
		}

		void validmove(int x, int y, tree * root) {
			deque(root);

			for (int i = 0; i < 4; i++) {
				if (i == 0) {
					if (y - 1 >= 0) {
						tree * temp = newnode();
						temp->parent = root;
						temp->visited = true;
						temp->puzz = root->puzz;
						moveleft(temp, x, y);
						addnode(temp);
						if (checksol(temp))

						continue;
					}
				}
				if (i == 1) {
					if (x - 1 >= 0) {
						tree * temp = newnode();
						temp->parent = root;
						temp->visited = true;
						temp->puzz = root->puzz;
						moveup(temp, x, y);
						addnode(temp);
						if (checksol(temp))
						continue;
					}
				}
				if (i == 2) {
					if (y + 1 < n) {
						tree * temp = newnode();
						temp->parent = root;
						temp->visited = true;
						temp->puzz = root->puzz;
						moveup(temp, x, y);
						addnode(temp);
						if (checksol(temp))
						continue;
					}
				}
				if (i == 3) {
					if (x + 1 < n) {
						tree * temp = newnode();
						temp->parent = root;
						temp->visited = true;
						temp->puzz = root->puzz;
						moveup(temp, x, y);
						addnode(temp);
						if (checksol(temp))
						continue;
					}
				}
			}

			while (top != 0) {

			}
		}

		void move() {
			tree * node = newnode();
			node->n = n;
			node->puzz = puzze
			node->visited = true;
			node->posx = posx;
			node->posy = posy;
			addnode(node);
			validmove(posx, posy, node);
		}
};

int main()
{
	int t;

	scanf("%d", &t);

	for (int i = 0; i < t; i++) {
		Environment e1;
		e1.agent();
	}
}
1
