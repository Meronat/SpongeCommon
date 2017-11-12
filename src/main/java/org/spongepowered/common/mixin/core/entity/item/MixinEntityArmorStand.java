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
package org.spongepowered.common.mixin.core.entity.item;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.Rotations;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.mutable.entity.ArmorStandData;
import org.spongepowered.api.data.manipulator.mutable.entity.BodyPartRotationalData;
import org.spongepowered.api.data.type.BodyPart;
import org.spongepowered.api.data.type.BodyParts;
import org.spongepowered.api.data.type.DisabledSlotType;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeArmorStandData;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeBodyPartRotationalData;
import org.spongepowered.common.data.type.SpongeEquipmentType;
import org.spongepowered.common.data.value.mutable.SpongeValue;
import org.spongepowered.common.interfaces.entity.item.IMixinArmorStand;
import org.spongepowered.common.mixin.core.entity.MixinEntityLivingBase;
import org.spongepowered.common.util.VecHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(EntityArmorStand.class)
@Implements(@Interface(iface = ArmorStand.class, prefix = "armor$"))
public abstract class MixinEntityArmorStand extends MixinEntityLivingBase implements ArmorStand, IMixinArmorStand {

    @Shadow public Rotations leftArmRotation;
    @Shadow public Rotations rightArmRotation;
    @Shadow public Rotations leftLegRotation;
    @Shadow public Rotations rightLegRotation;

    @Shadow public abstract boolean getShowArms(); // getShowArms
    @Shadow public abstract boolean hasNoBasePlate(); // hasNoBasePlate
    @Shadow public abstract boolean hasMarker();
    @Shadow public abstract boolean shadow$isSmall();
    @Shadow public abstract Rotations shadow$getHeadRotation();
    @Shadow public abstract Rotations getBodyRotation();

    @Shadow private int disabledSlots;

    @Override
    public Value<Boolean> marker() {
        return new SpongeValue<>(Keys.ARMOR_STAND_MARKER, false, this.hasMarker());
    }

    @Override
    public Value<Boolean> small() {
        return new SpongeValue<>(Keys.ARMOR_STAND_IS_SMALL, false, this.shadow$isSmall());
    }

    @Override
    public Value<Boolean> basePlate() {
        return new SpongeValue<>(Keys.ARMOR_STAND_HAS_BASE_PLATE, true, !this.hasNoBasePlate());
    }

    @Override
    public Value<Boolean> arms() {
        return new SpongeValue<>(Keys.ARMOR_STAND_HAS_ARMS, false, this.getShowArms());
    }

    @Override
    public ArmorStandData getArmorStandData() {
        return new SpongeArmorStandData(this.hasMarker(), this.shadow$isSmall(), this.getShowArms(),
                !this.hasNoBasePlate(), this.getDisabledSlotTypes());
    }

    @Override
    public BodyPartRotationalData getBodyPartRotationalData() {
        Map<BodyPart, Vector3d> rotations = Maps.newHashMapWithExpectedSize(6);
        rotations.put(BodyParts.HEAD, VecHelper.toVector3d(this.shadow$getHeadRotation()));
        rotations.put(BodyParts.CHEST, VecHelper.toVector3d(this.getBodyRotation()));
        rotations.put(BodyParts.LEFT_ARM, VecHelper.toVector3d(this.leftArmRotation));
        rotations.put(BodyParts.RIGHT_ARM, VecHelper.toVector3d(this.rightArmRotation));
        rotations.put(BodyParts.LEFT_LEG, VecHelper.toVector3d(this.leftLegRotation));
        rotations.put(BodyParts.RIGHT_LEG, VecHelper.toVector3d(this.rightLegRotation));
        return new SpongeBodyPartRotationalData(rotations);
    }

    @Override
    public void supplyVanillaManipulators(List<DataManipulator<?, ?>> manipulators) {
        super.supplyVanillaManipulators(manipulators);
        manipulators.add(getBodyPartRotationalData());
        manipulators.add(getArmorStandData());
    }

    @Override
    public Set<DisabledSlotType> getDisabledSlotTypes() {
        final Set<DisabledSlotType> disabledSlotTypes = new HashSet<>();
        for (DisabledSlotType type : Sponge.getRegistry().getAllOf(DisabledSlotType.class)) {
            if (isDisabled(((SpongeEquipmentType) type.getEquipmentType()).getSlots()[0], type.getDisableType())) {
                disabledSlotTypes.add(type);
            }
        }
        return disabledSlotTypes;
    }

    @Override
    public void setDisabledSlotTypes(final Set<DisabledSlotType> disabledSlotTypes) {
        int newDisabledSlots = 0;
        for (DisabledSlotType type : disabledSlotTypes) {
            newDisabledSlots += 1 << (((SpongeEquipmentType) type.getEquipmentType()).getSlots()[0].getSlotIndex() + toValue(type.getDisableType()));
        }
        this.disabledSlots = newDisabledSlots;
    }

    private int toValue(DisabledSlotType.DisableType disableType) {
        switch (disableType) {
            case REPLACE:
                return 8;
            case PLACE:
                return 16;
        }
        return 0;
    }

    private boolean isDisabled(EntityEquipmentSlot entityEquipmentSlot, DisabledSlotType.DisableType disableType) {
        return (this.disabledSlots & 1 << entityEquipmentSlot.getSlotIndex() + toValue(disableType)) != 0;
    }

}
