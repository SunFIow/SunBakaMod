package com.sunflow.sunbakamod.item.experience_book;

import java.util.List;

import javax.annotation.Nullable;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.item.BaseItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExperienceBookItem extends BaseItem {

	public static final int MAXLEVEL = 512;

	public ExperienceBookItem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack book = player.getHeldItem(hand);
		if (world.isRemote) openXpBook(player, book, hand);
		player.addStat(Stats.ITEM_USED.get(this));
		return ActionResult.func_226248_a_(book);
	}

	@OnlyIn(Dist.CLIENT)
	private void openXpBook(PlayerEntity player, ItemStack book, Hand hand) {
		SunBakaMod.proxy.getMinecraft().displayGuiScreen(new ExperienceBookScreen(player, book, hand));
	}

	public static int getXpStored(ItemStack book) {
		CompoundNBT tag = book.getOrCreateTag();
		return tag.getInt("xp");
	}

	public static double getLevelStored(ItemStack book) {
		CompoundNBT tag = book.getOrCreateTag();
		int totalXp = tag.getInt("xp");
		return getLevelfromXp(totalXp);
	}

	public static double getLevelfromXp(int totalXp) {
		double level;
		if (totalXp < 394) {
			level = Math.sqrt(totalXp + 9) - 3;
		} else if (totalXp < 1628) {
			level = Math.sqrt(0.4 * totalXp - 78.39) + 8.1;
		} else {
			level = Math.sqrt(0.22222182 * totalXp - 167.33) + 18.056;
		}
		return (float) level;
	}

	public static int getXpforLevelUp(int currentLevel) {
		int totalXp = 0;
		if (currentLevel < 16) {
			totalXp = 2 * currentLevel + 7;
		} else if (currentLevel < 31) {
			totalXp = 5 * currentLevel - 38;
		} else {
			totalXp = 9 * currentLevel - 158;
		}
		return totalXp;
	}

	public static int getXpfromLevel(int level) {
		int totalXp = 0;
		if (level < 17) {
			totalXp = level * level + 6 * level;
		} else if (level < 32) {
			totalXp = (int) (2.5 * level * level - 40.5 * level + 360);
		} else {
			totalXp = (int) (4.5 * level * level - 162.5 * level + 2220);
		}
		return totalXp;
	}

	public static int storeXp(ItemStack book, int maxAmount) {
		CompoundNBT tag = book.getOrCreateTag();
		int stored = tag.getInt("xp");
		int amount = Math.min(maxAmount, getXpfromLevel(MAXLEVEL) - stored);
		tag.putInt("xp", stored + amount);
		return amount;
	}

	public static int takeXp(ItemStack book, int maxAmount) {
		CompoundNBT tag = book.getOrCreateTag();
		int stored = tag.getInt("xp");
		int amount = Math.min(stored, maxAmount);
		tag.putInt("xp", stored - amount);
		return amount;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) { return true; }

	@Override
	public double getDurabilityForDisplay(ItemStack stack) { return (MAXLEVEL - getLevelStored(stack)) / MAXLEVEL; }

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(String.format("%.2f", getLevelStored(stack)) + " / " + MAXLEVEL));
	}

	public static boolean isNBTValid(@Nullable CompoundNBT nbt) {
		if (nbt == null) return false;
		if (!nbt.contains("xp")) return false;
		return true;
	}
}
