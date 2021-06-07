package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelMouse extends EntityModel<MoCEntityMouse> {

    public ModelRenderer Head;
    public ModelRenderer EarR;
    public ModelRenderer EarL;
    public ModelRenderer WhiskerR;
    public ModelRenderer WhiskerL;
    public ModelRenderer Tail;
    public ModelRenderer FrontL;
    public ModelRenderer FrontR;
    public ModelRenderer RearL;
    public ModelRenderer RearR;
    public ModelRenderer BodyF;

    public MoCModelMouse() {
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -1F, -6F, 3, 4, 6, 0.0F);
        this.Head.setPos(0.0F, 19F, -9F);
        this.EarR = new ModelRenderer(this, 16, 26);
        this.EarR.addBox(-3.5F, -3F, -2F, 3, 3, 1, 0.0F);
        this.EarR.setPos(0.0F, 19F, -9F);
        this.EarL = new ModelRenderer(this, 24, 26);
        this.EarL.addBox(0.5F, -3F, -1F, 3, 3, 1, 0.0F);
        this.EarL.setPos(0.0F, 19F, -10F);
        this.WhiskerR = new ModelRenderer(this, 20, 20);
        this.WhiskerR.addBox(-4.5F, -1F, -7F, 3, 3, 1, 0.0F);
        this.WhiskerR.setPos(0.0F, 19F, -9F);
        this.WhiskerL = new ModelRenderer(this, 24, 20);
        this.WhiskerL.addBox(1.5F, -1F, -6F, 3, 3, 1, 0.0F);
        this.WhiskerL.setPos(0.0F, 19F, -9F);
        this.Tail = new ModelRenderer(this, 56, 0);
        this.Tail.addBox(-0.5F, 0.0F, -1F, 1, 14, 1, 0.0F);
        this.Tail.setPos(0.0F, 20F, 3F);
        this.Tail.xRot = 1.570796F;
        this.FrontL = new ModelRenderer(this, 0, 18);
        this.FrontL.addBox(-2F, 0.0F, -3F, 2, 1, 4, 0.0F);
        this.FrontL.setPos(3F, 23F, -7F);
        this.FrontR = new ModelRenderer(this, 0, 18);
        this.FrontR.addBox(0.0F, 0.0F, -3F, 2, 1, 4, 0.0F);
        this.FrontR.setPos(-3F, 23F, -7F);
        this.RearL = new ModelRenderer(this, 0, 18);
        this.RearL.addBox(-2F, 0.0F, -4F, 2, 1, 4, 0.0F);
        this.RearL.setPos(4F, 23F, 2.0F);
        this.RearR = new ModelRenderer(this, 0, 18);
        this.RearR.addBox(0.0F, 0.0F, -4F, 2, 1, 4, 0.0F);
        this.RearR.setPos(-4F, 23F, 2.0F);
        this.BodyF = new ModelRenderer(this, 20, 0);
        this.BodyF.addBox(-3F, -3F, -7F, 6, 6, 12, 0.0F);
        this.BodyF.setPos(0.0F, 20F, -2F);
    }

    @Override
    public void setupAnim(MoCEntityMouse entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        this.Head.xRot = -(f4 / 57.29578F);
        this.Head.yRot = f3 / 57.29578F;
        this.EarR.xRot = this.Head.xRot;
        this.EarR.yRot = this.Head.yRot;
        this.EarL.xRot = this.Head.xRot;
        this.EarL.yRot = this.Head.yRot;
        this.WhiskerR.xRot = this.Head.xRot;
        this.WhiskerR.yRot = this.Head.yRot;
        this.WhiskerL.xRot = this.Head.xRot;
        this.WhiskerL.yRot = this.Head.yRot;
        this.FrontL.xRot = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        this.RearL.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        this.RearR.xRot = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        this.FrontR.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        this.Tail.yRot = this.FrontL.xRot * 0.625F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.EarR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.EarL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.WhiskerR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.WhiskerL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.FrontL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.FrontR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RearL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RearR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.BodyF.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
