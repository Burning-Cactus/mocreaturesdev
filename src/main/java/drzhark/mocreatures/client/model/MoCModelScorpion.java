package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelScorpion<T extends CreatureEntity> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer MouthL;
    ModelRenderer MouthR;
    ModelRenderer Body;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer Tail4;
    ModelRenderer Tail5;
    ModelRenderer Sting1;
    ModelRenderer Sting2;
    ModelRenderer LArm1;
    ModelRenderer LArm2;
    ModelRenderer LArm3;
    ModelRenderer LArm4;
    ModelRenderer RArm1;
    ModelRenderer RArm2;
    ModelRenderer RArm3;
    ModelRenderer RArm4;
    ModelRenderer Leg1A;
    ModelRenderer Leg1B;
    ModelRenderer Leg1C;
    ModelRenderer Leg2A;
    ModelRenderer Leg2B;
    ModelRenderer Leg2C;
    ModelRenderer Leg3A;
    ModelRenderer Leg3B;
    ModelRenderer Leg3C;
    ModelRenderer Leg4A;
    ModelRenderer Leg4B;
    ModelRenderer Leg4C;
    ModelRenderer Leg5A;
    ModelRenderer Leg5B;
    ModelRenderer Leg5C;
    ModelRenderer Leg6A;
    ModelRenderer Leg6B;
    ModelRenderer Leg6C;
    ModelRenderer Leg7A;
    ModelRenderer Leg7B;
    ModelRenderer Leg7C;
    ModelRenderer Leg8A;
    ModelRenderer Leg8B;
    ModelRenderer Leg8C;
    ModelRenderer baby1;
    ModelRenderer baby2;
    ModelRenderer baby3;
    ModelRenderer baby4;
    ModelRenderer baby5;
    protected boolean poisoning;
    protected boolean isTalking;
    protected boolean babies;
    protected int attacking;
    protected boolean sitting;

    float radianF = 57.29578F;

    public MoCModelScorpion() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-5F, 0F, 0F, 10, 5, 13);
        this.Head.setPos(0F, 14F, -9F);

        this.MouthL = new ModelRenderer(this, 18, 58);
        this.MouthL.addBox(-3F, -2F, -1F, 4, 4, 2);
        this.MouthL.setPos(3F, 17F, -9F);
        setRotation(this.MouthL, 0F, -0.3839724F, 0F);

        this.MouthR = new ModelRenderer(this, 30, 58);
        this.MouthR.addBox(-1F, -2F, -1F, 4, 4, 2);
        this.MouthR.setPos(-3F, 17F, -9F);
        setRotation(this.MouthR, 0F, 0.3839724F, 0F);

        this.Body = new ModelRenderer(this, 0, 18);
        this.Body.addBox(-4F, -2F, 0F, 8, 4, 10);
        this.Body.setPos(0F, 17F, 3F);
        setRotation(this.Body, 0.0872665F, 0F, 0F);

        this.Tail1 = new ModelRenderer(this, 0, 32);
        this.Tail1.addBox(-3F, -2F, 0F, 6, 4, 6);
        this.Tail1.setPos(0F, 16F, 12F);
        setRotation(this.Tail1, 0.6108652F, 0F, 0F);

        this.Tail2 = new ModelRenderer(this, 0, 42);
        this.Tail2.addBox(-2F, -2F, 0F, 4, 4, 6);
        this.Tail2.setPos(0F, 13F, 16.5F);
        setRotation(this.Tail2, 1.134464F, 0F, 0F);

        this.Tail3 = new ModelRenderer(this, 0, 52);
        this.Tail3.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail3.setPos(0F, 8F, 18.5F);
        setRotation(this.Tail3, 1.692143F, 0F, 0F);

        this.Tail4 = new ModelRenderer(this, 24, 32);
        this.Tail4.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail4.setPos(0F, 3F, 18F);
        setRotation(this.Tail4, 2.510073F, 0F, 0F);

        this.Tail5 = new ModelRenderer(this, 24, 41);
        this.Tail5.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail5.setPos(0F, -0.2F, 14F);
        setRotation(this.Tail5, 3.067752F, 0F, 0F);

        this.Sting1 = new ModelRenderer(this, 30, 50);
        this.Sting1.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
        this.Sting1.setPos(0F, -1F, 7F);
        setRotation(this.Sting1, 0.4089647F, 0F, 0F);

        this.Sting2 = new ModelRenderer(this, 26, 50);
        this.Sting2.addBox(-0.5F, 0F, 0.5F, 1, 4, 1);
        this.Sting2.setPos(0F, 2.6F, 8.8F);
        setRotation(this.Sting2, -0.2230717F, 0F, 0F);

        this.LArm1 = new ModelRenderer(this, 26, 18);
        this.LArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.LArm1.setPos(5F, 18F, -8F);
        setRotation(this.LArm1, -0.3490659F, 0F, 0.8726646F);

        this.LArm2 = new ModelRenderer(this, 42, 55);
        this.LArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        this.LArm2.setPos(10F, 14F, -6F);
        setRotation(this.LArm2, 0.1745329F, -0.3490659F, -0.2617994F);

        this.LArm3 = new ModelRenderer(this, 42, 39);
        this.LArm3.addBox(-0.5F, -0.5F, -7F, 2, 1, 7);
        this.LArm3.setPos(12F, 15F, -11F);
        setRotation(this.LArm3, 0.2617994F, 0.1570796F, -0.1570796F);

        this.LArm4 = new ModelRenderer(this, 42, 31);
        this.LArm4.addBox(-1.5F, -0.5F, -6F, 1, 1, 7);
        this.LArm4.setPos(11F, 15F, -11F);
        setRotation(this.LArm4, 0.2617994F, 0F, -0.1570796F);

        this.RArm1 = new ModelRenderer(this, 0, 18);
        this.RArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.RArm1.setPos(-5F, 18F, -8F);
        setRotation(this.RArm1, -0.3490659F, 0F, -0.8726646F);

        this.RArm2 = new ModelRenderer(this, 42, 55);
        this.RArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        this.RArm2.setPos(-10F, 14F, -6F);
        setRotation(this.RArm2, 0.1745329F, 0.3490659F, 0.2617994F);

        this.RArm3 = new ModelRenderer(this, 42, 47);
        this.RArm3.addBox(-1.5F, -0.5F, -7F, 2, 1, 7);
        this.RArm3.setPos(-12F, 15F, -11F);
        setRotation(this.RArm3, 0.2617994F, -0.1570796F, 0.1570796F);

        this.RArm4 = new ModelRenderer(this, 42, 31);
        this.RArm4.addBox(0.5F, -0.5F, -6F, 1, 1, 7);
        this.RArm4.setPos(-11F, 15F, -11F);
        setRotation(this.RArm4, 0.2617994F, 0F, 0.1570796F);

        this.Leg1A = new ModelRenderer(this, 38, 0);
        this.Leg1A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg1A.setPos(5F, 18F, -5F);
        setRotationG(this.Leg1A, -10F, 0F, 75F);

        this.Leg1B = new ModelRenderer(this, 50, 0);
        this.Leg1B.addBox(2F, -8F, -1F, 5, 2, 2);
        this.Leg1B.setPos(5F, 18F, -5F);
        setRotationG(this.Leg1B, -10F, 0F, 60F);

        this.Leg1C = new ModelRenderer(this, 52, 16);
        this.Leg1C.addBox(4.5F, -9F, -0.7F, 5, 1, 1);
        this.Leg1C.setPos(5F, 18F, -5F);
        setRotationG(this.Leg1C, -10F, 0F, 75F);

        this.Leg2A = new ModelRenderer(this, 38, 0);
        this.Leg2A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg2A.setPos(5F, 18F, -2F);
        setRotationG(this.Leg2A, -30F, 0F, 70F);

        this.Leg2B = new ModelRenderer(this, 50, 4);
        this.Leg2B.addBox(1F, -8F, -1F, 5, 2, 2);
        this.Leg2B.setPos(5F, 18F, -2F);
        setRotationG(this.Leg2B, -30F, 0F, 60F);

        this.Leg2C = new ModelRenderer(this, 50, 18);
        this.Leg2C.addBox(4F, -8.5F, -1F, 6, 1, 1);
        this.Leg2C.setPos(5F, 18F, -2F);
        setRotationG(this.Leg2C, -30F, 0F, 70F);

        this.Leg3A = new ModelRenderer(this, 38, 0);
        this.Leg3A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg3A.setPos(5F, 17.5F, 1F);
        setRotationG(this.Leg3A, -45F, 0F, 70F);

        this.Leg3B = new ModelRenderer(this, 48, 8);
        this.Leg3B.addBox(1F, -8F, -1F, 6, 2, 2);
        this.Leg3B.setPos(5F, 17.5F, 1F);
        setRotationG(this.Leg3B, -45F, 0F, 60F);

        this.Leg3C = new ModelRenderer(this, 50, 20);
        this.Leg3C.addBox(4.5F, -8.2F, -1.3F, 6, 1, 1);
        this.Leg3C.setPos(5F, 17.5F, 1F);
        setRotationG(this.Leg3C, -45F, 0F, 70F);

        this.Leg4A = new ModelRenderer(this, 38, 0);
        this.Leg4A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg4A.setPos(5F, 17F, 4F);
        setRotationG(this.Leg4A, -60F, 0F, 70F);

        this.Leg4B = new ModelRenderer(this, 46, 12);
        this.Leg4B.addBox(0.5F, -8.5F, -1F, 7, 2, 2);
        this.Leg4B.setPos(5F, 17F, 4F);
        setRotationG(this.Leg4B, -60F, 0F, 60F);

        this.Leg4C = new ModelRenderer(this, 48, 22);
        this.Leg4C.addBox(3.5F, -8.5F, -1.5F, 7, 1, 1);
        this.Leg4C.setPos(5F, 17F, 4F);
        setRotationG(this.Leg4C, -60F, 0F, 70F);

        this.Leg5A = new ModelRenderer(this, 0, 0);
        this.Leg5A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg5A.setPos(-5F, 18F, -5F);
        setRotationG(this.Leg5A, -10F, 0F, -75F);

        this.Leg5B = new ModelRenderer(this, 50, 0);
        this.Leg5B.addBox(-7F, -8F, -1F, 5, 2, 2);
        this.Leg5B.setPos(-5F, 18F, -5F);
        setRotationG(this.Leg5B, -10F, 0F, -60F);

        this.Leg5C = new ModelRenderer(this, 52, 16);
        this.Leg5C.addBox(-9.5F, -9F, -0.7F, 5, 1, 1);
        this.Leg5C.setPos(-5F, 18F, -5F);
        setRotationG(this.Leg5C, -10F, 0F, -75F);

        this.Leg6A = new ModelRenderer(this, 0, 0);
        this.Leg6A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg6A.setPos(-5F, 18F, -2F);
        setRotationG(this.Leg6A, -30F, 0F, -70F);

        this.Leg6B = new ModelRenderer(this, 50, 4);
        this.Leg6B.addBox(-6F, -8F, -1F, 5, 2, 2);
        this.Leg6B.setPos(-5F, 18F, -2F);
        setRotationG(this.Leg6B, -30F, 0F, -60F);

        this.Leg6C = new ModelRenderer(this, 50, 18);
        this.Leg6C.addBox(-10F, -8.5F, -1F, 6, 1, 1);
        this.Leg6C.setPos(-5F, 18F, -2F);
        setRotationG(this.Leg6C, -30F, 0F, -60F);

        this.Leg7A = new ModelRenderer(this, 0, 0);
        this.Leg7A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg7A.setPos(-5F, 17.5F, 1F);
        setRotationG(this.Leg7A, -45F, 0, -70F);

        this.Leg7B = new ModelRenderer(this, 48, 8);
        this.Leg7B.addBox(-7F, -8.5F, -1F, 6, 2, 2);
        this.Leg7B.setPos(-5F, 17.5F, 1F);
        setRotationG(this.Leg7B, -45F, 0F, -60F);

        this.Leg7C = new ModelRenderer(this, 50, 20);
        this.Leg7C.addBox(-10.5F, -8.7F, -1.3F, 6, 1, 1);
        this.Leg7C.setPos(-5F, 17.5F, 1F);
        setRotationG(this.Leg7C, -45F, 0F, -70F);

        this.Leg8A = new ModelRenderer(this, 0, 0);
        this.Leg8A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg8A.setPos(-5F, 17F, 4F);
        setRotationG(this.Leg8A, -60F, 0F, -70F);

        this.Leg8B = new ModelRenderer(this, 46, 12);
        this.Leg8B.addBox(-7.5F, -8.5F, -1F, 7, 2, 2);
        this.Leg8B.setPos(-5F, 17F, 4F);
        setRotationG(this.Leg8B, -60F, 0F, -60F);

        this.Leg8C = new ModelRenderer(this, 48, 22);
        this.Leg8C.addBox(-10.5F, -8.5F, -1.5F, 7, 1, 1);
        this.Leg8C.setPos(-5F, 17F, 4F);
        setRotationG(this.Leg8C, -60F, 0F, -70F);

        this.baby1 = new ModelRenderer(this, 48, 24);
        this.baby1.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby1.setPos(0F, 12F, 0F);

        this.baby2 = new ModelRenderer(this, 48, 24);
        this.baby2.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby2.setPos(-5F, 13.4F, -1F);
        setRotation(this.baby2, 0.4461433F, 2.490967F, 0.5205006F);

        this.baby3 = new ModelRenderer(this, 48, 24);
        this.baby3.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby3.setPos(-2F, 13F, 4F);
        setRotation(this.baby3, 0F, 0.8551081F, 0F);

        this.baby4 = new ModelRenderer(this, 48, 24);
        this.baby4.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby4.setPos(4F, 13F, 2F);
        setRotation(this.baby4, 0F, 2.714039F, -0.3717861F);

        this.baby5 = new ModelRenderer(this, 48, 24);
        this.baby5.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby5.setPos(1F, 13F, 8F);
        setRotation(this.baby5, 0F, -1.189716F, 0F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityIn instanceof MoCEntityScorpion) {
            poisoning = ((MoCEntityScorpion)entityIn).swingingTail();
            isTalking = ((MoCEntityScorpion)entityIn).mouthCounter != 0;
            babies = ((MoCEntityScorpion)entityIn).getHasBabies();
            attacking = ((MoCEntityScorpion)entityIn).armCounter;
        }
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    private void setRotationG(ModelRenderer model, float x, float y, float z) {
        model.xRot = x / this.radianF;
        model.yRot = y / this.radianF;
        model.zRot = z / this.radianF;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.4F * f1;

        if (!poisoning) {
            this.Body.xRot = 5F / this.radianF;
            this.Tail1.xRot = 35F / this.radianF;
            this.Tail1.y = 16F;
            this.Tail1.z = 12F;

            this.Tail2.xRot = 65F / this.radianF;
            this.Tail2.y = 13F;
            this.Tail2.z = 16.5F;

            this.Tail3.xRot = 90F / this.radianF;
            this.Tail3.y = 8F;
            this.Tail3.z = 18.5F;

            this.Tail4.xRot = 143F / this.radianF;
            this.Tail4.y = 3F;
            this.Tail4.z = 18F;

            this.Tail5.xRot = 175F / this.radianF;
            this.Tail5.y = -0.2F;
            this.Tail5.z = 14F;

            this.Sting1.xRot = 24F / this.radianF;
            this.Sting1.y = -1F;
            this.Sting1.z = 7F;

            this.Sting2.xRot = -12F / this.radianF;
            this.Sting2.y = 2.6F;
            this.Sting2.z = 8.8F;

        } else {
            this.Body.xRot = 50F / this.radianF;
            this.Tail1.xRot = 100F / this.radianF;
            this.Tail1.y = 9F;
            this.Tail1.z = 10F;

            this.Tail2.xRot = 160F / this.radianF;
            this.Tail2.y = 3F;
            this.Tail2.z = 9.5F;

            this.Tail3.xRot = -170F / this.radianF;
            this.Tail3.y = 1F;
            this.Tail3.z = 3.5F;

            this.Tail4.xRot = -156F / this.radianF;
            this.Tail4.y = 1.8F;
            this.Tail4.z = -2F;

            this.Tail5.xRot = -154F / this.radianF;
            this.Tail5.y = 3.8F;
            this.Tail5.z = -7F;

            this.Sting1.xRot = -57F / this.radianF;
            this.Sting1.y = 6F;
            this.Sting1.z = -12F;

            this.Sting2.xRot = -93.7F / this.radianF;
            this.Sting2.y = 8F;
            this.Sting2.z = -15.2F;
        }

        /**
         * Mouth animation
         */
        float MouthRot = 0F;
        if (isTalking) {
            MouthRot = MathHelper.cos((f2 * 1.1F)) * 0.2F;
        }
        this.MouthR.yRot = (22F / this.radianF) + MouthRot;
        this.MouthL.yRot = (-22F / this.radianF) - MouthRot;

        //Larm normal position
        this.LArm1.xRot = -20F / this.radianF;
        this.LArm2.x = 10F;
        this.LArm2.y = 14F;
        this.LArm2.z = -6F;
        this.LArm3.x = 12F;
        this.LArm3.y = 15F;
        this.LArm3.z = -11F;
        this.LArm4.x = 11F;
        this.LArm4.y = 15F;
        this.LArm4.z = -11F;
        this.LArm4.yRot = 0F;

        //Rarm normal position
        this.RArm1.xRot = -20F / this.radianF;
        this.RArm2.x = -10F;
        this.RArm2.y = 14F;
        this.RArm2.z = -6F;
        this.RArm3.x = -12F;
        this.RArm3.y = 15F;
        this.RArm3.z = -11F;
        this.RArm4.x = -11F;
        this.RArm4.y = 15F;
        this.RArm4.z = -11F;
        this.RArm4.yRot = 0F;

        /**
         * Random hand animations
         */
        if (attacking == 0) {

            /**
             * LHand random animation
             */
            float lHand = 0F;

            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 20) {
                lHand = f2a / this.radianF;
            }
            this.LArm3.yRot = (9F / this.radianF) - lHand;
            this.LArm4.yRot = +lHand;

            /**
             * RHand random animation
             */
            float RHand = 0F;
            float f2b = f2 % 75F;
            if (f2b > 30 & f2b < 50) {
                RHand = (f2b - 29) / this.radianF;
            }
            this.RArm3.yRot = (-9F / this.radianF) + RHand;
            this.RArm4.yRot = -RHand;
        } else
        //hand attacking sequence
        {
            if (attacking > 0 && attacking < 5) {
                //LArm ahead open
                this.LArm1.xRot = 50F / this.radianF;
                this.LArm2.x = 8F;
                this.LArm2.y = 15F;
                this.LArm2.z = -13F;
                this.LArm3.x = 10F;
                this.LArm3.y = 16F;
                this.LArm3.z = -18F;
                this.LArm4.x = 9F;
                this.LArm4.y = 16F;
                this.LArm4.z = -18F;
                this.LArm4.yRot = 40F / this.radianF;
            }

            if (attacking >= 5 && attacking < 10) {
                //LArm ahead closed
                this.LArm1.xRot = 70F / this.radianF;
                this.LArm2.x = 7F;
                this.LArm2.y = 16F;
                this.LArm2.z = -14F;
                this.LArm3.x = 9F;
                this.LArm3.y = 17F;
                this.LArm3.z = -19F;
                this.LArm4.x = 8F;
                this.LArm4.y = 17F;
                this.LArm4.z = -19F;
                this.LArm4.yRot = 0F;
            }
            if (attacking >= 10 && attacking < 15) {
                //Rarm ahead open
                this.RArm1.xRot = 50F / this.radianF;
                this.RArm2.x = -8F;
                this.RArm2.y = 15F;
                this.RArm2.z = -13F;
                this.RArm3.x = -10F;
                this.RArm3.y = 16F;
                this.RArm3.z = -18F;
                this.RArm4.x = -9F;
                this.RArm4.y = 16F;
                this.RArm4.z = -18F;
                this.RArm4.yRot = -40F / this.radianF;
            }
            if (attacking >= 15 && attacking < 20) {
                //RArm ahead closed
                this.RArm1.xRot = 70F / this.radianF;
                this.RArm2.x = -7F;
                this.RArm2.y = 16F;
                this.RArm2.z = -14F;
                this.RArm3.x = -9F;
                this.RArm3.y = 17F;
                this.RArm3.z = -19F;
                this.RArm4.x = -8F;
                this.RArm4.y = 17F;
                this.RArm4.z = -19F;
                this.RArm4.yRot = 0F;
            }
        }

        /**
         * Babies animation
         */
        if (babies) {
            float fmov = f2 % 100;
            float fb1 = 0F;
            float fb2 = 142F / this.radianF;
            float fb3 = 49F / this.radianF;
            float fb4 = 155F / this.radianF;
            float fb5 = -68F / this.radianF;

            //fb2 -= fmov;
            //fb5 += fmov;
            //fb1 += (fmov/2F);
            //fb3 -= (fmov*2F);
            //fb4 -= fmov;

            if (fmov > 0F && fmov < 20F) {
                fb2 -= (MathHelper.cos((f2 * 0.8F)) * 0.3F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
                fb1 += (MathHelper.cos((f2 * 0.4F)) * 0.4F);
                fb5 += (MathHelper.cos((f2 * 0.7F)) * 0.5F);
            }

            if (fmov > 30F && fmov < 50F) {
                fb4 -= (MathHelper.cos((f2 * 0.8F)) * 0.4F);
                fb1 += (MathHelper.cos((f2 * 0.7F)) * 0.1F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
            }
            if (fmov > 80F) {
                fb5 += (MathHelper.cos((f2 * 0.2F)) * 0.4F);
                fb2 -= (MathHelper.cos((f2 * 0.6F)) * 0.3F);
                fb4 -= (MathHelper.cos((f2 * 0.4F)) * 0.2F);
            }

            /*
             * if (fmov>0F && fmov < 30F) { fb2 -= fmov; fb5 += fmov; } if (fmov
             * > 30F && fmov <70F) { fb1 += (fmov/2F); fb3 -= (fmov*2F); } if
             * (fmov >70F) { fb4 -= fmov; }
             */
            this.baby1.yRot = fb1;///radianF;
            this.baby2.yRot = fb2;///radianF;
            this.baby3.yRot = fb3;///radianF;
            this.baby4.yRot = fb4;///radianF;
            this.baby5.yRot = fb5;///radianF;
        }

        /**
         * floats used for the scorpion's leg animations
         */
        float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
        float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + 3.141593F) * 0.4F) * f1;
        float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + 1.570796F) * 0.4F) * f1;
        float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.141593F) * 0.4F) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.570796F) * 0.4F) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;

        if (sitting) {
            this.Leg1A.xRot = -10F / this.radianF;
            this.Leg1A.zRot = 35F / this.radianF;
            this.Leg1B.zRot = 20F / this.radianF;
            this.Leg1C.zRot = 35F / this.radianF;

            this.Leg2A.xRot = -30F / this.radianF;
            this.Leg2A.zRot = 35F / this.radianF;
            this.Leg2B.zRot = 20F / this.radianF;
            this.Leg2C.zRot = 35F / this.radianF;

            this.Leg3A.xRot = -45F / this.radianF;
            this.Leg3A.zRot = 35F / this.radianF;
            this.Leg3B.zRot = 20F / this.radianF;
            this.Leg3C.zRot = 35F / this.radianF;

            this.Leg4A.xRot = -60F / this.radianF;
            this.Leg4A.zRot = 35F / this.radianF;
            this.Leg4B.zRot = 20F / this.radianF;
            this.Leg4C.zRot = 35F / this.radianF;

            this.Leg5A.xRot = -10F / this.radianF;
            this.Leg5A.zRot = -35F / this.radianF;
            this.Leg5B.zRot = -20F / this.radianF;
            this.Leg5C.zRot = -35F / this.radianF;

            this.Leg6A.xRot = -30F / this.radianF;
            this.Leg6A.zRot = -35F / this.radianF;
            this.Leg6B.zRot = -20F / this.radianF;
            this.Leg6C.zRot = -35F / this.radianF;

            this.Leg7A.xRot = -45F / this.radianF;
            this.Leg7A.zRot = -35F / this.radianF;
            this.Leg7B.zRot = -20F / this.radianF;
            this.Leg7C.zRot = -35F / this.radianF;

            this.Leg8A.xRot = -60F / this.radianF;
            this.Leg8A.zRot = -35F / this.radianF;
            this.Leg8B.zRot = -20F / this.radianF;
            this.Leg8C.zRot = -35F / this.radianF;

        } else {
            this.Leg1A.xRot = -10F / this.radianF;
            this.Leg1A.zRot = 75F / this.radianF;
            this.Leg1B.zRot = 60F / this.radianF;
            this.Leg1C.zRot = 75F / this.radianF;

            this.Leg1A.xRot += f9;
            this.Leg1B.xRot = this.Leg1A.xRot;
            this.Leg1C.xRot = this.Leg1A.xRot;

            this.Leg1A.zRot += f13;
            this.Leg1B.zRot += f13;
            this.Leg1C.zRot += f13;

            this.Leg2A.xRot = -30F / this.radianF;
            this.Leg2A.zRot = 70F / this.radianF;
            this.Leg2B.zRot = 60F / this.radianF;
            this.Leg2C.zRot = 70F / this.radianF;

            this.Leg2A.xRot += f10;
            this.Leg2B.xRot = this.Leg2A.xRot;
            this.Leg2C.xRot = this.Leg2A.xRot;

            this.Leg2A.zRot += f14;
            this.Leg2B.zRot += f14;
            this.Leg2C.zRot += f14;

            this.Leg3A.xRot = -45F / this.radianF;
            this.Leg3A.zRot = 70F / this.radianF;
            this.Leg3B.zRot = 60F / this.radianF;
            this.Leg3C.zRot = 70F / this.radianF;

            this.Leg3A.xRot += f11;
            this.Leg3B.xRot = this.Leg3A.xRot;
            this.Leg3C.xRot = this.Leg3A.xRot;

            this.Leg3A.zRot += f15;
            this.Leg3B.zRot += f15;
            this.Leg3C.zRot += f15;

            this.Leg4A.xRot = -60F / this.radianF;
            this.Leg4A.zRot = 70F / this.radianF;
            this.Leg4B.zRot = 60F / this.radianF;
            this.Leg4C.zRot = 70F / this.radianF;

            this.Leg4A.xRot += f12;
            this.Leg4B.xRot = this.Leg4A.xRot;
            this.Leg4C.xRot = this.Leg4A.xRot;

            this.Leg4A.zRot += f16;
            this.Leg4B.zRot += f16;
            this.Leg4C.zRot += f16;

            this.Leg5A.xRot = -10F / this.radianF;
            this.Leg5A.zRot = -75F / this.radianF;
            this.Leg5B.zRot = -60F / this.radianF;
            this.Leg5C.zRot = -75F / this.radianF;

            this.Leg5A.xRot -= f9;
            this.Leg5B.xRot = this.Leg5A.xRot;
            this.Leg5C.xRot = this.Leg5A.xRot;

            this.Leg5A.zRot -= f13;
            this.Leg5B.zRot -= f13;
            this.Leg5C.zRot -= f13;

            this.Leg6A.xRot = -30F / this.radianF;
            this.Leg6A.zRot = -70F / this.radianF;
            this.Leg6B.zRot = -60F / this.radianF;
            this.Leg6C.zRot = -70F / this.radianF;

            this.Leg6A.xRot -= f10;
            this.Leg6B.xRot = this.Leg6A.xRot;
            this.Leg6C.xRot = this.Leg6A.xRot;

            this.Leg6A.zRot -= f14;
            this.Leg6B.zRot -= f14;
            this.Leg6C.zRot -= f14;

            this.Leg7A.xRot = -45F / this.radianF;
            this.Leg7A.zRot = -70F / this.radianF;
            this.Leg7B.zRot = -60F / this.radianF;
            this.Leg7C.zRot = -70F / this.radianF;

            this.Leg7A.xRot -= f11;
            this.Leg7B.xRot = this.Leg7A.xRot;
            this.Leg7C.xRot = this.Leg7A.xRot;

            this.Leg7A.zRot -= f15;
            this.Leg7B.zRot -= f15;
            this.Leg7C.zRot -= f15;

            this.Leg8A.xRot = -60F / this.radianF;
            this.Leg8A.zRot = -70F / this.radianF;
            this.Leg8B.zRot = -60F / this.radianF;
            this.Leg8C.zRot = -70F / this.radianF;

            this.Leg8A.xRot -= f12;
            this.Leg8B.xRot = this.Leg8A.xRot;
            this.Leg8C.xRot = this.Leg8A.xRot;

            this.Leg8A.zRot -= f16;
            this.Leg8B.zRot -= f16;
            this.Leg8C.zRot -= f16;
        }

    }

    protected void renderParts(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.MouthL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.MouthR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Sting1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Sting2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LArm1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LArm2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LArm3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LArm4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RArm1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RArm2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RArm3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RArm4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg1A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg1B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg1C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg2A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg2B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg2C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg3A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg3B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg3C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg4A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg4B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg4C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg5A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg5B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg5C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg6A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg6B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg6C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg7A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg7B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg7C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg8A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg8B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Leg8C.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        if (babies) {
            this.baby1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.baby2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.baby3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.baby4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.baby5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderParts(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
