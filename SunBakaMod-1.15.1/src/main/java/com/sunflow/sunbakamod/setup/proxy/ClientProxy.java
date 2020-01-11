package com.sunflow.sunbakamod.setup.proxy;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.registration.ClientRegistrations;
import com.sunflow.sunbakamod.util.handlers.ClientForgeEventHandlers;
import com.sunflow.sunbakamod.util.handlers.KeyBindingHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ClientProxy extends CommonProxy {

	@Override
	public void setup() {
		super.setup();
		ClientRegistrations.registerScreens();
		KeyBindingHandler.setup();
		final IEventBus eventBus = MinecraftForge.EVENT_BUS;
		eventBus.register(KeyBindingHandler.class);
		eventBus.register(ClientForgeEventHandlers.class);

		config();
	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getInstance();
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	private void config() {
		ForgeConfigSpec cconfig;
		ForgeConfigSpec.Builder cbuilder = new ForgeConfigSpec.Builder();
		Path path = FMLPaths.CONFIGDIR.get().resolve("sunbakamod-client.toml");

		cbuilder.comment("Client only settings").push("client");
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
}
