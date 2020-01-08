package com.sunflow.sunbakamod.block;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.setup.ModBlocks;
import com.sunflow.sunbakamod.setup.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class BlockBase extends Block {

	public BlockBase(String name, ItemGroup group, Properties properties) {
		super(properties);
		this.setRegistryName(name);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(group)).setRegistryName(name));
	}

	public BlockBase(String name, Properties properties) {
		this(name, SunBakaMod.itemGroup, properties);
	}

	public BlockBase(String name, Material material) {
		this(name, Block.Properties.create(material).hardnessAndResistance(2.5f));
	}

	public BlockBase(String name, Material material, MaterialColor color, float hardness, float resistance, SoundType soundtype) {
		this(name, Block.Properties.create(material, color).hardnessAndResistance(hardness, resistance).sound(soundtype));
	}

	public BlockBase(String name, Material material, float hardness, float resistance, ToolType tooltype, int harvestlevel, SoundType soundtype, int lightlevel) {
		this(name, Block.Properties.create(material).hardnessAndResistance(hardness, resistance).harvestTool(tooltype).harvestLevel(harvestlevel).sound(soundtype).lightValue(lightlevel));
	}
}
