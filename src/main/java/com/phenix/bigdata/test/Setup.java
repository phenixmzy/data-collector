package com.phenix.bigdata.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Setup {
    public static void main(String[] args) {
        int times = Integer.valueOf(args[0]);
        String bootStrapServer = args[1];
        String topic = args[2];
        long delay = Long.parseLong(args[3]); // 5L
        long unit = Long.parseLong(args[4]);  // 60L
        int gamePlayMaxDelay = Integer.parseInt(args[5]); // 5
        int gameIdMaxNum = Integer.parseInt(args[6]);
        int userIdMaxNum = Integer.parseInt(args[7]);
//        int times = 5;
//        String bootStrapServer = "localhost:9092";
//        String topic = "gameplay";
//        long delay = 5L;
//        long unit = 60L;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(new GamePlayRunnable(times, bootStrapServer,topic, gameIdMaxNum, userIdMaxNum, gamePlayMaxDelay), delay, unit, TimeUnit.SECONDS);
    }

}
