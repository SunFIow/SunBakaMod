package com.sunflow.sunbakamod.util.handlers;

import org.lwjgl.opengl.GL11;

import com.sunflow.sunbakamod.SunBakaMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockHighlightHandler {

	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event) {
		PlayerEntity player = SunBakaMod.proxy.getClientPlayer();

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

		Vec3d pos = player.getPositionVector();

		GL11.glTranslated(-pos.x, -pos.y, -pos.z);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST); // draw the line on top of the geometry

		// player.sendMessage(new
		// StringTextComponent(""+player.getRotationYawHead()%180));

		Vec3d posA = new Vec3d(pos.x + 1, pos.y - 2, pos.z - 1);
		Vec3d posB = new Vec3d(pos.x + 1, pos.y - 2, pos.z + 1);

		// drawLine(posA, posB);
		drawBoxHighlight(event.getTarget().getHitVec());

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private void drawLine(Vec3d blockA, Vec3d blockB) {
		// GL11.glColor4f(1F, 0F, 1F, 0F); // change color an set alpha
		GL11.glColor3f(1F, 0F, 1F);

		GL11.glBegin(GL11.GL_QUAD_STRIP);
		// GL11.glBegin(GL11.GL_LINE_STRIP);

		if (Math.abs(blockA.x - blockB.x) > Math.abs(blockA.z - blockB.z)) {
			GL11.glVertex3d(blockA.x, blockA.y, blockA.z);
			GL11.glVertex3d(blockB.x, blockB.y, blockB.z);
			GL11.glVertex3d(blockA.x, blockA.y, blockA.z + 0.1);
			GL11.glVertex3d(blockB.x, blockB.y, blockB.z + 0.1);
		} else {
			GL11.glVertex3d(blockA.x, blockA.y, blockA.z);
			GL11.glVertex3d(blockB.x, blockB.y, blockB.z);
			GL11.glVertex3d(blockA.x + 0.1, blockA.y, blockA.z);
			GL11.glVertex3d(blockB.x + 0.1, blockB.y, blockB.z);
		}

		GL11.glEnd();
	}

	private void drawBoxHighlight(Vec3d block) {
		block = new Vec3d(Math.round(block.x + 0.5), Math.round(block.y - 1) - 0.6, Math.round(block.z + 0.5));
		GL11.glColor3f(1F, 0F, 0F);

		GL11.glBegin(GL11.GL_QUAD_STRIP);

		GL11.glVertex3d(block.x, block.y, block.z);
		GL11.glVertex3d(block.x, block.y, block.z - 1);
		GL11.glVertex3d(block.x - 1, block.y, block.z);
		GL11.glVertex3d(block.x - 1, block.y, block.z - 1);

		GL11.glEnd();
	}
}
