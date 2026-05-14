package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class inputTest {
    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        String Line ;
        StringTokenizer st = new StringTokenizer(br.readLine());
        long T = Long.parseLong(st.nextToken());
        while(T-->0){
            Line = br.readLine();
            st = new StringTokenizer(Line);
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            System.out.println(a+b);
        }
    }
}
