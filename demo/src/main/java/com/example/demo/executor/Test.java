package com.example.demo.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;

/**
 * ClassName Test
 * Description
 * Author Mr.jimmy
 * Date 2019/1/13 20:19
 * Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Test.class);
//        List<Future> list = new ArrayList<Future>();
//        ExecutorService fixPool = Executors.newFixedThreadPool(10);
//        try{
//            for (int i = 0; i < 10; i++) {
//                Future<Integer> result =  fixPool.submit(new ExecCallThread("a"+i));
//                list.add(result);
//                throw new RuntimeException("a");
//            }
//        }finally {
//            System.out.println("shutdown");
//            fixPool.shutdown();
//        }
//
//        System.out.println("list");
//        int count = 0;
//        for (Future<Integer> f : list) {
//            // 从Future对象上获取任务的返回值，并输出到控制台
//            try {
//                int a = f.get();
//                count +=a;
//                System.out.println(">>>" + f.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(count);
        File file = Paths.get("d:\\", "text.txt").toFile();

        OutputStream output = null;
        InputStream input = null;
        try {

            output = new FileOutputStream(file);

            String outputStr = String.format("%-15s", "你好");
            for(int i=0;i<=100;i++){
                output.write(outputStr.getBytes());
                output.flush();
            }
            output.close();
            input = new FileInputStream(file);
            byte[] bytes = new byte[1024];

            if(input.read()>0){
                input.read(bytes);
                System.out.println("|" + new String(bytes, 15,15)+ "|");
            }
            input.close();

        } catch (FileNotFoundException e) {
            logger.error("error", e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }


    }
}