package me.ponktacology.economy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import me.ponktacology.economy.command.BalanceCommand;
import me.ponktacology.economy.command.EarnCommand;
import me.ponktacology.economy.command.GiveCommand;
import me.ponktacology.economy.command.SetBalanceCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class EconomyPlugin extends JavaPlugin {

    private static final String SECRET = "";

    @Override
    public void onEnable() {
        final MongoClient mongoClient = MongoClients.create(SECRET);
        final MongoDatabase database = mongoClient.getDatabase("economy");

        final PlayerBalanceRepository playerBalanceRepository = new MongoPlayerBalanceRepository(database);
        final PlayerTransferService playerTransferService = new PlayerTransferService(playerBalanceRepository);
        final PlayerCooldownRepository playerCooldownRepository = new MongoPlayerCooldownRepository(database);

        getCommand("bal").setExecutor(new BalanceCommand(playerBalanceRepository));
        getCommand("earn").setExecutor(new EarnCommand(playerCooldownRepository, playerBalanceRepository));
        getCommand("give").setExecutor(new GiveCommand(playerTransferService));
        getCommand("setbal").setExecutor(new SetBalanceCommand(playerBalanceRepository));
    }
}
