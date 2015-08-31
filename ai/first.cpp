#include <bits/stdc++.h>

using namespace std;

class Agent {
	
};

class Environment {
	public:
		int cars;
		int car[100];
		inr tires[100][4];
		memset(tires, -1, sizeof(tires));
		memset(car, -1, sizeof(car));

		void collectdata() {
			cin >> cars;
			for(int i = 0; i < cars; i++) {
				for(int k = 0; k < 4; k++) {
					cin >> tires[i][k];
				}
			}
			return ;
		}
};

int main() {

	int n;
	cin >> n;

	for (int i = 0; i < n; i++) {
		Environment e1;
		e1.collectdata();
	}
}
