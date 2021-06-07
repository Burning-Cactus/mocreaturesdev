package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat> extends LivingRenderer<T, MoCModelRat<T>> {

    public MoCRenderRat(EntityRendererManager manager, MoCModelRat modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected float getBob(T entityrat, float f) {
        return entityrat.tickCount + f;
    }

    @Override
    protected void scale(T entityrat, MatrixStack stack, float f) {
        if (entityrat.climbing()) {
            rotateAnimal(entityrat, stack);
        }
        stretch(entityrat, stack);
    }

    protected void rotateAnimal(T entityrat, MatrixStack stack) {
        stack.mulPose(Vector3f.XN.rotationDegrees(90F));
    }

    protected void stretch(T entityrat, MatrixStack stack) {
        float f = 0.8F;
        stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entityrat) {
        return entityrat.getTexture();
    }
}
