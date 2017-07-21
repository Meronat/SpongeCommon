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
package org.spongepowered.common.data.processor.data.entity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableElytraFlyingData;
import org.spongepowered.api.data.manipulator.mutable.entity.ElytraFlyingData;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeElytraFlyingData;
import org.spongepowered.common.data.processor.common.AbstractEntitySingleDataProcessor;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeValue;
import org.spongepowered.common.data.value.mutable.SpongeValue;

import java.util.Optional;

public class ElytraFlyingDataProcessor extends AbstractEntitySingleDataProcessor<EntityPlayerMP, Boolean, Value<Boolean>,
        ElytraFlyingData, ImmutableElytraFlyingData> {

    public ElytraFlyingDataProcessor() {
        super(EntityPlayerMP.class, Keys.IS_ELYTRA_FLYING);
    }

    @Override
    protected boolean set(EntityPlayerMP dataHolder, Boolean value) {
        if (value == Boolean.TRUE) {
            if (dataHolder.armorArray.get(2).getItem().equals(Items.ELYTRA)) {
                dataHolder.setElytraFlying();
                return true;
            }
        } else if (value == Boolean.FALSE) {
            dataHolder.clearElytraFlying();
            return true;
        }
        return false;
    }

    @Override
    protected Optional<Boolean> getVal(EntityPlayerMP dataHolder) {
        return Optional.of(dataHolder.isElytraFlying());
    }

    @Override
    protected ImmutableValue<Boolean> constructImmutableValue(Boolean value) {
        return ImmutableSpongeValue.cachedOf(Keys.IS_ELYTRA_FLYING, false, value);
    }

    @Override
    protected Value<Boolean> constructValue(Boolean actualValue) {
        return new SpongeValue<>(Keys.IS_ELYTRA_FLYING, false, actualValue);
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }

    @Override
    protected ElytraFlyingData createManipulator() {
        return new SpongeElytraFlyingData();
    }

}
