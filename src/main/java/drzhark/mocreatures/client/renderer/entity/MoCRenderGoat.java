package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGoat extends LivingRenderer<MoCEntityGoat, MoCModelGoat> {

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGoat entitygoat) {
        return entitygoat.getTexture();
    }

    public MoCRenderGoat(EntityRendererManager manager, MoCModelGoat modelbase, float f) {
        super(manager, modelbase, f);
        this.tempGoat = modelbase;
    }

    @Override
    protected void preRenderCallback(MoCEntityGoat entitygoat, MatrixStack stack, float f) {
        stack.translate(0.0F, this.depth, 0.0F);
        stretch(entitygoat, stack);

    }

    @Override
    public void render(MoCEntityGoat entitygoat, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.tempGoat.typeInt = entitygoat.getSubType();
        this.tempGoat.edad = entitygoat.getEdad() * 0.01F;
        this.tempGoat.bleat = entitygoat.getBleating();
        this.tempGoat.attacking = entitygoat.getAttacking();
        this.tempGoat.legMov = entitygoat.legMovement();
        this.tempGoat.earMov = entitygoat.earMovement();
        this.tempGoat.tailMov = entitygoat.tailMovement();
        this.tempGoat.eatMov = entitygoat.mouthMovement();

        super.render(entitygoat, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitygoat.getPetName()).isEmpty();
//        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
//        if (entitygoat.renderName()) {
//            float f2 = 1.6F;
//            float f3 = 0.01666667F * f2;
//            float f4 = entitygoat.getDistance(this.renderManager.pointedEntity);
//            if (f4 < 16F) {
//                String s = "";
//                s = s + entitygoat.getPetName();
//                float f5 = 0.1F;
//                FontRenderer fontrenderer = getFontRendererFromRenderManager();
//                matrixStackIn.push();
////                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f5, (float) d2);
////                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
//                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-this.renderManager.pointedEntity.getEyeHeight()));
//                matrixStackIn.scale(-f3, -f3, f3);
////                GL11.glDisable(2896 /* GL_LIGHTING */);
//                Tessellator tessellator = Tessellator.getInstance();
//                byte byte0 = (byte) (-15 + (-40 * entitygoat.getEdad() * 0.01F));
//                if (flag1) {
////                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
//                    if (!flag) {
//                        byte0 += 8;
//                    }
//                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
//                    // might break SSP
//                    float f6 = entitygoat.getHealth();
//                    // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
//                    float f7 = entitygoat.getMaxHealth();
//                    float f8 = f6 / f7;
//                    float f9 = 40F * f8;
//                    tessellator.getBuffer().pos(-20F + f9, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(-20F + f9, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(f9 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
//                    tessellator.getBuffer().pos(f9 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
//                    tessellator.draw();
////                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
//                }
//                if (flag) {
////                    GL11.glDepthMask(false);
////                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
////                    GL11.glEnable(3042 /* GL_BLEND */);
////                    GL11.glBlendFunc(770, 771);
////                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
//                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
//                    int i = fontrenderer.getStringWidth(s) / 2;
//                    tessellator.getBuffer().pos(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//                    tessellator.getBuffer().pos(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//                    tessellator.getBuffer().pos(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//                    tessellator.getBuffer().pos(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//                    tessellator.draw();
////                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
//                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
////                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
////                    GL11.glDepthMask(true);
//                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
////                    GL11.glDisable(3042 /* GL_BLEND */);
////                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//                }
////                GL11.glEnable(2896 /* GL_LIGHTING */);
//                matrixStackIn.pop();
//            }
//        }
    }

    protected void stretch(MoCEntityGoat entitygoat, MatrixStack stack) {
        stack.scale(entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F);
    }

    private final MoCModelGoat tempGoat;
    float depth = 0F;
}
