package com.sunflow.sunbakamod.setup.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy extends CommonProxy {

//	@Override
//	public void setup() {
//		super.setup();
//		final IEventBus eventBus = MinecraftForge.EVENT_BUS;
//		eventBus.register(ServerForgeEventHandlers.class);
//	}

	@Override
	public Minecraft getMinecraft() {
		throw new IllegalStateException("Only run this on the client!");
	}

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Only run this on the client!");
	}

	@Override
	public PlayerEntity getClientPlayer() {
		throw new IllegalStateException("Only run this on the client!");
	}

}
