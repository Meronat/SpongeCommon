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
package org.spongepowered.common.world.biome;

import static com.google.common.base.Preconditions.checkNotNull;

import org.spongepowered.api.world.biome.BiomeGenerationSettings;
import org.spongepowered.api.world.biome.BiomeGenerationSettings.Builder;
import org.spongepowered.api.world.biome.GroundCoverLayer;
import org.spongepowered.api.world.gen.GenerationPopulator;
import org.spongepowered.api.world.gen.Populator;

import java.util.ArrayList;
import java.util.List;

public class SpongeBiomeGenerationSettingsBuilder implements BiomeGenerationSettings.Builder {

    private float min = 0;
    private float max = 0;
    private final List<GroundCoverLayer> groundCoverLayers = new ArrayList<>();
    private final List<Populator> populators = new ArrayList<>();
    private final List<GenerationPopulator> generationPopulators = new ArrayList<>();

    @Override
    public Builder from(BiomeGenerationSettings value) {
        this.min = value.getMinHeight();
        this.max = value.getMaxHeight();
        this.groundCoverLayers.clear();
        this.groundCoverLayers.addAll(value.getGroundCoverLayers());
        this.generationPopulators.clear();
        this.generationPopulators.addAll(value.getGenerationPopulators());
        this.populators.clear();
        this.populators.addAll(value.getPopulators());
        return this;
    }

    @Override
    public Builder reset() {
        this.min = 0;
        this.max = 0;
        this.groundCoverLayers.clear();
        this.populators.clear();
        this.generationPopulators.clear();
        return this;
    }

    @Override
    public Builder minHeight(float height) {
        this.min = height;
        return this;
    }

    @Override
    public Builder maxHeight(float height) {
        this.max = height;
        return this;
    }

    @Override
    public Builder groundCover(GroundCoverLayer... coverLayers) {
        checkNotNull(coverLayers, "The ground cover layers cannot be null!");
        this.groundCoverLayers.clear();
        for (GroundCoverLayer layer : coverLayers) {
            this.groundCoverLayers.add(checkNotNull(layer, "A ground cover layer cannot be null!"));
        }
        return this;
    }

    @Override
    public Builder groundCover(Iterable<GroundCoverLayer> coverLayers) {
        checkNotNull(coverLayers, "The ground cover layers cannot be null!");
        this.groundCoverLayers.clear();
        for (GroundCoverLayer layer : coverLayers) {
            this.groundCoverLayers.add(checkNotNull(layer, "A ground cover layer cannot be null!"));
        }
        return this;
    }

    @Override
    public Builder generationPopulators(GenerationPopulator... generationPopulators) {
        checkNotNull(generationPopulators, "The generation populators cannot be null!");
        this.generationPopulators.clear();
        for (GenerationPopulator pop : generationPopulators) {
            this.generationPopulators.add(checkNotNull(pop, "A generation populator cannot be null!"));
        }
        return this;
    }

    @Override
    public Builder generationPopulators(Iterable<GenerationPopulator> generationPopulators) {
        checkNotNull(generationPopulators, "The generation populators cannot be null!");
        this.generationPopulators.clear();
        for (GenerationPopulator pop : generationPopulators) {
            this.generationPopulators.add(checkNotNull(pop, "A generation populator cannot be null!"));
        }
        return this;
    }

    @Override
    public Builder populators(Populator... populators) {
        checkNotNull(populators, "The populators cannot be null!");
        this.populators.clear();
        for (Populator pop : populators) {
            this.populators.add(checkNotNull(pop, "A populator cannot be null!"));
        }
        return this;
    }

    @Override
    public Builder populators(Iterable<Populator> populators) {
        checkNotNull(populators, "The populators cannot be null!");
        this.populators.clear();
        for (Populator pop : populators) {
            this.populators.add(checkNotNull(pop, "A populator cannot be null!"));
        }
        return this;
    }

    @Override
    public BiomeGenerationSettings build() throws IllegalStateException {
        SpongeBiomeGenerationSettings settings = new SpongeBiomeGenerationSettings();
        settings.setMinHeight(this.min);
        settings.setMaxHeight(this.max);
        settings.getGroundCoverLayers().addAll(this.groundCoverLayers);
        settings.getPopulators().addAll(this.populators);
        settings.getGenerationPopulators().addAll(this.generationPopulators);
        return settings;
    }

}
