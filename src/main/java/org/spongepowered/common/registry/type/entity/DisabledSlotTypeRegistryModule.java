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
package org.spongepowered.common.registry.type.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import org.spongepowered.api.data.type.DisabledSlotType;
import org.spongepowered.api.data.type.DisabledSlotTypes;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.registry.util.RegistrationDependency;
import org.spongepowered.common.data.type.SpongeDisabledSlotType;
import org.spongepowered.common.registry.type.item.EquipmentTypeRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RegistrationDependency(EquipmentTypeRegistryModule.class)
public class DisabledSlotTypeRegistryModule implements CatalogRegistryModule<DisabledSlotType> {

    public static DisabledSlotTypeRegistryModule getInstance() {
        return Holder.INSTANCE;
    }

    private DisabledSlotTypeRegistryModule() { }

    @RegisterCatalog(DisabledSlotTypes.class)
    private final Map<String, DisabledSlotType> disabledSlotTypeMap = new HashMap<>();

    @Override
    public Optional<DisabledSlotType> getById(String id) {
        checkNotNull(id, "Id cannot be null!");
        if (!id.contains(":")) {
            id = "sponge:" + id;
        }
        return Optional.ofNullable(this.disabledSlotTypeMap.get(id.replace("minecraft:", "sponge:").toLowerCase(Locale.ENGLISH)));
    }

    @Override
    public Collection<DisabledSlotType> getAll() {
        return ImmutableList.copyOf(this.disabledSlotTypeMap.values());
    }

    @Override
    public void registerDefaults() {
        this.disabledSlotTypeMap.put("sponge:place_boots", new SpongeDisabledSlotType("sponge:place_boots", "Place Boots",
                EquipmentTypes.BOOTS, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:place_chestplate", new SpongeDisabledSlotType("sponge:place_chestplate", "Place Chestplate",
                EquipmentTypes.CHESTPLATE, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:place_helmet", new SpongeDisabledSlotType("sponge:place_helmet", "Place Helmet",
                EquipmentTypes.HEADWEAR, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:place_leggings", new SpongeDisabledSlotType("sponge:place_leggings", "Place Leggings",
                EquipmentTypes.LEGGINGS, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:place_main_hand_item", new SpongeDisabledSlotType("sponge:place_main_hand_item", "Place Main Hand Item",
                EquipmentTypes.MAIN_HAND, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:place_off_hand_item", new SpongeDisabledSlotType("sponge:place_off_hand_item", "Place Off Hand Item",
                EquipmentTypes.OFF_HAND, DisabledSlotType.DisableType.PLACE));
        this.disabledSlotTypeMap.put("sponge:remove_boots", new SpongeDisabledSlotType("sponge:remove_boots", "Remove Boots",
                EquipmentTypes.BOOTS, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:remove_chestplate", new SpongeDisabledSlotType("sponge:remove_chestplate", "Remove Chestplate",
                EquipmentTypes.CHESTPLATE, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:remove_helmet", new SpongeDisabledSlotType("sponge:remove_helmet", "Remove Helmet",
                EquipmentTypes.HEADWEAR, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:remove_leggings", new SpongeDisabledSlotType("sponge:remove_leggings", "Remove Leggings",
                EquipmentTypes.LEGGINGS, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:remove_main_hand_item", new SpongeDisabledSlotType("sponge:remove_main_hand_item", "Remove Main Hand Item",
                EquipmentTypes.MAIN_HAND, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:remove_off_hand_item", new SpongeDisabledSlotType("sponge:remove_off_hand_item", "Remove Off Hand Item",
                EquipmentTypes.OFF_HAND, DisabledSlotType.DisableType.REMOVE));
        this.disabledSlotTypeMap.put("sponge:replace_boots", new SpongeDisabledSlotType("sponge:replace_boots", "Replace Boots",
                EquipmentTypes.BOOTS, DisabledSlotType.DisableType.REPLACE));
        this.disabledSlotTypeMap.put("sponge:replace_chestplate", new SpongeDisabledSlotType("sponge:replace_chestplate", "Replace Chestplate",
                EquipmentTypes.CHESTPLATE, DisabledSlotType.DisableType.REPLACE));
        this.disabledSlotTypeMap.put("sponge:replace_helmet", new SpongeDisabledSlotType("sponge:replace_helmet", "Replace Helmet",
                EquipmentTypes.HEADWEAR, DisabledSlotType.DisableType.REPLACE));
        this.disabledSlotTypeMap.put("sponge:replace_leggings", new SpongeDisabledSlotType("sponge:replace_leggings", "Replace Leggings",
                EquipmentTypes.LEGGINGS, DisabledSlotType.DisableType.REPLACE));
        this.disabledSlotTypeMap.put("sponge:replace_main_hand_item", new SpongeDisabledSlotType("sponge:replace_main_hand_item", "Replace Main Hand Item",
                EquipmentTypes.MAIN_HAND, DisabledSlotType.DisableType.REPLACE));
        this.disabledSlotTypeMap.put("sponge:replace_off_hand_item", new SpongeDisabledSlotType("sponge:replace_off_hand_item", "Replace Off Hand Item",
                EquipmentTypes.OFF_HAND, DisabledSlotType.DisableType.REPLACE));
    }

    private static final class Holder {
        final static DisabledSlotTypeRegistryModule INSTANCE = new DisabledSlotTypeRegistryModule();
    }
}
