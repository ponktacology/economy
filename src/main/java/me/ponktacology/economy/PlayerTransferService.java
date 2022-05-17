package me.ponktacology.economy;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;

public class PlayerTransferService {

    private final PlayerBalanceRepository playerBalanceRepository;

    PlayerTransferService(PlayerBalanceRepository playerBalanceRepository) {
        this.playerBalanceRepository = playerBalanceRepository;
    }

    public Result transfer(Player sender, Player receiver, int amount) {
        Preconditions.checkState(amount > 0, "amount can't be less than 0");

        final boolean success = playerBalanceRepository.removeFromBalance(sender, amount);

        if (!success) {
            return new Result(Result.Type.FAILED, "Insufficient funds");
        }

        playerBalanceRepository.addToBalance(receiver, amount);

        return new Result(Result.Type.SUCCESS, "Success");
    }


    public record Result(Type type, String message) {
        enum Type {
            FAILED,
            SUCCESS;
        }
    }
}
