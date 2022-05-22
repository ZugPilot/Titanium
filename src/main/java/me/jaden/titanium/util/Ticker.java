package me.jaden.titanium.util;

import lombok.Getter;
import me.jaden.titanium.Settings;
import me.jaden.titanium.Titanium;
import me.jaden.titanium.data.DataManager;
import me.jaden.titanium.data.PlayerData;

public class Ticker {
    @Getter
    private static Ticker instance;
    @Getter
    private int currentTick;

    public Ticker() {
        instance = this;

        Titanium plugin = Titanium.getPlugin();
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> currentTick++, 1, 1);

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            int maxPacketsPerSecond = Settings.getSettings().getMaxPacketsPerSecond();
            int maxPacketAllowance = maxPacketsPerSecond * 3;

            for (PlayerData value : DataManager.getInstance().getPlayerData().values()) {
                value.setPacketAllowance(Math.min(maxPacketAllowance, value.getPacketAllowance() + maxPacketsPerSecond));
                System.out.println(value.getPacketCount() + " < " + value.getPacketAllowance());
                value.setPacketCount(0);

            }
        }, 20, 20);
    }
}