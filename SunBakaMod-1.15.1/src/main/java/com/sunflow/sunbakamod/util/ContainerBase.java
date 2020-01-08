package com.sunflow.sunbakamod.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.sunflow.sunbakamod.item.bag.BackpackItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBase extends Container {

	protected ItemStackHandler handler;
	protected PlayerInventory playerInv;
	protected ItemStack itemstack;

	protected int containerSlotCount;
	private int dragType;

	public ContainerBase(ContainerType<?> type, int id, int containerSlotCount) {
		super(type, id);
		this.containerSlotCount = containerSlotCount;
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
		this.dragType = dragType;
		return super.slotClick(slotId, dragType, clickType, player);
	}

	public boolean mouseScrolled(double mouseX, double mouseY, double scrollDir, int guiLeft, int guiTop) {
		Slot slot = getSlot(mouseX, mouseY, guiLeft, guiTop);
		if (slot == null) return false;
		if (itemstack.getItem() instanceof BackpackItem && slot.getStack().getItem() instanceof BackpackItem) return false;

		ItemStack stack = slot.getStack();
		if (scrollDir > 0) {
			ItemStack stack2 = stack.copy().split(1);
			if (mergeItemStack(stack2, slot.slotNumber < containerSlotCount ? containerSlotCount : 0, slot.slotNumber < containerSlotCount ? containerSlotCount + 9 * 4 : containerSlotCount, false)) {
				slot.decrStackSize(1);
				if (stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
				else slot.onSlotChanged();
			}

		} else {
			if (stack.getMaxStackSize() == stack.getCount()) return false;
			Slot oSlot = getFirstItemStacksOf(stack, slot.slotNumber < containerSlotCount ? containerSlotCount : 0, slot.slotNumber < containerSlotCount ? containerSlotCount + 9 * 4 : containerSlotCount);

			oSlot.decrStackSize(1);
			stack.setCount(stack.getCount() + 1);

			slot.onSlotChanged();
			if (!oSlot.getHasStack()) oSlot.putStack(ItemStack.EMPTY);
			else oSlot.onSlotChanged();
		}

		detectAndSendChanges();
		return false;
	}

	@Nullable
	private Slot getFirstItemStacksOf(ItemStack compareStack, int startIndex, int endIndex) {
		for (int index = startIndex; index < endIndex; index++) {
			Slot slot = getSlot(index);
			if (ItemStack.areItemsEqualIgnoreDurability(slot.getStack(), compareStack)) return slot;
		}
		return null;
	}

	@Nullable
	private Slot getSlot(double mouseX, double mouseY, int guiLeft, int guiTop) {
		double x = mouseX - guiLeft;
		double y = mouseY - guiTop;
		for (Slot s : inventorySlots) if (x >= s.xPos && y >= s.yPos && x <= s.xPos + 16 && y <= s.yPos + 16) return s;
		return null;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		Slot slot = getSlot(index);
		if (slot == null || !slot.getHasStack()) return ItemStack.EMPTY;

		ItemStack stack = slot.getStack();
		ItemStack itemstack = stack.copy();

		if (index < containerSlotCount) {
			if (player.inventory.getItemStack().getItem() != ItemStack.EMPTY.getItem()) {
				if (dragType == 0) return swapAllItemsXfromInvAtoInvB(slot, stack, 0, containerSlotCount, containerSlotCount, containerSlotCount + 9 * 4);
				else if (dragType == 1) return swapAllItemsXfromInvAtoInvB(slot, stack, containerSlotCount, containerSlotCount + 9 * 4, 0, containerSlotCount);
			}
			if (!this.mergeItemStack(stack, containerSlotCount, containerSlotCount + 9 * 4, true)) return ItemStack.EMPTY;

			slot.onSlotChange(stack, itemstack);
		} else {
			if (!this.mergeItemStack(stack, 0, containerSlotCount, false)) {
				if (index < containerSlotCount + 9 * 3) if (!this.mergeItemStack(stack, containerSlotCount + 9 * 3, containerSlotCount + 9 * 4, false)) return ItemStack.EMPTY;
				else if (index < containerSlotCount + 9 * 4 && !this.mergeItemStack(stack, containerSlotCount, containerSlotCount + 9 * 3, false)) return ItemStack.EMPTY;
			}
		}

		if (stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
		else slot.onSlotChanged();

		if (stack.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

		slot.onTake(player, stack);
		return itemstack;

		// return super.transferStackInSlot(playerIn, index);
	}

	// Swap all items from type == stack.item from this inventory A to inventory B
	private ItemStack swapAllItemsXfromInvAtoInvB(Slot slot, ItemStack stack, int startIndexA, int endIndexA, int startIndexB, int endIndexB) {
		List<Slot> slots = getAllItemStacksOf(stack, startIndexA, endIndexA);
		for (Slot s : slots) {
			ItemStack newStack = s.getStack();
			ItemStack newStackCopy = newStack.copy();
			if (!this.mergeItemStack(newStack, startIndexB, endIndexB, true)) {
				return ItemStack.EMPTY;
			}
			slot.onSlotChange(newStack, newStackCopy);
		}
		return ItemStack.EMPTY;
	}

	private List<Slot> getAllItemStacksOf(ItemStack compareStack, int startIndex, int endIndex) {
		List<Slot> returnList = new ArrayList<>();
		for (int index = startIndex; index < endIndex; index++) {
			Slot slot = getSlot(index);
			if (ItemStack.areItemsEqualIgnoreDurability(slot.getStack(), compareStack)) returnList.add(slot);
		}
		return returnList;
	}

	protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}

	protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for (int j = 0; j < verAmount; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}

	protected void layoutPlayerInventorySlots(IItemHandler handler, int leftCol, int topRow) {
		// Player inventory
		addSlotBox(handler, 9, leftCol, topRow, 9, 18, 3, 18);

		// Hotbar
		topRow += 58;
		addSlotRange(handler, 0, leftCol, topRow, 9, 18);
	}
}
