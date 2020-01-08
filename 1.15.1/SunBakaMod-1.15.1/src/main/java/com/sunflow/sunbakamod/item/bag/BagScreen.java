package com.sunflow.sunbakamod.item.bag;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.network.Networking;
import com.sunflow.sunbakamod.network.packet.ScrollPacket;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BagScreen extends ContainerScreen<BagContainer> {

	private static final ResourceLocation GUI = new ResourceLocation(SunBakaMod.MODID, "textures/gui/bag_large_gui.png");

	public BagScreen(BagContainer container, PlayerInventory inv, ITextComponent name) {
		super(container, inv, name);

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
		int relY = (this.height - this.ySize) / 2;
		this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX > guiLeft && mouseY > guiTop && mouseX < guiLeft + width && mouseY < guiTop + height;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollDir) {
//		container.mouseScrolled(mouseX, mouseY, scrollDir);
		Networking.sendToServer(new ScrollPacket(mouseX, mouseY, scrollDir, guiLeft, guiTop));
		return super.mouseScrolled(mouseX, mouseY, scrollDir);
	}
}
