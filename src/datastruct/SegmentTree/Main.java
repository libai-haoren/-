package datastruct.SegmentTree;

import java.io.*;
import java.util.StringTokenizer;
// + \ * 混合
public class Main {
    public static int MAXN = (int) 1e5 + 10;
    public static long[] arr = new long[MAXN];
    public static long[] sum = new long[MAXN << 2];
    public static long[] add = new long[MAXN << 2];
    public static long[] mul = new long[MAXN << 2];
    public static int mod; // 模数，全局变量

    // 向上更新：合并左右子树的和
    public static void pushUp(int i) {
        sum[i] = (sum[i << 1] + sum[i << 1 | 1]) % mod;
    }

    // 统一的懒标记下推方法（核心！）
    // i: 当前节点编号, ln: 左子树长度, rn: 右子树长度
    public static void pushDown(int i, int ln, int rn) {
        // 先处理乘法标记，再处理加法标记
        if (mul[i] != 1) {
            // 下推到左子树
            sum[i << 1] = sum[i << 1] * mul[i] % mod;
            mul[i << 1] = mul[i << 1] * mul[i] % mod;
            add[i << 1] = add[i << 1] * mul[i] % mod;

            // 下推到右子树
            sum[i << 1 | 1] = sum[i << 1 | 1] * mul[i] % mod;
            mul[i << 1 | 1] = mul[i << 1 | 1] * mul[i] % mod;
            add[i << 1 | 1] = add[i << 1 | 1] * mul[i] % mod;

            // 清空当前节点的乘法标记
            mul[i] = 1;
        }

        // 再处理加法标记
        if (add[i] != 0) {
            // 下推到左子树
            sum[i << 1] = (sum[i << 1] + add[i] * ln) % mod;
            add[i << 1] = (add[i << 1] + add[i]) % mod;

            // 下推到右子树
            sum[i << 1 | 1] = (sum[i << 1 | 1] + add[i] * rn) % mod;
            add[i << 1 | 1] = (add[i << 1 | 1] + add[i]) % mod;

            // 清空当前节点的加法标记
            add[i] = 0;
        }
    }

    // 构建线段树
    public static void build(int l, int r, int i) {
        mul[i] = 1; // 乘法标记初始化为1
        add[i] = 0; // 加法标记初始化为0
        if (l == r) {
            sum[i] = arr[l] % mod;
            return;
        }
        int mid = (l + r) >> 1;
        build(l, mid, i << 1);
        build(mid + 1, r, i << 1 | 1);
        pushUp(i);
    }

    // 区间乘法操作
    public static void updateMul(int jobl, int jobr, long v, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            // 当前区间完全在操作区间内，直接更新标记
            sum[i] = sum[i] * v % mod;
            mul[i] = mul[i] * v % mod;
            add[i] = add[i] * v % mod; // 加法标记也要乘以v
            return;
        }
        int mid = (l + r) >> 1;
        pushDown(i, mid - l + 1, r - mid); // 下推所有懒标记
        if (jobl <= mid) updateMul(jobl, jobr, v, l, mid, i << 1);
        if (jobr > mid) updateMul(jobl, jobr, v, mid + 1, r, i << 1 | 1);
        pushUp(i);
    }

    // 区间加法操作
    public static void updateAdd(int jobl, int jobr, long v, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            // 当前区间完全在操作区间内，直接更新标记
            sum[i] = (sum[i] + v * (r - l + 1)) % mod;
            add[i] = (add[i] + v) % mod;
            return;
        }
        int mid = (l + r) >> 1;
        pushDown(i, mid - l + 1, r - mid); // 下推所有懒标记
        if (jobl <= mid) updateAdd(jobl, jobr, v, l, mid, i << 1);
        if (jobr > mid) updateAdd(jobl, jobr, v, mid + 1, r, i << 1 | 1);
        pushUp(i);
    }

    // 区间查询操作
    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        pushDown(i, mid - l + 1, r - mid); // 查询前必须下推懒标记
        long ans = 0;
        if (jobl <= mid) ans = (ans + query(jobl, jobr, l, mid, i << 1)) % mod;
        if (jobr > mid) ans = (ans + query(jobl, jobr, mid + 1, r, i << 1 | 1)) % mod;
        return ans;
    }

    // 快速输入输出
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public static int nextInt() {
        return Integer.parseInt(next());
    }

    public static long nextLong() {
        return Long.parseLong(next());
    }

    public static void main(String[] args) {
        int n = nextInt();
        int q = nextInt();
        mod = nextInt();

        for (int i = 1; i <= n; i++) {
            arr[i] = nextLong();
        }

        build(1, n, 1);

        while (q-- > 0) {
            int op = nextInt();
            int x = nextInt();
            int y = nextInt();
            if (op == 1) {
                long k = nextLong();
                updateMul(x, y, k, 1, n, 1);
            } else if (op == 2) {
                long k = nextLong();
                updateAdd(x, y, k, 1, n, 1);
            } else if (op == 3) {
                pw.println(query(x, y, 1, n, 1));
            }
        }

        pw.flush();
    }
}