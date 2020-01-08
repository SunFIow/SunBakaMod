package com.sunflow.sunbakamod.enchantment;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class EnchantmentTimber extends EnchantmentBase {
	public EnchantmentTimber() {
		super("timber", Rarity.VERY_RARE, EnchantmentType.DIGGER, EquipmentSlotType.MAINHAND);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 15;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof AxeItem;
	}
}
