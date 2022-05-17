package me.ponktacology.economy;

import org.bukkit.entity.Player;

public interface PlayerCooldownRepository {

    boolean hasCooldown(Player player);

    void resetCooldown(Player player);
}
