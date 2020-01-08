package com.sunflow.sunbakamod.util.handlers;

import com.sunflow.sunbakamod.SunBakaMod;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientForgeEventHandlers {
	@SubscribeEvent
	public static void onRenderOverlayText(RenderGameOverlayEvent.Text event) {
		if (!SunBakaMod.proxy.getMinecraft().gameSettings.showDebugInfo && SunBakaMod.showOverlay) {
			BlockPos pos = SunBakaMod.proxy.getClientPlayer().getPosition();
			event.getLeft().add(0, String.format("X: %s, Y: %s, Z: %s", pos.getX(), pos.getY(), pos.getZ()));
			event.getLeft().add(0, String.format("%sfps", SunBakaMod.proxy.getMinecraft().debug.split("fps")[0]));
		}
	}
}
