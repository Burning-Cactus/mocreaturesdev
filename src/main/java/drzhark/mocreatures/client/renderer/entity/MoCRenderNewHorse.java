package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.client.model.MoCModelNewHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderNewHorse<T extends MoCEntityHorse, M extends MoCModelNewHorse<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderNewHorse(EntityRendererManager manager, M modelbase) {
        super(manager, modelbase, 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityHorse entityhorse) {
        return entityhorse.getTexture();
    }

    protected void adjustHeight(MoCEntityHorse entityhorse, float FHeight) {
        RenderSystem.translatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(T entityhorse, MatrixStack matrixStack, float f) {
        if (!entityhorse.getIsAdult() || entityhorse.getSubType() > 64) {
            stretch(entityhorse);
        }
        if (entityhorse.getIsGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F));
        }
        super.preRenderCallback(entityhorse, matrixStack, f);
    }

    protected void stretch(MoCEntityHorse entityhorse) {
        float sizeFactor = entityhorse.getEdad() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getSubType() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        RenderSystem.scalef(sizeFactor, sizeFactor, sizeFactor);
    }

}
