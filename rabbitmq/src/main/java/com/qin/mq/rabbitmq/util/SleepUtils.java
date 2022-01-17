package com.qin.mq.rabbitmq.util;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/14 22:40.
 */
public class SleepUtils {

    public static void sleep(int sleepTime, TimeUnit timeUnit){
        try {
            timeUnit.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public static void sleep(int sleepTime){
        sleep(sleepTime, TimeUnit.SECONDS);
    }
}
