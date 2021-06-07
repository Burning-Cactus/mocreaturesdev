package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCModelGoat extends EntityModel<MoCEntityGoat> {

    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer LEar;
    ModelRenderer REar;
    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Tongue;
    ModelRenderer Mouth;
    ModelRenderer RHorn1;
    ModelRenderer RHorn2;
    ModelRenderer RHorn3;
    ModelRenderer RHorn4;
    ModelRenderer RHorn5;
    ModelRenderer LHorn1;
    ModelRenderer LHorn2;
    ModelRenderer LHorn3;
    ModelRenderer LHorn4;
    ModelRenderer LHorn5;
    ModelRenderer Goatie;
    ModelRenderer Neck;
    ModelRenderer Tits;

    public int typeInt;
    public int attacking;
    public float edad;
    public boolean bleat;
    public int legMov;
    public int earMov;
    public int tailMov;
    public int eatMov;

    public MoCModelGoat() {
        this.texWidth = 64;
        this.texHeight = 32;

        this.Leg1 = new ModelRenderer(this, 0, 23);
        this.Leg1.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg1.setPos(2F, 17F, -6F);

        // Leg1.mirror = true;

        this.Leg2 = new ModelRenderer(this, 0, 23);
        this.Leg2.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg2.setPos(-2F, 17F, -6F);

        // Leg2.mirror = true;

        this.Leg3 = new ModelRenderer(this, 0, 23);
        this.Leg3.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg3.setPos(-2F, 17F, 6F);

        // Leg3.mirror = true;

        this.Leg4 = new ModelRenderer(this, 0, 23);
        this.Leg4.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg4.setPos(2F, 17F, 6F);

        // Leg4.mirror = true;

        this.Body = new ModelRenderer(this, 20, 8);
        this.Body.addBox(-3F, -4F, -8F, 6, 8, 16);
        this.Body.setPos(0F, 13F, 0F);

        this.Tail = new ModelRenderer(this, 22, 8);
        this.Tail.addBox(-1.5F, -1F, 0F, 3, 2, 4);
        this.Tail.setPos(0F, 10F, 8F);

        this.LEar = new ModelRenderer(this, 52, 8);
        this.LEar.addBox(1.5F, -2F, 0F, 2, 1, 1);
        this.LEar.setPos(0F, 8F, -12F);

        // LEar.mirror = true;

        this.REar = new ModelRenderer(this, 52, 8);
        this.REar.addBox(-3.5F, -2F, 0F, 2, 1, 1);
        this.REar.setPos(0F, 8F, -12F);

        // REar.mirror = true;

        this.Head = new ModelRenderer(this, 52, 16);
        this.Head.addBox(-1.5F, -2F, -2F, 3, 5, 3);
        this.Head.setPos(0F, 8F, -12F);

        this.Nose = new ModelRenderer(this, 52, 10);
        this.Nose.addBox(-1.5F, -1F, -5F, 3, 3, 3);
        this.Nose.setPos(0F, 8F, -12F);

        this.Tongue = new ModelRenderer(this, 56, 5);
        this.Tongue.addBox(-0.5F, 2F, -5F, 1, 0, 3);
        this.Tongue.setPos(0F, 8F, -12F);

        this.Mouth = new ModelRenderer(this, 54, 0);
        this.Mouth.addBox(-1F, 2F, -5F, 2, 1, 3);
        this.Mouth.setPos(0F, 8F, -12F);

        this.RHorn1 = new ModelRenderer(this, 0, 0);
        this.RHorn1.addBox(-1.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        this.RHorn1.setPos(0F, 8F, -12F);

        this.RHorn2 = new ModelRenderer(this, 0, 0);
        this.RHorn2.addBox(-1.9F, -4F, -0.2F, 1, 1, 1);
        this.RHorn2.setPos(0F, 8F, -12F);

        this.RHorn3 = new ModelRenderer(this, 0, 0);
        this.RHorn3.addBox(-2.1F, -4.8F, 0.5F, 1, 1, 1, -0.05F);
        this.RHorn3.setPos(0F, 8F, -12F);

        this.RHorn4 = new ModelRenderer(this, 0, 0);
        this.RHorn4.addBox(-2.3F, -5.2F, 1.4F, 1, 1, 1, -0.1F);
        this.RHorn4.setPos(0F, 8F, -12F);

        this.RHorn5 = new ModelRenderer(this, 0, 0);
        this.RHorn5.addBox(-2.6F, -4.9F, 2.0F, 1, 1, 1, -0.15F);
        this.RHorn5.setPos(0F, 8F, -12F);

        this.LHorn1 = new ModelRenderer(this, 0, 0);
        this.LHorn1.addBox(0.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        this.LHorn1.setPos(0F, 8F, -12F);

        this.LHorn2 = new ModelRenderer(this, 0, 0);
        this.LHorn2.addBox(0.9F, -4F, -0.2F, 1, 1, 1);
        this.LHorn2.setPos(0F, 8F, -12F);

        this.LHorn3 = new ModelRenderer(this, 0, 0);
        this.LHorn3.addBox(1.2F, -4.9F, 0.5F, 1, 1, 1, -0.05F);
        this.LHorn3.setPos(0F, 8F, -12F);

        this.LHorn4 = new ModelRenderer(this, 0, 0);
        this.LHorn4.addBox(1.4F, -5.3F, 1.4F, 1, 1, 1, -0.1F);
        this.LHorn4.setPos(0F, 8F, -12F);

        this.LHorn5 = new ModelRenderer(this, 0, 0);
        this.LHorn5.addBox(1.7F, -4.9F, 2.1F, 1, 1, 1, -0.15F);
        this.LHorn5.setPos(0F, 8F, -12F);

        this.Goatie = new ModelRenderer(this, 52, 5);
        this.Goatie.addBox(-0.5F, 3F, -4F, 1, 2, 1);
        this.Goatie.setPos(0F, 8F, -12F);

        this.Neck = new ModelRenderer(this, 18, 14);
        this.Neck.addBox(-1.5F, -2.0F, -5F, 3, 4, 6, -0.2F);
        this.Neck.setPos(0F, 11F, -8F);
        this.Neck.xRot = -24 / 57.29578F;

        this.Tits = new ModelRenderer(this, 18, 0);
        this.Tits.addBox(-2.5F, 0F, -2F, 5, 1, 4);
        this.Tits.setPos(0F, 17F, 3F);

    }

    @Override
    public void setupAnim(MoCEntityGoat entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Leg1.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.Leg2.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg3.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg4.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float baseAngle = (30 / 57.29578F) + (f4 / 57.29578F);

        // neck = -30 to 30
        // head 30 to 75

        if (f3 > 20F) {
            f3 = 20F;
        }
        if (f3 < -20F) {
            f3 = -20F;
        }
        this.Head.yRot = (f3 / 57.29578F);// * 0.6F; //fixes SMP bug
        this.Neck.xRot = -30 / 57.29578F;
        this.Tail.xRot = this.tailMov / 57.29578F;

        this.Head.xRot = baseAngle;
        if (this.bleat) {
            this.Head.xRot = -15 / 57.29578F;
        }
        if (this.attacking != 0) {
            this.Head.xRot = this.attacking / 57.29578F;
            this.Neck.xRot = ((1.33F * this.attacking) - 70) / 57.29578F;
            if (this.legMov != 0) {
                this.Leg1.xRot = this.legMov / 57.29578F;
                // leg -20 to + 30
                // 0 to -20 normal speed
                // -20 to 30 fast x2 or x3
            }
        }

        this.LEar.xRot = this.Head.xRot;
        this.REar.xRot = this.Head.xRot;
        if (!this.bleat && this.attacking == 0) {
            this.LEar.xRot = baseAngle + (this.earMov / 57.29578F);
            this.REar.xRot = baseAngle + (this.earMov / 57.29578F);
        }

        this.Nose.xRot = this.Head.xRot;
        this.Mouth.xRot = this.Head.xRot;
        this.Tongue.xRot = this.Head.xRot;
        this.Goatie.xRot = this.Head.xRot;
        this.RHorn1.xRot = this.Head.xRot;
        this.LHorn1.xRot = this.Head.xRot;
        this.RHorn2.xRot = this.Head.xRot;
        this.LHorn2.xRot = this.Head.xRot;
        this.RHorn3.xRot = this.Head.xRot;
        this.LHorn3.xRot = this.Head.xRot;
        this.RHorn4.xRot = this.Head.xRot;
        this.LHorn4.xRot = this.Head.xRot;
        this.RHorn5.xRot = this.Head.xRot;
        this.LHorn5.xRot = this.Head.xRot;
        if (this.bleat)// && !attacking)
        {
            this.Mouth.xRot = 0.0F;
            this.Tongue.xRot = -5 / 57.29578F;
            this.Goatie.xRot = 0.0F;
        }

        this.Nose.yRot = this.Head.yRot;
        this.Mouth.yRot = this.Head.yRot;
        this.Tongue.yRot = this.Head.yRot;
        this.LEar.yRot = this.Head.yRot;
        this.REar.yRot = this.Head.yRot;
        this.Goatie.yRot = this.Head.yRot;
        this.RHorn1.yRot = this.Head.yRot;
        this.LHorn1.yRot = this.Head.yRot;
        this.RHorn2.yRot = this.Head.yRot;
        this.LHorn2.yRot = this.Head.yRot;
        this.RHorn3.yRot = this.Head.yRot;
        this.LHorn3.yRot = this.Head.yRot;
        this.RHorn4.yRot = this.Head.yRot;
        this.LHorn4.yRot = this.Head.yRot;
        this.RHorn5.yRot = this.Head.yRot;
        this.LHorn5.yRot = this.Head.yRot;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        if (this.typeInt > 1 && this.typeInt < 5) {
            this.Tits.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
        matrixStackIn.pushPose();

        if (this.attacking != 0) {
            // float yOff = f5*((2.0F*attacking)/45F)-1.33333F;
            // float zOff = f5*(attacking/9F)-3.33333F;

            // 0.3! yOff = (3F*attacking/450F) - (3F*3F/45F);
            // float yOff = 0.3F;
            // yOff = (3F*attacking/450F) - (3F*3F/45F);
            float yOff = (this.attacking / 150F) - (1F / 5F);
            // float zOff = 0.1F;
            float zOff = (this.attacking / 450F) - (1F / 15F);
            matrixStackIn.translate(0.0F, yOff, -zOff);
        }
        this.LEar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.REar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        if (this.typeInt > 1) {
            if (this.edad > 0.7) {
                this.RHorn1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.LHorn1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
            if (this.edad > 0.8) {
                this.RHorn2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.LHorn2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
        }
        if (this.typeInt > 4) {
            if (this.edad > 0.8) {
                this.RHorn3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.LHorn3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
            if (this.edad > 0.85) {
                this.RHorn4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.LHorn4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
            if (this.edad > 0.9) {
                this.RHorn5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.LHorn5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

                // Goatie.render(f5);
            }
        }
        // mouth movement
        // GL11.glTranslatef(eatMov, 0.0F, 0.0F);
        if (this.eatMov != 0 && !this.bleat) {
            matrixStackIn.translate(this.eatMov / 100F, 0.0F, 0.0F);
        }
        if (this.typeInt > 4 && this.edad > 0.9) {
            this.Goatie.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
        this.Tongue.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Mouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        matrixStackIn.popPose();

    }
}
