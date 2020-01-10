package com.sunflow.sunbakamod.setup.proxy;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.registration.CommonRegistrations;
import com.sunflow.sunbakamod.util.handlers.CommonForgeEventHandlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

public abstract class CommonProxy {

	public void preSetup() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(SunBakaMod.class);
		modEventBus.register(CommonRegistrations.class);

		MinecraftForge.EVENT_BUS.register(SunBakaMod.class);

		config();
	}

	public void setup() {
		final IEventBus eventBus = MinecraftForge.EVENT_BUS;
		eventBus.register(CommonForgeEventHandlers.class);
	}

	private void config() {
		ForgeConfigSpec cconfig;
		ForgeConfigSpec.Builder cbuilder = new ForgeConfigSpec.Builder();
		Path path = FMLPaths.CONFIGDIR.get().resolve("sunbakamod-client.toml");

		cbuilder.comment("Client GameSettings").push("Game Settings").pop();
		cbuilder.comment("Overlay Settings").push("overlay");
		SunBakaMod.CONFIG_SHOW_OVERLAY = cbuilder.comment("Show the Overlay InGame").define("show", false);
		cbuilder.pop();
		cconfig = cbuilder.build();

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, cconfig);

		CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		configData.load();
		cconfig.setConfig(configData);
	}

	public abstract Minecraft getMinecraft();

	public abstract World getClientWorld();

	public abstract PlayerEntity getClientPlayer();

}
