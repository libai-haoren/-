package jvm;

public class TestGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否有回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];
    public static void main(String[] args) {
        TestGC objA = new TestGC();
        TestGC objB = new TestGC();
        objA.instance = objB;
        objB.instance = objA;
        System.out.println("建立循环引用");
        objA = null;
        objB = null;
        System.out.println("将objA和objB设置为null");
        System.out.println("开始强制GC...");
        // 假设在这行发生GC，objA和objB是否能被回收？
        System.gc();
        // 给GC一些时间执行
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("程序执行完毕");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("对象被GC回收: " + this);
    }

}
