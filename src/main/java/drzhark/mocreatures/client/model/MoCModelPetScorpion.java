package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelPetScorpion extends MoCModelScorpion<MoCEntityPetScorpion> {

    @Override
    public void setupAnim(MoCEntityPetScorpion entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntityPetScorpion scorpy = entityIn;
        poisoning = scorpy.swingingTail();
        isTalking = scorpy.mouthCounter != 0;
        babies = scorpy.getHasBabies();
        attacking = scorpy.armCounter;
        sitting = scorpy.getIsSitting();
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderParts(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
