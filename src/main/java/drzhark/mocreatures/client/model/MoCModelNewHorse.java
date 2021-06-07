// TODO
// open mouth animation (done)
// eating animation
// change mane to the right center (done)
// change mule ears to the right center (done)
// bridles to be different when not ridden (done)
// legsaddles to extend when ridden (done)
// butterfly wings animation
// pegasus wing animation (done)
// pegasus resting wings (done)
// butterfly resting wings
// special fx for fairy horses (magic items), zombie horses(flies), ghost horses
// (?), unicorns (?), pegasus (?)
// articulated legs (done)
// frisky horse animation (done)
// stay animation (neck down) (done)
// when flying, the legs should stop moving and flip backwards or be flexed at
// the knee (done)

// flying pegasus:
// about 15-20 degrees horizontal
// about 45 when abduction
// about -15 when adduction
// if flying and not flapping, keep wings horizontal with small tremor and
// perhaps variation of elbow extension
// when user jumps, flap wings fully
// flap wings rarely as well

package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCModelNewHorse<T extends CreatureEntity> extends AgeableModel<T> {

    ModelRenderer Head;
    ModelRenderer UMouth;
    ModelRenderer LMouth;
    ModelRenderer UMouth2;
    ModelRenderer LMouth2;
    ModelRenderer Unicorn;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer MuleEarL;
    ModelRenderer MuleEarR;
    ModelRenderer Neck;
    ModelRenderer HeadSaddle;
    ModelRenderer Mane;

    ModelRenderer Body;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;

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

    ModelRenderer Bag1;
    ModelRenderer Bag2;

    ModelRenderer Saddle;
    ModelRenderer SaddleB;
    ModelRenderer SaddleC;

    ModelRenderer SaddleL;
    ModelRenderer SaddleL2;

    ModelRenderer SaddleR;
    ModelRenderer SaddleR2;

    ModelRenderer SaddleMouthL;
    ModelRenderer SaddleMouthR;

    ModelRenderer SaddleMouthLine;
    ModelRenderer SaddleMouthLineR;

    ModelRenderer MidWing;
    ModelRenderer InnerWing;
    ModelRenderer OuterWing;

    ModelRenderer InnerWingR;
    ModelRenderer MidWingR;
    ModelRenderer OuterWingR;

    ModelRenderer ButterflyL;
    ModelRenderer ButterflyR;

    //private float fMov1;
    //private float fMov2;
    //private boolean kneeSwitch;

    public MoCModelNewHorse() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.Body = new ModelRenderer(this, 0, 34);
        this.Body.addBox(-5F, -8F, -19F, 10, 10, 24);
        this.Body.setPos(0F, 11F, 9F);

        /*
         * Tail = new ModelRenderer(this, 24, 0); Tail.addBox(-1.5F, -2F, 3F, 3,
         * 4, 14); Tail.setRotationPoint(0F, 3F, 14F); setRotation(Tail,
         * -1.134464F, 0F, 0F);
         */

        this.TailA = new ModelRenderer(this, 44, 0);
        this.TailA.addBox(-1F, -1F, 0F, 2, 2, 3);
        this.TailA.setPos(0F, 3F, 14F);
        setRotation(this.TailA, -1.134464F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 38, 7);
        this.TailB.addBox(-1.5F, -2F, 3F, 3, 4, 7);
        this.TailB.setPos(0F, 3F, 14F);
        setRotation(this.TailB, -1.134464F, 0F, 0F);

        this.TailC = new ModelRenderer(this, 24, 3);
        this.TailC.addBox(-1.5F, -4.5F, 9F, 3, 4, 7);
        this.TailC.setPos(0F, 3F, 14F);
        setRotation(this.TailC, -1.40215F, 0F, 0F);

        this.Leg1A = new ModelRenderer(this, 78, 29);
        this.Leg1A.addBox(-2.5F, -2F, -2.5F, 4, 9, 5);
        this.Leg1A.setPos(4F, 9F, 11F);

        this.Leg1B = new ModelRenderer(this, 78, 43);
        this.Leg1B.addBox(-2F, 0F, -1.5F, 3, 5, 3);
        this.Leg1B.setPos(4F, 16F, 11F);

        this.Leg1C = new ModelRenderer(this, 78, 51);
        this.Leg1C.addBox(-2.5F, 5.1F, -2F, 4, 3, 4);
        this.Leg1C.setPos(4F, 16F, 11F);

        this.Leg2A = new ModelRenderer(this, 96, 29);
        this.Leg2A.addBox(-1.5F, -2F, -2.5F, 4, 9, 5);
        this.Leg2A.setPos(-4F, 9F, 11F);

        this.Leg2B = new ModelRenderer(this, 96, 43);
        this.Leg2B.addBox(-1F, 0F, -1.5F, 3, 5, 3);
        this.Leg2B.setPos(-4F, 16F, 11F);

        this.Leg2C = new ModelRenderer(this, 96, 51);
        this.Leg2C.addBox(-1.5F, 5.1F, -2F, 4, 3, 4);
        this.Leg2C.setPos(-4F, 16F, 11F);

        this.Leg3A = new ModelRenderer(this, 44, 29);
        this.Leg3A.addBox(-1.9F, -1F, -2.1F, 3, 8, 4);
        this.Leg3A.setPos(4F, 9F, -8F);

        this.Leg3B = new ModelRenderer(this, 44, 41);
        this.Leg3B.addBox(-1.9F, 0F, -1.6F, 3, 5, 3);
        this.Leg3B.setPos(4F, 16F, -8F);

        this.Leg3C = new ModelRenderer(this, 44, 51);
        this.Leg3C.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        this.Leg3C.setPos(4F, 16F, -8F);

        this.Leg4A = new ModelRenderer(this, 60, 29);
        this.Leg4A.addBox(-1.1F, -1F, -2.1F, 3, 8, 4);
        this.Leg4A.setPos(-4F, 9F, -8F);

        this.Leg4B = new ModelRenderer(this, 60, 41);
        this.Leg4B.addBox(-1.1F, 0F, -1.6F, 3, 5, 3);
        this.Leg4B.setPos(-4F, 16F, -8F);

        this.Leg4C = new ModelRenderer(this, 60, 51);
        this.Leg4C.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        this.Leg4C.setPos(-4F, 16F, -8F);

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-2.5F, -10F, -1.5F, 5, 5, 7);
        this.Head.setPos(0F, 4F, -10F);
        setRotation(this.Head, 0.5235988F, 0F, 0F);

        this.UMouth = new ModelRenderer(this, 24, 18);
        this.UMouth.addBox(-2F, -10F, -7F, 4, 3, 6);
        this.UMouth.setPos(0F, 4F, -10F);
        setRotation(this.UMouth, 0.5235988F, 0F, 0F);

        this.LMouth = new ModelRenderer(this, 24, 27);
        this.LMouth.addBox(-2F, -7F, -6.5F, 4, 2, 5);
        this.LMouth.setPos(0F, 4F, -10F);
        setRotation(this.LMouth, 0.5235988F, 0F, 0F);

        this.UMouth2 = new ModelRenderer(this, 24, 18);
        this.UMouth2.addBox(-2F, -10F, -8F, 4, 3, 6);
        this.UMouth2.setPos(0F, 4F, -10F);
        setRotation(this.UMouth2, 0.4363323F, 0F, 0F);

        this.LMouth2 = new ModelRenderer(this, 24, 27);
        this.LMouth2.addBox(-2F, -7F, -5.5F, 4, 2, 5);
        this.LMouth2.setPos(0F, 4F, -10F);
        setRotation(this.LMouth2, 0.7853982F, 0F, 0F);

        this.Unicorn = new ModelRenderer(this, 24, 0);
        this.Unicorn.addBox(-0.5F, -18F, 2F, 1, 8, 1);
        this.Unicorn.setPos(0F, 4F, -10F);
        setRotation(this.Unicorn, 0.5235988F, 0F, 0F);

        this.Ear1 = new ModelRenderer(this, 0, 0);
        this.Ear1.addBox(0.45F, -12F, 4F, 2, 3, 1);
        this.Ear1.setPos(0F, 4F, -10F);
        setRotation(this.Ear1, 0.5235988F, 0F, 0F);

        this.Ear2 = new ModelRenderer(this, 0, 0);
        this.Ear2.addBox(-2.45F, -12F, 4F, 2, 3, 1);
        this.Ear2.setPos(0F, 4F, -10F);
        setRotation(this.Ear2, 0.5235988F, 0F, 0F);

        this.MuleEarL = new ModelRenderer(this, 0, 12);
        this.MuleEarL.addBox(-2F, -16F, 4F, 2, 7, 1);
        this.MuleEarL.setPos(0F, 4F, -10F);
        setRotation(this.MuleEarL, 0.5235988F, 0F, 0.2617994F);

        this.MuleEarR = new ModelRenderer(this, 0, 12);
        this.MuleEarR.addBox(0F, -16F, 4F, 2, 7, 1);
        this.MuleEarR.setPos(0F, 4F, -10F);
        setRotation(this.MuleEarR, 0.5235988F, 0F, -0.2617994F);

        this.Neck = new ModelRenderer(this, 0, 12);
        this.Neck.addBox(-2.05F, -9.8F, -2F, 4, 14, 8);
        this.Neck.setPos(0F, 4F, -10F);
        setRotation(this.Neck, 0.5235988F, 0F, 0F);

        this.Bag1 = new ModelRenderer(this, 0, 34);
        this.Bag1.addBox(-3F, 0F, 0F, 8, 8, 3);
        this.Bag1.setPos(-7.5F, 3F, 10F);
        setRotation(this.Bag1, 0F, 1.570796F, 0F);

        this.Bag2 = new ModelRenderer(this, 0, 47);
        this.Bag2.addBox(-3F, 0F, 0F, 8, 8, 3);
        this.Bag2.setPos(4.5F, 3F, 10F);
        setRotation(this.Bag2, 0F, 1.570796F, 0F);

        this.Saddle = new ModelRenderer(this, 80, 0);
        this.Saddle.addBox(-5F, 0F, -3F, 10, 1, 8);
        this.Saddle.setPos(0F, 2F, 2F);

        this.SaddleB = new ModelRenderer(this, 106, 9);
        this.SaddleB.addBox(-1.5F, -1F, -3F, 3, 1, 2);
        this.SaddleB.setPos(0F, 2F, 2F);

        this.SaddleC = new ModelRenderer(this, 80, 9);
        this.SaddleC.addBox(-4F, -1F, 3F, 8, 1, 2);
        this.SaddleC.setPos(0F, 2F, 2F);

        this.SaddleL2 = new ModelRenderer(this, 74, 0);
        this.SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleL2.setPos(5F, 3F, 2F);

        this.SaddleL = new ModelRenderer(this, 70, 0);
        this.SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleL.setPos(5F, 3F, 2F);

        this.SaddleR2 = new ModelRenderer(this, 74, 4);
        this.SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleR2.setPos(-5F, 3F, 2F);

        this.SaddleR = new ModelRenderer(this, 80, 0);
        this.SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleR.setPos(-5F, 3F, 2F);

        this.SaddleMouthL = new ModelRenderer(this, 74, 13);
        this.SaddleMouthL.addBox(1.5F, -8F, -4F, 1, 2, 2);
        this.SaddleMouthL.setPos(0F, 4F, -10F);
        setRotation(this.SaddleMouthL, 0.5235988F, 0F, 0F);

        this.SaddleMouthR = new ModelRenderer(this, 74, 13);
        this.SaddleMouthR.addBox(-2.5F, -8F, -4F, 1, 2, 2);
        this.SaddleMouthR.setPos(0F, 4F, -10F);
        setRotation(this.SaddleMouthR, 0.5235988F, 0F, 0F);

        this.SaddleMouthLine = new ModelRenderer(this, 44, 10);
        this.SaddleMouthLine.addBox(2.6F, -6F, -6F, 0, 3, 16);
        this.SaddleMouthLine.setPos(0F, 4F, -10F);

        this.SaddleMouthLineR = new ModelRenderer(this, 44, 5);
        this.SaddleMouthLineR.addBox(-2.6F, -6F, -6F, 0, 3, 16);
        this.SaddleMouthLineR.setPos(0F, 4F, -10F);

        this.Mane = new ModelRenderer(this, 58, 0);
        this.Mane.addBox(-1F, -11.5F, 5F, 2, 16, 4);
        this.Mane.setPos(0F, 4F, -10F);
        //Mane.addBox(-1F, -9.5F, 6F, 2, 16, 4);
        //Mane.setRotationPoint(0F, 3F, -12F);
        setRotation(this.Mane, 0.5235988F, 0F, 0F);

        this.HeadSaddle = new ModelRenderer(this, 80, 12);
        this.HeadSaddle.addBox(-2.5F, -10.1F, -7F, 5, 5, 12, 0.2F);
        this.HeadSaddle.setPos(0F, 4F, -10F);
        setRotation(this.HeadSaddle, 0.5235988F, 0F, 0F);

        this.MidWing = new ModelRenderer(this, 82, 68);
        this.MidWing.addBox(1F, 0.1F, 1F, 12, 2, 11);
        this.MidWing.setPos(5F, 3F, -6F);
        setRotation(this.MidWing, 0F, 0.0872665F, 0F);

        this.InnerWing = new ModelRenderer(this, 0, 96);
        this.InnerWing.addBox(0F, 0F, 0F, 7, 2, 11);
        this.InnerWing.setPos(5F, 3F, -6F);
        setRotation(this.InnerWing, 0F, -0.3490659F, 0F);

        this.OuterWing = new ModelRenderer(this, 0, 68);
        this.OuterWing.addBox(0F, 0F, 0F, 22, 2, 11);
        this.OuterWing.setPos(17F, 3F, -6F);
        setRotation(this.OuterWing, 0F, -0.3228859F, 0F);

        this.InnerWingR = new ModelRenderer(this, 0, 110);
        this.InnerWingR.addBox(-7F, 0F, 0F, 7, 2, 11);
        this.InnerWingR.setPos(-5F, 3F, -6F);
        setRotation(this.InnerWingR, 0F, 0.3490659F, 0F);

        this.MidWingR = new ModelRenderer(this, 82, 82);
        this.MidWingR.addBox(-13F, 0.1F, 1F, 12, 2, 11);
        this.MidWingR.setPos(-5F, 3F, -6F);
        setRotation(this.MidWingR, 0F, -0.0872665F, 0F);

        this.OuterWingR = new ModelRenderer(this, 0, 82);
        this.OuterWingR.addBox(-22F, 0F, 0F, 22, 2, 11);
        this.OuterWingR.setPos(-17F, 3F, -6F);
        setRotation(this.OuterWingR, 0F, 0.3228859F, 0F);

        this.ButterflyL = new ModelRenderer(this, 0, 98);
        this.ButterflyL.addBox(-1F, 0F, -14F, 26, 0, 30);
        this.ButterflyL.setPos(4.5F, 3F, -2F);
        setRotation(this.ButterflyL, 0F, 0F, -0.78539F);

        this.ButterflyR = new ModelRenderer(this, 0, 68);
        this.ButterflyR.addBox(-25F, 0F, -14F, 26, 0, 30);
        this.ButterflyR.setPos(-4.5F, 3F, -2F);
        setRotation(this.ButterflyR, 0F, 0F, 0.78539F);

    }

    /**
     * Note: I copied most of the code over from the original render function, since the new render function doesn't have an entity parameter.
     * the vanilla horse model sets the extra parts as visible using setRotationAngles, so I am basing this code off of that. The vast
     * majority of the code here will have to be rewritten later, but this should compile for now.
     *  - Burning Cactus
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntityHorse entityhorse = (MoCEntityHorse) entityIn;
        int type = entityhorse.getSubType();
        int vanishingInt = entityhorse.getVanishC();
        boolean isGhost = entityhorse.getIsGhost();
        int wingflapInt = entityhorse.wingFlapCounter;
        boolean flapwings = (entityhorse.wingFlapCounter != 0);
        boolean shuffling = (entityhorse.shuffleCounter != 0);
        boolean saddled = entityhorse.getIsRideable();
        boolean wings = (entityhorse.isFlyer() && !entityhorse.getIsGhost() && type < 45);
        //boolean chested = entityhorse.getChestedHorse();
        boolean eating = entityhorse.getIsSitting();
        //boolean flyer = entityhorse.isFlyer();
        boolean standing = (entityhorse.standCounter != 0 && entityhorse.getVehicle() == null);
        boolean openMouth = (entityhorse.mouthCounter != 0);
        boolean moveTail = (entityhorse.tailCounter != 0);

        boolean rider = (entityhorse.isVehicle());
        boolean floating = (entityhorse.getIsGhost() || (entityhorse.isFlyer() && entityhorse.isOnAir()));
        //                || (entityhorse.getRidingEntity() == null && !entityhorse.onGround)
        //                || (entityhorse.isBeingRidden() && !entityhorse.getRidingEntity().onGround));
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, eating, rider, floating, standing, saddled, moveTail, wings, flapwings, shuffling, type);
        boolean corporeal = !isGhost && vanishingInt == 0;


//        if (!isGhost && vanishingInt == 0) {
//            if (saddled) {
        boolean showSaddle = (corporeal && saddled);

        this.HeadSaddle.visible = showSaddle;
        this.Saddle.visible = showSaddle;
        this.SaddleB.visible = showSaddle;
        this.SaddleC.visible = showSaddle;
        this.SaddleL.visible = showSaddle;
        this.SaddleL2.visible = showSaddle;
        this.SaddleR.visible = showSaddle;
        this.SaddleR2.visible = showSaddle;
        this.SaddleMouthL.visible = showSaddle;
        this.SaddleMouthR.visible = showSaddle;
        if (rider) {
            this.SaddleMouthLine.visible = showSaddle;
            this.SaddleMouthLineR.visible = showSaddle;
                }
//
//            }

            if (type == 65 || type == 66 || type == 67) //mule, donkey or zonkey
            {
                this.MuleEarL.visible = true;
                this.MuleEarR.visible = true;
            } else {
                this.Ear1.visible = true;
                this.Ear2.visible = true;

            }
            this.UMouth2.visible = openMouth;
            this.LMouth2.visible = openMouth;
            this.UMouth.visible = !openMouth;
            this.LMouth.visible = !openMouth;

            this.Unicorn.visible = entityhorse.isUnicorned();

            this.Bag1.visible = entityhorse.getIsChested();
            this.Bag2.visible = entityhorse.getIsChested();

            if (entityhorse.isFlyer() && type < 45) { //pegasus
                this.MidWing.visible = true;
                this.InnerWing.visible = true;
                this.OuterWing.visible = true;
                this.InnerWingR.visible = true;
                this.MidWingR.visible = true;
                this.OuterWingR.visible = true;
            } else if (type > 44 && type < 60) { //fairys
//                GL11.glPushMatrix();
//                GL11.glEnable(3042 /* GL_BLEND */);
//                float transparency = 0.7F;
//                GL11.glBlendFunc(770, 771);
//                GL11.glColor4f(1.2F, 1.2F, 1.2F, transparency);
//                GL11.glScalef(1.3F, 1.0F, 1.3F);
                this.ButterflyL.visible = true;
                this.ButterflyR.visible = true;
