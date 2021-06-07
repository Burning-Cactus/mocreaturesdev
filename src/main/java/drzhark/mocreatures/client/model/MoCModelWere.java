package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWere extends SegmentedModel<MoCEntityWerewolf> {

    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Snout;
    ModelRenderer TeethU;
    ModelRenderer TeethL;
    ModelRenderer Mouth;
    ModelRenderer LEar;
    ModelRenderer REar;
    ModelRenderer Neck;
    ModelRenderer Neck2;
    ModelRenderer SideburnL;
    ModelRenderer SideburnR;
    ModelRenderer Chest;
    ModelRenderer Abdomen;
    ModelRenderer TailA;
    ModelRenderer TailC;
    ModelRenderer TailB;
    ModelRenderer TailD;
    ModelRenderer RLegA;
    ModelRenderer RFoot;
    ModelRenderer RLegB;
    ModelRenderer RLegC;
    ModelRenderer LLegB;
    ModelRenderer LFoot;
    ModelRenderer LLegC;
    ModelRenderer LLegA;
    ModelRenderer RArmB;
    ModelRenderer RArmC;
    ModelRenderer LArmB;
    ModelRenderer RHand;
    ModelRenderer RArmA;
    ModelRenderer LArmA;
    ModelRenderer LArmC;
    ModelRenderer LHand;
    ModelRenderer RFinger1;
    ModelRenderer RFinger2;
    ModelRenderer RFinger3;
    ModelRenderer RFinger4;
    ModelRenderer RFinger5;
    ModelRenderer LFinger1;
    ModelRenderer LFinger2;
    ModelRenderer LFinger3;
    ModelRenderer LFinger4;
    ModelRenderer LFinger5;

    public boolean hunched;

    public MoCModelWere() {
        this.texWidth = 64;
        this.texHeight = 128;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-4F, -3F, -6F, 8, 8, 6);
        this.Head.setPos(0F, -8F, -6F);
        this.Head.setTexSize(64, 128);

        this.Nose = new ModelRenderer(this, 44, 33);
        this.Nose.addBox(-1.5F, -1.7F, -12.3F, 3, 2, 7);
        this.Nose.setPos(0F, -8F, -6F);
        setRotation(this.Nose, 0.2792527F, 0F, 0F);

        this.Snout = new ModelRenderer(this, 0, 25);
        this.Snout.addBox(-2F, 2F, -12F, 4, 2, 6);
        this.Snout.setPos(0F, -8F, -6F);

        this.TeethU = new ModelRenderer(this, 46, 18);
        this.TeethU.addBox(-2F, 4.01F, -12F, 4, 2, 5);
        this.TeethU.setPos(0F, -8F, -6F);

        this.TeethL = new ModelRenderer(this, 20, 109);
        this.TeethL.addBox(-1.5F, -12.5F, 2.01F, 3, 5, 2);
        this.TeethL.setPos(0F, -8F, -6F);
        setRotation(this.TeethL, 2.530727F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 42, 69);
        this.Mouth.addBox(-1.5F, -12.5F, 0F, 3, 9, 2);
        this.Mouth.setPos(0F, -8F, -6F);
        setRotation(this.Mouth, 2.530727F, 0F, 0F);

        this.LEar = new ModelRenderer(this, 13, 14);
        this.LEar.addBox(0.5F, -7.5F, -1F, 3, 5, 1);
        this.LEar.setPos(0F, -8F, -6F);
        setRotation(this.LEar, 0F, 0F, 0.1745329F);

        this.REar = new ModelRenderer(this, 22, 0);
        this.REar.addBox(-3.5F, -7.5F, -1F, 3, 5, 1);
        this.REar.setPos(0F, -8F, -6F);
        setRotation(this.REar, 0F, 0F, -0.1745329F);

        this.Neck = new ModelRenderer(this, 28, 0);
        this.Neck.addBox(-3.5F, -3F, -7F, 7, 8, 7);
        this.Neck.setPos(0F, -5F, -2F);
        setRotation(this.Neck, -0.6025001F, 0F, 0F);

        this.Neck2 = new ModelRenderer(this, 0, 14);
        this.Neck2.addBox(-1.5F, -2F, -5F, 3, 4, 7);
        this.Neck2.setPos(0F, -1F, -6F);
        setRotation(this.Neck2, -0.4537856F, 0F, 0F);

        this.SideburnL = new ModelRenderer(this, 28, 33);
        this.SideburnL.addBox(3F, 0F, -2F, 2, 6, 6);
        this.SideburnL.setPos(0F, -8F, -6F);
        setRotation(this.SideburnL, -0.2094395F, 0.418879F, -0.0872665F);

        this.SideburnR = new ModelRenderer(this, 28, 45);
        this.SideburnR.addBox(-5F, 0F, -2F, 2, 6, 6);
        this.SideburnR.setPos(0F, -8F, -6F);
        setRotation(this.SideburnR, -0.2094395F, -0.418879F, 0.0872665F);

        this.Chest = new ModelRenderer(this, 20, 15);
        this.Chest.addBox(-4F, 0F, -7F, 8, 8, 10);
        this.Chest.setPos(0F, -6F, -2.5F);
        setRotation(this.Chest, 0.641331F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 0, 40);
        this.Abdomen.addBox(-3F, -8F, -8F, 6, 14, 8);
        this.Abdomen.setPos(0F, 4.5F, 5F);
        setRotation(this.Abdomen, 0.2695449F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 52, 42);
        this.TailA.addBox(-1.5F, -1F, -2F, 3, 4, 3);
        this.TailA.setPos(0F, 9.5F, 6F);
        setRotation(this.TailA, 1.064651F, 0F, 0F);

        this.TailC = new ModelRenderer(this, 48, 59);
        this.TailC.addBox(-2F, 6.8F, -4.6F, 4, 6, 4);
        this.TailC.setPos(0F, 9.5F, 6F);
        setRotation(this.TailC, 1.099557F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 48, 49);
        this.TailB.addBox(-2F, 2F, -2F, 4, 6, 4);
        this.TailB.setPos(0F, 9.5F, 6F);
        setRotation(this.TailB, 0.7504916F, 0F, 0F);

        this.TailD = new ModelRenderer(this, 52, 69);
        this.TailD.addBox(-1.5F, 9.8F, -4.1F, 3, 5, 3);
        this.TailD.setPos(0F, 9.5F, 6F);
        setRotation(this.TailD, 1.099557F, 0F, 0F);

        this.RLegA = new ModelRenderer(this, 12, 64);
        this.RLegA.addBox(-2.5F, -1.5F, -3.5F, 3, 8, 5);
        this.RLegA.setPos(-3F, 9.5F, 3F);
        setRotation(this.RLegA, -0.8126625F, 0F, 0F);

        this.RFoot = new ModelRenderer(this, 14, 93);
        this.RFoot.addBox(-2.506667F, 12.5F, -5F, 3, 2, 3);
        this.RFoot.setPos(-3F, 9.5F, 3F);

        this.RLegB = new ModelRenderer(this, 14, 76);
        this.RLegB.addBox(-1.9F, 4.2F, 0.5F, 2, 2, 5);
        this.RLegB.setPos(-3F, 9.5F, 3F);
        setRotation(this.RLegB, -0.8445741F, 0F, 0F);

        this.RLegC = new ModelRenderer(this, 14, 83);
        this.RLegC.addBox(-2F, 6.2F, 0.5F, 2, 8, 2);
        this.RLegC.setPos(-3F, 9.5F, 3F);
        setRotation(this.RLegC, -0.2860688F, 0F, 0F);

        this.LLegB = new ModelRenderer(this, 0, 76);
        this.LLegB.addBox(-0.1F, 4.2F, 0.5F, 2, 2, 5);
        this.LLegB.setPos(3F, 9.5F, 3F);
        setRotation(this.LLegB, -0.8445741F, 0F, 0F);

        this.LFoot = new ModelRenderer(this, 0, 93);
        this.LFoot.addBox(-0.5066667F, 12.5F, -5F, 3, 2, 3);
        this.LFoot.setPos(3F, 9.5F, 3F);

        this.LLegC = new ModelRenderer(this, 0, 83);
        this.LLegC.addBox(0F, 6.2F, 0.5F, 2, 8, 2);
        this.LLegC.setPos(3F, 9.5F, 3F);
        setRotation(this.LLegC, -0.2860688F, 0F, 0F);

        this.LLegA = new ModelRenderer(this, 0, 64);
        this.LLegA.addBox(-0.5F, -1.5F, -3.5F, 3, 8, 5);
        this.LLegA.setPos(3F, 9.5F, 3F);
        setRotation(this.LLegA, -0.8126625F, 0F, 0F);

        this.RArmB = new ModelRenderer(this, 48, 77);
        this.RArmB.addBox(-3.5F, 1F, -1.5F, 4, 8, 4);
        this.RArmB.setPos(-4F, -4F, -2F);
        setRotation(this.RArmB, 0.2617994F, 0F, 0.3490659F);

        this.RArmC = new ModelRenderer(this, 48, 112);
        this.RArmC.addBox(-6F, 5F, 3F, 4, 7, 4);
        this.RArmC.setPos(-4F, -4F, -2F);
        setRotation(this.RArmC, -0.3490659F, 0F, 0F);

        this.LArmB = new ModelRenderer(this, 48, 89);
        this.LArmB.addBox(-0.5F, 1F, -1.5F, 4, 8, 4);
        this.LArmB.setPos(4F, -4F, -2F);
        setRotation(this.LArmB, 0.2617994F, 0F, -0.3490659F);

        this.RHand = new ModelRenderer(this, 32, 118);
        this.RHand.addBox(-6F, 12.5F, -1.5F, 4, 3, 4);
        this.RHand.setPos(-4F, -4F, -2F);

        this.RArmA = new ModelRenderer(this, 0, 108);
        this.RArmA.addBox(-5F, -3F, -2F, 5, 5, 5);
        this.RArmA.setPos(-4F, -4F, -2F);
        setRotation(this.RArmA, 0.6320364F, 0F, 0F);

        this.LArmA = new ModelRenderer(this, 0, 98);
        this.LArmA.addBox(0F, -3F, -2F, 5, 5, 5);
        this.LArmA.setPos(4F, -4F, -2F);
        setRotation(this.LArmA, 0.6320364F, 0F, 0F);

        this.LArmC = new ModelRenderer(this, 48, 101);
        this.LArmC.addBox(2F, 5F, 3F, 4, 7, 4);
        this.LArmC.setPos(4F, -4F, -2F);
        setRotation(this.LArmC, -0.3490659F, 0F, 0F);

        this.LHand = new ModelRenderer(this, 32, 111);
        this.LHand.addBox(2F, 12.5F, -1.5F, 4, 3, 4);
        this.LHand.setPos(4F, -4F, -2F);

        this.RFinger1 = new ModelRenderer(this, 8, 120);
        this.RFinger1.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        this.RFinger1.setPos(-6.5F, 11.5F, -0.5F);

        this.RFinger1 = new ModelRenderer(this, 8, 120);
        this.RFinger1.addBox(-3F, 15.5F, 1F, 1, 3, 1);
        this.RFinger1.setPos(-4F, -4F, -2F);

        this.RFinger2 = new ModelRenderer(this, 12, 124);
        this.RFinger2.addBox(-3.5F, 15.5F, -1.5F, 1, 3, 1);
        this.RFinger2.setPos(-4F, -4F, -2F);

        this.RFinger3 = new ModelRenderer(this, 12, 119);
        this.RFinger3.addBox(-4.8F, 15.5F, -1.5F, 1, 4, 1);
        this.RFinger3.setPos(-4F, -4F, -2F);

        this.RFinger4 = new ModelRenderer(this, 16, 119);
        this.RFinger4.addBox(-6F, 15.5F, -0.5F, 1, 4, 1);
        this.RFinger4.setPos(-4F, -4F, -2F);

        this.RFinger5 = new ModelRenderer(this, 16, 124);
        this.RFinger5.addBox(-6F, 15.5F, 1F, 1, 3, 1);
        this.RFinger5.setPos(-4F, -4F, -2F);

        this.LFinger1 = new ModelRenderer(this, 8, 124);
        this.LFinger1.addBox(2F, 15.5F, 1F, 1, 3, 1);
        this.LFinger1.setPos(4F, -4F, -2F);

        this.LFinger2 = new ModelRenderer(this, 0, 124);
        this.LFinger2.addBox(2.5F, 15.5F, -1.5F, 1, 3, 1);
        this.LFinger2.setPos(4F, -4F, -2F);

        this.LFinger3 = new ModelRenderer(this, 0, 119);
        this.LFinger3.addBox(3.8F, 15.5F, -1.5F, 1, 4, 1);
        this.LFinger3.setPos(4F, -4F, -2F);

        this.LFinger4 = new ModelRenderer(this, 4, 119);
        this.LFinger4.addBox(5F, 15.5F, -0.5F, 1, 4, 1);
        this.LFinger4.setPos(4F, -4F, -2F);

        this.LFinger5 = new ModelRenderer(this, 4, 124);
        this.LFinger5.addBox(5F, 15.5F, 1F, 1, 3, 1);
        this.LFinger5.setPos(4F, -4F, -2F);

    }

    @Override
    public void setupAnim(MoCEntityWerewolf entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(Head, Nose, Snout, TeethU, TeethL, Mouth, LEar, REar, Neck, Neck2, SideburnL, SideburnR, Chest,
                Abdomen, TailA, TailC, TailB, TailD, RLegA, RFoot, RLegB, RLegC, RLegB, LFoot, LLegC, LLegA, RArmB, RArmC,
                RHand, RArmA, LArmA, LArmB, LArmC, LHand, RFinger1, RFinger2, RFinger3, RFinger4, RFinger5, LFinger1, LFinger2,
                LFinger3, LFinger4, LFinger5);
    }


    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {

        float radianF = 57.29578F;
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        this.Head.yRot = f3 / radianF; //this moves head to left and right
        //Head.rotateAngleX = f4 / radianF; //this moves head up and down

        if (!this.hunched) {
            this.Head.y = -8F;
            this.Head.z = -6F;
            this.Head.xRot = f4 / radianF;
            this.Neck.xRot = -34 / radianF;
            this.Neck.y = -5F;
            this.Neck.z = -2F;
            this.Neck2.y = -1F;
            this.Neck2.z = -6F;
            this.Chest.y = -6F;
            this.Chest.z = -2.5F;
            this.Chest.xRot = 36 / radianF;
            this.Abdomen.xRot = 15 / radianF;
            this.LLegA.z = 3F;

            this.LArmA.y = -4F;
            this.LArmA.z = -2F;

            this.TailA.y = 9.5F;
            this.TailA.z = 6F;

        } else {
            this.Head.y = 0F;
            this.Head.z = -11F;
            this.Head.xRot = (15F + f4) / radianF;

            this.Neck.xRot = -10 / radianF;
            this.Neck.y = 2F;
            this.Neck.z = -6F;
            this.Neck2.y = 9F;
            this.Neck2.z = -9F;
            this.Chest.y = 1F;
            this.Chest.z = -7.5F;
            this.Chest.xRot = 60 / radianF;
            this.Abdomen.xRot = 75 / radianF;
            this.LLegA.z = 7F;
            this.LArmA.y = 4.5F;
            this.LArmA.z = -6F;
            this.TailA.y = 7.5F;
            this.TailA.z = 10F;

        }

        this.Nose.y = this.Head.y;
        this.Snout.y = this.Head.y;
        this.TeethU.y = this.Head.y;
        this.LEar.y = this.Head.y;
        this.REar.y = this.Head.y;
        this.TeethL.y = this.Head.y;
        this.Mouth.y = this.Head.y;
        this.SideburnL.y = this.Head.y;
        this.SideburnR.y = this.Head.y;

        this.Nose.z = this.Head.z;
        this.Snout.z = this.Head.z;
        this.TeethU.z = this.Head.z;
        this.LEar.z = this.Head.z;
        this.REar.z = this.Head.z;
        this.TeethL.z = this.Head.z;
        this.Mouth.z = this.Head.z;
        this.SideburnL.z = this.Head.z;
        this.SideburnR.z = this.Head.z;

        this.LArmB.y = this.LArmA.y;
        this.LArmC.y = this.LArmA.y;
        this.LHand.y = this.LArmA.y;
        this.LFinger1.y = this.LArmA.y;
        this.LFinger2.y = this.LArmA.y;
        this.LFinger3.y = this.LArmA.y;
        this.LFinger4.y = this.LArmA.y;
        this.LFinger5.y = this.LArmA.y;
        this.RArmA.y = this.LArmA.y;
        this.RArmB.y = this.LArmA.y;
        this.RArmC.y = this.LArmA.y;
        this.RHand.y = this.LArmA.y;
        this.RFinger1.y = this.LArmA.y;
        this.RFinger2.y = this.LArmA.y;
        this.RFinger3.y = this.LArmA.y;
        this.RFinger4.y = this.LArmA.y;
        this.RFinger5.y = this.LArmA.y;

        this.LArmB.z = this.LArmA.z;
        this.LArmC.z = this.LArmA.z;
        this.LHand.z = this.LArmA.z;
        this.LFinger1.z = this.LArmA.z;
        this.LFinger2.z = this.LArmA.z;
        this.LFinger3.z = this.LArmA.z;
        this.LFinger4.z = this.LArmA.z;
        this.LFinger5.z = this.LArmA.z;
        this.RArmA.z = this.LArmA.z;
        this.RArmB.z = this.LArmA.z;
        this.RArmC.z = this.LArmA.z;
        this.RHand.z = this.LArmA.z;
        this.RFinger1.z = this.LArmA.z;
        this.RFinger2.z = this.LArmA.z;
        this.RFinger3.z = this.LArmA.z;
        this.RFinger4.z = this.LArmA.z;
        this.RFinger5.z = this.LArmA.z;

        this.RLegA.z = this.LLegA.z;
        this.RLegB.z = this.LLegA.z;
        this.RLegC.z = this.LLegA.z;
        this.RFoot.z = this.LLegA.z;
        this.LLegB.z = this.LLegA.z;
        this.LLegC.z = this.LLegA.z;
        this.LFoot.z = this.LLegA.z;

        this.TailB.y = this.TailA.y;
        this.TailB.z = this.TailA.z;
        this.TailC.y = this.TailA.y;
        this.TailC.z = this.TailA.z;
        this.TailD.y = this.TailA.y;
        this.TailD.z = this.TailA.z;

        this.Nose.yRot = this.Head.yRot;
        this.Snout.yRot = this.Head.yRot;
        this.TeethU.yRot = this.Head.yRot;
        this.LEar.yRot = this.Head.yRot;
        this.REar.yRot = this.Head.yRot;
        this.TeethL.yRot = this.Head.yRot;
        this.Mouth.yRot = this.Head.yRot;

        this.TeethL.xRot = this.Head.xRot + 2.530727F;
        this.Mouth.xRot = this.Head.xRot + 2.530727F;

        this.SideburnL.xRot = -0.2094395F + this.Head.xRot;
        this.SideburnL.yRot = 0.418879F + this.Head.yRot;
        this.SideburnR.xRot = -0.2094395F + this.Head.xRot;
        this.SideburnR.yRot = -0.418879F + this.Head.yRot;

        this.Nose.xRot = 0.2792527F + this.Head.xRot;
        this.Snout.xRot = this.Head.xRot;
        this.TeethU.xRot = this.Head.xRot;

        this.LEar.xRot = this.Head.xRot;
        this.REar.xRot = this.Head.xRot;

        this.RLegA.xRot = -0.8126625F + RLegXRot;
        this.RLegB.xRot = -0.8445741F + RLegXRot;
        this.RLegC.xRot = -0.2860688F + RLegXRot;
        this.RFoot.xRot = RLegXRot;

        this.LLegA.xRot = -0.8126625F + LLegXRot;
        this.LLegB.xRot = -0.8445741F + LLegXRot;
        this.LLegC.xRot = -0.2860688F + LLegXRot;
        this.LFoot.xRot = LLegXRot;

        this.RArmA.zRot = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.LArmA.zRot = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
        this.RArmA.xRot = LLegXRot;//MathHelper.sin(f2 * 0.067F) * 0.05F;
        this.LArmA.xRot = RLegXRot;//MathHelper.sin(f2 * 0.067F) * 0.05F;

        this.RArmB.zRot = 0.3490659F + this.RArmA.zRot;
        this.LArmB.zRot = -0.3490659F + this.LArmA.zRot;
        this.RArmB.xRot = 0.2617994F + this.RArmA.xRot;
        this.LArmB.xRot = 0.2617994F + this.LArmA.xRot;

        this.RArmC.zRot = this.RArmA.zRot;
        this.LArmC.zRot = this.LArmA.zRot;
        this.RArmC.xRot = -0.3490659F + this.RArmA.xRot;
        this.LArmC.xRot = -0.3490659F + this.LArmA.xRot;

        this.RHand.zRot = this.RArmA.zRot;
        this.LHand.zRot = this.LArmA.zRot;
        this.RHand.xRot = this.RArmA.xRot;
        this.LHand.xRot = this.LArmA.xRot;

        this.RFinger1.xRot = this.RArmA.xRot;
        this.RFinger2.xRot = this.RArmA.xRot;
        this.RFinger3.xRot = this.RArmA.xRot;
        this.RFinger4.xRot = this.RArmA.xRot;
        this.RFinger5.xRot = this.RArmA.xRot;

        this.LFinger1.xRot = this.LArmA.xRot;
        this.LFinger2.xRot = this.LArmA.xRot;
        this.LFinger3.xRot = this.LArmA.xRot;
        this.LFinger4.xRot = this.LArmA.xRot;
        this.LFinger5.xRot = this.LArmA.xRot;

        this.RFinger1.zRot = this.RArmA.zRot;
        this.RFinger2.zRot = this.RArmA.zRot;
        this.RFinger3.zRot = this.RArmA.zRot;
        this.RFinger4.zRot = this.RArmA.zRot;
        this.RFinger5.zRot = this.RArmA.zRot;

        this.LFinger1.zRot = this.LArmA.zRot;
        this.LFinger2.zRot = this.LArmA.zRot;
        this.LFinger3.zRot = this.LArmA.zRot;
        this.LFinger4.zRot = this.LArmA.zRot;
        this.LFinger5.zRot = this.LArmA.zRot;

    }

}
