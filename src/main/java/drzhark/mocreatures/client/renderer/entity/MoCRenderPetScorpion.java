package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelPetScorpion;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderPetScorpion extends MoCRenderMoC<MoCEntityPetScorpion, MoCModelPetScorpion> {

    public MoCRenderPetScorpion(EntityRendererManager manager, MoCModelPetScorpion modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityPetScorpion entityscorpion, MatrixStack stack, float f) {
        boolean sitting = entityscorpion.getIsSitting();
        if (entityscorpion.climbing()) {
            rotateAnimal(entityscorpion);
        }
        if (sitting) {
            float factorY = 0.4F * (float) (entityscorpion.getEdad() / 100F);
            stack.translate(0F, factorY, 0F);
        }
        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion, stack);
            if (entityscorpion.getRidingEntity() != null) {
                upsideDown(entityscorpion, stack);
            }
        } else {
            adjustHeight(entityscorpion);
        }
    }

    protected void upsideDown(MoCEntityPetScorpion entityscorpion, MatrixStack stack) {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F); //TODO: GL call
        stack.translate(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(MoCEntityPetScorpion entityscorpion) {
        GL11.glTranslatef(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityPetScorpion entityscorpion) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityPetScorpion entityscorpion, MatrixStack stack) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getEdad() * 0.01F;
        }
        stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityPetScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
