package me.fishbythefin.gladiators.client;

public class ClientGayRayData {
    private static long playerGayRayTimer;

    public static void set(long timer) {
        ClientGayRayData.playerGayRayTimer = timer;
    }

    public static long getPlayerGayRayTimer() {
        return playerGayRayTimer;
    }
}
