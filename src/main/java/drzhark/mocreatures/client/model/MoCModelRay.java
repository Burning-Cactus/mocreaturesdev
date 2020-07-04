package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelRay<T extends MoCEntityRay> extends EntityModel<T> {

    public MoCModelRay() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Body = new ModelRenderer(this, 26, 0);
        this.Body.addBox(-4F, -1F, 0F, 8, 2, 11);
        this.Body.setRotationPoint(0F, 22F, -5F);

        this.Right = new ModelRenderer(this, 10, 26);
        this.Right.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        this.Right.setRotationPoint(-3F, 22F, -4.8F);

        this.Left = new ModelRenderer(this, 0, 26);
        this.Left.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        this.Left.setRotationPoint(3F, 22F, -4.8F);

        this.BodyU = new ModelRenderer(this, 0, 11);
        this.BodyU.addBox(-3F, -1F, 0F, 6, 1, 8);
        this.BodyU.setRotationPoint(0F, 21F, -4F);

        this.Tail = new ModelRenderer(this, 30, 15);
        this.Tail.addBox(-0.5F, -0.5F, 1F, 1, 1, 16);
        this.Tail.setRotationPoint(0F, 22F, 8F);

        this.BodyTail = new ModelRenderer(this, 0, 20);
        this.BodyTail.addBox(-1.8F, -0.5F, -3.2F, 5, 1, 5);
        this.BodyTail.setRotationPoint(0F, 22F, 7F);
        setRotation(this.BodyTail, 0F, 1F, 0F);

        this.RWingA = new ModelRenderer(this, 0, 0);
        this.RWingA.addBox(-3F, -0.5F, -5F, 3, 1, 10);
        this.RWingA.setRotationPoint(-4F, 22F, 1F);

        this.RWingB = new ModelRenderer(this, 2, 2);
        this.RWingB.addBox(-6F, -0.5F, -4F, 3, 1, 8);
        this.RWingB.setRotationPoint(-4F, 22F, 1F);

        this.RWingC = new ModelRenderer(this, 5, 4);
        this.RWingC.addBox(-8F, -0.5F, -3F, 2, 1, 6);
        this.RWingC.setRotationPoint(-4F, 22F, 1F);

        this.RWingD = new ModelRenderer(this, 6, 5);
        this.RWingD.addBox(-10F, -0.5F, -2.5F, 2, 1, 5);
        this.RWingD.setRotationPoint(-4F, 22F, 1F);

        this.RWingE = new ModelRenderer(this, 7, 6);
        this.RWingE.addBox(-12F, -0.5F, -2F, 2, 1, 4);
        this.RWingE.setRotationPoint(-4F, 22F, 1F);

        this.RWingF = new ModelRenderer(this, 8, 7);
        this.RWingF.addBox(-14F, -0.5F, -1.5F, 2, 1, 3);
        this.RWingF.setRotationPoint(-4F, 22F, 1F);

        this.RWingG = new ModelRenderer(this, 9, 8);
        this.RWingG.addBox(-16F, -0.5F, -1F, 2, 1, 2);
        this.RWingG.setRotationPoint(-4F, 22F, 1F);

        this.LWingA = new ModelRenderer(this, 0, 0);
        this.LWingA.addBox(0F, -0.5F, -5F, 3, 1, 10);
        this.LWingA.setRotationPoint(4F, 22F, 1F);
        this.LWingA.mirror = true;

        this.LWingB = new ModelRenderer(this, 2, 2);
        this.LWingB.addBox(3F, -0.5F, -4F, 3, 1, 8);
        this.LWingB.setRotationPoint(4F, 22F, 1F);
        this.LWingB.mirror = true;

        this.LWingC = new ModelRenderer(this, 5, 4);
        this.LWingC.addBox(6F, -0.5F, -3F, 2, 1, 6);
        this.LWingC.setRotationPoint(4F, 22F, 1F);
        this.LWingC.mirror = true;

        this.LWingD = new ModelRenderer(this, 6, 5);
        this.LWingD.addBox(8F, -0.5F, -2.5F, 2, 1, 5);
        this.LWingD.setRotationPoint(4F, 22F, 1F);
        this.LWingD.mirror = true;

        this.LWingE = new ModelRenderer(this, 7, 6);
        this.LWingE.addBox(10F, -0.5F, -2F, 2, 1, 4);
        this.LWingE.setRotationPoint(4F, 22F, 1F);
        this.LWingE.mirror = true;

        this.LWingF = new ModelRenderer(this, 8, 7);
        this.LWingF.addBox(12F, -0.5F, -1.5F, 2, 1, 3);
        this.LWingF.setRotationPoint(4F, 22F, 1F);
        this.LWingF.mirror = true;

        this.LWingG = new ModelRenderer(this, 9, 8);
        this.LWingG.addBox(14F, -0.5F, -1F, 2, 1, 2);
        this.LWingG.setRotationPoint(4F, 22F, 1F);
        this.LWingG.mirror = true;

        this.LEye = new ModelRenderer(this, 0, 0);
        this.LEye.addBox(-3F, -2F, 1F, 1, 1, 2);
        this.LEye.setRotationPoint(0F, 21F, -4F);

        this.REye = new ModelRenderer(this, 0, 3);
        this.REye.addBox(2F, -2F, 1F, 1, 1, 2);
        this.REye.setRotationPoint(0F, 21F, -4F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.attacking = entityIn.isPoisoning();
        this.isMantaRay = entityIn.isMantaRay();

        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float rotF = MathHelper.cos(f * 0.6662F) * 1.5F * f1;
        float f6 = 20F;
        this.Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        this.RWingA.rotateAngleZ = rotF;
        this.LWingA.rotateAngleZ = -rotF;
        rotF += (rotF / f6);
        this.RWingB.rotateAngleZ = rotF;
        this.LWingB.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingC.rotateAngleZ = rotF;
        this.LWingC.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingD.rotateAngleZ = rotF;
        this.LWingD.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingE.rotateAngleZ = rotF;
        this.LWingE.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingF.rotateAngleZ = rotF;
        this.LWingF.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingG.rotateAngleZ = rotF;
        this.LWingG.rotateAngleZ = -rotF;

        if (this.attacking) {
            this.Tail.rotateAngleX = 0.5F;
        } else {
            this.Tail.rotateAngleX = 0F;
        }

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BodyU.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BodyTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.RWingA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RWingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.LWingA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LWingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        if (this.isMantaRay) {
            this.Right.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Left.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

            this.RWingD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingE.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingF.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingG.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingE.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingF.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingG.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        } else {
            this.REye.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LEye.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
    }

    //public int typeInt;
    public boolean isMantaRay;
    public boolean attacking;
    ModelRenderer Tail;
    ModelRenderer Body;
    ModelRenderer Right;
    ModelRenderer Left;
    ModelRenderer BodyU;
    ModelRenderer RWingA;
    ModelRenderer RWingB;
    ModelRenderer RWingC;
    ModelRenderer RWingD;
    ModelRenderer RWingE;
    ModelRenderer RWingF;
    ModelRenderer BodyTail;
    ModelRenderer RWingG;
    ModelRenderer LWingA;
    ModelRenderer LWingB;
    ModelRenderer LWingC;
    ModelRenderer LWingD;
    ModelRenderer LWingE;
    ModelRenderer LWingF;
    ModelRenderer LWingG;
    ModelRenderer LEye;
    ModelRenderer REye;


}
