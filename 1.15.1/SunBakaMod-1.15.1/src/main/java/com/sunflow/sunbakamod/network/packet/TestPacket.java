package com.sunflow.sunbakamod.network.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TestPacket extends BasePacket {

	public TestPacket(PacketBuffer buf) {}

	public TestPacket() {}

	@Override
	protected boolean action(NetworkEvent.Context ctx) {
		return true;
	}
}
