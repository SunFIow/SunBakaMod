package com.sunflow.sunbakamod.setup;

import java.util.ArrayList;
import java.util.List;

import com.sunflow.sunbakamod.dimension.ModDimensionBase;
import com.sunflow.sunbakamod.dimension.redworld.RedWorldDimension;

import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
	public static final List<ModDimensionBase> DIMENSIONS = new ArrayList<>();

	public static final ModDimensionBase REDSTONEWORLD = new ModDimensionBase("redworld", RedWorldDimension::new, (type) -> REDSTONEWORLD_TYPE = type);
	public static DimensionType REDSTONEWORLD_TYPE;
}
