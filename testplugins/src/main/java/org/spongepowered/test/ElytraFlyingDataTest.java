/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.test;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.ElytraCapability;

@Plugin(id = "elytraflyingdatatest", name = "ElytraFlyingDataTest", description = "A plugin to test elytra flying data.")
public final class ElytraFlyingDataTest {

    @Listener
    public void onGameInitialization(final GameInitializationEvent event) {
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .description(Text.of("Applies elytra flying status to the player."))
                .executor((src, args) -> {
                    if (src instanceof Player) {
                        final Player player = (Player) src;
                        final boolean newValue = !player.get(Keys.IS_ELYTRA_FLYING).orElse(false);

                        player.offer(Keys.IS_ELYTRA_FLYING, true);

                        if (newValue) {
                            player.sendMessage(Text.of(TextColors.GOLD, "You are now elytra flying!"));
                        } else {
                            player.sendMessage(Text.of(TextColors.GOLD, "You are no longer elytra flying!"));
                        }

                        return CommandResult.success();
                    }
                    throw new CommandException(Text.of(TextColors.RED, "You must be a player to execute this command."));
                })
                .build(), "setelytra");

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .description(Text.of("Tests if you are currently elytra flying."))
                .executor((src, args) -> {
                    if (src instanceof Player) {
                        src.sendMessage(Text.of(TextColors.GOLD, "Elytra flying status: ", TextColors.GRAY, ((Player) src).get(Keys.IS_ELYTRA_FLYING).orElse(false)));

                        return CommandResult.success();
                    }
                    throw new CommandException(Text.of(TextColors.RED, "You must be a player to execute this command."));
                })
                .build(), "iselytra");

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .description(Text.of("Switches your current elytra capability state."))
                .arguments(GenericArguments.onlyOne(GenericArguments.enumValue(Text.of("capability"), ElytraCapability.class)))
                .executor((src, args) -> {
                    if (src instanceof Player) {
                        ((Player) src).offer(Keys.ELYTRA_CAPABILITY, args.<ElytraCapability>getOne(Text.of("capability")).get());
                        src.sendMessage(Text.of(TextColors.GOLD, "Successfully switched to elytra capability: ", TextColors.GRAY, ((Player) src).get(Keys.ELYTRA_CAPABILITY).get()));

                        return CommandResult.success();
                    }
                    throw new CommandException(Text.of(TextColors.RED, "You must be a player to execute this command."));
                })
                .build(), "elytracapabilities");
    }

}
