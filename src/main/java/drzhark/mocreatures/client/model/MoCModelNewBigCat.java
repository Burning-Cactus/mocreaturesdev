package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelNewBigCat<T extends MoCEntityBigCat> extends EntityModel<T> {

    //fields
    ModelRenderer RightHindFoot;
    ModelRenderer Stinger;
    ModelRenderer RightHindUpperLeg;
    ModelRenderer RightAnkle;
    ModelRenderer RightHindLowerLeg;
    ModelRenderer Ass;
    ModelRenderer TailTusk;
    ModelRenderer LeftChinBeard;
    ModelRenderer NeckBase;
    ModelRenderer RightEar;
    ModelRenderer LeftEar;
    ModelRenderer ForeheadHair;
    ModelRenderer LeftHarness;
    ModelRenderer RightHarness;
    ModelRenderer LeftUpperLip;
    ModelRenderer RightChinBeard;
    ModelRenderer LeftHindUpperLeg;
    ModelRenderer LeftHindLowerLeg;
    ModelRenderer LeftHindFoot;
    ModelRenderer LeftAnkle;
    ModelRenderer InsideMouth;
    ModelRenderer RightUpperLip;
    ModelRenderer LowerJawTeeth;
    ModelRenderer Nose;
    ModelRenderer LeftFang;
    ModelRenderer UpperTeeth;
    ModelRenderer RightFang;
    ModelRenderer LowerJaw;
    ModelRenderer SaddleFront;
    ModelRenderer LeftUpperLeg;
    ModelRenderer LeftLowerLeg;
    ModelRenderer LeftFrontFoot;
    ModelRenderer LeftClaw2;
    ModelRenderer LeftClaw1;
    ModelRenderer LeftClaw3;
    ModelRenderer RightClaw1;
    ModelRenderer RightClaw2;
    ModelRenderer RightClaw3;
    ModelRenderer RightFrontFoot;
    ModelRenderer RightLowerLeg;
    ModelRenderer RightUpperLeg;
    ModelRenderer Head;
    ModelRenderer ChinHair;
    ModelRenderer NeckHair;
    ModelRenderer Mane;
    ModelRenderer InnerWing;
    ModelRenderer MidWing;
    ModelRenderer OuterWing;
    ModelRenderer InnerWingR;
    ModelRenderer MidWingR;
    ModelRenderer OuterWingR;
    ModelRenderer Abdomen;
    ModelRenderer STailRoot;
    ModelRenderer STail2;
    ModelRenderer STail3;
    ModelRenderer STail4;
    ModelRenderer STail5;
    ModelRenderer StingerLump;
    ModelRenderer TailRoot;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer Tail4;
    ModelRenderer TailTip;
    ModelRenderer Chest;
    ModelRenderer SaddleBack;
    ModelRenderer LeftFootRing;
    ModelRenderer Saddle;
    ModelRenderer LeftFootHarness;
    ModelRenderer RightFootHarness;
    ModelRenderer RightFootRing;
    ModelRenderer HeadBack;
    ModelRenderer HarnessStick;
    ModelRenderer NeckHarness;
    ModelRenderer Collar;
    ModelRenderer StorageChest;

    private float radianF = 57.29578F;

    protected boolean hasMane;
    protected boolean isRidden;
    private boolean isChested;
    protected boolean isSaddled;
    protected boolean flapwings;
    protected boolean onAir;
    private boolean diving;
    private boolean isSitting;
    protected boolean isFlyer;
    protected boolean floating;
    protected boolean poisoning;
    protected boolean isTamed;
    protected boolean movingTail;
    private float lLegMov = 0F;
    private float rLegMov = 0F;
    protected int openMouthCounter;
    protected boolean hasSaberTeeth;
    protected boolean hasChest;
    protected boolean hasStinger;
    protected boolean isGhost;
    protected boolean isMovingVertically;

    public MoCModelNewBigCat() {
        this.texWidth = 128;
        this.texHeight = 128;

        this.Chest = new ModelRenderer(this, 0, 18);
        this.Chest.addBox(-3.5F, 0F, -8F, 7, 8, 9);
        this.Chest.setPos(0F, 8F, 0F);

        this.NeckBase = new ModelRenderer(this, 0, 7);
        this.NeckBase.addBox(-2.5F, 0F, -2.5F, 5, 6, 5);
        this.NeckBase.setPos(0F, -0.5F, -8F);
        setRotation(this.NeckBase, -14F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.NeckBase);

        this.Collar = new ModelRenderer(this, 18, 0);
        this.Collar.addBox(-2.5F, 0F, 0F, 5, 4, 1, 0.0F);
        this.Collar.setPos(0.0F, 6F, -2F);
        setRotation(this.Collar, 20F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.Collar);

        this.HeadBack = new ModelRenderer(this, 0, 0);
        this.HeadBack.addBox(-2.51F, -2.5F, -1F, 5, 5, 2);
        this.HeadBack.setPos(0F, 2.7F, -2.9F);
        setRotation(this.HeadBack, 14F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.HeadBack);

        this.NeckHarness = new ModelRenderer(this, 85, 32);
        this.NeckHarness.addBox(-3F, -3F, -2F, 6, 6, 2);
        this.NeckHarness.setPos(0F, 0F, 0.95F);
        this.HeadBack.addChild(this.NeckHarness);

        this.HarnessStick = new ModelRenderer(this, 85, 42);
        this.HarnessStick.addBox(-3.5F, -0.5F, -0.5F, 7, 1, 1);
        this.HarnessStick.setPos(0F, -1.8F, 0.5F);
        setRotation(this.HarnessStick, 45F / this.radianF, 0F, 0F);
        this.HeadBack.addChild(this.HarnessStick);

        this.LeftHarness = new ModelRenderer(this, 85, 32);
        this.LeftHarness.addBox(3.2F, -0.6F, 1.5F, 0, 1, 9);
        this.LeftHarness.setPos(0F, 8.6F, -13F);
        setRotation(this.LeftHarness, 25F / this.radianF, 0F, 0F);

        this.RightHarness = new ModelRenderer(this, 85, 31);
        this.RightHarness.addBox(-3.2F, -0.6F, 1.5F, 0, 1, 9);
        this.RightHarness.setPos(0F, 8.6F, -13F);
        setRotation(this.RightHarness, 25F / this.radianF, 0F, 0F);

        this.Head = new ModelRenderer(this, 32, 0);
        this.Head.addBox(-3.5F, -3F, -2F, 7, 6, 4);
        this.Head.setPos(0F, 0.2F, -2.2F);
        this.HeadBack.addChild(this.Head);

        this.Nose = new ModelRenderer(this, 46, 19);
        this.Nose.addBox(-1.5F, -1F, -2F, 3, 2, 4);
        this.Nose.setPos(0F, 0F, -3F);
        setRotation(this.Nose, 27F / this.radianF, 0F, 0F);
        this.Head.addChild(this.Nose);

        this.RightUpperLip = new ModelRenderer(this, 34, 19);
        this.RightUpperLip.addBox(-1F, -1F, -2F, 2, 2, 4);
        this.RightUpperLip.setPos(-1.25F, 1F, -2.8F);
        setRotation(this.RightUpperLip, 10F / this.radianF, 2F / this.radianF, -15F / this.radianF);
        this.Head.addChild(this.RightUpperLip);

        this.LeftUpperLip = new ModelRenderer(this, 34, 25);
        this.LeftUpperLip.addBox(-1F, -1F, -2F, 2, 2, 4);
        this.LeftUpperLip.setPos(1.25F, 1F, -2.8F);
        setRotation(this.LeftUpperLip, 10F / this.radianF, -2F / this.radianF, 15F / this.radianF);
        this.Head.addChild(this.LeftUpperLip);

        this.UpperTeeth = new ModelRenderer(this, 20, 7);
        this.UpperTeeth.addBox(-1.5F, -1F, -1.5F, 3, 2, 3);
        this.UpperTeeth.setPos(0F, 2F, -2.5F);
        setRotation(this.UpperTeeth, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.UpperTeeth);

        this.LeftFang = new ModelRenderer(this, 44, 10);
        this.LeftFang.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        this.LeftFang.setPos(1.2F, 2.8F, -3.4F);
        setRotation(this.LeftFang, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.LeftFang);

        this.RightFang = new ModelRenderer(this, 48, 10);
        this.RightFang.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        this.RightFang.setPos(-1.2F, 2.8F, -3.4F);
        setRotation(this.RightFang, 15F / this.radianF, 0F, 0F);
        this.Head.addChild(this.RightFang);

        this.InsideMouth = new ModelRenderer(this, 50, 0);
        this.InsideMouth.addBox(-1.5F, -1F, -1F, 3, 2, 2);
        this.InsideMouth.setPos(0F, 2F, -1F);
        this.Head.addChild(this.InsideMouth);

        this.LowerJaw = new ModelRenderer(this, 46, 25);
        this.LowerJaw.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        this.LowerJaw.setPos(0F, 2.1F, 0F);
        this.Head.addChild(this.LowerJaw);

        this.LowerJawTeeth = new ModelRenderer(this, 20, 12);
        this.LowerJawTeeth.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.LowerJawTeeth.setPos(0F, -1.8F, -2.7F);
        this.LowerJawTeeth.mirror = true;
        this.LowerJaw.addChild(this.LowerJawTeeth);

        this.ChinHair = new ModelRenderer(this, 76, 7);
        this.ChinHair.addBox(-2.5F, 0F, -2F, 5, 6, 4);
        this.ChinHair.setPos(0F, 0F, 1F);
        this.LowerJaw.addChild(this.ChinHair);

        this.LeftChinBeard = new ModelRenderer(this, 48, 10);
        this.LeftChinBeard.addBox(-1F, -2.5F, -2F, 2, 5, 4);
        this.LeftChinBeard.setPos(3.6F, 0F, 0.25F);
        setRotation(this.LeftChinBeard, 0F, 30F / this.radianF, 0F);
        this.Head.addChild(this.LeftChinBeard);

        this.RightChinBeard = new ModelRenderer(this, 36, 10);
        this.RightChinBeard.addBox(-1F, -2.5F, -2F, 2, 5, 4);
        this.RightChinBeard.setPos(-3.6F, 0F, 0.25F);
        setRotation(this.RightChinBeard, 0F, -30F / this.radianF, 0F);
        this.Head.addChild(this.RightChinBeard);

        this.ForeheadHair = new ModelRenderer(this, 88, 0);
        this.ForeheadHair.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
        this.ForeheadHair.setPos(0F, -3.2F, 0F);
        setRotation(this.ForeheadHair, 10F / this.radianF, 0F, 0F);
        this.Head.addChild(this.ForeheadHair);

        this.Mane = new ModelRenderer(this, 94, 0);
        this.Mane.addBox(-5.5F, -5.5F, -3F, 11, 11, 6);
        this.Mane.setPos(0F, 0.7F, 3.7F);
        setRotation(this.Mane, -5F / this.radianF, 0F, 0F);
        this.Head.addChild(this.Mane);

        this.RightEar = new ModelRenderer(this, 54, 7);
        this.RightEar.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        this.RightEar.setPos(-2.7F, -3.5F, 1F);
        setRotation(this.RightEar, 0F, 0F, -15F / this.radianF);
        this.Head.addChild(this.RightEar);

        this.LeftEar = new ModelRenderer(this, 54, 4);
        this.LeftEar.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        this.LeftEar.setPos(2.7F, -3.5F, 1F);
        setRotation(this.LeftEar, 0F, 0F, 15F / this.radianF);
        this.Head.addChild(this.LeftEar);

        this.NeckHair = new ModelRenderer(this, 108, 17);
        this.NeckHair.addBox(-2F, -1F, -3F, 4, 2, 6);
        this.NeckHair.setPos(0F, -0.5F, 3F);
        setRotation(this.NeckHair, -10.6F / this.radianF, 0F, 0F);
        this.NeckBase.addChild(this.NeckHair);

        this.InnerWing = new ModelRenderer(this, 26, 115);
        this.InnerWing.addBox(0F, 0F, 0F, 7, 2, 11);
        this.InnerWing.setPos(4F, 9F, -7F);//(5F, 3F, -6F);
        setRotation(this.InnerWing, 0F, -20F / this.radianF, 0F);

        this.MidWing = new ModelRenderer(this, 36, 89);
        this.MidWing.addBox(1F, 0.1F, 1F, 12, 2, 11);
        this.MidWing.setPos(4F, 9F, -7F);//(5F, 3F, -6F);
        setRotation(this.MidWing, 0F, 5F / this.radianF, 0F);

        this.OuterWing = new ModelRenderer(this, 62, 115);
        this.OuterWing.addBox(0F, 0F, 0F, 22, 2, 11);
        this.OuterWing.setPos(16F, 9F, -7F);//(17F, 3F, -6F);
        setRotation(this.OuterWing, 0F, -18F / this.radianF, 0F);

        this.InnerWingR = new ModelRenderer(this, 26, 102);
        this.InnerWingR.addBox(-7F, 0F, 0F, 7, 2, 11);
        this.InnerWingR.setPos(-4F, 9F, -7F);//(-5F, 3F, -6F);
        setRotation(this.InnerWingR, 0F, 20F / this.radianF, 0F);

        this.MidWingR = new ModelRenderer(this, 82, 89);
        this.MidWingR.addBox(-13F, 0.1F, 1F, 12, 2, 11);
        this.MidWingR.setPos(-4F, 9F, -7F);//(-5F, 3F, -6F);
        setRotation(this.MidWingR, 0F, -5F / this.radianF, 0F);

        this.OuterWingR = new ModelRenderer(this, 62, 102);
        this.OuterWingR.addBox(-22F, 0F, 0F, 22, 2, 11);
        this.OuterWingR.setPos(-16F, 9F, -7F);//(-17F, 3F, -6F);
        setRotation(this.OuterWingR, 0F, 18F / this.radianF, 0F);

        this.Abdomen = new ModelRenderer(this, 0, 35);
        this.Abdomen.addBox(-3F, 0F, 0F, 6, 7, 7);
        this.Abdomen.setPos(0F, 0F, 0F);
        setRotation(this.Abdomen, -0.0523599F, 0F, 0F);
        this.Chest.addChild(this.Abdomen);

        this.Ass = new ModelRenderer(this, 0, 49);
        this.Ass.addBox(-2.5F, 0F, 0F, 5, 5, 3);
        this.Ass.setPos(0F, 0F, 7F);
        setRotation(this.Ass, -20F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.Ass);

        this.TailRoot = new ModelRenderer(this, 96, 83);
        this.TailRoot.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.TailRoot.setPos(0F, 1F, 7F);
        setRotation(this.TailRoot, 87F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.TailRoot);

        this.Tail2 = new ModelRenderer(this, 96, 75);
        this.Tail2.addBox(-1F, 0F, -1F, 2, 6, 2);
        this.Tail2.setPos(-0.01F, 3.5F, 0F);
        setRotation(this.Tail2, -30F / this.radianF, 0F, 0F);
        this.TailRoot.addChild(this.Tail2);

        this.Tail3 = new ModelRenderer(this, 96, 67);
        this.Tail3.addBox(-1F, 0F, -1F, 2, 6, 2);
        this.Tail3.setPos(0.01F, 5.5F, 0F);
        setRotation(this.Tail3, -17F / this.radianF, 0F, 0F);
        this.Tail2.addChild(this.Tail3);

        this.Tail4 = new ModelRenderer(this, 96, 61);
        this.Tail4.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.Tail4.setPos(-0.01F, 5.5F, 0F);
        setRotation(this.Tail4, 21F / this.radianF, 0F, 0F);
        this.Tail3.addChild(this.Tail4);

        this.TailTip = new ModelRenderer(this, 96, 55);
        this.TailTip.addBox(-1F, 0F, -1F, 2, 4, 2);
        this.TailTip.setPos(0.01F, 3.5F, 0F);
        setRotation(this.TailTip, 21F / this.radianF, 0F, 0F);
        this.Tail4.addChild(this.TailTip);

        this.TailTusk = new ModelRenderer(this, 96, 49);
        this.TailTusk.addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
        this.TailTusk.setPos(0F, 3.5F, 0F);
        setRotation(this.TailTusk, 21F / this.radianF, 0F, 0F);
        this.Tail4.addChild(this.TailTusk);

        this.Saddle = new ModelRenderer(this, 79, 18);
        this.Saddle.addBox(-4F, -1F, -3F, 8, 2, 6);
        this.Saddle.setPos(0F, 0.5F, -1F);
        this.Chest.addChild(this.Saddle);

        this.SaddleFront = new ModelRenderer(this, 101, 26);
        this.SaddleFront.addBox(-2.5F, -1F, -1.5F, 5, 2, 3);
        this.SaddleFront.setPos(0F, -1.0F, -1.5F);
        setRotation(this.SaddleFront, -10.6F / this.radianF, 0F, 0F);
        this.Saddle.addChild(this.SaddleFront);

        this.SaddleBack = new ModelRenderer(this, 77, 26);
        this.SaddleBack.addBox(-4F, -2F, -2F, 8, 2, 4);
        this.SaddleBack.setPos(0F, 0.7F, 4F);
        setRotation(this.SaddleBack, 12.78F / this.radianF, 0F, 0F);
        this.Saddle.addChild(this.SaddleBack);

        this.LeftFootHarness = new ModelRenderer(this, 81, 18);
        this.LeftFootHarness.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.LeftFootHarness.setPos(4F, 0F, 0.5F);
        this.Saddle.addChild(this.LeftFootHarness);

        this.LeftFootRing = new ModelRenderer(this, 107, 31);
        this.LeftFootRing.addBox(0F, 0F, 0F, 1, 2, 2);
        this.LeftFootRing.setPos(-0.5F, 5F, -1F);
        this.LeftFootHarness.addChild(this.LeftFootRing);

        this.RightFootHarness = new ModelRenderer(this, 101, 18);
        this.RightFootHarness.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.RightFootHarness.setPos(-4F, 0F, 0.5F);
        this.Saddle.addChild(this.RightFootHarness);

        this.RightFootRing = new ModelRenderer(this, 101, 31);
        this.RightFootRing.addBox(0F, 0F, 0F, 1, 2, 2);
        this.RightFootRing.setPos(-0.5F, 5F, -1F);
        this.RightFootHarness.addChild(this.RightFootRing);

        this.StorageChest = new ModelRenderer(this, 32, 59);
        this.StorageChest.addBox(-5F, -2F, -2.5F, 10, 4, 5);
        this.StorageChest.setPos(0F, -2F, 5.5F);
        setRotation(this.StorageChest, -90F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.StorageChest);

        this.STailRoot = new ModelRenderer(this, 104, 79);
        this.STailRoot.addBox(-3F, 4F, 5F, 6, 4, 6);
        this.STailRoot.setPos(0F, 8F, 0F);
        this.STailRoot.setTexSize(128, 128);
        this.STailRoot.mirror = true;
        setRotation(this.STailRoot, 0.5796765F, 0F, 0F);
        this.STail2 = new ModelRenderer(this, 106, 69);
        this.STail2.addBox(-2.5F, 7.5F, 7.3F, 5, 4, 6);
        this.STail2.setPos(0F, 8F, 0F);
        this.STail2.setTexSize(128, 128);
        this.STail2.mirror = true;
        setRotation(this.STail2, 0.9514626F, 0F, 0F);
        this.STail3 = new ModelRenderer(this, 108, 60);
        this.STail3.addBox(-2F, 13.5F, 3.3F, 4, 3, 6);
        this.STail3.setPos(0F, 8F, 0F);
        this.STail3.setTexSize(128, 128);
        this.STail3.mirror = true;
        setRotation(this.STail3, 1.660128F, 0F, 0F);
        this.STail4 = new ModelRenderer(this, 108, 51);
        this.STail4.addBox(-2F, 15.2F, -5.3F, 4, 3, 6);
        this.STail4.setPos(0F, 8F, 0F);
        this.STail4.setTexSize(128, 128);
        this.STail4.mirror = true;
        setRotation(this.STail4, 2.478058F, 0F, 0F);
        this.STail5 = new ModelRenderer(this, 108, 42);
        this.STail5.addBox(-2F, 12.9F, -9F, 4, 3, 6);
        this.STail5.setPos(0F, 8F, 0F);
        this.STail5.setTexSize(128, 128);
        this.STail5.mirror = true;
        setRotation(this.STail5, 3.035737F, 0F, 0F);
        this.StingerLump = new ModelRenderer(this, 112, 34);
        this.StingerLump.addBox(-1.5F, 7.9F, 6F, 3, 3, 5);
        this.StingerLump.setPos(0F, 8F, 0F);
        this.StingerLump.setTexSize(128, 128);
        this.StingerLump.mirror = true;
        setRotation(this.StingerLump, 2.031914F, 0F, 0F);
        this.Stinger = new ModelRenderer(this, 118, 29);
        this.Stinger.addBox(-0.5F, 1.9F, 8F, 1, 1, 4);
        this.Stinger.setPos(0F, 8F, 0F);
        this.Stinger.setTexSize(128, 128);
        this.Stinger.mirror = true;
        setRotation(this.Stinger, 1.213985F, 0F, 0F);

        this.LeftUpperLeg = new ModelRenderer(this, 0, 96);
        this.LeftUpperLeg.addBox(-1.5F, 0F, -2F, 3, 7, 4);
        this.LeftUpperLeg.setPos(3.99F, 3F, -7F);
        setRotation(this.LeftUpperLeg, 15F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.LeftUpperLeg);

        this.LeftLowerLeg = new ModelRenderer(this, 0, 107);
        this.LeftLowerLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.LeftLowerLeg.setPos(-0.01F, 6.5F, 0.2F);
        setRotation(this.LeftLowerLeg, -21.5F / this.radianF, 0F, 0F);
        this.LeftUpperLeg.addChild(this.LeftLowerLeg);

        this.LeftFrontFoot = new ModelRenderer(this, 0, 116);
        this.LeftFrontFoot.addBox(-2F, 0F, -2F, 4, 2, 4);
        this.LeftFrontFoot.setPos(0F, 5F, -1.0F);
        setRotation(this.LeftFrontFoot, 6.5F / this.radianF, 0F, 0F);
        this.LeftLowerLeg.addChild(this.LeftFrontFoot);

        this.LeftClaw1 = new ModelRenderer(this, 16, 125);
        this.LeftClaw1.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw1.setPos(-1.3F, 1.2F, -3.0F);
        setRotation(this.LeftClaw1, 45F / this.radianF, 0F, -1F / this.radianF);
        this.LeftFrontFoot.addChild(this.LeftClaw1);

        this.LeftClaw2 = new ModelRenderer(this, 16, 125);
        this.LeftClaw2.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw2.setPos(0F, 1.1F, -3F);
        setRotation(this.LeftClaw2, 45F / this.radianF, 0F, 0F);
        this.LeftFrontFoot.addChild(this.LeftClaw2);

        this.LeftClaw3 = new ModelRenderer(this, 16, 125);
        this.LeftClaw3.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.LeftClaw3.setPos(1.3F, 1.2F, -3F);
        setRotation(this.LeftClaw3, 45F / this.radianF, 0F, 1F / this.radianF);
        this.LeftFrontFoot.addChild(this.LeftClaw3);

        this.RightUpperLeg = new ModelRenderer(this, 14, 96);
        this.RightUpperLeg.addBox(-1.5F, 0F, -2F, 3, 7, 4);
        this.RightUpperLeg.setPos(-3.99F, 3F, -7F);
        setRotation(this.RightUpperLeg, 15F / this.radianF, 0F, 0F);
        this.Chest.addChild(this.RightUpperLeg);

        this.RightLowerLeg = new ModelRenderer(this, 12, 107);
        this.RightLowerLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.RightLowerLeg.setPos(0.01F, 6.5F, 0.2F);
        setRotation(this.RightLowerLeg, -21.5F / this.radianF, 0F, 0F);
        this.RightUpperLeg.addChild(this.RightLowerLeg);

        this.RightFrontFoot = new ModelRenderer(this, 0, 122);
        this.RightFrontFoot.addBox(-2F, 0F, -2F, 4, 2, 4);
        this.RightFrontFoot.setPos(0F, 5F, -1.0F);
        setRotation(this.RightFrontFoot, 6.5F / this.radianF, 0F, 0F);
        this.RightLowerLeg.addChild(this.RightFrontFoot);

        this.RightClaw1 = new ModelRenderer(this, 16, 125);
        this.RightClaw1.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw1.setPos(-1.3F, 1.2F, -3.0F);
        setRotation(this.RightClaw1, 45F / this.radianF, 0F, -1F / this.radianF);
        this.RightFrontFoot.addChild(this.RightClaw1);

        this.RightClaw2 = new ModelRenderer(this, 16, 125);
        this.RightClaw2.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw2.setPos(0F, 1.1F, -3F);
        setRotation(this.RightClaw2, 45F / this.radianF, 0F, 0F);
        this.RightFrontFoot.addChild(this.RightClaw2);

        this.RightClaw3 = new ModelRenderer(this, 16, 125);
        this.RightClaw3.addBox(-0.5F, 0F, -0.5F, 1, 1, 2);
        this.RightClaw3.setPos(1.3F, 1.2F, -3F);
        setRotation(this.RightClaw3, 45F / this.radianF, 0F, 1F / this.radianF);
        this.RightFrontFoot.addChild(this.RightClaw3);

        this.LeftHindUpperLeg = new ModelRenderer(this, 0, 67);
        this.LeftHindUpperLeg.addBox(-2F, -1.0F, -1.5F, 3, 8, 5);
        this.LeftHindUpperLeg.setPos(3F, 3F, 6.8F);
        setRotation(this.LeftHindUpperLeg, -25F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.LeftHindUpperLeg);

        this.LeftAnkle = new ModelRenderer(this, 0, 80);
        this.LeftAnkle.addBox(-1F, 0F, -1.5F, 2, 3, 3);
        this.LeftAnkle.setPos(-0.5F, 4F, 5F);
        this.LeftHindUpperLeg.addChild(this.LeftAnkle);

        this.LeftHindLowerLeg = new ModelRenderer(this, 0, 86);
        this.LeftHindLowerLeg.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.LeftHindLowerLeg.setPos(0F, 3F, 0.5F);
        this.LeftAnkle.addChild(this.LeftHindLowerLeg);

        this.LeftHindFoot = new ModelRenderer(this, 0, 91);
        this.LeftHindFoot.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
        this.LeftHindFoot.setPos(0F, 2.6F, -0.8F);
        setRotation(this.LeftHindFoot, 27F / this.radianF, 0F, 0F);
        this.LeftHindLowerLeg.addChild(this.LeftHindFoot);

        this.RightHindUpperLeg = new ModelRenderer(this, 16, 67);
        this.RightHindUpperLeg.addBox(-2F, -1F, -1.5F, 3, 8, 5);
        this.RightHindUpperLeg.setPos(-2F, 3F, 6.8F);
        setRotation(this.RightHindUpperLeg, -25F / this.radianF, 0F, 0F);
        this.Abdomen.addChild(this.RightHindUpperLeg);

        this.RightAnkle = new ModelRenderer(this, 10, 80);
        this.RightAnkle.addBox(-1F, 0F, -1.5F, 2, 3, 3);
        this.RightAnkle.setPos(-0.5F, 4F, 5F);
        this.RightHindUpperLeg.addChild(this.RightAnkle);

        this.RightHindLowerLeg = new ModelRenderer(this, 8, 86);
        this.RightHindLowerLeg.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.RightHindLowerLeg.setPos(0F, 3F, 0.5F);
        this.RightAnkle.addChild(this.RightHindLowerLeg);

        this.RightHindFoot = new ModelRenderer(this, 12, 91);
        this.RightHindFoot.addBox(-1.5F, 0F, -1.5F, 3, 2, 3);
        this.RightHindFoot.setPos(0F, 2.6F, -0.8F);
        setRotation(this.RightHindFoot, 27F / this.radianF, 0F, 0F);
        this.RightHindLowerLeg.addChild(this.RightHindFoot);

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        updateAnimationModifiers(entityIn);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void updateAnimationModifiers(Entity entity) {
        MoCEntityBigCat bigcat = (MoCEntityBigCat) entity;
        this.isFlyer = bigcat.isFlyer();
        this.isSaddled = bigcat.getIsRideable();
        this.flapwings = (bigcat.wingFlapCounter != 0);
        this.onAir = (bigcat.isOnAir());
        this.floating = (this.isFlyer && this.onAir);
        //this.poisoning = bigcat.swingingTail();
        this.openMouthCounter = bigcat.mouthCounter;
        this.isRidden = (bigcat.isVehicle());
        this.hasMane = bigcat.hasMane();
        this.isTamed = bigcat.getHasAmulet();
        this.isSitting = bigcat.getIsSitting();
        this.movingTail = bigcat.tailCounter != 0;
        this.hasSaberTeeth = bigcat.hasSaberTeeth();
        this.hasChest = bigcat.getIsChested();
        this.hasStinger = bigcat.getHasStinger();
        this.isGhost = bigcat.getIsGhost();
        this.isMovingVertically = bigcat.getDeltaMovement().y != 0;
    }

    public float updateGhostTransparency(Entity entity) {
        MoCEntityBigCat bigcat = (MoCEntityBigCat) entity;
        return bigcat.tFloat();
    }

    private void renderTeeth(boolean flag) {
        this.LeftFang.visible = flag;
        this.RightFang.visible = flag;
    }

    private void renderCollar(boolean flag) {
        this.Collar.visible = flag;
    }

    private void renderSaddle(boolean flag) {
        this.NeckHarness.visible = flag;
        this.HarnessStick.visible = flag;
        this.Saddle.visible = flag;
    }

    private void renderMane(boolean flag) {
        this.Mane.visible = flag;
        this.LeftChinBeard.visible = flag;
        this.RightChinBeard.visible = flag;
        this.ForeheadHair.visible = flag;
        this.NeckHair.visible = flag;
        this.ChinHair.visible = flag;
    }

    private void renderChest(boolean flag) {
        this.StorageChest.visible = flag;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {

        //f = time
        //f1 = movement speed!
        //f2 = ??timer!
        //f3 = Head Y movement or rotation yaw
        //f4 = Head X movement or rotation Pitch
        //f5 = ?

        float RLegXRot = MathHelper.cos((f * 0.8F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.8F) * 0.8F * f1;
        float gallopRLegXRot = MathHelper.cos((f * 0.6F) + 3.141593F) * 0.8F * f1;
        float gallopLLegXRot = MathHelper.cos(f * 0.6F) * 0.8F * f1;
        
        float stingYOffset = 8F;
        float stingZOffset = 0F;

        //float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
        // cos(f * 0.4F) ==> determines the speed of the movement
        // * 0.2F * f1 ==> determines the amplitude of the pendulum movement

        //float legDisparity = (-3.141593F * f1 * 0.5F) + 3.141593F;
        //float rLegRotFinal = MathHelper.cos(this.rLegMov) * 0.8F * f1;
        //float lLegRotFinal = MathHelper.cos((this.rLegMov) + legDisparity) * 0.8F * f1;
        //this.rLegMov = this.rLegMov + (f1 * 0.25F);//(MathHelper.cos((f1 * 10F) + rLegMov) * f1);
        
        //lLegMov = lLegMov + (f1*0.05F);
        //leg should not return to 0 when stopped, and increment based on movement speed
        //the movement should still follow a cos function i.e. pendulum
        //the faster the speed, the higher the increment

        //TODO sync with legmovemnt speed
        if (this.movingTail) {
            this.Tail2.yRot = MathHelper.cos(f2 * 0.3F);

        } else {
            this.Tail2.yRot = 0F;
        }

        if (this.isSitting) {
            stingYOffset = 17F;
            stingZOffset = -3F;
            this.Chest.y = 14F;
            this.Abdomen.xRot = -10F / this.radianF;
            this.Chest.xRot = -45F / this.radianF;
            this.RightUpperLeg.xRot = (35F / this.radianF);
            this.RightLowerLeg.xRot = 5F / this.radianF;
            this.LeftUpperLeg.xRot = (35F / this.radianF);
            this.LeftLowerLeg.xRot = 5F / this.radianF;
            this.NeckBase.xRot = 20F / this.radianF;
            this.RightHindUpperLeg.y = 1F;
            this.RightHindUpperLeg.xRot = -50F / this.radianF;
            this.LeftHindUpperLeg.y = 1F;
            this.LeftHindUpperLeg.xRot = -50F / this.radianF;
            this.RightHindFoot.xRot = 90F / this.radianF;
            this.LeftHindFoot.xRot = 90F / this.radianF;
            //RightAnkle.rotateAngleX = 20F/radianF;
            //RightHindLowerLeg.rotateAngleX = 20F/radianF;
            this.TailRoot.xRot = 100F / this.radianF;
            this.Tail2.xRot = 35F / this.radianF;
            this.Tail3.xRot = 10F / this.radianF;
            this.NeckHair.y = 2F;
            //lying
            //NeckHair.setRotationPoint(0F, -1F, 3F);
            /*
             * 
            this.Chest.rotationPointY = 16F;
            Abdomen.rotateAngleZ= -30F/radianF;
             */

            //this.TailRoot.rotateAngleY = MathHelper.cos(f2 * 0.3F);
            /*float tailMov = 0F;
            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 20) {
                tailMov = f2a / this.radianF;
                
                
            }*/
            //this.LArm3.rotateAngleY = (9F / this.radianF) - lHand;
            //this.LArm4.rotateAngleY = +lHand;
            this.Collar.xRot = 0F / this.radianF;
            this.Collar.y = 7F;
            this.Collar.z = -4F;

        }

        else {
            stingYOffset = 8F;
            stingZOffset = 0F;

            this.Chest.y = 8F;
            this.Abdomen.xRot = 0F;
            this.Chest.xRot = 0F;
            this.NeckBase.xRot = -14F / this.radianF;
            this.TailRoot.xRot = 87F / this.radianF;
            this.Tail2.xRot = -30F / this.radianF;
            this.Tail3.xRot = -17F / this.radianF;
            this.RightLowerLeg.xRot = -21.5F / this.radianF;
            this.LeftLowerLeg.xRot = -21.5F / this.radianF;
            this.RightHindUpperLeg.y = 3F;
            this.LeftHindUpperLeg.y = 3F;
            this.RightHindFoot.xRot = 27F / this.radianF;
            this.LeftHindFoot.xRot = 27F / this.radianF;
            //this.TailRoot.rotateAngleY = 0F;
            //this.Tail2.rotateAngleY = 0F;
            this.Collar.z = -2F;
            this.NeckHair.y = -0.5F;
            if (this.hasMane) {
                this.Collar.y = 9F;
            } else {
                this.Collar.y = 6F;
            }
            this.Collar.xRot = (20F / this.radianF) + MathHelper.cos(f * 0.8F) * 0.5F * f1;
            //RightAnkle.rotateAngleX = 0F;
            //RightHindLowerLeg.rotateAngleX = 0F;

            boolean galloping = (f1 >= 0.97F);
            if (this.onAir || this.isGhost) {
                if (this.isGhost || (this.isFlyer && f1 > 0)) {
                    float speedMov = (f1 * 0.5F);
                    this.RightUpperLeg.xRot = (45F / this.radianF) + speedMov;//(15F/radianF);
                    this.LeftUpperLeg.xRot = (45F / this.radianF) + speedMov;//(15F/radianF);
                    this.RightHindUpperLeg.xRot = (10F / this.radianF) + speedMov;//(30F/radianF);//LLegXRot;
                    this.LeftHindUpperLeg.xRot = (10F / this.radianF) + speedMov;//(30F/radianF);//RLegXRot;  
                } else if (this.isMovingVertically) //only when moving up or down
                {
                    this.RightUpperLeg.xRot = (-35F / this.radianF);
                    this.LeftUpperLeg.xRot = (-35F / this.radianF);
                    this.RightHindUpperLeg.xRot = (35F / this.radianF);//LLegXRot;
                    this.LeftHindUpperLeg.xRot = (35F / this.radianF);//RLegXRot;    
                }

            } else {
                if (galloping) {

                    this.RightUpperLeg.xRot = (15F / this.radianF) + gallopRLegXRot;//RLegXRot;
                    this.LeftUpperLeg.xRot = (15F / this.radianF) + gallopRLegXRot;//LLegXRot;
                    this.RightHindUpperLeg.xRot = (-25F / this.radianF) + gallopLLegXRot;//LLegXRot;
                    this.LeftHindUpperLeg.xRot = (-25F / this.radianF) + gallopLLegXRot;//RLegXRot;

                    this.Abdomen.yRot = 0F;
                } else {
                    this.RightUpperLeg.xRot = (15F / this.radianF) + RLegXRot;//rLegRotFinal;//RLegXRot;
                    this.LeftHindUpperLeg.xRot = (-25F / this.radianF) + RLegXRot;//rLegRotFinal;//RLegXRot;
                    this.LeftUpperLeg.xRot = (15F / this.radianF) + LLegXRot;//lLegRotFinal;//LLegXRot;
                    this.RightHindUpperLeg.xRot = (-25F / this.radianF) + LLegXRot;//lLegRotFinal;//LLegXRot;
                    if (!this.hasStinger) {
                        this.Abdomen.yRot = MathHelper.cos(f * 0.3F) * 0.25F * f1;
                    }
                }
            }

            if (this.isRidden) {
                this.LeftFootHarness.xRot = -60 / this.radianF;
                this.RightFootHarness.xRot = -60 / this.radianF;
            } else {
                this.LeftFootHarness.xRot = RLegXRot / 3F;
                this.RightFootHarness.xRot = RLegXRot / 3F;

                this.LeftFootHarness.zRot = RLegXRot / 5F;
                this.RightFootHarness.zRot = -RLegXRot / 5F;
            }

            //Tail2.rotateAngleY = LLegXRot;

            //y = A * sin(w * t - k *x)
            /*
             * w1 = speed of wave propagation +/- as needed t = time k = number of
             * waves A = amplitude of wave (how much will it depart from center x =
             * position? :)A 1.3k 0.5w -3.5
             */

            float TailXRot = MathHelper.cos(f * 0.4F) * 0.15F * f1;
            //float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
            // cos(f * 0.4F) ==> determines the speed of the movement
            // * 0.2F * f1 ==> determines the amplitude of the pendulum movement

            this.TailRoot.xRot = (87F / this.radianF) + TailXRot;
            this.Tail2.xRot = (-30F / this.radianF) + TailXRot;
            this.Tail3.xRot = (-17F / this.radianF) + TailXRot;
            this.Tail4.xRot = (21F / this.radianF) + TailXRot;
            this.TailTip.xRot = (21F / this.radianF) + TailXRot;
            this.TailTusk.xRot = (21F / this.radianF) + TailXRot;

        }//end of not sitting

        float HeadXRot = (f4 / 57.29578F);

        /* if (f3 > 10F) {
             f3 = 10F;
         }
         if (f3 < -10F) {
             f3 = -10F;
         }*/

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        this.HeadBack.xRot = 14F / this.radianF + HeadXRot;
        this.HeadBack.yRot = (f3 / 57.29578F);

        float mouthMov = 0F;
        if (this.openMouthCounter != 0) {
            if (this.openMouthCounter < 10) {
                mouthMov = 22 + (this.openMouthCounter * 3);
            } else if (this.openMouthCounter > 20) {
                mouthMov = 22 + (90 - (this.openMouthCounter * 3));
            } else {
                mouthMov = 55F;
            }
        }
        this.LowerJaw.xRot = mouthMov / this.radianF;

        if (this.isSaddled) {
            this.LeftHarness.xRot = 25F / this.radianF + this.HeadBack.xRot;
            this.LeftHarness.yRot = this.HeadBack.yRot;
            this.RightHarness.xRot = 25F / this.radianF + this.HeadBack.xRot;
            this.RightHarness.yRot = this.HeadBack.yRot;
        }

        /*float t = f / 2;
        boolean movetail = false;
        if (movetail) {
            t = f2 / 4F;
        }
        float A = 0.35F;//0.8F;
        float w = 0.6F;
        float k = 0.6F;

        int i = 0;
        float tailLat = 0F;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.TailRoot.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail2.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail3.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail4.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.TailTip.rotateAngleY = TailTusk.rotateAngleY = tailLat;*/

        if (this.isFlyer) {

            /**
             * flapping wings or cruising. IF flapping wings, move up and down.
             * if cruising, movement depends of speed
             */
            float WingRot = 0F;
            if (this.flapwings) {
                WingRot = MathHelper.cos((f2 * 0.3F) + 3.141593F) * 1.2F;// * f1;
            } else
            //cruising
            {
                WingRot = MathHelper.cos((f * 0.5F)) * 0.1F;//* 1.2F * f1;
            }

            //float WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * f1 * 1.2F;
            //InnerWing.setRotationPoint(5F, 3F, -6F);
            //setRotation(InnerWing, 0F, -0.3490659F, 0F);
            //X dist = 12
            //OuterWing.setRotationPoint(17F, 3F, -6F);
            //setRotation(OuterWing, 0F, -0.3228859F, 0F);

            if (this.floating) {
                this.OuterWing.yRot = -0.3228859F + (WingRot / 2F);
                this.OuterWingR.yRot = 0.3228859F - (WingRot / 2F);

            } else {
                WingRot = 60 / this.radianF;//0.7854F;
                this.OuterWing.yRot = -90 / this.radianF;//-1.396F;
                this.OuterWingR.yRot = 90 / this.radianF;//1.396F;
            }

            this.InnerWingR.y = this.InnerWing.y;
            this.InnerWingR.z = this.InnerWing.z;

            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //the rotation point X rotates depending of the cos of rotation times the distance of the other block:
            //cos (WingRot) * 12F
            //the rotation PointX of Innerwing = 4
            //the rotation PointX of Outerwing = 16
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

            if (this.hasStinger) {

                if (!this.poisoning) {
                    //this.Body.rotateAngleX = 5F / this.radianF;
                    this.STailRoot.xRot = 33F / this.radianF;
                    this.STailRoot.y = stingYOffset;//8F;
                    this.STailRoot.z = stingZOffset;

                    this.STail2.xRot = 54.5F / this.radianF;
                    this.STail2.y = stingYOffset;//8F;
                    this.STail2.z = stingZOffset;

                    this.STail3.xRot = 95.1F / this.radianF;
                    this.STail3.y = stingYOffset;//8F;
                    this.STail3.z = stingZOffset;

                    this.STail4.xRot = 141.8F / this.radianF;
                    this.STail4.y = stingYOffset;//8F;
                    this.STail4.z = stingZOffset;

                    this.STail5.xRot = 173.9F / this.radianF;
                    this.STail5.y = stingYOffset;//8F;
                    this.STail5.z = stingZOffset;

                    this.StingerLump.xRot = 116.4F / this.radianF;
                    this.StingerLump.y = stingYOffset;//8F;
                    this.StingerLump.z = stingZOffset;

                    this.Stinger.xRot = 69.5F / this.radianF;
                    this.Stinger.y = stingYOffset;//8F;
                    this.Stinger.z = stingZOffset;

                } else if (!this.isSitting) {
                    //this.Body.rotateAngleX = 50F / this.radianF;
                    this.STailRoot.xRot = 95.2F / this.radianF;
                    this.STailRoot.y = 14.5F;
                    this.STailRoot.z = 2F;

                    this.STail2.xRot = 128.5F / this.radianF;
                    this.STail2.y = 15F;
                    this.STail2.z = 4F;

                    this.STail3.xRot = 169F / this.radianF;
                    this.STail3.y = 14F;
                    this.STail3.z = 3.8F;

                    this.STail4.xRot = 177F / this.radianF;
                    this.STail4.y = 13.5F;
                    this.STail4.z = -8.5F;

                    this.STail5.xRot = 180F / this.radianF;
                    this.STail5.y = 11.5F;
                    this.STail5.z = -17F;

                    this.StingerLump.xRot = 35.4F / this.radianF;
                    this.StingerLump.y = -4F;
                    this.StingerLump.z = -28F;

                    this.Stinger.xRot = 25.5F / this.radianF;
                    this.Stinger.y = 4F;
                    this.Stinger.z = -29F;

                }
            }

        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        renderSaddle(this.isSaddled);
        renderMane(this.hasMane);
        renderCollar(this.isTamed);
        renderTeeth(this.hasSaberTeeth);
        renderChest(this.hasChest);

        matrixStackIn.pushPose();
//        matrixStackIn.translate(0F, yOffset, 0F);

        if (this.isGhost) {
//            GL11.glEnable(3042 /* GL_BLEND */); //TODO: GL calls
//            GL11.glBlendFunc(770, 771);
//            GL11.glColor4f(0.8F, 0.8F, 0.8F, updateGhostTransparency(entity));
            //GL11.glScalef(1.3F, 1.0F, 1.3F);
        }

        this.Chest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        if (this.isFlyer) {
            this.InnerWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.MidWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.OuterWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.InnerWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.MidWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.OuterWingR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.hasStinger) {
            this.STailRoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.STail2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.STail3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.STail4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.STail5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.StingerLump.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Stinger.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.isSaddled && this.isRidden) {
            this.LeftHarness.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RightHarness.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.isGhost) {
//            GL11.glDisable(3042/* GL_BLEND */);
        }
        matrixStackIn.popPose();

    }
}

//TODO
//size is too small? (specially manticores)
//legs back when flying
//saddle movement, position when ridden
//galloping when ridden
//FIRECAT!!
//LIZARDCAT
//BONE CAT
/*



*/
