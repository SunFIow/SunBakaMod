package com.sunflow.sunbakamod.items;

import java.util.ArrayList;

import com.sunflow.sunbakamod.bag.ContainerBag;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	public static final ArrayList<Item> ITEMS = new ArrayList<>();

	// Items
	public static final ItemEnderPack ITEM_ENDERPACK = new ItemEnderPack();
	public static final ItemBackpack ITEM_BACKPACK_BAKA = new ItemBackpack("backpackbaka", "Baka's Backpack");
	public static final ItemBackpack ITEM_BACKPACK_SUN = new ItemBackpack("backpacksun", "SunFlow's Backpack");

	// Containers
	@ObjectHolder("sunbakamod:backpack")
	public static ContainerType<ContainerBag> CONTAINER_BAG;
}
