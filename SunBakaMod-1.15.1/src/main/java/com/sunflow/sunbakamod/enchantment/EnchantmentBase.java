package com.sunflow.sunbakamod.enchantment;

import com.sunflow.sunbakamod.setup.ModEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class EnchantmentBase extends Enchantment {

	public EnchantmentBase(String name, Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType... slots) {
		super(rarityIn, typeIn, slots);
		this.setRegistryName(name);

		ModEnchantments.ENCHANTMENTS.add(this);
	}
}