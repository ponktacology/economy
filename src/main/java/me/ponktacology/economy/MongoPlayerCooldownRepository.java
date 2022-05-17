package me.ponktacology.economy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.ponktacology.economy.PlayerCooldownRepository;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

class MongoPlayerCooldownRepository implements PlayerCooldownRepository {

    private final long COOLDOWN = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
    private final MongoCollection<Document> collection;

    MongoPlayerCooldownRepository(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("cooldowns");
    }

    @Override
    public boolean hasCooldown(Player player) {
        final String uuid = player.getUniqueId().toString();
        final Document document = collection.find(Filters.eq("uuid", uuid)).first();

        if (document == null) return false;

        return System.currentTimeMillis() - document.getLong("timeStamp") < COOLDOWN;
    }

    @Override
    public void resetCooldown(Player player) {
        final String uuid = player.getUniqueId().toString();
        final Document document = new Document("uuid", uuid);
        document.put("timeStamp", System.currentTimeMillis());
        collection.replaceOne(Filters.eq("uuid", uuid), document, new ReplaceOptions().upsert(true));
    }
}
