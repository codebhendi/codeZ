package CriticalPath;

import java.util.HashMap;
import java.util.Scanner;

class CriticalPath {

    int n;
    HashMap<Character, Dependency> hm = new HashMap<Character, Dependency>();
    HashMap<Character, TimeCal> tim = new HashMap<Character, TimeCal>();

    void timecalc() {
        for (int i = 0; i < n; i++) {
            Dependency d = hm.get((char) ('a' + i));
            if (d.no_dep == 0) {
                TimeCal tmp = new TimeCal();

                tmp.startTime = 0;
                tmp.endTime = d.completion_time;

                tim.put((char) ('a' + i), tmp);
            }
        }

        int i = 0;
        while (true) {
            i = i % n;
            TimeCal t = tim.get((char)('a' + i));
            
        }
    }

    public static void main(String[] args) {
        CriticalPath p = new CriticalPath();

        Scanner in = new Scanner(System.in);
        p.n = in.nextInt();

        for (int i = 0; i < p.n; i++) {
            Dependency d = new Dependency();
            TimeCal t = new TimeCal();
            t.startTime = -1;
            d.completion_time = in.nextInt();
            d.no_dep = in.nextInt();

            for (int j = 0; j < d.no_dep; j++) {
                d.dep[j] = in.next().charAt(0);
            }
            p.tim.put((char) ('a' + i), t);
            p.hm.put((char) ('a' + i), d);
        }
    }
}

class Dependency {

    char dep[];
    int no_dep;
    int completion_time;

    public Dependency() {
        dep = new char[100];
    }
}

class TimeCal {

    int startTime;
    int endTime;
}