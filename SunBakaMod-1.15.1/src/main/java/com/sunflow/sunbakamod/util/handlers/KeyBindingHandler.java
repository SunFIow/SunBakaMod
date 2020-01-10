package com.sunflow.sunbakamod.util.handlers;

import java.util.ArrayList;

import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.util.Log;
import com.sunflow.sunbakamod.util.handlers.enums.KeyBindings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyBindingHandler {

	public static void setup() {
		Log.info("-RegisterKeybindings-");
		KeyBindings.register();
	}

	@SubscribeEvent
	public static void handleKeyBindingZoom(FOVUpdateEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity)) return;
		ArrayList<KeyBindings> keyList = KeyBindings.getDownKeys();
		if (!keyList.isEmpty()) for (KeyBindings key : keyList) if (key == KeyBindings.ZOOM) event.setNewfov(event.getNewfov() / 4);

	}

	@SubscribeEvent
	public static void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
		if (KeyBindings.OVERLAY.isPressed()) {
			SunBakaMod.CONFIG_SHOW_OVERLAY.set(!SunBakaMod.CONFIG_SHOW_OVERLAY.get());
			SunBakaMod.CONFIG_SHOW_OVERLAY.save();
		}
	}
}
