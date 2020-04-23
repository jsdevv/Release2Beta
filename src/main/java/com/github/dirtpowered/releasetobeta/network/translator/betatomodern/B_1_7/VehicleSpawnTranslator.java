/*
 * Copyright (c) 2020 Dirt Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.dirtpowered.releasetobeta.network.translator.betatomodern.B_1_7;

import com.github.dirtpowered.betaprotocollib.packet.Version_B1_7.data.VehicleSpawnPacketData;
import com.github.dirtpowered.releasetobeta.configuration.R2BConfiguration;
import com.github.dirtpowered.releasetobeta.network.session.BetaClientSession;
import com.github.dirtpowered.releasetobeta.network.translator.model.BetaToModern;
import com.github.dirtpowered.releasetobeta.utils.Utils;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.FallingBlockData;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.MinecartType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectData;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ProjectileData;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityVelocityPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import com.github.steveice10.packetlib.Session;

import java.util.UUID;

public class VehicleSpawnTranslator implements BetaToModern<VehicleSpawnPacketData> {

    @Override
    public void translate(VehicleSpawnPacketData packet, BetaClientSession session, Session modernSession) {
        int ownerId = session.getPlayer().getEntityId();
        int entityId = packet.getEntityId();
        int hasVelocity = packet.getVelocity();
        UUID uuid = UUID.randomUUID();

        boolean customVelocity = false;

        ObjectType type = null;
        ObjectData data = null;

        double x = Utils.toModernPos(packet.getX());
        double y = Utils.toModernPos(packet.getY());
        double z = Utils.toModernPos(packet.getZ());

        double vecX = 0;
        double vecY = 0;
        double vecZ = 0;

        switch (packet.getType()) {
            case 10:
                type = ObjectType.MINECART;
                data = MinecartType.NORMAL;
                break;
            case 11:
                type = ObjectType.MINECART;
                data = MinecartType.CHEST;
                break;
            case 12:
                type = ObjectType.MINECART;
                data = MinecartType.POWERED;
                break;
            case 60:
                type = R2BConfiguration.arrowFix ? ObjectType.SNOWBALL : ObjectType.TIPPED_ARROW;
                data = new ProjectileData(ownerId);
                break;
            case 50:
                type = ObjectType.PRIMED_TNT;
                break;
            case 61:
                type = ObjectType.SNOWBALL;
                data = new ProjectileData(ownerId);
                break;
            case 62:
                type = ObjectType.EGG;
                data = new ProjectileData(ownerId);
                break;
            case 90:
                type = ObjectType.FISH_HOOK;
                break;
            case 1:
                type = ObjectType.BOAT;
                break;
            case 63:
                type = ObjectType.GHAST_FIREBALL;
                data = new ProjectileData(0);
                //TODO: Get direction & calculate new velocity
                customVelocity = true;
                break;
            case 70:
                type = ObjectType.FALLING_BLOCK;
                data = new FallingBlockData(12, 0);
                break;
            case 71:
                type = ObjectType.FALLING_BLOCK;
                data = new FallingBlockData(13, 0);
                break;
        }

        if (type == null) //server sends weird IDs sometimes
            return;

        if (hasVelocity > 0 && !customVelocity) {
            vecX = packet.getVelocityX() / 8000.0D;
            vecY = packet.getVelocityY() / 8000.0D;
            vecZ = packet.getVelocityZ() / 8000.0D;
        }

        modernSession.send(new ServerSpawnObjectPacket(entityId, uuid, type, data, x, y, z, 0, 0));
        modernSession.send(new ServerEntityVelocityPacket(entityId, vecX, vecY, vecZ));
    }
}
