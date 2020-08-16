package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBunny;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBunny<T extends MoCEntityBunny, M extends MoCModelBunny<T>> extends MoCRenderMoC<T, M> {

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBunny entitybunny) {
        return ((MoCEntityBunny) entitybunny).getTexture();
    }

    public MoCRenderBunny(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBunny entitybunny, float f) {
        return entitybunny.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entitybunny, MatrixStack matrixStack, float f) {
        if (!entitybunny.getIsAdult()) {
            stretch(entitybunny, matrixStack);
        }
        rotBunny(entitybunny, matrixStack);
        adjustOffsets(matrixStack, entitybunny.getAdjustedXOffset(), entitybunny.getAdjustedYOffset(),entitybunny.getAdjustedZOffset());
    }

    protected void rotBunny(MoCEntityBunny entitybunny, MatrixStack stack) {
        if (entitybunny.isAirBorne && (entitybunny.getRidingEntity() == null)) {
            if (entitybunny.getMotion().y > 0.5D) {
                stack.rotate(Vector3f.XN.rotationDegrees(35F));
            } else if (entitybunny.getMotion().y < -0.5D) {
                stack.rotate(Vector3f.XN.rotationDegrees(-35F));
            } else {
                stack.rotate(Vector3f.XN.rotationDegrees((float) (entitybunny.getMotion().y * 70D)));
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny, MatrixStack stack) {
        float f = entitybunny.getEdad() * 0.01F;
        stack.scale(f, f, f);
    }
}
