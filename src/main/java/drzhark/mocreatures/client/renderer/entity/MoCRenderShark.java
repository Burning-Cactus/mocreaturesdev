package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelShark;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderShark extends LivingRenderer<MoCEntityShark, MoCModelShark> {

    public MoCRenderShark(EntityRendererManager manager, MoCModelShark modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    public void render(MoCEntityShark entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        boolean flag = MoCConfig.CLIENT_CONFIG.displayPetName.get() && !(entityIn.getPetName().isEmpty());
        boolean flag1 = MoCConfig.CLIENT_CONFIG.displayPetHealth.get();
        if (entityIn.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityIn.distanceTo(this.entityRenderDispatcher.crosshairPickEntity);
            if (f4 < 16F) {
                String s = "";
                s = (new StringBuilder()).append(s).append(entityIn.getPetName()).toString();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFont();
                matrixStackIn.pushPose();
//                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f5, (float) d2);
//                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-this.entityRenderDispatcher.crosshairPickEntity.getEyeHeight()));
                matrixStackIn.scale(-f3, -f3, f3);
//                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.getInstance();
                byte byte0 = -50;
                if (flag1) {
//                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag) {
                        byte0 += 8;
                    }
                    tessellator.getBuilder().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // might break SSP
                    float f6 = entityIn.getHealth();
                    float f7 = entityIn.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    tessellator.getBuilder().vertex(-20F + f9, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20F + f9, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(f9 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(f9 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.end();
//                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag) {
//                    GL11.glDepthMask(false);
//                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
//                    GL11.glEnable(3042 /* GL_BLEND */);
//                    GL11.glBlendFunc(770, 771);
//                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.getBuilder().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.width(s) / 2;
                    tessellator.getBuilder().vertex(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuilder().vertex(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuilder().vertex(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuilder().vertex(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.end();
//                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
//                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
//                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
//                    GL11.glDepthMask(true);
//                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
//                    GL11.glDisable(3042 /* GL_BLEND */);
//                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
//                GL11.glEnable(2896 /* GL_LIGHTING */);
                matrixStackIn.popPose();
            }
        }

        if (entityIn.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityIn.distanceTo(this.entityRenderDispatcher.crosshairPickEntity);
            String s = "";
            s = (new StringBuilder()).append(s).append(entityIn.getPetName()).toString();
            if ((f4 < 12F) && (s.length() > 0)) {
                FontRenderer fontrenderer = getFont();
                matrixStackIn.pushPose();
//                matrixStackIn.translate((float) d + 0.0F, (float) d1 + 0.2F, (float) d2);
//                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-this.entityRenderDispatcher.crosshairPickEntity.getEyeHeight()));
                matrixStackIn.scale(-f3, -f3, f3);
//                GL11.glDisable(2896 /* GL_LIGHTING */);
//                GL11.glDepthMask(false);
//                GL11.glDisable(2929 /* GL_DEPTH_TEST */);
//                GL11.glEnable(3042 /* GL_BLEND */);
//                GL11.glBlendFunc(770, 771);
                Tessellator tessellator = Tessellator.getInstance();
                byte byte0 = -50;
//                GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                tessellator.getBuilder().begin(7, DefaultVertexFormats.POSITION_COLOR);
                int i = fontrenderer.width(s) / 2;
                tessellator.getBuilder().vertex(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                tessellator.getBuilder().vertex(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                tessellator.getBuilder().vertex(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                tessellator.getBuilder().vertex(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                if (MoCConfig.CLIENT_CONFIG.displayPetHealth.get()) {
                    float f5 = entityIn.getHealth();
                    float f6 = entityIn.getMaxHealth();
                    float f7 = f5 / f6;
                    float f8 = 40F * f7;
                    tessellator.getBuilder().vertex(-20F + f8, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20F + f8, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(f8 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuilder().vertex(f8 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                }
                tessellator.end();
//                GL11.glEnable(3553 /* GL_TEXTURE_2D */);
//                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
//                GL11.glEnable(2929 /* GL_DEPTH_TEST */);
//                GL11.glDepthMask(true);
//                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
//                GL11.glEnable(2896 /* GL_LIGHTING */);
//                GL11.glDisable(3042 /* GL_BLEND */);
//                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                matrixStackIn.popPose();
            }
        }
    }

    //    @Override
    protected float handleRotationFloat(MoCEntityShark entityshark, MatrixStack stack, float f) {
        stretch(entityshark, stack);
        return entityshark.tickCount + f;
    }

    protected void stretch(MoCEntityShark entityshark, MatrixStack stack) {
        stack.scale(entityshark.getEdad() * 0.01F, entityshark.getEdad() * 0.01F, entityshark.getEdad() * 0.01F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityShark entityshark) {
        return entityshark.getTexture();
    }
}
