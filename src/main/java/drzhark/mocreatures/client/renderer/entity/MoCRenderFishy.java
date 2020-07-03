package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFishy extends LivingRenderer<MoCEntityFishy, MoCModelFishy<MoCEntityFishy>> {

    public MoCRenderFishy(EntityRendererManager manager, MoCModelFishy modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    public void render(MoCEntityFishy entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityIn.getSubType() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityIn.selectType();
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(MoCEntityFishy entityfishy, MatrixStack stack, float f) {
        stack.translate(0.0F, 0.3F, 0.0F);
        if (!entityfishy.getIsAdult()) {
            stretch(entityfishy, stack);
        }
    }

    @Override
    protected float handleRotationFloat(MoCEntityFishy entityfishy, float f) {
        return entityfishy.ticksExisted + f;
    }

    protected void stretch(MoCEntityFishy entityfishy, MatrixStack stack) {
        stack.scale(entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFishy entityfishy) {
        return entityfishy.getTexture();
    }
}
