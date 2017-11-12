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
package org.spongepowered.common.data.builder.meta;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.meta.Enchantment;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.item.EnchantmentType;
import org.spongepowered.common.SpongeImpl;
import org.spongepowered.common.data.meta.SpongeEnchantment;
import org.spongepowered.common.data.util.DataUtil;

import java.util.Optional;

import javax.annotation.Nullable;

public final class SpongeEnchantmentBuilder extends AbstractDataBuilder<Enchantment> implements Enchantment.Builder {

    @Nullable private EnchantmentType enchantmentType;
    private int level;

    public SpongeEnchantmentBuilder() {
        super(Enchantment.class, 1);
    }

    @Override
    public Enchantment.Builder from(final Enchantment value) {
        this.enchantmentType = value.getType();
        this.level = value.getLevel();
        return this;
    }

    @Override
    public Enchantment.Builder reset() {
        this.enchantmentType = null;
        this.level = 0;
        return this;
    }

    @Override
    public Enchantment.Builder type(final EnchantmentType enchantmentType) {
        this.enchantmentType = checkNotNull(enchantmentType, "Enchantment type cannot be null!");
        return this;
    }

    @Override
    public Enchantment.Builder level(final int level) {
        checkArgument(level > 0, "The specified level must be greater than 0 (was %s)", level);
        checkArgument(level <= Short.MAX_VALUE, "The specified level must not be greater than %s (was %s)", Short.MAX_VALUE, level);
        this.level = level;
        return this;
    }

    @Override
    public Enchantment build() {
        checkState(this.enchantmentType != null, "The enchantment type must be set!");
        checkState(this.level > 0, "The level must be greater than 0.");
        return new SpongeEnchantment(this.enchantmentType, this.level);
    }

    @Override
    protected Optional<Enchantment> buildContent(DataView container) throws InvalidDataException {
        checkNotNull(container, "The data view cannot be null!");
        if (container.contains(Queries.ENCHANTMENT_ID, Queries.LEVEL)) {
            final String id = DataUtil.getData(container, Queries.ENCHANTMENT_ID, String.class);
            final int level = DataUtil.getData(container, Queries.LEVEL, Integer.class);
            final Optional<EnchantmentType> enchantmentType = SpongeImpl.getRegistry().getType(EnchantmentType.class, id);
            if (enchantmentType.isPresent()) {
                return Optional.of(Enchantment.builder().type(enchantmentType.get()).level(level).build());
            }
        }
        return Optional.empty();
    }

}
