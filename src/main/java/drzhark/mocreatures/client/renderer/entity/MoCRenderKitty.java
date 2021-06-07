package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC<MoCEntityKitty, MoCModelKitty> {

    public MoCModelKitty pussy1;

    @Override
    public ResourceLocation getTextureLocation(MoCEntityKitty entitykitty) {
        return entitykitty.getTexture();
    }

    public MoCRenderKitty(EntityRendererManager manager, MoCModelKitty modelkitty, float f) {
        super(manager, modelkitty, f);
        this.pussy1 = modelkitty;
    }

//    @Override
//    public void doRender(MoCEntityKitty entitykitty, double d, double d1, double d2, float f, float f1) {
//        super.doRender(entitykitty, d, d1, d2, f, f1);
//        boolean flag2 = MoCreatures.proxy.getDisplayPetIcons();
//        if (entitykitty.renderName()) {
//            float f2 = 1.6F;
//            float f3 = 0.01666667F * f2;
//            float f4 = entitykitty.getDistance(this.renderManager.renderViewEntity);
//            if (f4 < 12F) {
//                float f5 = 0.2F;
//                if (this.pussy1.isSitting) {
//                    f5 = 0.4F;
//                }
//
//                GL11.glPushMatrix();
//                GL11.glTranslatef((float) d + 0.0F, (float) d1 - f5, (float) d2);
//                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
//                GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
//                GL11.glScalef(-f3, -f3, f3);
//                GL11.glDisable(2896 /* GL_LIGHTING */);
//                Tessellator tessellator = Tessellator.getInstance();
//
//                if (flag2 && entitykitty.getIsEmo()) {
//                    this.bindTexture(entitykitty.getEmoteIcon());
//                    int i = -90;
//                    int k = 32;
//                    int l = (k / 2) * -1;
//                    float f9 = 0.0F;
//                    float f11 = 1.0F / k;
//                    float f12 = 1.0F / k;
//                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
//                    tessellator.getBuffer().pos(l, i + k, f9).tex(0.0D, k * f12).endVertex();
//                    tessellator.getBuffer().pos(l + k, i + k, f9).tex(k * f11, k * f12).endVertex();
//                    tessellator.getBuffer().pos(l + k, i, f9).tex(k * f11, 0.0D).endVertex();
//                    tessellator.getBuffer().pos(l, i, f9).tex(0.0D, 0.0D).endVertex();
//                    tessellator.draw();
//                }
//
//                GL11.glEnable(2896 /* GL_LIGHTING */);
//                GL11.glPopMatrix();
//            }
//        }
//    }
//
//    public void doRender2(MoCEntityKitty entitykitty, double d, double d1, double d2, float f, float f1) {
//        super.doRender(entitykitty, d, d1, d2, f, f1);
//    }

    @Override
    protected float getBob(MoCEntityKitty entitykitty, float f) {
        return entitykitty.tickCount + f;
    }

    protected void onMaBack(MoCEntityKitty entitykitty, MatrixStack stack) {
        stack.mulPose(Vector3f.ZN.rotationDegrees(90F));
        if (!entitykitty.level.isClientSide && (entitykitty.getVehicle() != null)) {
            stack.translate(-1.5F, 0.2F, -0.2F);
        } else {
            stack.translate(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(MoCEntityKitty entityliving, MatrixStack stack) {
        stack.mulPose(Vector3f.ZN.rotationDegrees(90F));
        stack.translate(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void scale(MoCEntityKitty entitykitty, MatrixStack stack, float f) {
        this.pussy1.isSitting = entitykitty.getIsSitting();
        this.pussy1.isSwinging = entitykitty.getIsSwinging();
        this.pussy1.swingProgress = entitykitty.attackAnim;
        this.pussy1.kittystate = entitykitty.getKittyState();
        if (!entitykitty.getIsAdult()) {
            stretch(entitykitty, stack);
        }
        if (entitykitty.getKittyState() == 20) {
            onTheSide(entitykitty, stack);
        }
        if (entitykitty.climbingTree()) {
            rotateAnimal(entitykitty, stack);
        }
        if (entitykitty.upsideDown()) {
            upsideDown(entitykitty, stack);
        }
        if (entitykitty.onMaBack()) {
            onMaBack(entitykitty, stack);
        }
    }

    protected void rotateAnimal(MoCEntityKitty entitykitty, MatrixStack stack) {
        if (entitykitty.hasImpulse) {
            stack.mulPose(Vector3f.XN.rotationDegrees(90F));
        }
    }

    protected void stretch(MoCEntityKitty entitykitty, MatrixStack stack) {
        stack.scale(entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F);
    }

    protected void upsideDown(MoCEntityKitty entitykitty, MatrixStack stack) {
        stack.mulPose(Vector3f.ZN.rotationDegrees(180F));
        stack.translate(-0.35F, 0F, -0.55F);
    }
}
