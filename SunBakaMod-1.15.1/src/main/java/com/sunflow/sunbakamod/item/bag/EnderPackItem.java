package com.sunflow.sunbakamod.item.bag;

import net.minecraftforge.items.ItemStackHandler;

public class EnderPackItem extends BaseBagItem {

	public static ItemStackHandler handler = new ItemStackHandler(9 * 6);

	public EnderPackItem() {
		super("enderpack");
		setCustomName("Enderpack");
	}
}
