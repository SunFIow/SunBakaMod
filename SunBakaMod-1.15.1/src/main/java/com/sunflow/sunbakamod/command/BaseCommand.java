package com.sunflow.sunbakamod.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.sunflow.sunbakamod.setup.ModCommands;

import net.minecraft.command.CommandSource;

public abstract class BaseCommand {
	public BaseCommand() {
		ModCommands.COMMANDS.add(this);
	}

	public abstract ArgumentBuilder<CommandSource, ?> getBuilder();
}
