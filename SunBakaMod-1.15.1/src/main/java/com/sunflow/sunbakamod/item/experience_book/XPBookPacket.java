package com.sunflow.sunbakamod.item.experience_book;

import com.sunflow.sunbakamod.network.packet.BasePacket;
import com.sunflow.sunbakamod.setup.ModItems;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

public class XPBookPacket extends BasePacket {
	private int experienceTotal;
	private ItemStack stack;
	private Hand hand;

	public XPBookPacket(int experienceTotal, ItemStack stack, Hand hand) {
		this.experienceTotal = experienceTotal;
		this.stack = stack.copy();
		this.hand = hand;
	}

	public XPBookPacket(PacketBuffer buf) {
		experienceTotal = buf.readInt();
		stack = buf.readItemStack();
		hand = buf.readEnumValue(Hand.class);
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(experienceTotal);
		buf.writeItemStack(stack);
		buf.writeEnumValue(hand);
	}

	@Override
	public boolean action(NetworkEvent.Context ctx) {
		ServerPlayerEntity player = ctx.getSender();
		ItemStack itemstack = getStack();
		if (!itemstack.isEmpty()) {
			if (ExperienceBookItem.isNBTValid(itemstack.getTag())) {
				ItemStack itemstack1 = player.getHeldItem(getHand());
				if (itemstack.getItem() == ModItems.EXPERIENCE_BOOK && itemstack1.getItem() == ModItems.EXPERIENCE_BOOK) {
					itemstack1.getOrCreateTag().putInt("xp", itemstack.getTag().getInt("xp"));
					player.giveExperiencePoints(experienceTotal - player.experienceTotal);
				}
			}
		}
		return true;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public Hand getHand() {
		return this.hand;
	}
}