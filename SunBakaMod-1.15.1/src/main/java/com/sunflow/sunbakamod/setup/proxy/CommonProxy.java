package com.sunflow.sunbakamod.setup.proxy;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.registration.CommonRegistrations;
import com.sunflow.sunbakamod.util.handlers.CommonForgeEventHandlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class CommonProxy {

	public void preSetup() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(SunBakaMod.class);
		modEventBus.register(CommonRegistrations.class);

		MinecraftForge.EVENT_BUS.register(SunBakaMod.class);
	}

	public void setup() {
		final IEventBus eventBus = MinecraftForge.EVENT_BUS;
		eventBus.register(CommonForgeEventHandlers.class);
	}

	public abstract Minecraft getMinecraft();

	public abstract World getClientWorld();

	public abstract PlayerEntity getClientPlayer();

}
