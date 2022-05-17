package me.ponktacology.economy.command;

import me.ponktacology.economy.PlayerBalanceRepository;
import me.ponktacology.economy.PlayerCooldownRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class EarnCommand implements CommandExecutor {

    private final PlayerCooldownRepository playerCooldownRepository;
    private final PlayerBalanceRepository playerBalanceRepository;

    public EarnCommand(PlayerCooldownRepository playerCooldownRepository,
                       PlayerBalanceRepository playerBalanceRepository) {
        this.playerCooldownRepository = playerCooldownRepository;
        this.playerBalanceRepository = playerBalanceRepository;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Must be in game.");
            return false;
        }
        Player senderPlayer = (Player) sender;

        final boolean onCooldown = playerCooldownRepository.hasCooldown(senderPlayer);
        if (onCooldown) {
            sender.sendMessage("Wait before using this command again.");
            return false;
        }
        playerCooldownRepository.resetCooldown(senderPlayer);

        final int amount = ThreadLocalRandom.current().nextInt(1, 5);
        playerBalanceRepository.addToBalance(senderPlayer, amount);
        return true;
    }
}
