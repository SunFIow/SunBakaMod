package com.sunflow.sunbakamod.item.bag;

public class BackpackItem extends BaseBagItem {

	public BackpackItem(String name) {
		super(name);
	}

	public BackpackItem(String name, String customName) {
		super(name);
		setCustomName(customName);
	}
}
