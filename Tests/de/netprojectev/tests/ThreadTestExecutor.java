package de.netprojectev.tests;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTestExecutor {
 
    public static void main(String[] args)    throws Exception  {
        ExecutorService exec = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 5; i++)   {
            Runnable r = new Runnable()    {
                    public void run()    {
                        try      {
                            Thread.sleep((long)(Math.random() * 1000));
                        }  catch (InterruptedException e){  }
                    }
                };
            exec.execute(r);
        }
 
        Thread.sleep(1000);
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Thread t : map.keySet())   {
            if (t.toString().contains("pool"))  {
                System.out.println("t: " + t);
                for (StackTraceElement e : map.get(t))  {
                    System.out.println(e.toString());
                }
            }
        }
 
    }
}