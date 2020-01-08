package com.sunflow.sunbakamod.items;

import com.sunflow.sunbakamod.bag.ContainerBag;
import com.sunflow.sunbakamod.util.interfaces.IHasInventory;

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
import net.minecraftforge.items.ItemStackHandler;

public class ItemBagBase extends ItemBase implements INamedContainerProvider, IHasInventory {

	public static ItemStackHandler handler = new ItemStackHandler(9 * 6);

	public ItemBagBase(String name) {
		super(name, 1, 1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote) {
			openBag(player, hand);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void openBag(PlayerEntity player, Hand hand) {
		player.openContainer(this);
	}

	@Override
	public void closeBag(PlayerEntity player, ItemStack itemstack) {}

	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return new ContainerBag(windowId, inv, player, false);
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
