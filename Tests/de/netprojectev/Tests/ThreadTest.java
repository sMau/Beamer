package de.netprojectev.Tests;

class Arbeiter implements Runnable{
 
    @Override
    public void run() {
        for ( int i = 0;i<10000;i++) {
            System.out.println(Thread.currentThread().getName() + " : " +  i);
        }
    }
    
    
}
 
public class ThreadTest {
    
    public static void main(String[] args) {
        Arbeiter arbeiter1 = new Arbeiter();
        Arbeiter arbeiter2 = new Arbeiter();
        Thread eins = new Thread(arbeiter1);
        Thread zwei = new Thread(arbeiter2);
        
        eins.setName("ErsterTreat");
        zwei.setName("ZweiterTreat");
        
        System.out.println("start...");
        eins.start();
        zwei.start();
        System.out.println("...Ende");       
    }
     
}