package com.sunflow.sunbakamod.setup.registration;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.item.bag.BagContainer;
import com.sunflow.sunbakamod.setup.ModBlocks;
import com.sunflow.sunbakamod.setup.ModDimensions;
import com.sunflow.sunbakamod.setup.ModEnchantments;
import com.sunflow.sunbakamod.setup.ModItems;
import com.sunflow.sunbakamod.util.Log;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistrations {
	@SubscribeEvent
	public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
		Log.debug("I am going to register the blocks now senpai.");

		IForgeRegistry<Block> registry = event.getRegistry();
//		registry.registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		ModBlocks.BLOCKS.forEach((block) -> registry.register(block));
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		Log.debug("I am going to register the items now senpai.");

		IForgeRegistry<Item> registry = event.getRegistry();
		ModItems.ITEMS.forEach((item) -> registry.register(item));
	}

	@SubscribeEvent
	public static void onDimensionRegistry(RegistryEvent.Register<ModDimension> event) {
		Log.debug("I am going to register the dimensions now senpai.");

		IForgeRegistry<ModDimension> registry = event.getRegistry();
		ModDimensions.DIMENSIONS.forEach((dimension) -> registry.register(dimension));
	}

	@SubscribeEvent
	public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
		Log.debug("I am going to register the tileentites now senpai.");

		IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
//		registry.registerAll(ModTileEntitiyTypes.TILE_TYPES.toArray(new TileEntityType[0]));
		ModBlocks.TILE_TYPES.forEach((tileEntityType) -> registry.register(tileEntityType));
	}

	@SubscribeEvent
	public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event) {
		Log.debug("I am going to register the enchantments now senpai.");

		IForgeRegistry<Enchantment> registry = event.getRegistry();
//		registry.registerAll(ModEnchantments.ENCHANTMENTS.toArray(new Enchantment[0]));
		ModEnchantments.ENCHANTMENTS.forEach((enchantment) -> registry.register(enchantment));
	}

	@SubscribeEvent
	public static void onContainerRegister(RegistryEvent.Register<ContainerType<?>> event) {
		Log.debug("I am going to register the containers now senpai.");

		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new BagContainer(windowId, inv, SunBakaMod.proxy.getClientPlayer())).setRegistryName("backpack"));
	}
}
