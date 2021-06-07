package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityMole;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelMole extends EntityModel<MoCEntityMole> {

    ModelRenderer Nose;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer Tail;
    ModelRenderer LLeg;
    ModelRenderer LFingers;
    ModelRenderer RLeg;
    ModelRenderer RFingers;
    ModelRenderer LRearLeg;
    ModelRenderer RRearLeg;
    float yOffset = 0F;

    public MoCModelMole() {
        this.texWidth = 64;
        this.texHeight = 32;

        this.Nose = new ModelRenderer(this, 0, 25);
        this.Nose.addBox(-1F, 0F, -4F, 2, 2, 3);
        this.Nose.setPos(0F, 20F, -6F);
        setRotation(this.Nose, 0.2617994F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 18);
        this.Head.addBox(-3F, -2F, -2F, 6, 4, 3);
        this.Head.setPos(0F, 20F, -6F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-5F, 0F, 0F, 10, 6, 10);
        this.Body.setPos(0F, 17F, -6F);

        this.Back = new ModelRenderer(this, 18, 16);
        this.Back.addBox(-4F, -3F, 0F, 8, 5, 4);
        this.Back.setPos(0F, 21F, 4F);

        this.Tail = new ModelRenderer(this, 52, 8);
        this.Tail.addBox(-0.5F, 0F, 1F, 1, 1, 5);
        this.Tail.setPos(0F, 21F, 6F);
        setRotation(this.Tail, -0.3490659F, 0F, 0F);

        this.LLeg = new ModelRenderer(this, 10, 25);
        this.LLeg.addBox(0F, -2F, -1F, 6, 4, 2);
        this.LLeg.setPos(4F, 21F, -4F);
        setRotation(this.LLeg, 0F, 0F, 0.2268928F);

        this.LFingers = new ModelRenderer(this, 44, 8);
        this.LFingers.addBox(5F, -2F, 1F, 1, 4, 1);
        this.LFingers.setPos(4F, 21F, -4F);
        setRotation(this.LFingers, 0F, 0F, 0.2268928F);

        this.RLeg = new ModelRenderer(this, 26, 25);
        this.RLeg.addBox(-6F, -2F, -1F, 6, 4, 2);
        this.RLeg.setPos(-4F, 21F, -4F);
        setRotation(this.RLeg, 0F, 0F, -0.2268928F);

        this.RFingers = new ModelRenderer(this, 48, 8);
        this.RFingers.addBox(-6F, -2F, 1F, 1, 4, 1);
        this.RFingers.setPos(-4F, 21F, -4F);
        setRotation(this.RFingers, 0F, 0F, -0.2268928F);

        this.LRearLeg = new ModelRenderer(this, 36, 0);
        this.LRearLeg.addBox(0F, -2F, -1F, 2, 3, 5);
        this.LRearLeg.setPos(3F, 22F, 5F);
        setRotation(this.LRearLeg, -0.2792527F, 0.5235988F, 0F);

        this.RRearLeg = new ModelRenderer(this, 50, 0);
        this.RRearLeg.addBox(-2F, -2F, -1F, 2, 3, 5);
        this.RRearLeg.setPos(-3F, 22F, 5F);
        setRotation(this.RRearLeg, -0.2792527F, -0.5235988F, 0F);
    }

    @Override
    public void setupAnim(MoCEntityMole entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        MoCEntityMole mole = entityIn;
        this.yOffset = mole.getAdjustedYOffset();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        this.Head.yRot = f3 / 57.29578F;
        this.Head.xRot = f4 / 57.29578F;
        this.Nose.xRot = 0.2617994F + this.Head.xRot;
        this.Nose.yRot = this.Head.yRot;

        float RLegXRot = MathHelper.cos((f * 1.0F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 1.0F) * 0.8F * f1;

        this.RLeg.yRot = RLegXRot;
        this.RFingers.yRot = this.RLeg.yRot;
        this.LLeg.yRot = LLegXRot;
        this.LFingers.yRot = this.LLeg.yRot;
        this.RRearLeg.yRot = -0.5235988F + LLegXRot;
        this.LRearLeg.yRot = 0.5235988F + RLegXRot;

        this.Tail.zRot = this.LLeg.xRot * 0.625F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0F, this.yOffset, 0F);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Back.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LFingers.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RFingers.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LRearLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RRearLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.popPose();
    }
}
