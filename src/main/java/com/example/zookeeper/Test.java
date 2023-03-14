package com.example.zookeeper;

public class Test implements Runnable{

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        new Test().run();
        System.out.println("ending");
    }

    @Override
    public void run() {
        System.out.println("lalala");
        System.out.println(Thread.currentThread().getName());
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
