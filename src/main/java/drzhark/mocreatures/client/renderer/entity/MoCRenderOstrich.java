package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC<MoCEntityOstrich, MoCModelOstrich<MoCEntityOstrich>> {

    public MoCRenderOstrich(EntityRendererManager manager, MoCModelOstrich modelbase, float f) {
        super(manager, modelbase, 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityOstrich entityostrich) {
        return entityostrich.getTexture();
    }

    protected void adjustHeight(MoCEntityOstrich entityliving, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void scale(MoCEntityOstrich entityliving, MatrixStack stack, float f) {
        MoCEntityOstrich entityostrich = (MoCEntityOstrich) entityliving;
        if (entityostrich.getSubType() == 1) {
            stretch(entityostrich, stack);
        }

        super.scale(entityliving, stack, f);

    }

    protected void stretch(MoCEntityOstrich entityostrich, MatrixStack stack) {

        float f = entityostrich.getEdad() * 0.01F;
        stack.scale(f, f, f);
    }

}
