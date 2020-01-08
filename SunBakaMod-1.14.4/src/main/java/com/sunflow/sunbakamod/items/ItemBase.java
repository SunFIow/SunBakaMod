package com.sunflow.sunbakamod.items;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.util.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class ItemBase extends Item {

	protected String customName;

	public ItemBase(String name) {
		this(name, 64, 0);
	}

	public ItemBase(String name, int maxStackSizeIn, int maxDamageIn) {
		this(name, maxStackSizeIn, maxDamageIn, SunBakaMod.itemGroup);
	}

	public ItemBase(String name, int maxStackSizeIn, int maxDamageIn, ItemGroup groupIn) {
		super(new Item.Properties().maxStackSize(maxStackSizeIn).maxDamage(maxDamageIn).group(groupIn));
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

		ModItems.ITEMS.add(this);
	}
}
