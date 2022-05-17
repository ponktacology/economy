package me.ponktacology.economy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.ponktacology.economy.PlayerBalanceRepository;
import org.bson.Document;
import org.bukkit.entity.Player;

class MongoPlayerBalanceRepository implements PlayerBalanceRepository {

    private final MongoCollection<Document> collection;

    MongoPlayerBalanceRepository(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("balances");
    }

    @Override
    public int getBalance(Player player) {
        final Document document = collection.find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        if (document == null) return 0;
        return document.getInteger("balance");
    }

    @Override
    public void setBalance(Player player, int newBalance) {
        final String uuid = player.getUniqueId().toString();
        final Document document = new Document("uuid", uuid);
        document.put("balance", newBalance);
        collection.replaceOne(Filters.eq("uuid", uuid), document, new ReplaceOptions().upsert(true));
    }
}
