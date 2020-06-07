package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelBunny;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

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
    public void render(T entity, double d, double d1, double d2, float f, float f1) {
        super.render(entity,);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBunny entitybunny, float f) {
        if (!entitybunny.getIsAdult()) {
            stretch(entitybunny);
        }
        return entitybunny.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entitybunny, float f) {
        rotBunny(entitybunny);
        adjustOffsets(entitybunny.getAdjustedXOffset(), entitybunny.getAdjustedYOffset(),entitybunny.getAdjustedZOffset());
    }

    protected void rotBunny(MoCEntityBunny entitybunny) {
        if (!entitybunny.onGround && (entitybunny.getRidingEntity() == null)) {
            if (entitybunny.getMotion().y > 0.5D) {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            } else if (entitybunny.getMotion().y < -0.5D) {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitybunny.getMotion().y * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny) {
        float f = entitybunny.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }
}
