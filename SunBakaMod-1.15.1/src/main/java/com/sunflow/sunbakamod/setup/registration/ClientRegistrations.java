package com.sunflow.sunbakamod.setup.registration;

import com.sunflow.sunbakamod.setup.ModScreens;
import com.sunflow.sunbakamod.util.Log;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//@Mod.EventBusSubscriber(modid = TutorialMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientRegistrations {

	@SuppressWarnings("unchecked")
	public static void registerScreens() {
		Log.debug("-RegisterScreens-");

		ModScreens.SCREENS.forEach((screenentry) -> ScreenManager.registerFactory(screenentry.type, screenentry.factory));
	}
}
