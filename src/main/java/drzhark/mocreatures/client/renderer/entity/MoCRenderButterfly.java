package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderButterfly<T extends MoCEntityButterfly, M extends MoCModelButterfly<T>> extends MoCRenderInsect<T, M> {

    public MoCRenderButterfly(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);

    }

    @Override
    protected void scale(T entitybutterfly, MatrixStack stack, float par2) {
        if (entitybutterfly.isOnAir() || entitybutterfly.hasImpulse) {
            adjustHeight(entitybutterfly, stack, entitybutterfly.tFloat());
        }
        if (entitybutterfly.climbing()) {
            rotateAnimal(entitybutterfly);
        }
        stretch(entitybutterfly, stack);
    }

    protected void adjustHeight(MoCEntityButterfly entitybutterfly, MatrixStack stack, float FHeight) {
        stack.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
