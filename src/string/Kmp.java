package string;

public class Kmp {
    public static void main(String[] args) {
        StringBuilder sb=  new StringBuilder();
        String s  = "asd";
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
