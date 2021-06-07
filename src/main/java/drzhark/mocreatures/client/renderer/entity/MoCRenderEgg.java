package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelEgg;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderEgg<T extends MoCEntityEgg, M extends MoCModelEgg<T>> extends LivingRenderer<T, M> {

    public MoCRenderEgg(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected void scale(T entityegg, MatrixStack matrixStack, float f) {
        stretch(entityegg);
        super.scale(entityegg, matrixStack, f);

    }

    protected void stretch(T entityegg) {
        float f = entityegg.getSize() * 0.01F;
        GL11.glScalef(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entityegg) {
        return entityegg.getTexture();
    }
}
