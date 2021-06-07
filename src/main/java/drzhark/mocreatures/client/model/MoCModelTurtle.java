package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelTurtle<T extends MoCEntityTurtle> extends EntityModel<T> {

    public MoCModelTurtle() {
        this.Shell = new ModelRenderer(this, 28, 0);
        this.Shell.addBox(0F, 0F, 0F, 9, 1, 9);
        this.Shell.setPos(-4.5F, 19F, -4.5F);

        this.ShellUp = new ModelRenderer(this, 0, 22);
        this.ShellUp.addBox(0F, 0F, 0F, 8, 2, 8);
        this.ShellUp.setPos(-4F, 17F, -4F);

        this.ShellTop = new ModelRenderer(this, 40, 10);
        this.ShellTop.addBox(0F, 0F, 0F, 6, 1, 6);
        this.ShellTop.setPos(-3F, 16F, -3F);

        this.Belly = new ModelRenderer(this, 0, 12);
        this.Belly.addBox(0F, 0F, 0F, 8, 1, 8);
        this.Belly.setPos(-4F, 20F, -4F);

        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg1.setPos(3.5F, 20F, -3.5F);

        this.Leg2 = new ModelRenderer(this, 0, 9);
        this.Leg2.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg2.setPos(-3.5F, 20F, -3.5F);

        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg3.setPos(3.5F, 20F, 3.5F);

        this.Leg4 = new ModelRenderer(this, 0, 9);
        this.Leg4.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg4.setPos(-3.5F, 20F, 3.5F);

        this.Head = new ModelRenderer(this, 10, 0);
        this.Head.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        this.Head.setPos(0F, 20F, -4.5F);

        this.Tail = new ModelRenderer(this, 0, 5);
        this.Tail.addBox(-1F, -1F, 0F, 2, 1, 3);
        this.Tail.setPos(0F, 21F, 4F);

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntityTurtle entityturtle = (MoCEntityTurtle) entityIn;
        this.TMNT = entityturtle.isTMNT();
        this.turtleHat = entityturtle.getVehicle() != null;
        this.isSwimming = entityturtle.isInWater();
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        if (this.upsidedown) {
            float f25 = this.swingProgress;
            float f26 = f25;
            if (f25 >= 0.8F) {
                f26 = (0.6F - (f25 - 0.8F));
            }
            if (f26 > 1.6F) {
                f26 = 1.6F;
            }
            if (f26 < -1.6F) {
                f26 = -1.6F;
            }
            this.Leg1.xRot = 0F - f26;
            this.Leg2.xRot = 0F + f26;
            this.Leg3.xRot = 0F + f26;
            this.Leg4.xRot = 0F - f26;
            this.Tail.yRot = 0F - f26;

        } else if (this.turtleHat) {
            this.Leg1.xRot = 0F;
            this.Leg2.xRot = 0F;
            this.Leg3.xRot = 0F;
            this.Leg4.xRot = 0F;
            this.Tail.yRot = 0F;
        } else if (this.isSwimming) {
            float swimmLegs = MathHelper.cos(f * 0.5F) * 6.0F * f1;
            this.Leg1.xRot = -1.2F;
            this.Leg1.yRot = -1.2F + swimmLegs;
            this.Leg2.xRot = -1.2F;
            this.Leg2.yRot = 1.2F - swimmLegs;
            this.Leg3.xRot = 1.2F;
            this.Leg4.xRot = 1.2F;//swimmLegs;
            this.Tail.yRot = 0F;
        } else {
            this.Leg1.xRot = MathHelper.cos(f * 2.0F) * 2.0F * f1;
            this.Leg2.xRot = MathHelper.cos((f * 2.0F) + 3.141593F) * 2.0F * f1;
            this.Leg3.xRot = MathHelper.cos((f * 2.0F) + 3.141593F) * 2.0F * f1;
            this.Leg4.xRot = MathHelper.cos(f * 2.0F) * 2.0F * f1;
            this.Tail.yRot = MathHelper.cos(f * 0.6662F) * 0.7F * f1;

            this.Leg1.yRot = 0F;
            this.Leg2.yRot = 0F;
        }

        if (this.isHiding && !this.isSwimming) {
            this.Head.xRot = 0F;
            this.Head.yRot = 0F;

            //Leg1.setRotationPoint(2.9F, 18.5F, -2.9F);
            this.Leg1.x = 2.9F;
            this.Leg1.y = 18.5F;
            this.Leg1.z = -2.9F;

            //Leg2.setRotationPoint(-2.9F, 18.5F, -2.9F);
            this.Leg2.x = -2.9F;
            this.Leg2.y = 18.5F;
            this.Leg2.z = -2.9F;

            //Leg3.setRotationPoint(2.9F, 18.5F, 2.9F);
            this.Leg3.x = 2.9F;
            this.Leg3.y = 18.5F;
            this.Leg3.z = 2.9F;

            //Leg4.setRotationPoint(-2.9F, 18.5F, 2.9F);
            this.Leg4.x = -2.9F;
            this.Leg4.y = 18.5F;
            this.Leg4.z = 2.9F;

            //Head.setRotationPoint(0F, 20F, -1F);
            //Head.rotationPointX = 0F;
            this.Head.y = 19.5F;
            this.Head.z = -1F;

            //Tail.setRotationPoint(0F, 21F, 2F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            this.Tail.z = 2F;
        } else {
            this.Head.xRot = f4 / 57.29578F;
            this.Head.yRot = f3 / 57.29578F;

            //Leg1.setRotationPoint(3.5F, 20F, -3.5F);
            this.Leg1.x = 3.5F;
            this.Leg1.y = 20F;
            this.Leg1.z = -3.5F;

            //Leg2.setRotationPoint(-3.5F, 20F, -3.5F);
            this.Leg2.x = -3.5F;
            this.Leg2.y = 20F;
            this.Leg2.z = -3.5F;

            //Leg3.setRotationPoint(3.5F, 20F, 3.5F);
            this.Leg3.x = 3.5F;
            this.Leg3.y = 20F;
            this.Leg3.z = 3.5F;

            //Leg4.setRotationPoint(-3.5F, 20F, 3.5F);
            this.Leg4.x = -3.5F;
            this.Leg4.y = 20F;
            this.Leg4.z = 3.5F;

            //Head.setRotationPoint(0F, 20F, -4.5F);
            //Head.rotationPointX = 0F;
            this.Head.y = 20F;
            this.Head.z = -4.5F;

            //Tail.setRotationPoint(0F, 21F, 4F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            this.Tail.z = 4F;
        }
    }

    ModelRenderer Shell;
    ModelRenderer ShellUp;
    ModelRenderer ShellTop;
    ModelRenderer Belly;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Head;
    ModelRenderer Tail;
    public boolean isHiding;
    public boolean upsidedown;
    private boolean turtleHat;
    private boolean TMNT;
    private boolean isSwimming;
    public float swingProgress;

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Shell.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.ShellUp.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (!this.TMNT) {
            this.ShellTop.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        this.Belly.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
