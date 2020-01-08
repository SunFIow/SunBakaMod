package com.sunflow.sunbakamod.item;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class BaseItem extends Item {

	protected String customName;

	public BaseItem(String name) {
		this(name, 64, 0);
	}

	public BaseItem(String name, int maxStackSizeIn, int maxDamageIn) {
		this(name, maxStackSizeIn, maxDamageIn, SunBakaMod.itemGroup);
	}

	public BaseItem(String name, int maxStackSizeIn, int maxDamageIn, ItemGroup groupIn) {
		super(new Item.Properties().maxStackSize(maxStackSizeIn).maxDamage(maxDamageIn).group(groupIn));
		this.setRegistryName(new ResourceLocation(SunBakaMod.MODID, name));

		ModItems.ITEMS.add(this);
	}
}
