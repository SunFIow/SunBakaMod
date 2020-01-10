package com.sunflow.sunbakamod.network.packet;

import com.sunflow.sunbakamod.item.bag.BagContainer;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ScrollPacket extends BasePacket {
	private double mouseX, mouseY, scrollDir;
	private int guiLeft, guiTop;

	public ScrollPacket(double mouseX, double mouseY, double scrollDir, int guiLeft, int guiTop) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.scrollDir = scrollDir;
		this.guiLeft = guiLeft;
		this.guiTop = guiTop;
	}

	public ScrollPacket(PacketBuffer buf) {
		this.mouseX = buf.readDouble();
		this.mouseY = buf.readDouble();
		this.scrollDir = buf.readDouble();
		this.guiLeft = buf.readInt();
		this.guiTop = buf.readInt();
	}

	public static ScrollPacket decode(PacketBuffer buf) {
		return new ScrollPacket(
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readInt(),
				buf.readInt());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeDouble(mouseX);
		buf.writeDouble(mouseY);
		buf.writeDouble(scrollDir);
		buf.writeInt(guiLeft);
		buf.writeInt(guiTop);
	}

	@Override
	protected boolean action(NetworkEvent.Context ctx) {
		if (!(ctx.getSender().openContainer instanceof BagContainer)) return true;
		BagContainer container = (BagContainer) ctx.getSender().openContainer;
		container.mouseScrolled(mouseX, mouseY, scrollDir, guiLeft, guiTop);
		return true;
	}
}
