package com.phenix.bigdata.model;

public class UserGameOrderEvent {
    private long orderId;
    private String gameId;
    private String userId;
    private String actionType;
    private String userIp;

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    private int orderTimeStamp;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public void setOrderTimeStamp(int orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }
}
