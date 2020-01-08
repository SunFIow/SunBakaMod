package com.sunflow.sunbakamod.setup;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.sunflow.sunbakamod.SunBakaMod;
import com.sunflow.sunbakamod.command.BaseCommand;
import com.sunflow.sunbakamod.command.RedWorldCommand;
import com.sunflow.sunbakamod.command.SlimeChunkCommand;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {
	public static final List<BaseCommand> COMMANDS = new ArrayList<>();

	public static final BaseCommand SLIME_CHUNK_COMMAND = new SlimeChunkCommand();
	public static final BaseCommand RED_WORLD_COMMAND = new RedWorldCommand();

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal(SunBakaMod.MODID);
		for (BaseCommand command : COMMANDS) {
			builder.then(command.getBuilder());
		}
		LiteralCommandNode<CommandSource> cmdTut = dispatcher.register(builder);
		dispatcher.register(Commands.literal("tut").redirect(cmdTut));
	}
}