package com.sunflow.sunbakamod.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class BasePacket {

	protected BasePacket(PacketBuffer buf) {}

	protected BasePacket() {}

	public void encode(PacketBuffer buf) {}

	public final void onMessage(Supplier<NetworkEvent.Context> ctx) { ctx.get().enqueueWork(() -> ctx.get().setPacketHandled(action(ctx.get()))); }

	protected abstract boolean action(NetworkEvent.Context ctx);
}
