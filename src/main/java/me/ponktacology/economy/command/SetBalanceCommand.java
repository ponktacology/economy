package me.ponktacology.economy.command;

import me.ponktacology.economy.PlayerBalanceRepository;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBalanceCommand implements CommandExecutor {

    private final PlayerBalanceRepository playerBalanceRepository;

    public SetBalanceCommand(PlayerBalanceRepository playerBalanceRepository) {
        this.playerBalanceRepository = playerBalanceRepository;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender.isOp()) {
            sender.sendMessage("No permission.");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage("Invalid usage");
            return false;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Invalid player.");
            return false;
        }

        final int amount = Integer.parseInt(args[1]);
        if (amount <= 0) {
            sender.sendMessage("Amount must be positive.");
            return false;
        }

        playerBalanceRepository.setBalance(target, amount);
        sender.sendMessage("Success.");
        return true;
    }
}
