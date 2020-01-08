package com.sunflow.sunbakamod.util.handlers;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.bag.ContainerBag;
import com.sunflow.sunbakamod.bag.ScreenLargeBag;
import com.sunflow.sunbakamod.items.ModItems;
import com.sunflow.sunbakamod.util.Reference;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onContainerRegister(RegistryEvent.Register<ContainerType<?>> event) {
		Reference.NEKOLOGGER.info("-RegisterContainer-");
		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
			return new ContainerBag(windowId, inv, SunBakaMod.proxy.getClientPlayer(), true);
		}).setRegistryName("backpack"));
	}

	public static void registerScreens() {
		Reference.NEKOLOGGER.info("-RegisterScreens-");

		ScreenManager.registerFactory(ModItems.CONTAINER_BAG, ScreenLargeBag::new);
	}
}
