package com.phenix.bigdata.model.factory;
import com.phenix.bigdata.model.GameBrowse;
import com.phenix.bigdata.model.GamePlay;

public class GameBrowseFactory {
    private final static String IP_SPLIT = ".";
    private static final String[] GAME_TYPES = new String[]{"exe", "web", "onlie", "flash"};
    private static final String[] CHANNEL_FROMS = new String[]{"my", "category", "game_helper", "recommend", "726", "4399", "kuwo", "relateflash"};
    private static final String[] SITES = new String[]{"index", "kw", "qvod", "kugo", "qq", "qvod"};

    public static GameBrowse build(int gameIdMaxNum, int userIdMaxNum, int maxDelay) {

        String gameId = String.valueOf((int)((Math.random()*9+1) * gameIdMaxNum));
        String userId = String.valueOf((long)((Math.random()*9+1) * userIdMaxNum));
        int currTimeStamp = (int)(System.currentTimeMillis()/1000) ;

        int timeLen = getRandNum(1, 300);
        String gameType = GAME_TYPES[getRandNum(0,4) % 4];
        String channelFrom = CHANNEL_FROMS[getRandNum(0,8) % 8];
        String site = SITES[getRandNum(0,6) % 6];
        String userIp = getUserIp();
        GameBrowse browse = new GameBrowse();
        browse.setGameId(gameId);
        browse.setUserId(userId);
        browse.setTimeLen(timeLen);
        browse.setBrowseTime(currTimeStamp);
        browse.setGameType(gameType);
        browse.setSite(site);
        browse.setChannelFrom(channelFrom);
        browse.setUserIp(userIp);

        return browse;
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