package com.sunflow.sunbakamod.util;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class VersionHelper {
	private VersionHelper() {}

	public static final double getX(Entity e) { return e.func_226277_ct_(); }

	public static final double getY(Entity e) { return e.func_226278_cu_(); }

	public static final double getZ(Entity e) { return e.func_226281_cx_(); }

	public static final void setPos(Entity e, double x, double y, double z) { e.func_226288_n_(x, y, z); }

	public static boolean isSneaking(Entity e) { return e.func_226271_bk_(); }

	public static <C extends IFeatureConfig> void addStructure(Biome biome, ConfiguredFeature<C, ? extends Structure<C>> feature) {
		biome.func_226711_a_(feature);
	}

	public static <C extends IFeatureConfig> ConfiguredFeature<C, ? extends Structure<C>> getFeature(Structure<C> structure, C config) {
		return structure.func_225566_b_(config);
	}

	public static void addBox(ModelRenderer modelRenderer, float f, float g, float h, int i, int j, int k, float l) {
		// .addBox
		modelRenderer.func_228301_a_(f, g, h, i, j, k, l);
	}
}
