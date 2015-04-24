#include <bits/stdc++.h>

using namespace std;

double xy[2][100000];
double yx[2][100000];
//**********************************************************************************************************************//
//	convex hull
//**********************************************************************************************************************//
struct node {
	double x;
	double y;
	struct node * next;
	struct node * prev;
};

struct node * create_node(double x, double y) {

	struct node * new_node = new node;
	new_node->x = x;
	new_node->y = y;
	new_node->next = NULL; 
	new_node->prev = NULL;
	
	return new_node;
}

void print(struct node *head) {

	struct node * pt = head->next;
	cout << head->x << " " << head->y << endl;

	while (pt != head) {
		cout << pt->x << " " << pt->y << endl;
		pt = pt->next;
	}
}

double slope(struct node * left, struct node * right) {

	double y = left->y - right->y;
	double x = left->x - right->x;
	double m = y / x;

	m = atan (m) * 180 / 3.14;
	
	if (m < 0) {
		m += 180;
	}

	return m;
}

double slopr(struct node * left, struct node * right) {

	double y = left->y - right->y;
	double x = left->x - right->x;

	return (y / x);
}

struct node * merge(struct node * left, struct node * right) {

	left->next = right;
	right->prev = left;

	return left;
}

struct node * y_max(struct node * left, struct node * right) {

	if (left->y > right->y) {
		return left;
	} else {
		return right;
	}
}

struct node * y_min(struct node * left, struct node * right) {

	if (left->y < right->y) {
		return left;
	} else {
		return right;
	}
}

struct node * triangle(struct node * left, struct node * middle, struct node * right) {

	double ml = slope(left, middle);
	double mr = slope(left, right);
	
	if (ml == mr) {
		return merge(left, merge (y_max(middle, right), left));		
	} else if (ml > mr) {
		return merge(left, merge(middle, merge(right, left)));
	} else {
		return merge(left, merge(right, merge(middle, left)));
	}
}

struct node * find_max(struct node * head) {

	double large = head->y;
	struct node * pt = head->next;
	struct node * max = head;

	while (pt != head) {
		if (pt->y > large) {
			large = pt->y;
			max = pt;
		}
		pt = pt->next;
	}

	return max;
}

struct node * find_min(struct node * head) {

	double small = head->y;
	struct node * pt = head->next;
	struct node * min = head;

	while (pt != head) {
		if (pt->y < small) {
			small = pt->y;
			min = pt;
		}
		pt = pt->next;
	}

	return min;
}

double constant(struct node * left, struct node * right) {

	double c;
	double y1 = left->y;
	double y2 = right->y;
	double x1 = left->x;
	double x2 = right->x;

	c = ((y1 * x2) - (y2 * x1)) / (x2 - x1);
	return c;
}

double check(struct node * point, double m, double c) {

	double k = (point->y) - m * (point->x) - c;
	return k;
}

bool test(struct node * point, struct node * left, struct node * right, int k) {

	double m = slopr(left, right);
	double c = constant(left, right);
	
	if (m >= 100000000000000 || m <= -100000000000000) {
	if (((left->prev)->x <= (left->x)) && ((left->next)->x <= (left->x)) && ((right->prev)->x <= (right->x)) && ((right->next)->x <= (right->x))){
		return true;
	} else if (((left->prev)->x >= (left->x)) && ((left->next)->x >= (left->x)) && ((right->prev)->x >= (right->x)) && ((right->next)->x >= (right->x))){
		return true;
	} else {
		return false;
	}
	}	

	double p = check(point, m, c);
	double p1 = check(left->prev, m, c);
	double p2 = check(left->next, m, c);
	double p3 = check(right->prev, m, c);
	double p4 = check(right->next, m, c);

	if (point == left || point == right || p == 0) {
		if (((p1 * p2) >= 0) && ((p3 * p4) >= 0) && ((p1 * p3) >= 0) && ((p1 * k) >= 0)) {
			return true;
		} else {
			return false;
		}
	}
	if ((p * k) < 0) {
		return false;
	}
	if (((p * p1) >= 0 ) && ((p * p2) >= 0) && ((p * p3) >= 0) && ((p * p4) >= 0)) {
		return true;
	} else {
		return false;
	}
}

struct node * merge_function(struct node * left, struct node * right) {

	struct node * left_max = find_max(left);
	struct node * left_min = find_min(left);
	struct node * right_max = find_max(right);
	struct node * right_min = find_min(right);

	// upper tangent
	
	struct node * upper_left = left_max;
	struct node * upper_right = right_max;
	struct node * pl = left_max;
	struct node * pr = right_max;
	
	struct node * l_min = y_min(left_min, right_min);

	while (test(l_min, pl, pr, -1) == false) {
		
		pr = pr-> next;
		while (pr != right_max) {

			if (test(l_min, pl, pr, -1)) {
				upper_left = pl;
				upper_right = pr;
				goto xy;
			} 

			pr = pr->next;
		}
		pl = pl->next;
		if (pl == left_max)
			break;
	}
	upper_left = pl;
	xy:
	// lower tangent

