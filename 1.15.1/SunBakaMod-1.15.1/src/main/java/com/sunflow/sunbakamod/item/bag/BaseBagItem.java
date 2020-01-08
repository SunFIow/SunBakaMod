package com.sunflow.sunbakamod.item.bag;

import com.sunflow.sunbakamod.item.BaseItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BaseBagItem extends BaseItem implements INamedContainerProvider {

	public BaseBagItem(String name) {
		super(name, 1, 1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote) {
			player.openContainer(this);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return new BagContainer(windowId, inv, player);
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.customName != null ? new StringTextComponent(customName)
				: new StringTextComponent(getRegistryName().getPath());
	}

	protected void setCustomName(String customName) {
		this.customName = customName;
	}
}
