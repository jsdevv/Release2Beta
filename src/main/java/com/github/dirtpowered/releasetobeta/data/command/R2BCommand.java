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

package com.github.dirtpowered.releasetobeta.data.command;

import com.github.dirtpowered.releasetobeta.data.command.model.Command;
import com.github.dirtpowered.releasetobeta.data.player.ModernPlayer;
import com.github.dirtpowered.releasetobeta.utils.chat.ChatUtils;
import io.netty.util.internal.StringUtil;

public class R2BCommand extends Command {

    @Override
    public void execute(ModernPlayer sender, String[] args) {
        sender.sendMessage(ChatUtils.colorize("&fThis server is using &bReleaseToBeta &fversion: &b" + getClass().getPackage().getImplementationVersion()));
        sender.sendMessage(StringUtil.EMPTY_STRING);
        sender.sendMessage(ChatUtils.colorize("&7If you find any bug let me know on my discord server:"));
        sender.sendMessage(ChatUtils.colorize("&ahttps://discord.com/invite/v6xsRdc"));
    }
}
