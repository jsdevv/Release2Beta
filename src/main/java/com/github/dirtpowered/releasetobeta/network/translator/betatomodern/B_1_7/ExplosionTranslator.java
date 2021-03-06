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

import com.github.dirtpowered.betaprotocollib.packet.Version_B1_7.data.ExplosionPacketData;
import com.github.dirtpowered.betaprotocollib.utils.BlockLocation;
import com.github.dirtpowered.releasetobeta.ReleaseToBeta;
import com.github.dirtpowered.releasetobeta.data.Block;
import com.github.dirtpowered.releasetobeta.network.session.BetaClientSession;
import com.github.dirtpowered.releasetobeta.network.translator.model.BetaToModern;
import com.github.dirtpowered.releasetobeta.utils.Utils;
import com.github.steveice10.mc.protocol.data.game.world.block.ExplodedBlockRecord;
import com.github.steveice10.mc.protocol.data.game.world.sound.BuiltinSound;
import com.github.steveice10.mc.protocol.data.game.world.sound.SoundCategory;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerExplosionPacket;
import com.github.steveice10.packetlib.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExplosionTranslator implements BetaToModern<ExplosionPacketData> {

    @Override
    public void translate(ReleaseToBeta main, ExplosionPacketData packet, BetaClientSession session, Session modernSession) {
        float x = Utils.toFloat(packet.getX());
        float y = Utils.toFloat(packet.getY());
        float z = Utils.toFloat(packet.getZ());

        float explosionSize = packet.getExplosionSize();

        List<ExplodedBlockRecord> records = new ArrayList<>();
        for (BlockLocation destroyedBlockPosition : packet.getDestroyedBlockPositions()) {
            int posX = destroyedBlockPosition.getX();
            int posY = destroyedBlockPosition.getY();
            int posZ = destroyedBlockPosition.getZ();

            records.add(new ExplodedBlockRecord(posX, posY, posZ));
        }

        // update block cache
        for (ExplodedBlockRecord record : records) {
            session.getChunkCache().onBlockUpdate(record.getX(), record.getY(), record.getZ(), Block.AIR, 0);
        }

        modernSession.send(new ServerExplosionPacket(x, y, z, explosionSize, records, 0, 0, 0));

        Random rand = session.getPlayer().getRand();

        float pitch = (1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F) * 0.7F;
        main.getServer().playWorldSound(modernSession, x, y, z, BuiltinSound.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, pitch);
    }
}
