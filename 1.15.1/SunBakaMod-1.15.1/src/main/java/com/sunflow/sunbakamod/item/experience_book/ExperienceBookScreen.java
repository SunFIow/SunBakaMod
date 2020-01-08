package com.sunflow.sunbakamod.item.experience_book;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.network.Networking;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExperienceBookScreen extends Screen {
	private static final ResourceLocation GUI = new ResourceLocation(SunBakaMod.MODID, "textures/gui/experience_book_gui.png");

	private PlayerEntity player;
	private ItemStack book;
	private Hand hand;

	public ExperienceBookScreen(PlayerEntity player, ItemStack book, Hand hand) {
		super(new TranslationTextComponent("item.sunbakamod.experience_book"));
		this.player = player;
		this.book = book;
		this.hand = hand;
	}

	@Override
	protected void init() {
		for (int i = 0; i < 3; i++) {
			int x = this.width / 2 + 2;
			int y = this.height / 2 - 20 + 30 * i;
			int width = 114;
			int height = 20;
			String maxLevel = i == 0 ? "1" : i == 1 ? "5" : "All";
			this.addButton(new Button(x, y, width, height, I18n.format("Store " + maxLevel + " Level"), (button) -> {
				int maxAmount = maxLevel == "1" ? ExperienceBookItem.getXpforLevelUp(Math.max(player.experienceLevel, 1) - 1)
						: maxLevel == "5" ? ExperienceBookItem.getXpfromLevel(Math.max(player.experienceLevel, 5)) - ExperienceBookItem.getXpfromLevel(Math.max(player.experienceLevel, 5) - 5)
								: player.experienceTotal;
				int amount = Math.min(player.experienceTotal, Math.abs(maxAmount));
				int endXp = ExperienceBookItem.storeXp(book, amount);
				player.giveExperiencePoints(-endXp);
				sendBookToServer();
			}));

			x = this.width / 2 - 116;
			this.addButton(new Button(x, y, width, height, I18n.format("Take " + maxLevel + " Level"), (button) -> {
				int maxAmount = maxLevel == "1" ? ExperienceBookItem.getXpforLevelUp(player.experienceLevel)
						: maxLevel == "5" ? ExperienceBookItem.getXpfromLevel(player.experienceLevel + 5) - ExperienceBookItem.getXpfromLevel(player.experienceLevel)
								: ExperienceBookItem.getXpStored(book);
				int amount = ExperienceBookItem.takeXp(book, maxAmount);
				player.giveExperiencePoints(amount);
				sendBookToServer();
			}));
		}
	}

	private void sendBookToServer() {
		Networking.sendToServer(new XPBookPacket(player.experienceTotal, book, hand));
	}

	@Override
	public void renderBackground() {
		super.renderBackground();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int x = (this.width - 248) / 2;
		int y = (this.height - 166) / 2;
		this.blit(x, y, 0, 0, 248, 166);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();

		int x = (this.width - 248) / 2;
		int y = (this.height - 166) / 2;

		this.font.drawString(this.title.getFormattedText(), x + 10, y + 8, 2039583);
		this.font.drawString((int) ExperienceBookItem.getLevelStored(book) + " Levels stored", x + 10, y + 23, 2039583);
		this.font.drawString("You have " + String.format("%.2f", player.experienceLevel + player.experience) + " Level", x + 10, y + 40, 2039583);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);

		int k = getProgressScaled(ExperienceBookItem.getLevelStored(book) - (int) (ExperienceBookItem.getLevelStored(book)), 1, 75);
		this.blit(x + 120 + k, y + 18, 0 + k, 166, 76 - k, 16);

		super.render(mouseX, mouseY, partialTicks);
	}

	protected int getProgressScaled(double current, double max, int pixels) {
		return (int) (max != 0 && current != 0 ? current * pixels / max : 0);
	}
}