	struct node * lower_left = left_min;
	struct node * lower_right = right_min;
	pl = left_min;
	pr = right_min;
	
	left_max = y_max(left_max, right_max);

	while (test(left_max, pl, pr, 1) == false) {
		
		pr = pr-> next;
		while (pr != right_min) {

			if (test(left_max, pl, pr, 1)) {
				lower_left = pl;
				lower_right = pr;
				goto yx;
			} 

			pr = pr->next;
		}
		pl = pl->next;
		if (pl == left_min)
			break;
	}
	lower_left = pl;
	yx:

	upper_left = merge(upper_left, upper_right);
	lower_left = merge(lower_right, lower_left);

	return upper_left;
}


struct node * divide_nd_conquer(int l, int r) {

	if (r == l + 1) {

		struct node * left = create_node(xy[0][l], xy[1][l]);
		struct node * right = create_node(xy[0][r], xy[1][r]);
		return merge(left, merge(right, left));
	}

	if (r == l + 2) {
		
		struct node * left = create_node(xy[0][l], xy[1][l]);
		struct node * middle = create_node(xy[0][l + 1], xy[1][l + 1]);
		struct node * right = create_node(xy[0][r], xy[1][r]);

		if ((right->y < middle->y) && (right->y < left->y)) {
			return  triangle(right, middle, left);
		} else if ((middle->y < left->y) && (middle->y < right->y)) {
			return triangle(middle, left, right);
		} else {
			return triangle(left, right, middle);
		}
	}

	struct node * left = divide_nd_conquer(l, (l + r) / 2);
	struct node * right = divide_nd_conquer((l + r) / 2 + 1, r);

	return merge_function(left, right);
}
//***************************************************************************************************************************//
// sorting according to the x values
//**************************************************************************************************************************//

void swap(int i, int j) {
	
	double x = xy[0][i];
	double y = xy[1][i];

	xy[0][i] = xy[0][j];
	xy[1][i] = xy[1][j];

	xy[0][j] = x;
	xy[1][j] = y;
}

int partition(int start, int end, int k) {

	int part, i, l;
	if (k == 0) {
		l = 1;
	} else {
		l = 0;
	}

	part = start;
	i = start;

	while (i < end) {

		if (xy[k][i] < xy[k][end]) {
			swap(part, i);
			part += 1;
		} else if (xy[k][i] == xy[k][end]) {
			if (xy[l][i] < xy[l][end]) {
				swap(part, i);
				part += 1;
			}
		}
		i += 1;
	}

	swap(part, end);
	return part;
}

void quick_sort(int start, int end, int k) {

	if (start < end) {
		int mid = partition(start, end, k);

		quick_sort(start, mid - 1, k);
		quick_sort(mid + 1, end, k);
	}
}

int linear_sweep_for_x(int n) {
	int l = 0, i;
	yx[0][l] = xy[0][l];
	yx[1][l] = xy[1][l];
	l = 1;

	for (i = 1; i < n - 1; i++) {
		if ((xy[0][i] == xy[0][i - 1]) && (xy[0][i] == xy[0][i + 1]) && (i < n - 1)) {
			continue;
		}
		yx[0][l] = xy[0][i];
		yx[1][l] = xy[1][i];
		l = l + 1;
	}
	yx[0][l] = xy[0][i];
	yx[1][l] = xy[1][i];
	l = l + 1;

	for (i = 0; i < l; i++) {
		xy[0][i] =  yx[0][i];
		xy[1][i] =  yx[1][i];
	}
	return l;
}

int linear_sweep_for_y(int n) {
	int l = 0, i;
	yx[0][l] = xy[0][l];
	yx[1][l] = xy[1][l];
	l = 1;

	for (i = 1; i < n - 1; i++) {
		if ((xy[1][i] == xy[1][i - 1]) && (xy[1][i] == xy[1][i + 1]) && (i < n - 1)) {
			continue;
		}
		yx[0][l] = xy[0][i];
		yx[1][l] = xy[1][i];
		l = l + 1;
	}
	yx[0][l] = xy[0][i];
	yx[1][l] = xy[1][i];
	l = l + 1;

	for (i = 0; i < l; i++) {
		xy[0][i] =  yx[0][i];
		xy[1][i] =  yx[1][i];
	}
	return l;
}

int main()
{	
	int n, i, l;
	cin >> n;

	for (i = 0; i < n; i++) {
		cin >> xy[0][i] >> xy[1][i];
	}
	
	quick_sort(0, n - 1, 1);
	n = linear_sweep_for_y(n);	

	quick_sort(0, n - 1, 0);
	n = linear_sweep_for_x(n);		
	
	struct node * head = divide_nd_conquer(0, n - 1);
	cout << endl;
	print(head);
	cout << head->x << " " << head->y << endl;

	return 0;
}
