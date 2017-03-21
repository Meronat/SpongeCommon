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
package org.spongepowered.common.data.processor.value.entity;

import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.common.data.processor.common.AbstractSpongeValueProcessor;
import org.spongepowered.common.data.util.DataConstants;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeValue;
import org.spongepowered.common.data.value.mutable.SpongeValue;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class ZombieTypeValueProcessor extends AbstractSpongeValueProcessor<EntityZombie, org.spongepowered.api.data.type.ZombieType, Value<org.spongepowered.api.data.type.ZombieType>> {

    public ZombieTypeValueProcessor() {
        super(EntityZombie.class, Keys.ZOMBIE_TYPE);
    }

    @Override
    protected Value<org.spongepowered.api.data.type.ZombieType> constructValue(org.spongepowered.api.data.type.ZombieType actualValue) {
        return new SpongeValue<>(Keys.ZOMBIE_TYPE, DataConstants.Catalog.DEFAULT_ZOMBIE_TYPE, actualValue);
    }

    @Override
    protected boolean set(EntityZombie container, org.spongepowered.api.data.type.ZombieType value) {
        throw new UnsupportedOperationException("ZombieData is deprecated - zombie types are now separate entities!");
    }

    @Override
    protected Optional<org.spongepowered.api.data.type.ZombieType> getVal(EntityZombie dataHolder) {
        org.spongepowered.api.data.type.ZombieType type = org.spongepowered.api.data.type.ZombieTypes.NORMAL;
        if (dataHolder instanceof EntityHusk) {
            type = org.spongepowered.api.data.type.ZombieTypes.HUSK;
        } else if (dataHolder instanceof EntityZombieVillager) {
            type = org.spongepowered.api.data.type.ZombieTypes.VILLAGER;
        }

        return Optional.of(type);
    }

    @Override
    protected ImmutableValue<org.spongepowered.api.data.type.ZombieType> constructImmutableValue(org.spongepowered.api.data.type.ZombieType value) {
        return ImmutableSpongeValue.cachedOf(Keys.ZOMBIE_TYPE, DataConstants.Catalog.DEFAULT_ZOMBIE_TYPE, value);
    }

    @Override
    public DataTransactionResult removeFrom(ValueContainer<?> container) {
        return DataTransactionResult.failNoData();
    }
}
