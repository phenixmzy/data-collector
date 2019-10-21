package com.phenix.bigdata.model.factory;
import com.phenix.bigdata.model.GamePlay;

public class GamePlayFactory {
    private final static String IP_SPLIT = ".";
    private static final String[] GAME_TYPES = new String[]{"exe", "web", "onlie", "flash"};
    private static final String[] CHANNEL_FROMS = new String[]{"my", "category", "game_helper", "recommend", "726", "4399", "kuwo", "relateflash"};
    private static final String[] SITES = new String[]{"index", "kw", "qvod", "kugo", "qq", "qvod"};
    private static final String[] IPS = new String[]{"192.168.1.10", "192.168.1.62", "192.168.1.127", "192.168.1.135", "192.168.1.156", "192.168.1.181", "192.168.1.197", "192.168.1.205", "192.168.1.210", "192.168.1.223"};

    public static GamePlay build(int gameIdMaxNum, int userIdMaxNum, int maxDelay) {

        String gameId = String.valueOf((int)((Math.random()*9+1) * gameIdMaxNum));
        String userId = String.valueOf((long)((Math.random()*9+1) * userIdMaxNum));
        int currTimeStamp = (int)(System.currentTimeMillis()/1000) ;

        int delay = getRandNum(1, maxDelay);
        int timeLen = getRandNum(1, 300);
        int leaveTime = currTimeStamp - delay;
        int startTime = leaveTime - timeLen;
        String gameType = GAME_TYPES[getRandNum(0,4) % 4];
        String channelFrom = CHANNEL_FROMS[getRandNum(0,8) % 8];
        String site = SITES[getRandNum(0,6) % 6];
        String userIp = getUserIp();
        GamePlay gamePlay = new GamePlay();
        gamePlay.setGameId(gameId);
        gamePlay.setUserId(userId);
        gamePlay.setTimeLen(timeLen);
        gamePlay.setStartTime(startTime);
        gamePlay.setLeaveTime(leaveTime);
        gamePlay.setGameType(gameType);
        gamePlay.setSite(site);
        gamePlay.setChannelFrom(channelFrom);
        gamePlay.setUserIp(userIp);

        return gamePlay;
    }

    public static String getUserIp() {
        StringBuilder builder = new StringBuilder();
        builder.append(getRandNum(10,220))
                .append(IP_SPLIT).append(getRandNum(20,192))
                .append(IP_SPLIT).append(getRandNum(2,160))
                .append(IP_SPLIT).append(getRandNum(2,220));
        return builder.toString();
    }

    private static int getRandNum(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }
}