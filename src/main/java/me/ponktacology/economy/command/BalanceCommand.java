package me.ponktacology.economy.command;

import me.ponktacology.economy.PlayerBalanceRepository;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private final PlayerBalanceRepository playerBalanceRepository;

    public BalanceCommand(PlayerBalanceRepository playerBalanceRepository) {
        this.playerBalanceRepository = playerBalanceRepository;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        final Player target;

        if (args.length == 0) {
            if (sender instanceof Player senderPlayer) {
                target = senderPlayer;
            } else {
                sender.sendMessage("Console usage: /bal <name>");
                return false;
            }
        } else {
            target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("Invalid player.");
                return false;
            }
        }

        sender.sendMessage(target.getName() + "'s balance: " + playerBalanceRepository.getBalance(target));
        return true;
    }
}
