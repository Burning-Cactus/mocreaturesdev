package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBear<T extends MoCEntityBear, M extends MoCModelBear<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderBear(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected void preRenderCallback(T entitybear, MatrixStack matrixStackIn,  float f) {
        stretch(entitybear, matrixStackIn);
        super.preRenderCallback(entitybear, matrixStackIn, f);

    }

    protected void stretch(T entitybear, MatrixStack matrixStack) {
        float sizeFactor = entitybear.getEdad() * 0.01F;
        if (entitybear.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entitybear.getBearSize();
        matrixStack.scale(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBear entitybear) {
        return entitybear.getTexture();
    }
}
