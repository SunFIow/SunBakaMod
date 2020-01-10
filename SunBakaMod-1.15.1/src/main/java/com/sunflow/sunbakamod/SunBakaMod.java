package com.sunflow.sunbakamod;

import com.sunflow.sunbakamod.block.chunkloader.ChunkLoaderTile;
import com.sunflow.sunbakamod.network.Networking;
import com.sunflow.sunbakamod.setup.ModCommands;
import com.sunflow.sunbakamod.setup.ModItems;
import com.sunflow.sunbakamod.setup.proxy.ClientProxy;
import com.sunflow.sunbakamod.setup.proxy.CommonProxy;
import com.sunflow.sunbakamod.setup.proxy.ServerProxy;
import com.sunflow.sunbakamod.util.Log;
import com.sunflow.sunbakamod.util.MyWorldData;
import com.sunflow.sunbakamod.util.MyWorldData.ChunkLoader;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.versions.mcp.MCPVersion;

@Mod(SunBakaMod.MODID)
public class SunBakaMod {
	public static final String MODID = "sunbakamod";
	public static final String NAME = "Sun Baka Mod";
	public static final String VERSION = "2.2.0";
	public static final String ACCEPTED_VERSION = "[1.15.1,)";

	public static SunBakaMod INSTANCE;

	public static SunBakaMod getInstance() {
		return INSTANCE;
	}

	public static CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	public static final ItemGroup itemGroup = new ItemGroup("sunbaka") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.ITEM_ENDERPACK);
		}
	};

	public static MyWorldData data;
//	public static boolean showOverlay = true;
	public static BooleanValue CONFIG_SHOW_OVERLAY;

	public SunBakaMod() {
		Log.info("{} loading, version {}, accepted for {}, for MC {} with MCP {}", NAME, VERSION, ACCEPTED_VERSION, MCPVersion.getMCVersion(), MCPVersion.getMCPVersion());
		Log.info("Loading Network data for {} net version: {}", NAME, Networking.init());

		INSTANCE = this;

		proxy.preSetup();

	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		Log.info("-setup-");

		proxy.setup();
	}

	@SubscribeEvent
	public static void serverStarting(FMLServerStartingEvent event) {
		Log.info("-serverStarting-");

		ModCommands.register(event.getCommandDispatcher());
		data = event.getServer().getWorld(DimensionType.OVERWORLD).getSavedData().getOrCreate(MyWorldData::new, MyWorldData.ID_ENDERPACK);

		loadChunkLoaders(event.getServer());
	}

	private static void loadChunkLoaders(MinecraftServer server) {
		Log.warn("data_cl: " + data.chunkloader);
		for (int i = data.chunkloader.size() - 1; i >= 0; i--) {
			ChunkLoader cl = data.chunkloader.get(i);
			Log.warn("{} , {}", cl.getType(), cl.powered);
			ServerWorld world = server.getWorld(cl.getType());
			if (world.getTileEntity(cl.pos) instanceof ChunkLoaderTile) {
				if (cl.powered) ChunkLoaderTile.forceChunk(world, cl.pos, cl.chunkPos, cl.powered);
			} else SunBakaMod.data.removeChunkLoader(cl);
		}
	}
}