package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBird;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBird<T extends MoCEntityBird, M extends MoCModelBird<T>> extends MoCRenderMoC<T, M> {

    @Override
    public ResourceLocation getEntityTexture(T par1Entity) {
        return ((MoCEntityBird) par1Entity).getTexture();
    }

    public MoCRenderBird(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    @Override
    protected float handleRotationFloat(MoCEntityBird entitybird, float f) {
        float f1 = entitybird.winge + ((entitybird.wingb - entitybird.winge) * f);
        float f2 = entitybird.wingd + ((entitybird.wingc - entitybird.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(MoCEntityBird entitybird, MatrixStack matrixStack, float f) {
        if (!entitybird.world.isRemote && (entitybird.getRidingEntity() != null)) {
            matrixStack.translate(0.0F, 1.3F, 0.0F);
        }
    }
}
