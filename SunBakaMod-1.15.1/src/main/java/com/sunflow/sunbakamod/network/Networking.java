package com.sunflow.sunbakamod.network;

import java.util.function.Supplier;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.item.experience_book.XPBookPacket;
import com.sunflow.sunbakamod.network.packet.ScrollPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
	private static int ID = 0;

	public static final String SUNBAKAMOD_NETMARKER = SunBakaMod.MODID.toUpperCase();;
	public static final int SUNBAKAMOD_NETVERSION = 1;
	public static final String NETVERSION = SUNBAKAMOD_NETMARKER + ":" + SUNBAKAMOD_NETVERSION;

	public static final ResourceLocation SUNBAKAMOD_MAIN_RESOURCE = new ResourceLocation(SunBakaMod.MODID, "main");
	public static final SimpleChannel SUNBAKAMOD_CHANNEL = getTutorialModChannel();

	private Networking() {}

	private static int nextID() { return ID++; }

	public static String init() { return NETVERSION; }

	public static SimpleChannel getTutorialModChannel() {
		SimpleChannel channel = NetworkRegistry.ChannelBuilder
				.named(SUNBAKAMOD_MAIN_RESOURCE)
				.networkProtocolVersion(() -> NETVERSION)
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();

//		channel.messageBuilder(TestPacket.class, nextID())
//				.encoder(TestPacket::encode)
//				.decoder(TestPacket::new)
//				.consumer(TestPacket::onMessage)
//				.add();

		channel.messageBuilder(XPBookPacket.class, nextID())
				.encoder(XPBookPacket::encode)
				.decoder(XPBookPacket::new)
				.consumer(XPBookPacket::onMessage)
				.add();

		channel.messageBuilder(ScrollPacket.class, nextID())
				.encoder(ScrollPacket::encode)
				.decoder(ScrollPacket::new)
				.consumer(ScrollPacket::onMessage)
				.add();

		channel.messageBuilder(ScrollPacket.class, nextID())
				.encoder(ScrollPacket::encode)
				.decoder(ScrollPacket::new)
				.consumer(ScrollPacket::onMessage)
				.add();

		channel.messageBuilder(ScrollPacket.class, nextID())
				.encoder(ScrollPacket::encode)
				.decoder(ScrollPacket::decode)
				.consumer(ScrollPacket::onMessage)
				.add();
		return channel;
	}

	// Sending to Server
	public static <MSG> void sendToServer(MSG msg) {
		SUNBAKAMOD_CHANNEL.sendToServer(msg);
	}

	// Sending to one player
	public static <MSG> void sendToPlayer(Supplier<ServerPlayerEntity> player, MSG msg) {
		SUNBAKAMOD_CHANNEL.send(PacketDistributor.PLAYER.with(player), msg);
	}

	// Send to all players tracking this chunk
	public static <MSG> void sendToChunk(Supplier<Chunk> chunk, MSG msg) {
		SUNBAKAMOD_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(chunk), msg);
	}

	// Sending to all connected players
	public static <MSG> void sendToConnected(MSG msg) {
		SUNBAKAMOD_CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
	}

	public static <MSG> void sendTo(MSG packet, NetworkManager manager, NetworkDirection direction) {
		SUNBAKAMOD_CHANNEL.sendTo(packet, manager, direction);
	}
}
