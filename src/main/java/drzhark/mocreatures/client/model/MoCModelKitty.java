package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCModelKitty extends EntityModel<MoCEntityKitty> {

    public boolean isSitting;
    public boolean isSwinging;
    public float swingProgress;
    public int kittystate;
    public ModelRenderer headParts[];
    public ModelRenderer tail;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    private ModelRenderer body;

    public MoCModelKitty() {
        this(0.0F);
    }

    public MoCModelKitty(float f) {
        this(f, 0.0F);
    }

    public MoCModelKitty(float f, float f1) {
        this.headParts = new ModelRenderer[10];
        this.headParts[0] = new ModelRenderer(this, 16, 0);
        this.headParts[0].addBox(-2F, -5F, -3F, 1, 1, 1, f);
        this.headParts[0].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[1] = new ModelRenderer(this, 16, 0);
        this.headParts[1].mirror = true;
        this.headParts[1].addBox(1.0F, -5F, -3F, 1, 1, 1, f);
        this.headParts[1].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[2] = new ModelRenderer(this, 20, 0);
        this.headParts[2].addBox(-2.5F, -4F, -3F, 2, 1, 1, f);
        this.headParts[2].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[3] = new ModelRenderer(this, 20, 0);
        this.headParts[3].mirror = true;
        this.headParts[3].addBox(0.5F, -4F, -3F, 2, 1, 1, f);
        this.headParts[3].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[4] = new ModelRenderer(this, 40, 0);
        this.headParts[4].addBox(-4F, -1.5F, -5F, 3, 3, 1, f);
        this.headParts[4].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[5] = new ModelRenderer(this, 40, 0);
        this.headParts[5].mirror = true;
        this.headParts[5].addBox(1.0F, -1.5F, -5F, 3, 3, 1, f);
        this.headParts[5].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[6] = new ModelRenderer(this, 21, 6);
        this.headParts[6].addBox(-1F, -1F, -5F, 2, 2, 1, f);
        this.headParts[6].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[7] = new ModelRenderer(this, 50, 0);
        this.headParts[7].addBox(-2.5F, 0.5F, -1F, 5, 4, 1, f);
        this.headParts[7].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[8] = new ModelRenderer(this, 60, 0);
        this.headParts[8].addBox(-1.5F, -2F, -4.1F, 3, 1, 1, f);
        this.headParts[8].setPos(0.0F, 0.0F + f1, -2F);
        this.headParts[9] = new ModelRenderer(this, 1, 1);
        this.headParts[9].addBox(-2.5F, -3F, -4F, 5, 4, 4, f);
        this.headParts[9].setPos(0.0F, 0.0F + f1, -2F);

        this.body = new ModelRenderer(this, 20, 0);
        this.body.addBox(-2.5F, -2F, -0F, 5, 5, 10, f);
        this.body.setPos(0.0F, 0.0F + f1, -2F);
        this.rightArm = new ModelRenderer(this, 0, 9);
        this.rightArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.rightArm.setPos(-1.5F, 3F + f1, -1F);
        this.leftArm = new ModelRenderer(this, 0, 9);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.leftArm.setPos(1.5F, 3F + f1, -1F);
        this.rightLeg = new ModelRenderer(this, 8, 9);
        this.rightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.rightLeg.setPos(-1.5F, 3F + f1, 7F);
        this.leftLeg = new ModelRenderer(this, 8, 9);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.leftLeg.setPos(1.5F, 3F + f1, 7F);
        this.tail = new ModelRenderer(this, 16, 9);
        this.tail.mirror = true;
        this.tail.addBox(-0.5F, -8F, -1F, 1, 8, 1, f);
        this.tail.setPos(0.0F, -0.5F + f1, 7.5F);
    }

    @Override
    public void setupAnim(MoCEntityKitty entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.isSitting = entityIn.getIsSitting();
        this.isSwinging = entityIn.getIsSwinging();
        this.swingProgress = entityIn.attackAnim;
        this.kittystate = entityIn.getKittyState();
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        this.headParts[9].yRot = f3 / 57.29578F;
        this.headParts[9].xRot = f4 / 57.29578F;
        for (int i = 0; i < 9; i++) {
            this.headParts[i].yRot = this.headParts[9].yRot;
            this.headParts[i].xRot = this.headParts[9].xRot;
        }

        this.rightArm.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 2.0F * f1 * 0.5F;
        this.leftArm.xRot = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftLeg.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        if (this.isSwinging) {
            this.rightArm.xRot = -2F + this.swingProgress;
            this.rightArm.yRot = 2.25F - (this.swingProgress * 2.0F);
        } else {
            this.rightArm.yRot = 0.0F;
        }
        this.leftArm.yRot = 0.0F;
        this.tail.xRot = -0.5F;
        this.tail.zRot = this.leftLeg.xRot * 0.625F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.pushPose();
        if (this.isSitting) {
            matrixStackIn.translate(0.0F, 0.25F, 0.0F);
            this.tail.zRot = 0.0F;
            this.tail.xRot = -2.3F;
        }
        //this.bipedHead.render(f5);
        for (int i = 0; i < 7; i++) {
            this.headParts[i].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.kittystate > 2) {
            this.headParts[7].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
        if (this.kittystate == 12) {
            this.headParts[8].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
        this.headParts[9].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        if (this.isSitting) {
            matrixStackIn.translate(0.0F, 0.0625F, 0.0625F);
            float f6 = -1.570796F;
            this.rightArm.xRot = f6;
            this.leftArm.xRot = f6;
            this.rightLeg.xRot = f6;
            this.leftLeg.xRot = f6;
            this.rightLeg.yRot = 0.1F;
            this.leftLeg.yRot = -0.1F;
        }
        this.rightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.leftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.popPose();
    }
}
