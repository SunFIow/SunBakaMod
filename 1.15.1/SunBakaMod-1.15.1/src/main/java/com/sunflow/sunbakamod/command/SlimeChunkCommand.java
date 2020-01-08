package com.sunflow.sunbakamod.command;

import java.awt.geom.Point2D;
import java.util.Random;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;

public class SlimeChunkCommand extends BaseCommand {

	@Override
	public ArgumentBuilder<CommandSource, ?> getBuilder() {
		return Commands.literal("slimechunk")
				.then(Commands.literal("check")
						.then(Commands.argument("pos", BlockPosArgument.blockPos())
								.executes(ctx -> check(ctx.getSource(), BlockPosArgument.getBlockPos(ctx, "pos"))))
						.executes(ctx -> check(ctx.getSource(), ctx.getSource().asPlayer().getPosition())))
				.then(Commands.literal("find")
						.then(Commands.argument("radius", IntegerArgumentType.integer(0))
								.executes(ctx -> find(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "radius"))))
						.executes(ctx -> find(ctx.getSource(), 1000)));

	}

	private static int check(CommandSource source, BlockPos pos) throws CommandSyntaxException {
		ChunkPos chunk = new ChunkPos(pos);
		long seed = source.getServer().getWorld(DimensionType.OVERWORLD).getSeed();
		boolean isSlimeChunk = checkChunk(seed, chunk.x, chunk.z);
		source.asPlayer().sendMessage(new TranslationTextComponent("This is " + (!isSlimeChunk ? "not " : "") + "a Slime Chunk"));
		return 1;
	}

	private static int find(CommandSource source, int radius) throws CommandSyntaxException {
		ChunkPos chunk = new ChunkPos(source.asPlayer().getPosition());
		long seed = source.getServer().getWorld(DimensionType.OVERWORLD).getSeed();
		ChunkPos closest;
		if (checkChunk(seed, chunk.x, chunk.z)) {
			source.asPlayer().sendMessage(new StringTextComponent("You are in a Slime Chunk"));
			return 1;
		} else {
			closest = new ChunkPos(chunk.x - radius, chunk.z - radius);
			double closestDistance = distance(chunk, closest);
			for (int i = -radius; i < radius; i++) {
				for (int j = -radius; j < radius; j++) {
					ChunkPos current = new ChunkPos(chunk.x + i, chunk.z + j);
					boolean isSlimeChunk = checkChunk(seed, current.x, current.z);
					double currentDistance = distance(chunk, current);
					if (isSlimeChunk && currentDistance < closestDistance) {
						closestDistance = currentDistance;
						closest = current;
					}
				}
			}
		}
		source.asPlayer().sendMessage(new StringTextComponent("Your closest Slime Chunk is at [X:" + ((closest.x << 4) + 8) + ", Z:" + ((closest.z << 4) + 8) + "]"));
		return 1;
	}

	private static double distance(ChunkPos c1, ChunkPos c2) {
		return Point2D.distance(c1.x, c1.z, c2.x, c2.z);
	}

	private static boolean checkChunk(long seed, int xPosition, int zPosition) {
		Random rnd = new Random(
				seed +
						xPosition * xPosition * 0x4c1906 +
						xPosition * 0x5ac0db +
						zPosition * zPosition * 0x4307a7L +
						zPosition * 0x5f24f ^ 0x3ad8025f);
		return rnd.nextInt(10) == 0;
	}
}
