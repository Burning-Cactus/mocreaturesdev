package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf extends LivingRenderer<MoCEntityWerewolf, MoCModelWere<MoCEntityWerewolf>> {

    private final MoCModelWere tempWerewolf;

    public MoCRenderWerewolf(EntityRendererManager manager, MoCModelWereHuman modelwerehuman, MoCModelWere modelbase, float f) {
        super(manager, modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWere) modelbase;
    }

    @Override
    public void doRender(MoCEntityWerewolf entitywerewolf, double d, double d1, double d2, float f, float f1) {
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRender(entitywerewolf, d, d1, d2, f, f1);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWerewolf entitywerewolf) {
        return entitywerewolf.getTexture();
    }

    private class LayerMoCWereHuman implements LayerRenderer<MoCEntityWerewolf> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWereHuman mocModel = new MoCModelWereHuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityWerewolf entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            int myType = entity.getSubType();

            if (!entity.getIsHumanForm()) {
                bindTexture(MoCreatures.getTexture("wereblank.png"));
            } else {
                switch (myType) {

                    case 1:
                        bindTexture(MoCreatures.getTexture("weredude.png"));
                        break;
                    case 2:
                        bindTexture(MoCreatures.getTexture("werehuman.png"));
                        break;
                    case 3:
                        bindTexture(MoCreatures.getTexture("wereoldie.png"));
                        break;
                    case 4:
                        bindTexture(MoCreatures.getTexture("werewoman.png"));
                        break;
                    default:
                        bindTexture(MoCreatures.getTexture("wereoldie.png"));
                }

            }

            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entity, f, f1, f2);
            this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