//                GL11.glDisable(3042/* GL_BLEND */);
//                GL11.glPopMatrix();
            }/*
             * else { ButterflyL.render(f5); ButterflyR.render(f5); }
             */
            if(!corporeal)
        //rendering a ghost or vanishing
            {
            float transparency; //= entityhorse.tFloat();

            if (vanishingInt != 0)// && vanishingInt > 30)
            {
                transparency = 1.0F - (((float) (vanishingInt)) / 100);
            } else {
                transparency = entityhorse.tFloat();
            }

//            GL11.glPushMatrix();
//            GL11.glEnable(3042 /* GL_BLEND */);
//            GL11.glBlendFunc(770, 771);
//            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
//            GL11.glScalef(1.3F, 1.0F, 1.3F);

//            this.Ear1.showModel = true;
//            this.Ear2.showModel = true;
//
//            this.Neck.render(f5);
//            this.Head.render(f5);
            this.UMouth2.visible = openMouth;
            this.LMouth2.visible = openMouth;
            this.UMouth.visible = !openMouth;
            this.LMouth.visible = !openMouth;

//            this.Mane.render(f5);
//            this.Body.render(f5);
//            this.TailA.render(f5);
//            this.TailB.render(f5);
//            this.TailC.render(f5);
//
//            this.Leg1A.render(f5);
//            this.Leg1B.render(f5);
//            this.Leg1C.render(f5);
//
//            this.Leg2A.render(f5);
//            this.Leg2B.render(f5);
//            this.Leg2C.render(f5);
//
//            this.Leg3A.render(f5);
//            this.Leg3B.render(f5);
//            this.Leg3C.render(f5);
//
//            this.Leg4A.render(f5);
//            this.Leg4B.render(f5);
//            this.Leg4C.render(f5);

            if (type == 39 || type == 40 || type == 28) {
                this.MidWing.visible = true;
                this.InnerWing.visible = true;
                this.OuterWing.visible = true;
                this.InnerWingR.visible = true;
                this.MidWingR.visible = true;
                this.OuterWingR.visible = true;
            }
            if (type >= 50 && type < 60) {
                this.ButterflyL.visible = true;
                this.ButterflyR.visible = true;
            }

                this.HeadSaddle.visible = saddled;
                this.Saddle.visible = saddled;
                this.SaddleB.visible = saddled;
                this.SaddleC.visible = saddled;
                this.SaddleL.visible = saddled;
                this.SaddleL2.visible = saddled;
                this.SaddleR.visible = saddled;
                this.SaddleR2.visible = saddled;
                this.SaddleMouthL.visible = saddled;
                this.SaddleMouthR.visible = saddled;
                this.SaddleMouthLine.visible = rider && saddled;
                this.SaddleMouthLineR.visible = rider && saddled;

//            GL11.glDisable(3042/* GL_BLEND */);
//            GL11.glPopMatrix();

            if (type == 21 || type == 22)//|| (type >=50 && type <60))
            {
//                float wingTransparency = 0F;
//                if (wingflapInt != 0) {
//                    wingTransparency = 1F - (((float) wingflapInt) / 25);
//                }
//                if (wingTransparency > transparency) {
//                    wingTransparency = transparency;
//                }
//                GL11.glPushMatrix();
//                GL11.glEnable(3042 /* GL_BLEND */);
//                GL11.glBlendFunc(770, 771);
//                GL11.glColor4f(0.8F, 0.8F, 0.8F, wingTransparency);
//                GL11.glScalef(1.3F, 1.0F, 1.3F);
                this.ButterflyL.visible = true;
                this.ButterflyR.visible = true;
//                GL11.glDisable(3042/* GL_BLEND */);
//                GL11.glPopMatrix();
            }
        }

    }


    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(Head, UMouth, LMouth, UMouth2, LMouth2, Unicorn, Ear1, Ear2, MuleEarL, MuleEarR, Neck, HeadSaddle, Mane);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(Body, TailA, TailB, TailC, Leg1A, Leg1B, Leg1C, Leg2A, Leg2B, Leg2C, Leg3A, Leg3B, Leg3C, Leg4A, Leg4B, Leg4C,
                Bag1, Bag2, Saddle, SaddleB, SaddleC, SaddleL, SaddleL2, SaddleR, SaddleR2, SaddleMouthL, SaddleMouthR, SaddleMouthLine,
                SaddleMouthLineR, MidWing, InnerWing, OuterWing, InnerWingR, MidWingR, OuterWingR, ButterflyL, ButterflyR);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, boolean eating, boolean rider, boolean floating,
            boolean standing, boolean saddled, boolean tail, boolean wings, boolean flapwings, boolean shuffle, int type) {
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float HeadXRot = (f4 / 57.29578F);
        if (f3 > 20F) {
            f3 = 20F;
        }
        if (f3 < -20F) {
            f3 = -20F;
        }

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        if (shuffle) {
            HeadXRot = HeadXRot + (MathHelper.cos(f2 * 0.4F) * 0.15F);
        } else if (f1 > 0.2F && !floating) {
            HeadXRot = HeadXRot + (MathHelper.cos(f * 0.4F) * 0.15F * f1);
        }

        this.Head.y = 4.0F;
        this.Head.z = -10F;
        this.Head.xRot = 0.5235988F + (HeadXRot);
        this.Head.yRot = (f3 / 57.29578F);//fixes SMP bug
        this.TailA.y = 3F;
        this.TailB.z = 14F;
        this.Bag2.y = 3F;
        this.Bag2.z = 10F;
        this.Body.xRot = 0F;

        if (standing && !shuffle) //standing on hind legs
        {
            this.Head.y = -6F;
            this.Head.z = -1F;
            this.Head.xRot = (15 / 57.29578F) + (HeadXRot);
            ;//120 degrees
            this.Head.yRot = (f3 / 57.29578F);
            this.TailA.y = 9F;
            this.TailB.z = 18F;
            this.Bag2.y = 5.5F;
            this.Bag2.z = 15F;
            this.Body.xRot = -45 / 57.29578F;

        } else if (eating && !shuffle)//neck down
        {
            this.Head.y = 11.0F; //new lower position
            this.Head.z = -10F;
            this.Head.xRot = 2.18166F;//120 degrees
            this.Head.yRot = 0.0F;//don't twist your neck if eating

        }

        this.Ear1.y = this.Head.y;
        this.Ear2.y = this.Head.y;
        this.MuleEarL.y = this.Head.y;
        this.MuleEarR.y = this.Head.y;
        this.Neck.y = this.Head.y;
        this.UMouth.y = this.Head.y;
        this.UMouth2.y = this.Head.y;
        this.LMouth.y = this.Head.y;
        this.LMouth2.y = this.Head.y;
        this.Mane.y = this.Head.y;
        this.Unicorn.y = this.Head.y;

        this.Ear1.z = this.Head.z;
        this.Ear2.z = this.Head.z;
        this.MuleEarL.z = this.Head.z;
        this.MuleEarR.z = this.Head.z;
        this.Neck.z = this.Head.z;
        this.UMouth.z = this.Head.z;
        this.UMouth2.z = this.Head.z;
        this.LMouth.z = this.Head.z;
        this.LMouth2.z = this.Head.z;
        this.Mane.z = this.Head.z;
        this.Unicorn.z = this.Head.z;

        this.Ear1.xRot = this.Head.xRot;
        this.Ear2.xRot = this.Head.xRot;
        this.MuleEarL.xRot = this.Head.xRot;
        this.MuleEarR.xRot = this.Head.xRot;
        this.Neck.xRot = this.Head.xRot;
        this.UMouth.xRot = this.Head.xRot;
        this.UMouth2.xRot = this.Head.xRot - 0.0872664F;
        this.LMouth.xRot = this.Head.xRot;
        this.LMouth2.xRot = this.Head.xRot + 0.261799F;
        this.Mane.xRot = this.Head.xRot;
        this.Unicorn.xRot = this.Head.xRot;

        this.Ear1.yRot = this.Head.yRot;
        this.Ear2.yRot = this.Head.yRot;
        this.MuleEarL.yRot = this.Head.yRot;
        this.MuleEarR.yRot = this.Head.yRot;
        this.Neck.yRot = this.Head.yRot;
        this.UMouth.yRot = this.Head.yRot;
        this.LMouth.yRot = this.Head.yRot;
        this.UMouth2.yRot = this.Head.yRot;
        this.LMouth2.yRot = this.Head.yRot;
        this.Mane.yRot = this.Head.yRot;
        this.Unicorn.yRot = this.Head.yRot;

        //(if chested)
        this.Bag1.xRot = RLegXRot / 5F;//(MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;
        this.Bag2.xRot = -RLegXRot / 5F;//(MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;

        if (wings) {
            this.InnerWing.xRot = this.Body.xRot;
            this.MidWing.xRot = this.Body.xRot;
            this.OuterWing.xRot = this.Body.xRot;
            this.InnerWingR.xRot = this.Body.xRot;
            this.MidWingR.xRot = this.Body.xRot;
            this.OuterWingR.xRot = this.Body.xRot;

            if (standing) {
                this.InnerWing.y = -5F;
                this.InnerWing.z = 4F;
            } else {
                this.InnerWing.y = 3F;
                this.InnerWing.z = -6F;
            }

            /**
             * flapping wings or cruising. IF flapping wings, move up and down.
             * if cruising, movement depends of speed
             */
            float WingRot = 0F;
            if (flapwings) {
                WingRot = MathHelper.cos((f2 * 0.3F) + 3.141593F) * 1.2F;// * f1;
            } else
            //cruising
            {
                //WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
                WingRot = MathHelper.cos((f * 0.5F)) * 0.1F;//* 1.2F * f1;
            }

            //float WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * f1 * 1.2F;
            //InnerWing.setRotationPoint(5F, 3F, -6F);
            //setRotation(InnerWing, 0F, -0.3490659F, 0F);
            //X dist = 12
            //OuterWing.setRotationPoint(17F, 3F, -6F);
            //setRotation(OuterWing, 0F, -0.3228859F, 0F);

            if (floating) {
                this.OuterWing.yRot = -0.3228859F + (WingRot / 2F);
                this.OuterWingR.yRot = 0.3228859F - (WingRot / 2F);

            } else {
                WingRot = 60 / 57.29578F;//0.7854F;
                this.OuterWing.yRot = -90 / 57.29578F;//-1.396F;
                this.OuterWingR.yRot = 90 / 57.29578F;//1.396F;
            }

            this.InnerWingR.y = this.InnerWing.y;
            this.InnerWingR.z = this.InnerWing.z;

            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //the rotation point X rotates depending of the cos of rotation times the distance of the other block:
            //cos (WingRot) * 12F
            //the rotation PointX of Innerwing = 5
            //the rotation PointX of Outerwing = 17
            //the difference = 12.
            // for the rotation point Y, sin is used instead.
            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //OuterWing.rotationPointY = InnerWing.rotationPointY + (MathHelper.sin(WingRot)*12F);

            this.OuterWing.x = this.InnerWing.x + (MathHelper.cos(WingRot) * 12F);
            this.OuterWingR.x = this.InnerWingR.x - (MathHelper.cos(WingRot) * 12F);

            this.MidWing.y = this.InnerWing.y;
            this.MidWingR.y = this.InnerWing.y;
            this.OuterWing.y = this.InnerWing.y + (MathHelper.sin(WingRot) * 12F);
            this.OuterWingR.y = this.InnerWingR.y + (MathHelper.sin(WingRot) * 12F);

            this.MidWing.z = this.InnerWing.z;
            this.MidWingR.z = this.InnerWing.z;
            this.OuterWing.z = this.InnerWing.z;
            this.OuterWingR.z = this.InnerWing.z;

            this.MidWing.zRot = WingRot;
            this.InnerWing.zRot = WingRot;
            this.OuterWing.zRot = WingRot;

            this.InnerWingR.zRot = -WingRot;
            this.MidWingR.zRot = -WingRot;
            this.OuterWingR.zRot = -WingRot;

            //45deg = 0.7854F
            //1.396 (80degrees folded)
            /*
             * //rear left. -4X(ignored), 9Y, 11Z the distance is 7Y
             * Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
             * LLegXRot )*7F); Leg1B.rotationPointZ = 11F +
             * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F); //rear right
             * Leg2B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
             * RLegXRot )*7F); Leg2B.rotationPointZ = 11F +
             * (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F); //front left
             * 4X(ign), 9Y, -8Z, the distance is again 7Y Leg3B.rotationPointY =
             * 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
             * Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) +
             * RLegXRot )*7F); //front right -4X(ign), 9Y, -8Z, the distance is
             * again 7Y Leg4B.rotationPointY = 9F + (MathHelper.sin((90/
             * 57.29578F) + LLegXRot )*7F); Leg4B.rotationPointZ = -8F +
             * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
             */

        }

        if (type > 44 && type < 60 || type == 21) //butterfly horses or ghost horse
        {
            /**
             * buttefly to have two / 3 movs: 1 slow movement when idle on
             * ground has to be random from closing up to horizontal 2 fast wing
             * flapping flying movement, short range close to 0 degree RLegXRot
             * = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
             */

            /**
             * f = distance walked f1 = speed 0 - 1 f2 = timer
             */

            float f2a = f2 % 100F;
            float WingRot = 0F;

            if (type != 21) {
                //for butterfly horses
                if (flapwings) //when user hits space or randomly
                {
                    WingRot = MathHelper.cos((f2 * 0.9F)) * 0.9F;

                } else
                //default movement
                {
                    if (floating) //cruising
                    {
                        WingRot = MathHelper.cos((f2 * 0.6662F)) * 0.5F;
                    } else {
                        if (f2a > 40 & f2a < 60) //random movement
                        {
                            WingRot = MathHelper.cos((f2 * 0.15F)) * 1.20F;
                        }
                    }

                }
            }

            else
            //for ghost horse
            {
                WingRot = MathHelper.cos((f2 * 0.1F));//* 0.2F;
            }

            //from regular horse
            /*
             * if (flapwings) { WingRot = MathHelper.cos((f2 * 0.3F) +
             * 3.141593F) * 1.2F;// * f1; }else //cruising { //WingRot =
             * MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1; WingRot =
             * MathHelper.cos((f * 0.5F)) *0.1F ;//* 1.2F * f1; }
             */

            /**
             * this part is needed for position and angle of the butterfly wings
             * and ghost horse wings
             */
            if (standing) {
                this.ButterflyL.y = -2.5F;
                this.ButterflyL.z = 6.5F;
            } else {
                this.ButterflyL.y = 3F;
                this.ButterflyL.z = -2F;
            }

            this.ButterflyR.y = this.ButterflyL.y;
            this.ButterflyR.z = this.ButterflyL.z;
            this.ButterflyL.xRot = this.Body.xRot;
            this.ButterflyR.xRot = this.Body.xRot;

            //this to be added for the ghost or adjusted
            //ButterflyL.rotateAngleZ = -0.52359F + RLegXRot;
            //ButterflyR.rotateAngleZ = 0.52359F -RLegXRot;

            float baseAngle = 0.52359F;
            if (type == 21) {
                baseAngle = 0F;
            }
            this.ButterflyL.zRot = -baseAngle + WingRot;
            this.ButterflyR.zRot = baseAngle - WingRot;
        }

        /**
         * knee joints Leg1 and Leg4 use LLegXRot Leg2 and Leg3 use RLegXRot
         */
        //RLegXRot = 45/57.29578F;
        //fMov1 = RLegXRot;
        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;
        float RLegXRotC = RLegXRot;
        float LLegXRotC = LLegXRot;

        if (floating) {
            RLegXRot = (15 / 57.29578F);
            LLegXRot = RLegXRot;
            RLegXRotB = (45 / 57.29578F);
            RLegXRotC = RLegXRotB;
            LLegXRotB = RLegXRotB;
            LLegXRotC = RLegXRotB;
        }

        if (standing) {
            this.Leg3A.y = -2F;
            this.Leg3A.z = -2F;
            this.Leg4A.y = this.Leg3A.y;
            this.Leg4A.z = this.Leg3A.z;

            RLegXRot = (-60 / 57.29578F) + MathHelper.cos((f2 * 0.4F) + 3.141593F);
            LLegXRot = (-60 / 57.29578F) + MathHelper.cos(f2 * 0.4F);

            RLegXRotB = (45 / 57.29578F);
            LLegXRotB = RLegXRotB;

            RLegXRotC = (-15 / 57.29578F);
            LLegXRotC = (15 / 57.29578F);

            this.Leg3B.y = this.Leg3A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg3B.z = this.Leg3A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.y = this.Leg4A.y + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg4B.z = this.Leg4A.z + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            this.Leg1B.y = this.Leg1A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRotC) * 7F);
            this.Leg1B.z = this.Leg1A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRotC) * 7F);

            //rear right
            this.Leg2B.y = this.Leg1B.y;// Leg2A.rotationPointY + (MathHelper.sin((90/ 57.29578F) + RLegXRotC )*7F);
            this.Leg2B.z = this.Leg1B.z;//Leg2A.rotationPointZ + (MathHelper.cos((270/ 57.29578F) + RLegXRotC )*7F);

            this.Leg1A.xRot = RLegXRotC;
            this.Leg1B.xRot = LLegXRotC;
            this.Leg1C.xRot = this.Leg1B.xRot;

            this.Leg2A.xRot = RLegXRotC;
            this.Leg2B.xRot = LLegXRotC;
            this.Leg2C.xRot = this.Leg2B.xRot;

            this.Leg3A.xRot = RLegXRot;
            this.Leg3B.xRot = RLegXRotB;
            this.Leg3C.xRot = RLegXRotB;

            this.Leg4A.xRot = LLegXRot;
            this.Leg4B.xRot = LLegXRotB;
            this.Leg4C.xRot = LLegXRotB;
        }

        else
        //not standing
        {
            this.Leg3A.y = 9F;
            this.Leg3A.z = -8F;
            this.Leg4A.y = this.Leg3A.y;
            this.Leg4A.z = this.Leg3A.z;

            if (!floating && f1 > 0.2F) {

                float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
                float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;
                if (RLegXRot > RLegXRot2) // - - >
                {
                    RLegXRotB = RLegXRot + (55 / 57.29578F);
                    //LLegXRotB = LLegXRot + (55/57.29578F);

                }
                if (RLegXRot < RLegXRot2) // < - -
                {
                    RLegXRotC = RLegXRot + (15 / 57.29578F);
                    //LLegXRotC = LLegXRot - (15/57.29578F);

                }
                if (LLegXRot > LLegXRot2) // - - >
                {
                    LLegXRotB = LLegXRot + (55 / 57.29578F);
                }
                if (LLegXRot < LLegXRot2) // < - -
                {
                    LLegXRotC = LLegXRot + (15 / 57.29578F);
                }

            }

            this.Leg1B.y = this.Leg1A.y + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg1B.z = this.Leg1A.z + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            this.Leg2B.y = this.Leg2A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg2B.z = this.Leg2A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg3B.y = this.Leg3A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg3B.z = this.Leg3A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.y = this.Leg4A.y + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg4B.z = this.Leg4A.z + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            this.Leg1A.xRot = LLegXRot;
            this.Leg1B.xRot = LLegXRotC;
            this.Leg1C.xRot = LLegXRotC;

            this.Leg2A.xRot = RLegXRot;
            this.Leg2B.xRot = RLegXRotC;
            this.Leg2C.xRot = RLegXRotC;

            this.Leg3A.xRot = RLegXRot;
            this.Leg3B.xRot = RLegXRotB;
            this.Leg3C.xRot = RLegXRotB;

            this.Leg4A.xRot = LLegXRot;
            this.Leg4B.xRot = LLegXRotB;
            this.Leg4C.xRot = LLegXRotB;

        }

        if (type == 60 && shuffle) {
            this.Leg3A.y = 9F;
            this.Leg3A.z = -8F;
            this.Leg4A.y = this.Leg3A.y;
            this.Leg4A.z = this.Leg3A.z;

            if (!floating)//&& f1 > 0.2F)
            {

                //float RLegXRot2 = MathHelper.cos(f2 * 0.4F);//MathHelper.cos(((f+0.1F) * 0.6662F) + 3.141593F) * 1.4F * f1;
                RLegXRot = MathHelper.cos(f2 * 0.4F);
                if (RLegXRot > 0.1F) {
                    RLegXRot = 0.3F;
                }
                //if (RLegXRot < -0.5F) RLegXRotB = RLegXRot + (45/57.29578F);
                LLegXRot = MathHelper.cos((f2 * 0.4F) + 3.141593F);
                if (LLegXRot > 0.1F) {
                    LLegXRot = 0.3F;
                }

                /*
                 * if (RLegXRot > RLegXRot2) // - - > { RLegXRotB = RLegXRot +
                 * (55/57.29578F); LLegXRotB = LLegXRot + (55/57.29578F); } if
                 * (RLegXRot < RLegXRot2) // < - - { RLegXRotC = RLegXRot -
                 * (15/57.29578F); LLegXRotC = LLegXRot - (15/57.29578F); }
                 */

                /*
                 * float RLegXRot2 = MathHelper.cos(((f+0.1F) * 0.6662F) +
                 * 3.141593F) * 1.4F * f1; if (RLegXRot > RLegXRot2) // - - > {
                 * RLegXRotB = RLegXRot + (55/57.29578F); LLegXRotB = LLegXRot +
                 * (55/57.29578F); } if (RLegXRot < RLegXRot2) // < - - {
                 * RLegXRotC = RLegXRot - (15/57.29578F); LLegXRotC = LLegXRot -
                 * (15/57.29578F); }
                 */
            }

            this.Leg1B.y = this.Leg1A.y + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg1B.z = this.Leg1A.z + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            this.Leg2B.y = this.Leg2A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg2B.z = this.Leg2A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg3B.y = this.Leg3A.y + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            this.Leg3B.z = this.Leg3A.z + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            this.Leg4B.y = this.Leg4A.y + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            this.Leg4B.z = this.Leg4A.z + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            this.Leg1A.xRot = LLegXRot;// + rand.nextFloat();
            this.Leg1B.xRot = LLegXRotB;
            this.Leg1C.xRot = LLegXRotB;

            this.Leg3A.xRot = LLegXRot;
            this.Leg3B.xRot = LLegXRotB;
            this.Leg3C.xRot = LLegXRotB;

            this.Leg2A.xRot = RLegXRot;
            this.Leg2B.xRot = RLegXRotB;
            this.Leg2C.xRot = RLegXRotB;

            this.Leg4A.xRot = RLegXRot;
            this.Leg4B.xRot = RLegXRotB;
            this.Leg4C.xRot = RLegXRotB;

        }

        this.Leg1C.y = this.Leg1B.y;
        this.Leg1C.z = this.Leg1B.z;
        this.Leg2C.y = this.Leg2B.y;
        this.Leg2C.z = this.Leg2B.z;
        this.Leg3C.y = this.Leg3B.y;
        this.Leg3C.z = this.Leg3B.z;
        this.Leg4C.y = this.Leg4B.y;
        this.Leg4C.z = this.Leg4B.z;

        /*
         * //rear left. -4X(ignored), 9Y, 11Z the distance is 7Y
         * Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
         * LLegXRot )*7F); Leg1B.rotationPointZ = 11F + (MathHelper.cos((270/
         * 57.29578F) + LLegXRot )*7F); //rear right Leg2B.rotationPointY = 9F +
         * (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
         * Leg2B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) +
         * RLegXRot )*7F); //front left 4X(ign), 9Y, -8Z, the distance is again
         * 7Y Leg3B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) +
         * RLegXRot )*7F); Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/
         * 57.29578F) + RLegXRot )*7F); //front right -4X(ign), 9Y, -8Z, the
         * distance is again 7Y Leg4B.rotationPointY = 9F + (MathHelper.sin((90/
         * 57.29578F) + LLegXRot )*7F); Leg4B.rotationPointZ = -8F +
         * (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
         */

        if (saddled) {

            if (standing) {
                this.Saddle.y = 0.5F;
                this.Saddle.z = 11F;

            } else {
                this.Saddle.y = 2F;
                this.Saddle.z = 2F;

            }

            this.SaddleB.y = this.Saddle.y;
            this.SaddleC.y = this.Saddle.y;
            this.SaddleL.y = this.Saddle.y;
            this.SaddleR.y = this.Saddle.y;
            this.SaddleL2.y = this.Saddle.y;
            this.SaddleR2.y = this.Saddle.y;
            this.Bag1.y = this.Bag2.y;

            this.SaddleB.z = this.Saddle.z;
            this.SaddleC.z = this.Saddle.z;
            this.SaddleL.z = this.Saddle.z;
            this.SaddleR.z = this.Saddle.z;
            this.SaddleL2.z = this.Saddle.z;
            this.SaddleR2.z = this.Saddle.z;
            this.Bag1.z = this.Bag2.z;

            this.Saddle.xRot = this.Body.xRot;
            this.SaddleB.xRot = this.Body.xRot;
            this.SaddleC.xRot = this.Body.xRot;

            this.SaddleMouthLine.y = this.Head.y;
            this.SaddleMouthLineR.y = this.Head.y;
            this.HeadSaddle.y = this.Head.y;
            this.SaddleMouthL.y = this.Head.y;
            this.SaddleMouthR.y = this.Head.y;

            this.SaddleMouthLine.z = this.Head.z;
            this.SaddleMouthLineR.z = this.Head.z;
            this.HeadSaddle.z = this.Head.z;
            this.SaddleMouthL.z = this.Head.z;
            this.SaddleMouthR.z = this.Head.z;

            this.SaddleMouthLine.xRot = HeadXRot;
            this.SaddleMouthLineR.xRot = HeadXRot;
            this.HeadSaddle.xRot = this.Head.xRot;
            this.SaddleMouthL.xRot = this.Head.xRot;
            this.SaddleMouthR.xRot = this.Head.xRot;
            this.HeadSaddle.yRot = this.Head.yRot;
            this.SaddleMouthL.yRot = this.Head.yRot;
            this.SaddleMouthLine.yRot = this.Head.yRot;
            this.SaddleMouthR.yRot = this.Head.yRot;
            this.SaddleMouthLineR.yRot = this.Head.yRot;

            if (rider) {
                this.SaddleL.xRot = -60 / 57.29578F;
                this.SaddleL2.xRot = -60 / 57.29578F;
                this.SaddleR.xRot = -60 / 57.29578F;
                this.SaddleR2.xRot = -60 / 57.29578F;

                this.SaddleL.zRot = 0F;
                this.SaddleL2.zRot = 0F;
                this.SaddleR.zRot = 0F;
                this.SaddleR2.zRot = 0F;
                ;
            } else {
                this.SaddleL.xRot = RLegXRot / 3F;
                this.SaddleL2.xRot = RLegXRot / 3F;
                this.SaddleR.xRot = RLegXRot / 3F;
                this.SaddleR2.xRot = RLegXRot / 3F;

                this.SaddleL.zRot = RLegXRot / 5F;
                this.SaddleL2.zRot = RLegXRot / 5F;
                this.SaddleR.zRot = -RLegXRot / 5F;
                this.SaddleR2.zRot = -RLegXRot / 5F;
            }
        }

        //f1 = movement speed!
        //f2 = timer!

        float tailMov = -1.3089F + (f1 * 1.5F);
        if (tailMov > 0) {
            tailMov = 0;
        }

        if (tail) {
            this.TailA.yRot = MathHelper.cos(f2 * 0.7F);
            tailMov = 0;
        } else {
            this.TailA.yRot = 0F;
        }
        this.TailB.yRot = this.TailA.yRot;
        this.TailC.yRot = this.TailA.yRot;

        this.TailB.y = this.TailA.y;
        this.TailC.y = this.TailA.y;
        this.TailB.z = this.TailA.z;
        this.TailC.z = this.TailA.z;

        this.TailA.xRot = tailMov;//-1.3089F+(f1*1.5F);
        this.TailB.xRot = tailMov;//-1.3089F+(f1*1.5F);
        this.TailC.xRot = -0.2618F + tailMov;//-1.5707F -tailMov;

    }

}
