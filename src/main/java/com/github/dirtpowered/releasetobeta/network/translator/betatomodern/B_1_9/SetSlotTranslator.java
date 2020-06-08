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

package com.github.dirtpowered.releasetobeta.network.translator.betatomodern.B_1_9;

import com.github.dirtpowered.betaprotocollib.data.BetaItemStack;
import com.github.dirtpowered.betaprotocollib.packet.Version_B1_7.data.SetSlotPacketData;
import com.github.dirtpowered.releasetobeta.data.inventory.PlayerInventory;
import com.github.dirtpowered.releasetobeta.data.player.ModernPlayer;
import com.github.dirtpowered.releasetobeta.network.session.BetaClientSession;
import com.github.dirtpowered.releasetobeta.network.translator.model.BetaToModern;
import com.github.dirtpowered.releasetobeta.utils.item.ItemConverter;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.packetlib.Session;

public class SetSlotTranslator implements BetaToModern<SetSlotPacketData> {

    @Override
    public void translate(SetSlotPacketData packet, BetaClientSession session, Session modernSession) {
        ModernPlayer player = session.getPlayer();

        int windowId = packet.getWindowId();
        int itemSlot = packet.getItemSlot();
        BetaItemStack itemStack = packet.getItemStack();
        PlayerInventory inventory = player.getInventory();
        ItemStack modernItemStack = ItemConverter.betaToModern(session, itemStack);

        if (itemStack != null) {
            itemStack.setBlockId(session.remapBlock(itemStack.getBlockId(), itemStack.getData(), true));
            itemStack.setData(session.remapMetadata(itemStack.getBlockId(), itemStack.getData()));
        }

        if (itemSlot != -1) //item in hand index
            inventory.setItem(itemSlot, modernItemStack);

        //Update armor bar
        session.getMain().getServer().updatePlayerProperties(modernSession, session.getPlayer());

        //Fix enchanting table de-syncs
        if (player.getOpenedInventoryType() == WindowType.ENCHANTING_TABLE) {
            if (itemSlot != 1) {
                return;
            }
        }

        modernSession.send(new ServerSetSlotPacket(windowId, itemSlot, modernItemStack));
    }
}