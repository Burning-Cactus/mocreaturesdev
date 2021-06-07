package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat<MoCEntityHellRat> {

    public MoCRenderHellRat(EntityRendererManager manager, MoCModelRat modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected void stretch(MoCEntityHellRat entityhellrat, MatrixStack stack) {
        float f = 1.3F;
        stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityHellRat entityhellrat) {
        return entityhellrat.getTexture();
    }
}
