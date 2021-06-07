package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWolf<T extends MoCEntityWWolf> extends SegmentedModel<T> {

    ModelRenderer Head;
    ModelRenderer MouthB;
    ModelRenderer Nose2;
    ModelRenderer Neck;
    ModelRenderer Neck2;
    ModelRenderer LSide;
    ModelRenderer RSide;
    ModelRenderer REar2;
    ModelRenderer Nose;
    ModelRenderer Mouth;
    ModelRenderer MouthOpen;
    ModelRenderer REar;
    ModelRenderer LEar2;
    ModelRenderer LEar;
    ModelRenderer UTeeth;
    ModelRenderer LTeeth;

    ModelRenderer Chest;
    ModelRenderer Body;

    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;
    ModelRenderer TailD;

    ModelRenderer Leg4A;
    ModelRenderer Leg4D;
    ModelRenderer Leg4B;
    ModelRenderer Leg4C;
    ModelRenderer Leg3B;
    ModelRenderer Leg2A;
    ModelRenderer Leg2B;
    ModelRenderer Leg2C;
    ModelRenderer Leg3D;
    ModelRenderer Leg3C;
    ModelRenderer Leg3A;
    ModelRenderer Leg1A;
    ModelRenderer Leg1B;
    ModelRenderer Leg1C;

    public MoCModelWolf() {
        this.texWidth = 64;
        this.texHeight = 128;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-4F, -3F, -6F, 8, 8, 6);
        this.Head.setPos(0F, 7F, -10F);

        this.MouthB = new ModelRenderer(this, 16, 33);
        this.MouthB.addBox(-2F, 4F, -7F, 4, 1, 2);
        this.MouthB.setPos(0F, 7F, -10F);

        this.Nose2 = new ModelRenderer(this, 0, 25);
        this.Nose2.addBox(-2F, 2F, -12F, 4, 2, 6);
        this.Nose2.setPos(0F, 7F, -10F);

        this.Neck = new ModelRenderer(this, 28, 0);
        this.Neck.addBox(-3.5F, -3F, -7F, 7, 8, 7);
        this.Neck.setPos(0F, 10F, -6F);
        setRotation(this.Neck, -0.4537856F, 0F, 0F);

        this.Neck2 = new ModelRenderer(this, 0, 14);
        this.Neck2.addBox(-1.5F, -2F, -5F, 3, 4, 7);
        this.Neck2.setPos(0F, 14F, -10F);
        setRotation(this.Neck2, -0.4537856F, 0F, 0F);

        this.LSide = new ModelRenderer(this, 28, 33);
        this.LSide.addBox(3F, -0.5F, -2F, 2, 6, 6);
        this.LSide.setPos(0F, 7F, -10F);
        setRotation(this.LSide, -0.2094395F, 0.418879F, -0.0872665F);

        this.RSide = new ModelRenderer(this, 28, 45);
        this.RSide.addBox(-5F, -0.5F, -2F, 2, 6, 6);
        this.RSide.setPos(0F, 7F, -10F);
        setRotation(this.RSide, -0.2094395F, -0.418879F, 0.0872665F);

        this.Nose = new ModelRenderer(this, 44, 33);
        this.Nose.addBox(-1.5F, -1.8F, -12.4F, 3, 2, 7);
        this.Nose.setPos(0F, 7F, -10F);
        setRotation(this.Nose, 0.2792527F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 1, 34);
        this.Mouth.addBox(-2F, 4F, -11.5F, 4, 1, 5);
        this.Mouth.setPos(0F, 7F, -10F);

        this.UTeeth = new ModelRenderer(this, 46, 18);
        this.UTeeth.addBox(-2F, 4F, -12F, 4, 2, 5);
        this.UTeeth.setPos(0F, 7F, -10F);

        this.LTeeth = new ModelRenderer(this, 20, 109);
        this.LTeeth.addBox(-1.5F, -12.9F, 1.2F, 3, 5, 2);
        this.LTeeth.setPos(0F, 7F, -10F);
        setRotation(this.LTeeth, 145 / 57.29578F, 0F, 0F);

        /*this.MouthOpen = new ModelRenderer(this, 1, 34);
        this.MouthOpen.addBox(-2F, 0F, -12.5F, 4, 1, 5);
        this.MouthOpen.setRotationPoint(0F, 7F, -10F);
        setRotation(this.MouthOpen, 0.6108652F, 0F, 0F);*/

        this.MouthOpen = new ModelRenderer(this, 42, 69);
        this.MouthOpen.addBox(-1.5F, -12.9F, -0.81F, 3, 9, 2);
        this.MouthOpen.setPos(0F, 7F, -10F);
        setRotation(this.MouthOpen, 145 / 57.29578F, 0F, 0F);

        this.REar = new ModelRenderer(this, 22, 0);
        this.REar.addBox(-3.5F, -7F, -1.5F, 3, 5, 1);
        this.REar.setPos(0F, 7F, -10F);
        setRotation(this.REar, 0F, 0F, -0.1745329F);

        this.LEar = new ModelRenderer(this, 13, 14);
        this.LEar.addBox(0.5F, -7F, -1.5F, 3, 5, 1);
        this.LEar.setPos(0F, 7F, -10F);
        setRotation(this.LEar, 0F, 0F, 0.1745329F);

        this.Chest = new ModelRenderer(this, 20, 15);
        this.Chest.addBox(-4F, -11F, -12F, 8, 8, 10);
        this.Chest.setPos(0F, 5F, 2F);
        setRotation(this.Chest, 1.570796F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 40);
        this.Body.addBox(-3F, -8F, -9F, 6, 16, 8);
        this.Body.setPos(0F, 6.5F, 2F);
        setRotation(this.Body, 1.570796F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 52, 42);
        this.TailA.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.TailA.setPos(0F, 8.5F, 9F);
        setRotation(this.TailA, 1.064651F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 48, 49);
        this.TailB.addBox(-2F, 3F, -1F, 4, 6, 4);
        this.TailB.setPos(0F, 8.5F, 9F);
        setRotation(this.TailB, 0.7504916F, 0F, 0F);

        this.TailC = new ModelRenderer(this, 48, 59);
        this.TailC.addBox(-2F, 7.8F, -4.1F, 4, 6, 4);
        this.TailC.setPos(0F, 8.5F, 9F);
        setRotation(this.TailC, 1.099557F, 0F, 0F);

        this.TailD = new ModelRenderer(this, 52, 69);
        this.TailD.addBox(-1.5F, 9.8F, -3.6F, 3, 5, 3);
        this.TailD.setPos(0F, 8.5F, 9F);
        setRotation(this.TailD, 1.099557F, 0F, 0F);

        this.Leg1A = new ModelRenderer(this, 28, 57);
        this.Leg1A.addBox(0.01F, -4F, -2.5F, 2, 8, 4);
        this.Leg1A.setPos(4F, 12.5F, -5.5F);
        setRotation(this.Leg1A, 0.2617994F, 0F, 0F);

        this.Leg1B = new ModelRenderer(this, 28, 69);
        this.Leg1B.addBox(0F, 3.2F, 0.5F, 2, 8, 2);
        this.Leg1B.setPos(4F, 12.5F, -5.5F);
        setRotation(this.Leg1B, -0.1745329F, 0F, 0F);

        this.Leg1C = new ModelRenderer(this, 28, 79);
        this.Leg1C.addBox(-0.5066667F, 9.5F, -2.5F, 3, 2, 3);
        this.Leg1C.setPos(4F, 12.5F, -5.5F);

        this.Leg2A = new ModelRenderer(this, 28, 84);
        this.Leg2A.addBox(-2.01F, -4F, -2.5F, 2, 8, 4);
        this.Leg2A.setPos(-4F, 12.5F, -5.5F);
        setRotation(this.Leg2A, 0.2617994F, 0F, 0F);

        this.Leg2B = new ModelRenderer(this, 28, 96);
        this.Leg2B.addBox(-2F, 3.2F, 0.5F, 2, 8, 2);
        this.Leg2B.setPos(-4F, 12.5F, -5.5F);
        setRotation(this.Leg2B, -0.1745329F, 0F, 0F);

        this.Leg2C = new ModelRenderer(this, 28, 106);
        this.Leg2C.addBox(-2.506667F, 9.5F, -2.5F, 3, 2, 3);
        this.Leg2C.setPos(-4F, 12.5F, -5.5F);

        this.Leg3A = new ModelRenderer(this, 0, 64);
        this.Leg3A.addBox(0F, -3.8F, -3.5F, 2, 7, 5);
        this.Leg3A.setPos(3F, 12.5F, 7F);
        setRotation(this.Leg3A, -0.3665191F, 0F, 0F);

        this.Leg3B = new ModelRenderer(this, 0, 76);
        this.Leg3B.addBox(-0.1F, 1.9F, -1.8F, 2, 2, 5);
        this.Leg3B.setPos(3F, 12.5F, 7F);
        setRotation(this.Leg3B, -0.7330383F, 0F, 0F);

        this.Leg3C = new ModelRenderer(this, 0, 83);
        this.Leg3C.addBox(0F, 3.2F, 0F, 2, 8, 2);
        this.Leg3C.setPos(3F, 12.5F, 7F);
        setRotation(this.Leg3C, -0.1745329F, 0F, 0F);

        this.Leg3D = new ModelRenderer(this, 0, 93);
        this.Leg3D.addBox(-0.5066667F, 9.5F, -3F, 3, 2, 3);
        this.Leg3D.setPos(3F, 12.5F, 7F);

        this.Leg4A = new ModelRenderer(this, 14, 64);
        this.Leg4A.addBox(-2F, -3.8F, -3.5F, 2, 7, 5);
        this.Leg4A.setPos(-3F, 12.5F, 7F);
        setRotation(this.Leg4A, -0.3665191F, 0F, 0F);

        this.Leg4B = new ModelRenderer(this, 14, 76);
        this.Leg4B.addBox(-1.9F, 1.9F, -1.8F, 2, 2, 5);
        this.Leg4B.setPos(-3F, 12.5F, 7F);
        setRotation(this.Leg4B, -0.7330383F, 0F, 0F);

        this.Leg4C = new ModelRenderer(this, 14, 83);
        this.Leg4C.addBox(-2F, 3.2F, 0F, 2, 8, 2);
        this.Leg4C.setPos(-3F, 12.5F, 7F);
        setRotation(this.Leg4C, -0.1745329F, 0F, 0F);

        this.Leg4D = new ModelRenderer(this, 14, 93);
        this.Leg4D.addBox(-2.506667F, 9.5F, -3F, 3, 2, 3);
        this.Leg4D.setPos(-3F, 12.5F, 7F);

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntityWWolf entitywolf = (MoCEntityWWolf) entityIn;
        boolean openMouth = (entitywolf.mouthCounter != 0);
        boolean moveTail = (entitywolf.tailCounter != 0);

        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, moveTail);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(Head, Nose2, Neck, Neck2, LSide, RSide, Nose, Mouth, MouthB, REar, LEar, Chest, Body,
                TailA, TailB, TailC, TailD, Leg4A, Leg4D, Leg4B, Leg4C, Leg3B, Leg2A, Leg2B, Leg2C, Leg3D, Leg3C, Leg3A, Leg1A, Leg1B,
                Leg1C);
    }

