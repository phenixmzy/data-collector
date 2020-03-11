package com.phenix.bigdata.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Setup {
    public static void main(String[] args) {
        int times = Integer.valueOf(args[0]); // 5
        String bootStrapServer = args[1]; // host-client-01:9092,host-client-02:9092,host-client-03:9092
        String browseTopic = args[2]; // gamebrowse-input
        String playTopic = args[3]; // gameplay-input
        long delay = Long.parseLong(args[4]); // 5L
        long unit = Long.parseLong(args[5]);  // 30L
        int gamePlayMaxDelay = Integer.parseInt(args[6]); // 300
        int gameIdMaxNum = Integer.parseInt(args[7]); // 100000
        int userIdMaxNum = Integer.parseInt(args[8]); // 100000000
        /*int times = 5;
        String bootStrapServer = "host-client-01:9092,host-client-02:9092,host-client-03:9092";
        String topic = "gameplay-input";
        long delay = 5L;
        long unit = 60L;
        int gamePlayMaxDelay =  300;
        int gameIdMaxNum = 100000;
        int userIdMaxNum = 100000000;*/
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(new GameBrowseRunnable(times, bootStrapServer,browseTopic, playTopic, gameIdMaxNum, userIdMaxNum, gamePlayMaxDelay), delay, unit, TimeUnit.SECONDS);
    }

}
