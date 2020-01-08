package com.sunflow.sunbakamod.items;

import net.minecraftforge.items.ItemStackHandler;

public class ItemEnderPack extends ItemBagBase {

	public static ItemStackHandler handler = new ItemStackHandler(9 * 6);

	public ItemEnderPack() {
		super("enderpack");
		setCustomName("Enderpack");
	}
}