//    @Override
//    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

//        if (openMouth) { TODO: Get open mouth working
//            this.MouthOpen.render(f5);
//            this.UTeeth.render(f5);
//            this.LTeeth.render(f5);
//
//        } else {
//            this.Mouth.render(f5);
//            this.MouthB.render(f5);
//        }
//    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, boolean tail) {

        this.Head.xRot = f4 / 57.29578F;
        this.Head.yRot = f3 / 57.29578F;
        float LLegX = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float RLegX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;

        this.Mouth.xRot = this.Head.xRot;
        this.Mouth.yRot = this.Head.yRot;
        this.MouthB.xRot = this.Head.xRot;
        this.MouthB.yRot = this.Head.yRot;
        this.UTeeth.xRot = this.Head.xRot;
        this.UTeeth.yRot = this.Head.yRot;
        this.MouthOpen.xRot = (145 / 57.29578F) + this.Head.xRot;
        this.MouthOpen.yRot = this.Head.yRot;
        this.LTeeth.xRot = (145 / 57.29578F) + this.Head.xRot;
        this.LTeeth.yRot = this.Head.yRot;
        this.Nose.xRot = (16 / 57.29578F) + this.Head.xRot;
        this.Nose.yRot = this.Head.yRot;
        this.Nose2.xRot = this.Head.xRot;
        this.Nose2.yRot = this.Head.yRot;

        this.LSide.xRot = (-12 / 57.29578F) + this.Head.xRot;
        this.LSide.yRot = (24 / 57.29578F) + this.Head.yRot;
        this.RSide.xRot = (-12 / 57.29578F) + this.Head.xRot;
        this.RSide.yRot = (-24 / 57.29578F) + this.Head.yRot;

        this.REar.xRot = this.Head.xRot;
        this.REar.yRot = this.Head.yRot;
        this.LEar.xRot = this.Head.xRot;
        this.LEar.yRot = this.Head.yRot;

        this.Leg1A.xRot = (15 / 57.29578F) + LLegX;
        this.Leg1B.xRot = (-10 / 57.29578F) + LLegX;
        this.Leg1C.xRot = LLegX;

        this.Leg2A.xRot = (15 / 57.29578F) + RLegX;
        this.Leg2B.xRot = (-10 / 57.29578F) + RLegX;
        this.Leg2C.xRot = RLegX;

        this.Leg3A.xRot = (-21 / 57.29578F) + RLegX;
        this.Leg3B.xRot = (-42 / 57.29578F) + RLegX;
        this.Leg3C.xRot = (-10 / 57.29578F) + RLegX;
        this.Leg3D.xRot = RLegX;

        this.Leg4A.xRot = (-21 / 57.29578F) + LLegX;
        this.Leg4B.xRot = (-42 / 57.29578F) + LLegX;
        this.Leg4C.xRot = (-10 / 57.29578F) + LLegX;
        this.Leg4D.xRot = LLegX;

        float tailMov = -1.3089F + (f1 * 1.5F);

        if (tail) {
            this.TailA.yRot = MathHelper.cos(f2 * 0.5F);
            tailMov = 0;
        } else {
            this.TailA.yRot = 0F;
        }

        this.TailA.xRot = (61 / 57.29F) - tailMov;//-1.3089F+(f1*1.5F);
        this.TailB.xRot = (43 / 57.29F) - tailMov;//-1.3089F+(f1*1.5F);
        this.TailC.xRot = (63 / 57.29F) - tailMov;//-1.5707F -tailMov;
        this.TailD.xRot = (63 / 57.29F) - tailMov;

        this.TailB.yRot = this.TailA.yRot;
        this.TailC.yRot = this.TailA.yRot;
        this.TailD.yRot = this.TailA.yRot;
    }

}
