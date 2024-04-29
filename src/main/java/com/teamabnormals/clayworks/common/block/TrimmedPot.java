package com.teamabnormals.clayworks.common.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface TrimmedPot {
	ResourceLocation getTrim();

	void setTrim(ResourceLocation name);

	ResourceLocation getTrimPattern();

	void setTrimPattern(ResourceLocation name);

	default Optional<TrimMaterial> getTrimMaterial(Level level) {
		return Optional.ofNullable(level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).get(this.getTrim()));
	}

	default Optional<Item> getTrimItem(Level level) {
		return this.getTrimMaterial(level).map(material -> material.ingredient().value());
	}
}
