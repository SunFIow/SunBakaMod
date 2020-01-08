package com.sunflow.sunbakamod.util.handlers;

import java.util.ArrayList;

import com.sunflow.sunbakamod.util.Reference;
import com.sunflow.sunbakamod.util.handlers.enums.KeyBindings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyBindingHandler {

	public KeyBindingHandler() {
		Reference.NEKOLOGGER.info("-RegisterKeybindings-");
		KeyBindings.register();
	}

	@SubscribeEvent
	public void handleKeyInputEvent(KeyInputEvent event) {
		ArrayList<KeyBindings> keyList = KeyBindings.getPressedKey();
		if (!keyList.isEmpty()) {
			for (KeyBindings key : keyList) {
//				if (key.hasCustomHandler()) {
//					key.press();
//				}
			}
		}
	}

	@SubscribeEvent
	public void handleKeyBindingZoom(FOVUpdateEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			ArrayList<KeyBindings> keyList = KeyBindings.getDownKey();
			if (!keyList.isEmpty()) {
				for (KeyBindings key : keyList) {
					if (key.hasCustomHandler()) {
						key.press();
					} else {
						if (key == KeyBindings.ZOOM) {
							event.setNewfov(event.getNewfov() / 4);
						}
					}
				}
			}
		}
	}

}
