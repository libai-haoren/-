package 单源最短路径;

import java.util.Arrays;

public class 链式前向星 {
    static final int N = 100005;
    static final int M = 200005;

    // head[u] = 点u的第一条边编号
    static int[] head = new int[N];

    // to[i] = 第i条边到哪个点
    static int[] to = new int[M];

    // next[i] = 下一条边编号
    static int[] next = new int[M];

    // weight[i] = 边权
    static int[] weight = new int[M];

    // 当前边编号
    static int idx = 0;

    // 加边
    static void add(int u, int v, int w) {
        to[idx] = v;
        weight[idx] = w;

        // 头插法
        next[idx] = head[u];

        head[u] = idx++;
    }

    public static void main(String[] args) {

        Arrays.fill(head, -1);

        add(1, 2, 5);
        add(1, 3, 2);
        add(1, 4, 8);

        // 遍历1的所有邻边
        for (int i = head[1]; i != -1; i = next[i]) {
            System.out.println(
                    "1 -> " + to[i] +
                            " weight=" + weight[i]
            );
        }
    }
}
