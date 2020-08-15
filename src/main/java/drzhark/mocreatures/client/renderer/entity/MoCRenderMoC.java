package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

/**
 * Base class for rendering most of the MoC entities. This is how tamed mobs will show their names and health.
 */
public class MoCRenderMoC<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {

    public MoCRenderMoC(EntityRendererManager manager, M modelBase, float f) {
        super(manager, modelBase, f);
    }

    @Override //TODO: Rewrite this entire method
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        IMoCEntity entityMoC = (IMoCEntity) entityIn;
        boolean flag = false; // MoCConfig.CLIENT_CONFIG.displayPetName.get() && !(entityMoC.getPetName().isEmpty());
        boolean flag1 = false; //MoCConfig.CLIENT_CONFIG.displayPetHealth.get();
        if (entityMoC.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = ((Entity) entityMoC).getDistance(this.renderManager.pointedEntity);
            if (f5 < 16F) {
                String s = "";
                s = (new StringBuilder()).append(s).append(entityMoC.getPetName()).toString();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
//                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f7, (float) d2);
//                matrixStackIn.normal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-this.renderManager.pointedEntity.getEyeHeight()));
                matrixStackIn.scale(-f3, -f3, f3);
//                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator1 = Tessellator.getInstance();
                int yOff = entityMoC.nameYOffset();
                if (flag1) {
//                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag) {
                        yOff += 8;
                    }
                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // might break SSP
                    float f8 = ((LivingEntity) entityMoC).getHealth();
                    float f9 = ((LivingEntity) entityMoC).getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getBuffer().pos(-20F + f11, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20F + f11, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();
//                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag) {
//                    GL11.glDepthMask(false);
//                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
//                    GL11.glEnable(3042 /* GL_BLEND */);
//                    GL11.glBlendFunc(770, 771);
//                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getBuffer().pos(-i - 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(-i - 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();
//                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, yOff, 0x20ffffff);
//                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
//                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, yOff, -1);
//                    GL11.glDisable(3042 /* GL_BLEND */);
//                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
//                GL11.glEnable(2896 /* GL_LIGHTING */);
                matrixStackIn.pop();
            }
        }
    }



    protected void stretch(MatrixStack stack, IMoCEntity mocreature) {
        float f = mocreature.getSizeFactor();
        if (f != 0) {
//            GL11.glScalef(f, f, f);
        }
    }

    @Override
    protected void preRenderCallback(T entityliving, MatrixStack stack, float f) {
        IMoCEntity mocreature = (IMoCEntity) entityliving;
        super.preRenderCallback(entityliving, stack, f);
        //adjustOffsets is not working well
        //adjustOffsets(mocreature.getAdjustedXOffset(), mocreature.getAdjustedYOffset(), mocreature.getAdjustedZOffset());
        adjustPitch(stack, mocreature);
        adjustRoll(stack, mocreature);
        adjustYaw(stack, mocreature);
        stretch(stack, mocreature);
        //super.preRenderCallback(entityliving, f);
    }

    /**
     * Tilts the creature to the front / back
     *
     * @param mocreature
     */
    protected void adjustPitch(MatrixStack stack, IMoCEntity mocreature) {
        float f = mocreature.pitchRotationOffset();

        if (f != 0) {
//            stack.rotate(f, -1F, 0.0F, 0.0F);
        }
    }

    /**
     * Rolls creature
     *
     * @param mocreature
     */
    protected void adjustRoll(MatrixStack stack, IMoCEntity mocreature) {
        float f = mocreature.rollRotationOffset();

        if (f != 0) {
//            GL11.glRotatef(f, 0F, 0F, -1F);
        }
    }

    protected void adjustYaw(MatrixStack stack, IMoCEntity mocreature) {
        float f = mocreature.yawRotationOffset();
        if (f != 0) {
//            GL11.glRotatef(f, 0.0F, -1.0F, 0.0F);
        }
    }

    /**
     * translates the model
     *
     */
    protected void adjustOffsets(MatrixStack stack, float xOffset, float yOffset, float zOffset) {
        stack.translate(xOffset, yOffset, zOffset);
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return ((IMoCEntity) entity).getTexture();
    }

}
