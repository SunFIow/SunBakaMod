package com.sunflow.sunbakamod.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {

	public void init();

	public World getClientWorld();

	public PlayerEntity getClientPlayer();
}
