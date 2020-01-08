package com.sunflow.sunbakamod.setup;

import java.util.ArrayList;

import com.sunflow.sunbakamod.item.CraftingStickItem;
import com.sunflow.sunbakamod.item.bag.BackpackItem;
import com.sunflow.sunbakamod.item.bag.BagContainer;
import com.sunflow.sunbakamod.item.bag.EnderPackItem;
import com.sunflow.sunbakamod.item.experience_book.ExperienceBookItem;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems {
	public static final ArrayList<Item> ITEMS = new ArrayList<>();

	// Items
	public static final Item ITEM_ENDERPACK = new EnderPackItem();
	public static final Item ITEM_BACKPACK_BAKA = new BackpackItem("backpackbaka", "Baka's Backpack");
	public static final Item ITEM_BACKPACK_SUN = new BackpackItem("backpacksun", "SunFlow's Backpack");
	public static final Item ITEM_CRAFTING_STICK = new CraftingStickItem("crafting_stick");
	public static final Item EXPERIENCE_BOOK = new ExperienceBookItem("experience_book");

	// Containers
	@ObjectHolder("sunbakamod:backpack")
	public static ContainerType<BagContainer> CONTAINER_BAG;
}
