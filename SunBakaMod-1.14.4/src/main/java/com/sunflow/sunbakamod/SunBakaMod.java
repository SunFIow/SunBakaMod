package com.sunflow.sunbakamod;

import com.sunflow.sunbakamod.items.ModItems;
import com.sunflow.sunbakamod.proxy.ClientProxy;
import com.sunflow.sunbakamod.proxy.IProxy;
import com.sunflow.sunbakamod.proxy.ServerProxy;
import com.sunflow.sunbakamod.util.MyWorldData;
import com.sunflow.sunbakamod.util.Reference;
import com.sunflow.sunbakamod.util.handlers.BlockHighlightHandler;
import com.sunflow.sunbakamod.util.handlers.KeyBindingHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sunbakamod")
public class SunBakaMod {

	public static SunBakaMod instance;

	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	public static final ItemGroup itemGroup = new ItemGroup("sunbaka") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.ITEM_ENDERPACK);
		}
	};

	public static MyWorldData data;

	public SunBakaMod() {
		instance = this;

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void preInit(FMLCommonSetupEvent evt) {
		Reference.NEKOLOGGER.info("-preInit-");

		proxy.init();
	}

	public void clientRegistries(FMLClientSetupEvent evt) {
		Reference.NEKOLOGGER.info("-ClientRegistries-");

		MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
		MinecraftForge.EVENT_BUS.register(new BlockHighlightHandler());
	}

	@SubscribeEvent
	public void serverRegistries(FMLServerStartingEvent evt) {
		Reference.NEKOLOGGER.info("-ServerRegistries-");

		data = evt.getServer().getWorld(DimensionType.OVERWORLD).getSavedData().getOrCreate(MyWorldData::new, MyWorldData.ID);
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity dyingPlayer = (PlayerEntity) event.getEntity();
			dyingPlayer.sendMessage(new StringTextComponent("You died at: " + dyingPlayer.getPosition()));
		}
	}
}