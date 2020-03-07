package com.github.dirtpowered.releasetobeta.network.translator.betatomodern;

import com.github.dirtpowered.betaprotocollib.packet.data.HandshakePacketData;
import com.github.dirtpowered.releasetobeta.network.session.BetaClientSession;
import com.github.dirtpowered.releasetobeta.network.translator.model.BetaToModern;
import com.github.steveice10.packetlib.Session;

public class HandshakeTranslator implements BetaToModern<HandshakePacketData> {

    @Override
    public void translate(HandshakePacketData packet, BetaClientSession session, Session modernSession) {
    }
}
