package com.github.dirtpowered.releasetobeta.data.entity.monster;

import com.github.dirtpowered.releasetobeta.data.entity.model.Entity;
import com.github.dirtpowered.releasetobeta.data.entity.model.Mob;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.data.game.world.sound.BuiltinSound;
import com.github.steveice10.mc.protocol.data.game.world.sound.SoundCategory;
import com.github.steveice10.packetlib.Session;

public class EntitySilverfish extends Entity implements Mob {

    public EntitySilverfish(int entityId) {
        super(entityId, MobType.SILVERFISH);
    }

    @Override
    public void onSpawn(Session session) {

    }

    @Override
    public void onDeath(Session session) {
        playSound(session, BuiltinSound.ENTITY_SILVERFISH_DEATH, SoundCategory.HOSTILE);
    }

    @Override
    public void onDamage(Session session) {
        playSound(session, BuiltinSound.ENTITY_SILVERFISH_HURT, SoundCategory.HOSTILE);
    }
}
