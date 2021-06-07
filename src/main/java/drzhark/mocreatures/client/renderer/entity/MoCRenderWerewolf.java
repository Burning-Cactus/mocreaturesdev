package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWerewolf extends LivingRenderer<MoCEntityWerewolf, MoCModelWere> {

    private final MoCModelWere tempWerewolf;

    public MoCRenderWerewolf(EntityRendererManager manager, MoCModelWereHuman modelwerehuman, MoCModelWere modelbase, float f) {
        super(manager, modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = modelbase;
    }

    @Override
    public void render(MoCEntityWerewolf entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.tempWerewolf.hunched = entityIn.getIsHunched();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityWerewolf entitywerewolf) {
        return entitywerewolf.getTexture();
    }

    private class LayerMoCWereHuman extends LayerRenderer<MoCEntityWerewolf, MoCModelWere> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWereHuman mocModel = new MoCModelWereHuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            super(render);
            mocRenderer = render;
        }

//        @Override
//        public boolean shouldCombineTextures() {
//            return true;
//        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityWerewolf entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            int myType = entitylivingbaseIn.getSubType();

            ResourceLocation texture;
            if (!entitylivingbaseIn.getIsHumanForm()) {
                texture = MoCreatures.getTexture("wereblank.png");
            } else {
                switch (myType) {

                    case 1:
                        texture = MoCreatures.getTexture("weredude.png");
                        break;
                    case 2:
                        texture = MoCreatures.getTexture("werehuman.png");
                        break;
                    case 3:
                        texture = MoCreatures.getTexture("wereoldie.png");
                        break;
                    case 4:
                        texture = MoCreatures.getTexture("werewoman.png");
                        break;
                    default:
                        texture = MoCreatures.getTexture("wereoldie.png");
                }

            }

            this.mocModel.copyPropertiesTo(mocModel);
            this.mocModel.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutout(texture));
            this.mocModel.renderToBuffer(matrixStackIn, builder, packedLightIn, getOverlayCoords(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
