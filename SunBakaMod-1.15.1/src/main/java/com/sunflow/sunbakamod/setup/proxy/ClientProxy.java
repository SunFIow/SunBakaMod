package com.sunflow.sunbakamod.setup.proxy;

import com.sunflow.sunbakamod.setup.registration.ClientRegistrations;
import com.sunflow.sunbakamod.util.handlers.ClientForgeEventHandlers;
import com.sunflow.sunbakamod.util.handlers.KeyBindingHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientProxy extends CommonProxy {

	@Override
	public void setup() {
		super.setup();
		ClientRegistrations.registerScreens();
		KeyBindingHandler.setup();
		final IEventBus eventBus = MinecraftForge.EVENT_BUS;
		eventBus.register(KeyBindingHandler.class);
		eventBus.register(ClientForgeEventHandlers.class);
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

}
