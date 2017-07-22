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
package org.spongepowered.common.data.manipulator.immutable.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableElytraFlyingData;
import org.spongepowered.api.data.manipulator.mutable.entity.ElytraFlyingData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.util.ElytraCapability;
import org.spongepowered.common.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeElytraFlyingData;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeValue;

public class ImmutableSpongeElytraFlyingData extends AbstractImmutableData<ImmutableElytraFlyingData, ElytraFlyingData>
        implements ImmutableElytraFlyingData {

    private ElytraCapability elytraCapability;
    private boolean isElytraFlying;

    private final ImmutableValue<ElytraCapability> capabilityValue;
    private final ImmutableValue<Boolean> isFlyingValue;

    public ImmutableSpongeElytraFlyingData(ElytraCapability capability, boolean isElytraFlying) {
        super(ImmutableElytraFlyingData.class);

        this.elytraCapability = checkNotNull(capability, "The elytra capability cannot be null!");
        this.isElytraFlying = isElytraFlying;

        this.capabilityValue = ImmutableSpongeValue.cachedOf(Keys.ELYTRA_CAPABILITY, ElytraCapability.EQUIPMENT, elytraCapability);
        this.isFlyingValue = ImmutableSpongeValue.cachedOf(Keys.IS_ELYTRA_FLYING, false, isElytraFlying);

        registerGetters();
    }

    @Override
    public ImmutableValue<ElytraCapability> elytraCapability() {
        return this.capabilityValue;
    }

    @Override
    public ImmutableValue<Boolean> elytraFlying() {
        return this.isFlyingValue;
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(Keys.ELYTRA_CAPABILITY, ImmutableSpongeElytraFlyingData.this::getElytraCapability);
        registerKeyValue(Keys.ELYTRA_CAPABILITY, ImmutableSpongeElytraFlyingData.this::elytraCapability);

        registerFieldGetter(Keys.IS_ELYTRA_FLYING, ImmutableSpongeElytraFlyingData.this::isElytraFlying);
        registerKeyValue(Keys.IS_ELYTRA_FLYING, ImmutableSpongeElytraFlyingData.this::elytraFlying);
    }

    @Override
    public ElytraFlyingData asMutable() {
        return new SpongeElytraFlyingData(this.elytraCapability, this.isElytraFlying);
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(Keys.ELYTRA_CAPABILITY.getQuery(), this.elytraCapability)
                .set(Keys.IS_ELYTRA_FLYING.getQuery(), this.isElytraFlying);
    }

    private ElytraCapability getElytraCapability() {
        return this.elytraCapability;
    }

    private boolean isElytraFlying() {
        return this.isElytraFlying;
    }

}
