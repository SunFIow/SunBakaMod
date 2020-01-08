package com.sunflow.sunbakamod.setup;

import java.util.ArrayList;
import java.util.List;

import com.sunflow.sunbakamod.item.bag.BagScreen;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModScreens {
	@SuppressWarnings("rawtypes")
	public static final List<ScreenEntry> SCREENS = new ArrayList<>();

	static {
		createScreen(ModItems.CONTAINER_BAG, BagScreen::new);
	}

	public static <M extends Container, U extends Screen & IHasContainer<M>> void createScreen(ContainerType<? extends M> type, ScreenManager.IScreenFactory<M, U> screenFactory) {
		SCREENS.add(new ScreenEntry<M, U>(type, screenFactory));
	}

	public static class ScreenEntry<M extends Container, U extends Screen & IHasContainer<M>> {
		public ContainerType<? extends M> type;
		public IScreenFactory<M, U> factory;

		public ScreenEntry(ContainerType<? extends M> type, IScreenFactory<M, U> screenFactory) {
			this.type = type;
			this.factory = screenFactory;
		}
	}
}
