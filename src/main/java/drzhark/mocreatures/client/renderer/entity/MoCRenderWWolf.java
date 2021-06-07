package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWWolf extends MobRenderer<MoCEntityWWolf, MoCModelWolf<MoCEntityWWolf>> {

    public MoCRenderWWolf(EntityRendererManager manager, MoCModelWolf modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityWWolf entity) {
        return entity.getTexture();
    }
}
