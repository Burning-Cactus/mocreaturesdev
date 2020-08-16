package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderScorpion extends MoCRenderMoC<MoCEntityScorpion, MoCModelScorpion<MoCEntityScorpion>> {

    public MoCRenderScorpion(EntityRendererManager manager, MoCModelScorpion modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityScorpion entityscorpion, MatrixStack stack, float f) {
        if (entityscorpion.climbing()) {
            rotateAnimal(entityscorpion, stack);
        }

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion, stack);
            if (entityscorpion.getIsPicked()) {
                upsideDown(entityscorpion, stack);
            }
        } else {
            adjustHeight(entityscorpion, stack);
        }
    }

    protected void upsideDown(MoCEntityScorpion entityscorpion, MatrixStack stack) {
        stack.rotate(Vector3f.XN.rotationDegrees(-90F));
        stack.translate(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(MoCEntityScorpion entityscorpion, MatrixStack stack) {
        stack.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityScorpion entityscorpion, MatrixStack stack) {
        stack.rotate(Vector3f.XN.rotationDegrees(90F));
    }

    protected void stretch(MoCEntityScorpion entityscorpion, MatrixStack stack) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getEdad() * 0.01F;
        }
        stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
