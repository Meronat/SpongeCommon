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
package org.spongepowered.common.data.processor.multi.entity;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableElytraFlyingData;
import org.spongepowered.api.data.manipulator.mutable.entity.ElytraFlyingData;
import org.spongepowered.api.data.property.item.ElytraFlyingProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.ElytraCapability;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeElytraFlyingData;
import org.spongepowered.common.data.processor.common.AbstractEntityDataProcessor;
import org.spongepowered.common.data.util.DataUtil;
import org.spongepowered.common.interfaces.entity.IMixinEntityLivingBase;

import java.util.Map;
import java.util.Optional;

public class ElytraFlyingDataProcessor extends AbstractEntityDataProcessor<EntityLivingBase, ElytraFlyingData, ImmutableElytraFlyingData> {

    public ElytraFlyingDataProcessor() {
        super(EntityLivingBase.class);
    }

    @Override
    protected boolean doesDataExist(EntityLivingBase dataHolder) {
        return true;
    }

    @Override
    protected boolean set(EntityLivingBase dataHolder, Map<Key<?>, Object> keyValues) {
        final ElytraCapability elytraCapability = (ElytraCapability) keyValues.getOrDefault(Keys.ELYTRA_CAPABILITY, ElytraCapability.EQUIPMENT);
        ((DataHolder) dataHolder).offer(Keys.ELYTRA_CAPABILITY, elytraCapability);
        if (!(Boolean) keyValues.getOrDefault(Keys.IS_ELYTRA_FLYING, true)) {
            switch (elytraCapability) {
                case EQUIPMENT:
                    final Optional<ElytraFlyingProperty> elytraFlyingProperty =
                            ((ItemStack) dataHolder.getItemStackFromSlot(EntityEquipmentSlot.CHEST)).getProperty(ElytraFlyingProperty.class);
                    //noinspection ConstantConditions
                    if (elytraFlyingProperty.isPresent() && elytraFlyingProperty.get().getValue()) {
                        ((IMixinEntityLivingBase) dataHolder).setElytraFlying();
                    }
                    break;
                case ENABLED:
                    ((IMixinEntityLivingBase) dataHolder).setElytraFlying();
                    break;
                case DISABLED:
                    break;
            }
        } else {
            ((IMixinEntityLivingBase) dataHolder).clearElytraFlying();
        }

        return true;
    }

    @Override
    protected Map<Key<?>, ?> getValues(EntityLivingBase dataHolder) {
        return ImmutableMap.of(
                Keys.ELYTRA_CAPABILITY, ((DataHolder) dataHolder).get(Keys.ELYTRA_CAPABILITY).orElse(ElytraCapability.EQUIPMENT),
                Keys.IS_ELYTRA_FLYING, dataHolder.isElytraFlying());
    }

    @Override
    protected ElytraFlyingData createManipulator() {
        return new SpongeElytraFlyingData();
    }

    @Override
    public Optional<ElytraFlyingData> fill(DataContainer container, ElytraFlyingData elytraFlyingData) {
        elytraFlyingData.set(Keys.ELYTRA_CAPABILITY, DataUtil.getData(container, Keys.ELYTRA_CAPABILITY));
        elytraFlyingData.set(Keys.IS_ELYTRA_FLYING, DataUtil.getData(container, Keys.IS_ELYTRA_FLYING));
        return Optional.of(elytraFlyingData);
    }

    @Override
    public DataTransactionResult remove(DataHolder dataHolder) {
        return DataTransactionResult.failNoData();
    }

}
