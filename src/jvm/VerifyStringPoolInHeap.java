package jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VerifyStringPoolInHeap {


    public static void main(String[] args) {
        // 方法1: 通过内存溢出验证
        //testOutOfMemory();
       // printVMInfo();

        // 方法2: 通过GC日志观察
       // testGCLog();

        // 方法3: 通过内存分析工具
       testMemoryAnalysis();
    }


    /**
     * 打印虚拟机详细信息
     */
    public static void printVMInfo() {
        System.out.println("=== 虚拟机信息 ===");

        // 1. 基本虚拟机信息
        System.out.println("Java版本: " + System.getProperty("java.version"));
        System.out.println("Java供应商: " + System.getProperty("java.vendor"));
        System.out.println("Java虚拟机名称: " + System.getProperty("java.vm.name"));
        System.out.println("Java虚拟机版本: " + System.getProperty("java.vm.version"));
        System.out.println("Java虚拟机供应商: " + System.getProperty("java.vm.vendor"));

        // 2. 运行时信息
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("虚拟机名称: " + runtimeMXBean.getVmName());
        System.out.println("虚拟机版本: " + runtimeMXBean.getVmVersion());
        System.out.println("虚拟机供应商: " + runtimeMXBean.getVmVendor());

        // 3. 内存信息
        Runtime runtime = Runtime.getRuntime();
        System.out.println("最大内存: " + runtime.maxMemory() / 1024 / 1024 + "MB");
        System.out.println("总内存: " + runtime.totalMemory() / 1024 / 1024 + "MB");
        System.out.println("空闲内存: " + runtime.freeMemory() / 1024 / 1024 + "MB");

        // 4. 系统信息
        System.out.println("操作系统: " + System.getProperty("os.name"));
        System.out.println("操作系统版本: " + System.getProperty("os.version"));
        System.out.println("系统架构: " + System.getProperty("os.arch"));

        System.out.println("========================\n");
    }

    /**
     * 验证1: 通过OOM错误验证字符串常量池在堆中
     */
    public static void testOutOfMemory() {
        // 设置较小的堆内存: -Xmx10m
        List<String> list = new ArrayList<>();

        try {
            // 不断创建字符串并intern，消耗字符串常量池空间
            for (int i = 0; ; i++) {
                String str = new String("string_" + i).intern();
                list.add(str);
                if (i % 1000 == 0) {
                    System.out.println("已创建: " + i + " 个字符串");
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("发生堆内存溢出: " + e.getMessage());
            // 错误信息会显示Java heap space，证明在堆中
        }
    }

    /**
     * 验证2: 通过GC日志观察字符串对象被回收
     */
    public static void testGCLog() {
        // 添加JVM参数: -Xmx20m -Xms20m -XX:+PrintGCDetails

        System.out.println("=== 创建临时字符串 ===");
        for (int i = 0; i < 100000; i++) {
            String temp = "temp_string_" + i; // 临时字符串
            // 这些字符串会在Young GC时被回收
        }

        System.gc(); // 手动触发GC

        System.out.println("=== 创建持久字符串 ===");
        List<String> persistent = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            persistent.add("persistent_string_" + i);
        }
    }

    /**
     * 验证3: 内存地址分析
     */
    public static void testMemoryAnalysis() {
        String heapStr1 = new String("heap_string");
        String heapStr2 = new String("heap_string");
        String poolStr1 = "pool_string";
        String poolStr2 = "pool_string";

        System.out.println("堆字符串比较:");
        System.out.println("heapStr1 == heapStr2: " + (heapStr1 == heapStr2)); // false
        System.out.println("heapStr1.equals(heapStr2): " + heapStr1.equals(heapStr2)); // true

        System.out.println("常量池字符串比较:");
        System.out.println("poolStr1 == poolStr2: " + (poolStr1 == poolStr2)); // true

        // 证明intern()方法将字符串放入堆中的字符串常量池
        String interned1 = heapStr1.intern();
        String interned2 = heapStr2.intern();
        System.out.println("intern后比较:");
        System.out.println("interned1 == interned2: " + (interned1 == interned2)); // true
    }


}