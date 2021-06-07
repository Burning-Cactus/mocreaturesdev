package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorseMob<T extends MoCEntityHorseMob, M extends MoCModelNewHorseMob<T>> extends LivingRenderer<T, M> {

    public MoCRenderHorseMob(EntityRendererManager manager, M modelbase) {
        super(manager, modelbase, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entityhorsemob, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(T entityhorsemob) {
        return entityhorsemob.getTexture();
    }
}
