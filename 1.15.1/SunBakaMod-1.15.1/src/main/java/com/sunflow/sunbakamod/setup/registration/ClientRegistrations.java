package com.sunflow.sunbakamod.setup.registration;

import com.sunflow.sunbakamod.setup.ModScreens;
import com.sunflow.sunbakamod.util.Log;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//@Mod.EventBusSubscriber(modid = TutorialMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientRegistrations {

	@SuppressWarnings("unchecked")
	public static <M extends Container, U extends Screen & IHasContainer<M>> void registerScreens() {
		Log.debug("-RegisterScreens-");

		ModScreens.SCREENS.forEach((screen) -> ScreenManager.registerFactory(screen.type, screen.factory));
	}
}
