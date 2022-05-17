package me.ponktacology.economy;

import org.bukkit.entity.Player;

public interface PlayerBalanceRepository {

    int getBalance(Player player);

    void setBalance(Player player, int newBalance);

    default void addToBalance(Player player, int amount) {
        final int currentBalance = getBalance(player);
        final int newBalance = currentBalance + amount;
        setBalance(player, newBalance);
    }

    default boolean removeFromBalance(Player player, int amount) {
        final int currentBalance = getBalance(player);

        if (currentBalance < amount) {
            return false;
        }

        final int newBalance = currentBalance - amount;
        setBalance(player, newBalance);
        return true;
    }
}
