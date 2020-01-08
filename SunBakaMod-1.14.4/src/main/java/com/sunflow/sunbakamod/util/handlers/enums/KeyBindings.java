package com.sunflow.sunbakamod.util.handlers.enums;

import java.util.ArrayList;

import com.sunflow.sunbakamod.util.Reference;
import com.sunflow.sunbakamod.util.interfaces.IHasCustomKeyHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public enum KeyBindings {
	ZOOM("zoom", 70);

	private final KeyBinding keybinding;

	public boolean hasCustomHandler;
	private IHasCustomKeyHandler keyHandler;

	private KeyBindings(String keyName, int defaultKeyCode) {
		keybinding = new KeyBinding("key." + Reference.MOD_ID + "." + keyName, defaultKeyCode,
				"key.categories." + Reference.MOD_ID);
		this.hasCustomHandler = false;
	}

	private KeyBindings(String keyName, int defaultKeyCode, IHasCustomKeyHandler keyHandler) {
		keybinding = new KeyBinding("key." + Reference.MOD_ID + "." + keyName, defaultKeyCode,
				"key.categories." + Reference.MOD_ID);
		this.hasCustomHandler = true;
		this.keyHandler = keyHandler;
	}

	public KeyBinding getKeybind() {
		return keybinding;
	}

	public boolean isPressed() {
		return keybinding.isPressed();
	}

	public boolean isDown() {
		return keybinding.isKeyDown();
	}

	public boolean hasCustomHandler() {
		return hasCustomHandler;
	}

	public IHasCustomKeyHandler getKeyHandler() {
		return keyHandler;
	}

	public void press() {
		if (hasCustomHandler) {
			keyHandler.press();
		}
	}

	public boolean update(boolean pressed) {
		if (hasCustomHandler) {
			keyHandler.update(pressed);
			return true;
		}
		return false;
	}

	public static ArrayList<KeyBindings> getPressedKey() {
		ArrayList<KeyBindings> pressedKeys = new ArrayList<KeyBindings>();
		for (KeyBindings key : KeyBindings.values()) {
			if (key.isPressed()) {
				pressedKeys.add(key);
			}
		}
		return pressedKeys;
	}

	public static ArrayList<KeyBindings> getDownKey() {
		ArrayList<KeyBindings> downKeys = new ArrayList<KeyBindings>();
		for (KeyBindings key : KeyBindings.values()) {
			if (key.isDown()) {
				downKeys.add(key);
			}
		}
		return downKeys;
	}

	public static void register() {
		for (KeyBindings key : KeyBindings.values()) {
			ClientRegistry.registerKeyBinding(key.getKeybind());
		}
	}
}
