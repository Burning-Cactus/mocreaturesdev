package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelKomodo extends EntityModel<MoCEntityKomodo> {

    //ModelRenderer TongueDown;
    ModelRenderer Tail;
    ModelRenderer Tail4;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer LegBackLeft1;
    ModelRenderer Neck;
    ModelRenderer Head;
    ModelRenderer Chest;
    ModelRenderer LegBackRight1;
    ModelRenderer LegFrontLeft1;
    ModelRenderer LegFrontLeft3;
    ModelRenderer LegFrontRight1;
    ModelRenderer LegBackLeft2;
    ModelRenderer LegFrontRight2;
    ModelRenderer LegFrontLeft2;
    ModelRenderer LegBackRight2;
    ModelRenderer LegFrontRight3;
    ModelRenderer LegBackLeft3;
    ModelRenderer LegBackRight3;
    ModelRenderer Abdomen;
    ModelRenderer Mouth;
    //ModelRenderer TongeUp;
    ModelRenderer LegFrontLeft;
    ModelRenderer LegBackLeft;
    ModelRenderer LegFrontRight;
    ModelRenderer LegBackRight;
    ModelRenderer Nose;
    ModelRenderer Tongue;

    ModelRenderer SaddleA;
    ModelRenderer SaddleC;
    ModelRenderer SaddleB;
    private float radianF = 57.29578F;
    private boolean rideable;

    public MoCModelKomodo() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.Head = new ModelRenderer(this);
        this.Head.setPos(0F, 13F, -8F);

        this.Neck = new ModelRenderer(this, 22, 34);
        this.Neck.addBox(-2F, 0F, -6F, 4, 5, 6);
        this.Neck.setPos(0F, 0F, 0F);
        this.Head.addChild(this.Neck);
        //setRotation(Neck, 0.1919862F, 0F, 0F);

        this.Nose = new ModelRenderer(this, 24, 45);
        this.Nose.addBox(-1.5F, -1F, -6.5F, 3, 2, 6);
        this.Nose.setPos(0F, 1F, -5F);
        this.Neck.addChild(this.Nose);
        //setRotation(Head, 0.3778793F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 0, 12);
        this.Mouth.addBox(-1F, -0.3F, -5F, 2, 1, 6);
        this.Mouth.setPos(0F, 3F, -5.8F);
        this.Neck.addChild(this.Mouth);
        //setRotation(Mouth, 0.2291648F, 0F, 0F);

        this.Tongue = new ModelRenderer(this, 48, 44);
        this.Tongue.addBox(-1.5F, 0.0F, -5F, 3, 0, 5);
        this.Tongue.setPos(0F, -0.4F, -4.7F);
        this.Mouth.addChild(this.Tongue);

        this.Chest = new ModelRenderer(this, 36, 2);
        this.Chest.addBox(-3F, 0F, -8F, 6, 6, 7);
        this.Chest.setPos(0F, 13F, 0F);

        this.Abdomen = new ModelRenderer(this, 36, 49);
        this.Abdomen.addBox(-3F, 0F, -1F, 6, 7, 8);
        this.Abdomen.setPos(0F, 13F, 0F);

        this.Tail = new ModelRenderer(this);
        this.Tail.setPos(0F, 13F, 7F);

        this.Tail1 = new ModelRenderer(this, 0, 21);
        this.Tail1.addBox(-2F, 0F, 0F, 4, 5, 8);
        this.Tail1.setPos(0F, 0F, 0F);
        //setRotation(Tail1, -0.2724366F, 0F, 0F);
        this.Tail.addChild(this.Tail1);

        this.Tail2 = new ModelRenderer(this, 0, 34);
        this.Tail2.addBox(-1.5F, 0F, 0F, 3, 4, 8);
        this.Tail2.setPos(0F, 0.1F, 7.7F);
        //setRotation(Tail2, -0.5698655F, 0F, 0F);
        this.Tail1.addChild(this.Tail2);

        this.Tail3 = new ModelRenderer(this, 0, 46);
        this.Tail3.addBox(-1F, 0F, 0F, 2, 3, 8);
        this.Tail3.setPos(0F, 0.1F, 7.3F);
        //setRotation(Tail3, -0.3361566F, 0F, 0F);
        this.Tail2.addChild(this.Tail3);

        this.Tail4 = new ModelRenderer(this, 24, 21);
        this.Tail4.addBox(-0.5F, 0F, 0F, 1, 2, 8);
        this.Tail4.setPos(0F, 0.1F, 7F);
        //setRotation(Tail4, -0.1502636F, 0F, 0F);
        this.Tail3.addChild(this.Tail4);

        this.LegFrontLeft = new ModelRenderer(this);
        this.LegFrontLeft.setPos(2F, 17F, -7F);

        this.LegFrontLeft1 = new ModelRenderer(this, 0, 0);
        this.LegFrontLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
        this.LegFrontLeft1.setPos(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegFrontLeft.addChild(this.LegFrontLeft1);

        this.LegFrontLeft2 = new ModelRenderer(this, 22, 0);
        this.LegFrontLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegFrontLeft2.setPos(3F, 0.5F, 0F);
        this.LegFrontLeft1.addChild(this.LegFrontLeft2);

        this.LegFrontLeft3 = new ModelRenderer(this, 16, 58);
        this.LegFrontLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegFrontLeft3.setPos(0F, 4F, 0F);
        setRotation(this.LegFrontLeft3, 0F, -10F / this.radianF, 0F);
        this.LegFrontLeft2.addChild(this.LegFrontLeft3);

        this.LegBackLeft = new ModelRenderer(this);
        this.LegBackLeft.setPos(2F, 17F, 6F);

        this.LegBackLeft1 = new ModelRenderer(this, 0, 0);
        this.LegBackLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
        this.LegBackLeft1.setPos(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegBackLeft.addChild(this.LegBackLeft1);

        this.LegBackLeft2 = new ModelRenderer(this, 22, 0);
        this.LegBackLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegBackLeft2.setPos(3F, 0.5F, 0F);
        this.LegBackLeft1.addChild(this.LegBackLeft2);

        this.LegBackLeft3 = new ModelRenderer(this, 16, 58);
        this.LegBackLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegBackLeft3.setPos(0F, 4F, 0F);
        setRotation(this.LegBackLeft3, 0F, -10F / this.radianF, 0F);
        this.LegBackLeft2.addChild(this.LegBackLeft3);

        this.LegFrontRight = new ModelRenderer(this);
        this.LegFrontRight.setPos(-2F, 17F, -7F);

        this.LegFrontRight1 = new ModelRenderer(this, 0, 6);
        this.LegFrontRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
        this.LegFrontRight1.setPos(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 10F/radianF0.5235988F);
        this.LegFrontRight.addChild(this.LegFrontRight1);

        this.LegFrontRight2 = new ModelRenderer(this, 22, 7);
        this.LegFrontRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegFrontRight2.setPos(-3F, 0.5F, 0F);
        this.LegFrontRight1.addChild(this.LegFrontRight2);

        this.LegFrontRight3 = new ModelRenderer(this, 0, 58);
        this.LegFrontRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegFrontRight3.setPos(0F, 4F, 0F);
        setRotation(this.LegFrontRight3, 0F, 10F / this.radianF, 0F);
        this.LegFrontRight2.addChild(this.LegFrontRight3);

        this.LegBackRight = new ModelRenderer(this);
        this.LegBackRight.setPos(-2F, 17F, 6F);

        this.LegBackRight1 = new ModelRenderer(this, 0, 6);
        this.LegBackRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
        this.LegBackRight1.setPos(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegBackRight.addChild(this.LegBackRight1);

        this.LegBackRight2 = new ModelRenderer(this, 22, 7);
        this.LegBackRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegBackRight2.setPos(-3F, 0.5F, 0F);
        this.LegBackRight1.addChild(this.LegBackRight2);

        this.LegBackRight3 = new ModelRenderer(this, 0, 58);
        this.LegBackRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegBackRight3.setPos(0F, 4F, 0F);
        setRotation(this.LegBackRight3, 0F, 10F / this.radianF, 0F);
        this.LegBackRight2.addChild(this.LegBackRight3);

        this.SaddleA = new ModelRenderer(this, 36, 28);
        this.SaddleA.addBox(-2.5F, 0.5F, -4F, 5, 1, 8);
        this.SaddleA.setPos(0F, 12F, 0F);
        this.SaddleA.setTexSize(64, 64);
        this.SaddleA.mirror = true;
        setRotation(this.SaddleA, 0F, 0F, 0F);
        this.SaddleC = new ModelRenderer(this, 36, 37);
        this.SaddleC.addBox(-2.5F, 0F, 2F, 5, 1, 2);
        this.SaddleC.setPos(0F, 12F, 0F);
        this.SaddleC.setTexSize(64, 64);
        this.SaddleC.mirror = true;
        setRotation(this.SaddleC, 0F, 0F, 0F);
        this.SaddleB = new ModelRenderer(this, 54, 37);
        this.SaddleB.addBox(-1.5F, 0F, -4F, 3, 1, 2);
        this.SaddleB.setPos(0F, 12F, 0F);
        this.SaddleB.setTexSize(64, 64);
        this.SaddleB.mirror = true;
        setRotation(this.SaddleB, 0F, 0F, 0F);
    }

    @Override
    public void setupAnim(MoCEntityKomodo entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
                //int type = komodo.getType();
        //byte harness = komodo.getHarness();
        //byte storage = komodo.getStorage();
        boolean mouth = (entityIn.mouthCounter != 0);
        boolean sitting = (entityIn.getIsSitting());
        boolean swimming = (entityIn.isSwimming());
        boolean moveTail = (entityIn.tailCounter != 0);
        boolean tongue = (entityIn.tongueCounter != 0);
        this.rideable = entityIn.getIsRideable();
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, sitting, moveTail, tongue, mouth, swimming);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    /**
     * Used to adjust the Y offset of the model cubes
     */
    private void AdjustY(float f) {
        float yOff = f;
        this.Tail.y = yOff + 13F;
        this.Head.y = yOff + 13F;
        this.Chest.y = yOff + 13F;
        this.LegFrontLeft.y = yOff + 17F;
        this.LegBackLeft.y = yOff + 17F;
        this.LegFrontRight.y = yOff + 17F;
        this.LegBackRight.y = yOff + 17F;
        this.Abdomen.y = yOff + 13F;
        this.SaddleA.y = yOff + 12F;
        this.SaddleB.y = yOff + 12F;
        this.SaddleC.y = yOff + 12F;

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, boolean sitting, boolean movetail, boolean tongue,
            boolean mouth, boolean swimming) {
        float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
        float LLegXRot = MathHelper.cos(f * 1.2F) * 1.2F * f1;
        float RLegXRot = MathHelper.cos((f * 1.2F) + 3.141593F) * 1.2F * f1;

        if (f3 > 60F) {
            f3 = 60F;
        }
        if (f3 < -60F) {
            f3 = -60F;
        }

        float f10 = 0F;
        if (swimming) {
            f10 = 4F;
            this.Tail1.xRot = (0F / this.radianF) - TailXRot;
            this.LegFrontLeft1.zRot = 0F / this.radianF;
            this.LegFrontLeft2.zRot = -65F / this.radianF;
            this.LegFrontLeft1.yRot = -80F / this.radianF;

            this.LegBackLeft1.zRot = 0F / this.radianF;
            this.LegBackLeft2.zRot = -65F / this.radianF;
            this.LegBackLeft1.yRot = -80F / this.radianF;

            this.LegFrontRight1.zRot = 0F / this.radianF;
            this.LegFrontRight2.zRot = 65F / this.radianF;
            this.LegFrontRight1.yRot = 80F / this.radianF;

            this.LegBackRight1.zRot = 0F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.zRot = 65F / this.radianF;
            this.LegBackRight1.yRot = 80F / this.radianF;
        } else if (sitting) {
            f10 = 4F;
            this.Tail1.xRot = (-5F / this.radianF) - TailXRot;
            this.LegFrontLeft1.zRot = -30F / this.radianF;
            this.LegFrontLeft2.zRot = 0F / this.radianF;
            this.LegFrontLeft1.yRot = 0F;//LLegXRot;

            this.LegBackLeft1.zRot = 0F / this.radianF;
            this.LegBackLeft2.zRot = -65F / this.radianF;
            this.LegBackLeft1.yRot = -40F / this.radianF;

            this.LegFrontRight1.zRot = 30F / this.radianF;
            this.LegFrontRight2.zRot = 0F / this.radianF;
            this.LegFrontRight1.yRot = 0F;//-RLegXRot;

            this.LegBackRight1.zRot = 0F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.zRot = 65F / this.radianF;
            this.LegBackRight1.yRot = 40F / this.radianF;
        } else {
            this.Tail1.xRot = (-15F / this.radianF) - TailXRot;
            this.LegFrontLeft1.zRot = 30F / this.radianF;
            this.LegFrontLeft2.zRot = -30F / this.radianF;
            this.LegFrontLeft1.yRot = LLegXRot;
            this.LegFrontLeft2.xRot = -LLegXRot;

            this.LegBackLeft1.zRot = 30F / this.radianF;
            this.LegBackLeft2.zRot = -30F / this.radianF;
            this.LegBackLeft1.yRot = RLegXRot;
            this.LegBackLeft2.xRot = -RLegXRot;

            this.LegFrontRight1.zRot = -30F / this.radianF;
            this.LegFrontRight2.zRot = 30F / this.radianF;
            this.LegFrontRight1.yRot = -RLegXRot;
            this.LegFrontRight2.xRot = -RLegXRot;

            this.LegBackRight1.zRot = -30F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.zRot = 30F / this.radianF;
            this.LegBackRight1.yRot = -LLegXRot;
            this.LegBackRight2.xRot = -LLegXRot;
        }
        AdjustY(f10);

        float tongueF = 0;
        if (!mouth && tongue) {
            tongueF = (MathHelper.cos(f2 * 3F) / 10F);
            this.Tongue.z = -4.7F;
        } else {
            this.Tongue.z = 0.3F;
        }

        float mouthF = 0;
        if (mouth) {
            mouthF = 35F / this.radianF;
            this.Tongue.z = -0.8F;
        }

        this.Neck.xRot = 11F / this.radianF + (f4 * 0.33F / this.radianF);
        this.Nose.xRot = 10.6F / this.radianF + (f4 * 0.66F / this.radianF);
        this.Mouth.xRot = mouthF + (-3F / this.radianF) + (f4 * 0.66F / this.radianF);
        this.Tongue.xRot = tongueF;

        this.Neck.yRot = (f3 * 0.33F / this.radianF);
        this.Nose.yRot = (f3 * 0.66F / this.radianF);
        this.Mouth.yRot = (f3 * 0.66F / this.radianF);
        //Tail2.rotateAngleY = LLegXRot;

        //y = A * sin(w * t - k *x)
        /*
         * w1 = speed of wave propagation +/- as needed t = time k = number of
         * waves A = amplitude of wave (how much will it depart from center x =
         * position? :)A 1.3k 0.5w -3.5
         */

        this.Tail2.xRot = (-17F / this.radianF) + TailXRot;
        this.Tail3.xRot = (13F / this.radianF) + TailXRot;
        this.Tail4.xRot = (11F / this.radianF) + TailXRot;

        float t = f / 2;

        if (movetail) {
            t = f2 / 4F;
        }
        float A = 0.35F;//0.8F;
        float w = 0.6F;
        float k = 0.6F;

        int i = 0;
        float tailLat = 0F;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail1.yRot = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail2.yRot = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail3.yRot = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail4.yRot = tailLat;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LegFrontLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LegBackLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LegFrontRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LegBackRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        if (this.rideable) {
            this.SaddleA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
    }
}
