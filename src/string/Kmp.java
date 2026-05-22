package string;


// KMP算法作用： 求 字符串s1中是否包含s2 ，如果包含返回s2在s1中的起始位置，否则返回-1

public class Kmp {
    public static void main(String[] args) {

    }
    public static int KMP(String a, String b) {
        int[] next = getNext(b);
        int i = 0, j = 0;
        while(i<a.length() && j<b.length()){
            if(j==-1 || a.charAt(i)==b.charAt(j)){
                i++;
                j++;
            }else{
                j=next[j];
            }
        }
        if(j==b.length()){
            return i-j;
        }
        return -1;
    }
    // 获取next数组  next[i] 表示 b字符串中前i个字符的最长公共前后缀的长度 （不能含整体，失去了意义）
    public static int[] getNext(String b) {
        int []next = new int[b.length()];
        next[0]=-1;
        int i=0, j=-1;
        while(i<b.length()-1){
            if(j==-1 || b.charAt(i)==b.charAt(j)){
                i++;
                j++;
                next[i]=j;
            }else{
                j=next[j];
            }
        }
        return next;
    }
}
