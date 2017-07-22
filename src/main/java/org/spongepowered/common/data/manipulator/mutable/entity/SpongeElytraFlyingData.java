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
package org.spongepowered.common.data.manipulator.mutable.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableElytraFlyingData;
import org.spongepowered.api.data.manipulator.mutable.entity.ElytraFlyingData;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.ElytraCapability;
import org.spongepowered.common.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.common.data.value.mutable.SpongeValue;

public class SpongeElytraFlyingData extends AbstractData<ElytraFlyingData, ImmutableElytraFlyingData> implements ElytraFlyingData {

    private ElytraCapability elytraCapability;
    private boolean isElytraFlying;

    public SpongeElytraFlyingData(ElytraCapability capability, boolean isElytraFlying) {
        super(ElytraFlyingData.class);

        this.elytraCapability = checkNotNull(capability, "The elytra capability cannot be null!");
        this.isElytraFlying = isElytraFlying;

        registerGettersAndSetters();
    }

    public SpongeElytraFlyingData() {
        this(ElytraCapability.EQUIPMENT, false);
    }

    @Override
    public Value<ElytraCapability> elytraCapability() {
        return new SpongeValue<>(Keys.ELYTRA_CAPABILITY, ElytraCapability.EQUIPMENT, this.elytraCapability);
    }

    @Override
    public Value<Boolean> elytraFlying() {
        return new SpongeValue<>(Keys.IS_ELYTRA_FLYING, false, this.isElytraFlying);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(Keys.ELYTRA_CAPABILITY, SpongeElytraFlyingData.this::getElytraCapability);
        registerFieldSetter(Keys.ELYTRA_CAPABILITY, SpongeElytraFlyingData.this::setElytraCapability);
        registerKeyValue(Keys.ELYTRA_CAPABILITY, SpongeElytraFlyingData.this::elytraCapability);

        registerFieldGetter(Keys.IS_ELYTRA_FLYING, SpongeElytraFlyingData.this::isElytraFlying);
        registerFieldSetter(Keys.IS_ELYTRA_FLYING, this::setElytraFlying);
        registerKeyValue(Keys.IS_ELYTRA_FLYING, SpongeElytraFlyingData.this::elytraFlying);
    }

    @Override
    public ElytraFlyingData copy() {
        return new SpongeElytraFlyingData(this.elytraCapability, this.isElytraFlying);
    }

    @Override
    public ImmutableElytraFlyingData asImmutable() {
        return null;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(Keys.IS_ELYTRA_FLYING.getQuery(), this.isElytraFlying)
                .set(Keys.ELYTRA_CAPABILITY.getQuery(), this.elytraCapability);
    }

    private void setElytraCapability(ElytraCapability elytraCapability) {
        this.elytraCapability = elytraCapability;
    }

    private ElytraCapability getElytraCapability() {
        return this.elytraCapability;
    }

    private void setElytraFlying(boolean elytraFlying) {
        this.isElytraFlying = elytraFlying;
    }

    private boolean isElytraFlying() {
        return this.isElytraFlying;
    }

}
