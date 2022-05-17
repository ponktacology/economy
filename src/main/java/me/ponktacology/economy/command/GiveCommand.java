package me.ponktacology.economy.command;

import me.ponktacology.economy.PlayerTransferService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements CommandExecutor {

    private final PlayerTransferService playerTransferService;

    public GiveCommand(PlayerTransferService playerTransferService) {
        this.playerTransferService = playerTransferService;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid usage");
            return false;
        }

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Must be in game.");
            return false;
        }
        final Player senderPlayer = (Player) sender;

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

        final PlayerTransferService.Result result = playerTransferService.transfer(senderPlayer, target, amount);
        sender.sendMessage(result.type() + ": " + result.message());
        return true;
    }
}
