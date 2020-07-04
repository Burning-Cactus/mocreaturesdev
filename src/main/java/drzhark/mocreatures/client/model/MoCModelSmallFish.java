package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelSmallFish extends EntityModel<MoCEntitySmallFish> {

    ModelRenderer BodyFlat;
    ModelRenderer BodyRomboid;
    ModelRenderer MidBodyFin;
    ModelRenderer UpperFinA;
    ModelRenderer UpperFinB;
    ModelRenderer UpperFinC;
    ModelRenderer LowerFinA;
    ModelRenderer LowerFinB;
    ModelRenderer LowerFinC;
    ModelRenderer Tail;

    float xOffset = 0F;
    float yOffset = 0F;
    float zOffset = 0F;

    public MoCModelSmallFish() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.BodyFlat = new ModelRenderer(this, 0, 2);
        this.BodyFlat.addBox(0F, -1.5F, -1F, 5, 3, 2);
        this.BodyFlat.setRotationPoint(-3F, 15F, 0F);

        this.BodyRomboid = new ModelRenderer(this, 0, 7);
        this.BodyRomboid.addBox(0F, 0F, -0.5F, 4, 4, 1);
        this.BodyRomboid.setRotationPoint(-4F, 15F, 0F);
        setRotation(this.BodyRomboid, 0F, 0F, -0.7853982F);

        this.MidBodyFin = new ModelRenderer(this, 0, 12);
        this.MidBodyFin.addBox(0F, -0.5F, 0F, 4, 2, 4);
        this.MidBodyFin.setRotationPoint(-3F, 15F, 0F);
        setRotation(this.MidBodyFin, 0F, 0.7853982F, 0F);

        this.UpperFinA = new ModelRenderer(this, 10, 0);
        this.UpperFinA.addBox(-0.5F, -1.3F, -0.5F, 2, 1, 1);
        this.UpperFinA.setRotationPoint(-0.65F, 13.5F, 0F);

        this.UpperFinB = new ModelRenderer(this, 0, 0);
        this.UpperFinB.addBox(-2.5F, -1F, -0.5F, 4, 1, 1);
        this.UpperFinB.setRotationPoint(0F, 13.5F, 0F);
        this.UpperFinB.setTextureSize(32, 32);

        this.UpperFinC = new ModelRenderer(this, 0, 18);
        this.UpperFinC.addBox(-5F, -2F, 0F, 8, 3, 0);
        this.UpperFinC.setRotationPoint(0F, 13.5F, 0F);

        this.LowerFinA = new ModelRenderer(this, 16, 0);
        this.LowerFinA.addBox(-0.5F, -0.3F, -0.5F, 2, 1, 1);
        this.LowerFinA.setRotationPoint(-0.65F, 17.2F, 0F);

        this.LowerFinB = new ModelRenderer(this, 0, 21);
        this.LowerFinB.addBox(0F, 0F, -3F, 5, 0, 6);
        this.LowerFinB.setRotationPoint(-3F, 16F, 0F);

        this.LowerFinC = new ModelRenderer(this, 16, 18);
        this.LowerFinC.addBox(-5F, 0F, 0F, 8, 3, 0);
        this.LowerFinC.setRotationPoint(0F, 15.5F, 0F);

        this.Tail = new ModelRenderer(this, 10, 7);
        this.Tail.addBox(0F, 0F, -0.5F, 3, 3, 1);
        this.Tail.setRotationPoint(1.3F, 15F, 0F);
        setRotation(this.Tail, 0F, 0F, -0.7853982F);
    }

    @Override
    public void setRotationAngles(MoCEntitySmallFish entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.yOffset = entityIn.getAdjustedYOffset();
        this.xOffset = entityIn.getAdjustedXOffset();
        this.zOffset = entityIn.getAdjustedZOffset();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        float tailMov = MathHelper.cos(f * 0.8F) * f1 * 0.6F;
        float finMov = MathHelper.cos(f2 * 0.4F) * 0.2F;

        this.Tail.rotateAngleY = tailMov;
        this.MidBodyFin.rotateAngleY = 0.7853982F + finMov;
        this.LowerFinB.rotateAngleZ = finMov;

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        matrixStackIn.push();
        matrixStackIn.translate(xOffset, yOffset, zOffset);
        this.BodyFlat.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BodyRomboid.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.MidBodyFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperFinA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperFinB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperFinC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerFinA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerFinB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerFinC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.pop();
    }
}
