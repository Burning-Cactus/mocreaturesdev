package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelShark extends EntityModel<MoCEntityShark> {

    public ModelRenderer LHead;

    public ModelRenderer RHead;

    public ModelRenderer UHead;

    public ModelRenderer DHead;

    public ModelRenderer RTail;

    public ModelRenderer LTail;

    public ModelRenderer PTail;

    public ModelRenderer Body;

    public ModelRenderer UpperFin;

    public ModelRenderer UpperTailFin;

    public ModelRenderer LowerTailFin;

    public ModelRenderer RightFin;

    public ModelRenderer LeftFin;

    public MoCModelShark() {
        this.Body = new ModelRenderer(this, 6, 6);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        this.Body.setRotationPoint(-4F, 17F, -10F);
        this.UHead = new ModelRenderer(this, 0, 0);
        this.UHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 8, 0.0F);
        this.UHead.setRotationPoint(-3.5F, 21F, -16.5F);
        this.UHead.rotateAngleX = 0.5235988F;
        this.DHead = new ModelRenderer(this, 44, 0);
        this.DHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 5, 0.0F);
        this.DHead.setRotationPoint(-3.5F, 21.5F, -13.5F);
        this.DHead.rotateAngleX = -0.261799F;
        this.RHead = new ModelRenderer(this, 0, 3);
        this.RHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.RHead.setRotationPoint(-3.45F, 21.3F, -13.85F);
        this.RHead.rotateAngleX = 0.7853981F;
        this.LHead = new ModelRenderer(this, 0, 3);
        this.LHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.LHead.setRotationPoint(0.45F, 21.3F, -13.8F);
        this.LHead.rotateAngleX = 0.7853981F;
        this.PTail = new ModelRenderer(this, 36, 8);
        this.PTail.addBox(0.0F, 0.0F, 0.0F, 4, 6, 10, 0.0F);
        this.PTail.setRotationPoint(-3F, 18F, 8F);
        this.UpperFin = new ModelRenderer(this, 6, 12);
        this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperFin.setRotationPoint(-1.5F, 17F, -1F);
        this.UpperFin.rotateAngleX = 0.7853981F;
        this.UpperTailFin = new ModelRenderer(this, 6, 12);
        this.UpperTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperTailFin.setRotationPoint(-1.5F, 18F, 16F);
        this.UpperTailFin.rotateAngleX = 0.5235988F;
        this.LowerTailFin = new ModelRenderer(this, 8, 14);
        this.LowerTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.LowerTailFin.setRotationPoint(-1.5F, 21F, 18F);
        this.LowerTailFin.rotateAngleX = -0.7853981F;
        this.LeftFin = new ModelRenderer(this, 18, 0);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.LeftFin.setRotationPoint(2.0F, 24F, -5F);
        this.LeftFin.rotateAngleY = -0.5235988F;
        this.LeftFin.rotateAngleZ = 0.5235988F;
        this.RightFin = new ModelRenderer(this, 18, 0);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.RightFin.setRotationPoint(-10F, 27.5F, -1F);
        this.RightFin.rotateAngleY = 0.5235988F;
        this.RightFin.rotateAngleZ = -0.5235988F;
    }

    @Override
    public void setRotationAngles(MoCEntityShark entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        this.UpperTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
        this.LowerTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.PTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.DHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.UpperTailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LowerTailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LeftFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RightFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
