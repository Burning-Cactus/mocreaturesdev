package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelMediumFish extends EntityModel<MoCEntityMediumFish> {

    //fields
    ModelRenderer Head;
    ModelRenderer LowerHead;
    ModelRenderer Nose;
    ModelRenderer MouthBottom;
    ModelRenderer MouthBottomB;
    ModelRenderer Body;
    ModelRenderer BackUp;
    ModelRenderer BackDown;
    ModelRenderer Tail;
    ModelRenderer TailFin;
    ModelRenderer RightPectoralFin;
    ModelRenderer LeftPectoralFin;
    ModelRenderer UpperFin;
    ModelRenderer LowerFin;
    ModelRenderer RightLowerFin;
    ModelRenderer LeftLowerFin;

    float xOffset = 0F;
    float yOffset = 0F;
    float zOffset = 0F;

    public MoCModelMediumFish() {
        this.texWidth = 64;
        this.texHeight = 32;

        this.Head = new ModelRenderer(this, 0, 10);
        this.Head.addBox(-5F, 0F, -1.5F, 5, 3, 3);
        this.Head.setPos(-8F, 6F, 0F);
        setRotation(this.Head, 0F, 0F, -0.4461433F);

        this.LowerHead = new ModelRenderer(this, 0, 16);
        this.LowerHead.addBox(-4F, -3F, -1.5F, 4, 3, 3);
        this.LowerHead.setPos(-8F, 12F, 0F);
        setRotation(this.LowerHead, 0F, 0F, 0.3346075F);

        this.Nose = new ModelRenderer(this, 14, 17);
        this.Nose.addBox(-1F, -1F, -1F, 1, 3, 2);
        this.Nose.setPos(-11F, 8.2F, 0F);
        setRotation(this.Nose, 0F, 0F, 1.412787F);

        this.MouthBottom = new ModelRenderer(this, 16, 10);
        this.MouthBottom.addBox(-2F, -0.4F, -1F, 2, 1, 2);
        this.MouthBottom.setPos(-11.5F, 10F, 0F);
        setRotation(this.MouthBottom, 0F, 0F, 0.3346075F);

        this.MouthBottomB = new ModelRenderer(this, 16, 13);
        this.MouthBottomB.addBox(-1.5F, -2.4F, -0.5F, 1, 1, 1);
        this.MouthBottomB.setPos(-11.5F, 10F, 0F);
        setRotation(this.MouthBottomB, 0F, 0F, -0.7132579F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0F, -3F, -2F, 9, 6, 4);
        this.Body.setPos(-8F, 9F, 0F);

        this.BackUp = new ModelRenderer(this, 26, 0);
        this.BackUp.addBox(0F, 0F, -1.5F, 8, 3, 3);
        this.BackUp.setPos(1F, 6F, 0F);
        setRotation(this.BackUp, 0F, 0F, 0.1858931F);

        this.BackDown = new ModelRenderer(this, 26, 6);
        this.BackDown.addBox(0F, -3F, -1.5F, 8, 3, 3);
        this.BackDown.setPos(1F, 12F, 0F);
        setRotation(this.BackDown, 0F, 0F, -0.1919862F);

        this.Tail = new ModelRenderer(this, 48, 0);
        this.Tail.addBox(0F, -1.5F, -1F, 4, 3, 2);
        this.Tail.setPos(8F, 9F, 0F);

        this.TailFin = new ModelRenderer(this, 48, 5);
        this.TailFin.addBox(3F, -5.3F, 0F, 5, 11, 0);
        this.TailFin.setPos(8F, 9F, 0F);

        this.RightPectoralFin = new ModelRenderer(this, 28, 12);
        this.RightPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.RightPectoralFin.setPos(-6.5F, 10F, 2F);
        setRotation(this.RightPectoralFin, 0F, -0.8726646F, 0.185895F);

        this.LeftPectoralFin = new ModelRenderer(this, 38, 12);
        this.LeftPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.LeftPectoralFin.setPos(-6.5F, 10F, -2F);
        setRotation(this.LeftPectoralFin, 0F, 0.8726646F, 0.1858931F);

        this.UpperFin = new ModelRenderer(this, 0, 22);
        this.UpperFin.addBox(0F, -4F, 0F, 15, 4, 0);
        this.UpperFin.setPos(-7F, 6F, 0F);
        setRotation(this.UpperFin, 0F, 0F, 0.1047198F);

        this.LowerFin = new ModelRenderer(this, 46, 20);
        this.LowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LowerFin.setPos(0F, 12F, 0F);
        setRotation(this.LowerFin, 0F, 0F, -0.1858931F);

        this.RightLowerFin = new ModelRenderer(this, 28, 16);
        this.RightLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.RightLowerFin.setPos(-7F, 12F, 1F);
        setRotation(this.RightLowerFin, 0.5235988F, 0F, 0F);

        this.LeftLowerFin = new ModelRenderer(this, 46, 16);
        this.LeftLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LeftLowerFin.setPos(-7F, 12F, -1F);
        setRotation(this.LeftLowerFin, -0.5235988F, 0F, 0F);
    }

    @Override
    public void setupAnim(MoCEntityMediumFish entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.yOffset = entityIn.getAdjustedYOffset();
        this.xOffset = entityIn.getAdjustedXOffset();
        this.zOffset = entityIn.getAdjustedZOffset();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        //TailA.rotateAngleY = MathHelper.cos(f2 * 0.7F);
        float tailMov = MathHelper.cos(f * 0.6662F) * f1 * 0.6F;
        float finMov = MathHelper.cos(f2 * 0.2F) * 0.4F;
        float mouthMov = MathHelper.cos(f2 * 0.3F) * 0.2F;

        this.Tail.yRot = tailMov;
        this.TailFin.yRot = tailMov;

        this.LeftPectoralFin.yRot = 0.8726646F + finMov;
        this.RightPectoralFin.yRot = -0.8726646F - finMov;

        this.MouthBottom.zRot = 0.3346075F + mouthMov;
        this.MouthBottomB.zRot = -0.7132579F + mouthMov;
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(xOffset, yOffset, zOffset);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.MouthBottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.MouthBottomB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BackUp.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BackDown.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.TailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RightPectoralFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LeftPectoralFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RightLowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LeftLowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.popPose();
    }
}
