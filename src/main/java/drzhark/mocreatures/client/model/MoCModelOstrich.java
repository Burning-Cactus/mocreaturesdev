// TODO
// move model to the front (done)
// fix mirror issues (done)
// fix eye (done)
// neck point of rotation (done)
// legs (done)
// wings (done basic)
// tail movement
// flipping wings (done)

package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelOstrich<T extends MoCEntityOstrich> extends EntityModel<T> {

    public MoCModelOstrich() {
        this.texWidth = 128;
        this.texHeight = 128; //64

        this.UBeak = new ModelRenderer(this, 12, 16);
        this.UBeak.addBox(-1.5F, -15F, -5.5F, 3, 1, 1);
        this.UBeak.setPos(0F, 3F, -6F);

        this.UBeak2 = new ModelRenderer(this, 20, 16);
        this.UBeak2.addBox(-1F, -15F, -7.5F, 2, 1, 2);
        this.UBeak2.setPos(0F, 3F, -6F);

        this.UBeakb = new ModelRenderer(this, 12, 16);
        this.UBeakb.addBox(-1.5F, -15F, -6.5F, 3, 1, 1);
        this.UBeakb.setPos(0F, 3F, -6F);
        setRotation(this.UBeakb, -0.0698132F, 0F, 0F);

        this.UBeak2b = new ModelRenderer(this, 20, 16);
        this.UBeak2b.addBox(-1F, -15F, -8.5F, 2, 1, 2);
        this.UBeak2b.setPos(0F, 3F, -6F);
        setRotation(this.UBeak2b, -0.0698132F, 0F, 0F);

        this.LBeak = new ModelRenderer(this, 12, 22);
        this.LBeak.addBox(-1.5F, -14F, -5.5F, 3, 1, 1);
        this.LBeak.setPos(0F, 3F, -6F);

        this.LBeakb = new ModelRenderer(this, 12, 22);
        this.LBeakb.addBox(-1.5F, -14F, -3.9F, 3, 1, 1);
        this.LBeakb.setPos(0F, 3F, -6F);
        setRotation(this.LBeakb, 0.122173F, 0F, 0F);

        this.LBeak2 = new ModelRenderer(this, 20, 22);
        this.LBeak2.addBox(-1F, -14F, -7.5F, 2, 1, 2);
        this.LBeak2.setPos(0F, 3F, -6F);

        this.LBeak2b = new ModelRenderer(this, 20, 22);
        this.LBeak2b.addBox(-1F, -14F, -5.9F, 2, 1, 2);
        this.LBeak2b.setPos(0F, 3F, -6F);
        setRotation(this.LBeak2b, 0.122173F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 38);
        this.Body.addBox(-4F, 1F, 0F, 8, 10, 16);
        this.Body.setPos(0F, 0F, -6F);

        this.LLegA = new ModelRenderer(this, 50, 28);
        this.LLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
        this.LLegA.setPos(4F, 5F, 4F);
        setRotation(this.LLegA, 0.1745329F, 0F, 0F);

        this.LLegB = new ModelRenderer(this, 50, 39);
        this.LLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
        this.LLegB.setPos(4F, 5F, 4F);
        setRotation(this.LLegB, 0.1745329F, 0F, 0F);

        this.LLegC = new ModelRenderer(this, 8, 38);
        this.LLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
        this.LLegC.setPos(4F, 5F, 4F);
        setRotation(this.LLegC, -0.2617994F, 0F, 0F);

        this.LFoot = new ModelRenderer(this, 32, 42);
        this.LFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
        this.LFoot.setPos(4F, 5F, 4F);
        setRotation(this.LFoot, 0.1745329F, 0F, 0F);

        this.RLegA = new ModelRenderer(this, 0, 27);
        this.RLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
        this.RLegA.setPos(-4F, 5F, 4F);
        setRotation(this.RLegA, 0.1745329F, 0F, 0F);

        this.RLegB = new ModelRenderer(this, 18, 27);
        this.RLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
        this.RLegB.setPos(-4F, 5F, 4F);
        setRotation(this.RLegB, 0.1745329F, 0F, 0F);

        this.RLegC = new ModelRenderer(this, 0, 38);
        this.RLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
        this.RLegC.setPos(-4F, 5F, 4F);
        setRotation(this.RLegC, -0.2617994F, 0F, 0F);

        this.RFoot = new ModelRenderer(this, 32, 48);
        this.RFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
        this.RFoot.setPos(-4F, 5F, 4F);
        setRotation(this.RFoot, 0.1745329F, 0F, 0F);

        this.Tail1 = new ModelRenderer(this, 44, 18);
        this.Tail1.addBox(-0.5F, -2F, -2F, 1, 4, 6);
        this.Tail1.setPos(0F, 4F, 15F);
        setRotation(this.Tail1, 0.3490659F, 0F, 0F);

        this.Tail2 = new ModelRenderer(this, 58, 18);
        this.Tail2.addBox(-2.6F, -2F, -2F, 1, 4, 6);
        this.Tail2.setPos(0F, 4F, 15F);
        setRotation(this.Tail2, 0.3490659F, -0.2617994F, 0F);

        this.Tail3 = new ModelRenderer(this, 30, 18);
        this.Tail3.addBox(1.6F, -2F, -2F, 1, 4, 6);
        this.Tail3.setPos(0F, 4F, 15F);
        setRotation(this.Tail3, 0.3490659F, 0.2617994F, 0F);

        this.LWingB = new ModelRenderer(this, 68, 46);
        this.LWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
        this.LWingB.setPos(4F, 4F, -3F);
        setRotation(this.LWingB, 0.0872665F, 0.0872665F, 0F);

        this.LWingC = new ModelRenderer(this, 98, 46);
        this.LWingC.addBox(-1F, 0F, 0F, 1, 4, 14);
        this.LWingC.setPos(4F, 4F, -3F);
        setRotation(this.LWingC, 0F, 0.0872665F, 0F);

        this.LWingD = new ModelRenderer(this, 26, 84);
        this.LWingD.addBox(0F, -1F, -1F, 15, 2, 2);
        this.LWingD.setPos(4F, 3F, -3F);
        setRotation(this.LWingD, 0F, 0F, -0.3490659F);

        this.LWingE = new ModelRenderer(this, 0, 103);
        this.LWingE.addBox(0F, 0F, 1F, 15, 0, 15);
        this.LWingE.setPos(4F, 3F, -3F);
        setRotation(this.LWingE, 0F, 0F, -0.3490659F);

        this.RWingB = new ModelRenderer(this, 68, 0);
        this.RWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
        this.RWingB.setPos(-4F, 4F, -3F);
        setRotation(this.RWingB, 0.0872665F, -0.0872665F, 0F);

        this.RWingC = new ModelRenderer(this, 98, 0);
        this.RWingC.addBox(0F, 0F, 0F, 1, 4, 14);
        this.RWingC.setPos(-4F, 4F, -3F);
        setRotation(this.RWingC, 0F, -0.0872665F, 0F);

        this.RWingD = new ModelRenderer(this, 26, 80);
        this.RWingD.addBox(-15F, -1F, -1F, 15, 2, 2);
        this.RWingD.setPos(-4F, 3F, -3F);
        setRotation(this.RWingD, 0F, 0F, 0.3490659F);

        this.RWingE = new ModelRenderer(this, 0, 88);
        this.RWingE.addBox(-15F, 0F, 1F, 15, 0, 15);
        this.RWingE.setPos(-4F, 3F, -3F);
        setRotation(this.RWingE, 0F, 0F, 0.3490659F);

        this.SaddleA = new ModelRenderer(this, 72, 18);
        this.SaddleA.addBox(-4F, 0.5F, -3F, 8, 1, 8);
        this.SaddleA.setPos(0F, 0F, 0F);
        this.SaddleB = new ModelRenderer(this, 72, 27);

        this.SaddleB.addBox(-1.5F, 0F, -3F, 3, 1, 2);
        this.SaddleB.setPos(0F, 0F, 0F);

        this.SaddleL = new ModelRenderer(this, 72, 30);
        this.SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleL.setPos(4F, 1F, 0F);

        this.SaddleR = new ModelRenderer(this, 84, 30);
        this.SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleR.setPos(-4F, 1F, 0F);

        this.SaddleL2 = new ModelRenderer(this, 76, 30);
        this.SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleL2.setPos(4F, 1F, 0F);

        this.SaddleR2 = new ModelRenderer(this, 88, 30);
        this.SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleR2.setPos(-4F, 1F, 0F);

        this.SaddleC = new ModelRenderer(this, 84, 27);
        this.SaddleC.addBox(-4F, 0F, 3F, 8, 1, 2);
        this.SaddleC.setPos(0F, 0F, 0F);

        this.NeckLFeather = new ModelRenderer(this, 8, 73);
        this.NeckLFeather.addBox(0F, -8F, -0.5F, 0, 7, 4);
        this.NeckLFeather.setPos(0F, 3F, -6F);
        setRotation(this.NeckLFeather, 0.2007129F, 0F, 0F);

        this.NeckUFeather = new ModelRenderer(this, 0, 73);
        this.NeckUFeather.addBox(0F, -16F, -2F, 0, 9, 4);
        this.NeckUFeather.setPos(0F, 3F, -6F);

        this.NeckD = new ModelRenderer(this, 0, 16);
        this.NeckD.addBox(-1.5F, -4F, -2F, 3, 8, 3);
        this.NeckD.setPos(0F, 3F, -6F);
        setRotation(this.NeckD, 0.4363323F, 0F, 0F);

        this.Saddlebag = new ModelRenderer(this, 32, 7);
        this.Saddlebag.addBox(-4.5F, -3F, 5F, 9, 4, 7);
        this.Saddlebag.setPos(0F, 0F, 0F);
        setRotation(this.Saddlebag, -0.2602503F, 0F, 0F);

        this.Flagpole = new ModelRenderer(this, 28, 0);
        this.Flagpole.addBox(-0.5F, -15F, -0.5F, 1, 17, 1);
        this.Flagpole.setPos(0F, 0F, 5F);
        setRotation(this.Flagpole, -0.2602503F, 0F, 0F);

        this.FlagBlack = new ModelRenderer(this, 108, 8);
        this.FlagBlack.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagBlack.setPos(0F, -12F, 8F);
        setRotation(this.FlagBlack, -0.2602503F, 0F, 0F);

        this.FlagDarkGrey = new ModelRenderer(this, 108, 12);
        this.FlagDarkGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkGrey.setPos(0F, -12F, 8F);
        setRotation(this.FlagDarkGrey, -0.2602503F, 0F, 0F);

        this.FlagYellow = new ModelRenderer(this, 48, 46);
        this.FlagYellow.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagYellow.setPos(0F, -12F, 8F);
        setRotation(this.FlagYellow, -0.2602503F, 0F, 0F);

        this.FlagBrown = new ModelRenderer(this, 48, 42);
        this.FlagBrown.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagBrown.setPos(0F, -12F, 8F);
        setRotation(this.FlagBrown, -0.2602503F, 0F, 0F);

        this.FlagGreen = new ModelRenderer(this, 48, 38);
        this.FlagGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagGreen.setPos(0F, -12F, 8F);
        setRotation(this.FlagGreen, -0.2602503F, 0F, 0F);

        /*
         * FlagCyan = new ModelRenderer(this, 48, 50); FlagCyan.addBox(0F,
         * -16.5F, 5F, 0, 4, 10); FlagCyan.setRotationPoint(0F, 0F, 0F);
         * setRotation(FlagCyan, -0.2602503F, 0F, 0F);
         */

        this.FlagCyan = new ModelRenderer(this, 48, 50);
        this.FlagCyan.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagCyan.setPos(0F, -12F, 8F);
        setRotation(this.FlagCyan, -0.2602503F, 0F, 0F);

        this.FlagLightBlue = new ModelRenderer(this, 68, 32);
        this.FlagLightBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagLightBlue.setPos(0F, -12F, 8F);
        setRotation(this.FlagLightBlue, -0.2602503F, 0F, 0F);

        this.FlagDarkBlue = new ModelRenderer(this, 68, 28);
        this.FlagDarkBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkBlue.setPos(0F, -12F, 8F);
        setRotation(this.FlagDarkBlue, -0.2602503F, 0F, 0F);

        this.FlagPurple = new ModelRenderer(this, 88, 32);
        this.FlagPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagPurple.setPos(0F, -12F, 8F);
        setRotation(this.FlagPurple, -0.2602503F, 0F, 0F);

        this.FlagDarkPurple = new ModelRenderer(this, 88, 28);
        this.FlagDarkPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkPurple.setPos(0F, -12F, 8F);
        setRotation(this.FlagDarkPurple, -0.2602503F, 0F, 0F);

        this.FlagDarkGreen = new ModelRenderer(this, 108, 32);
        this.FlagDarkGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkGreen.setPos(0F, -12F, 8F);
        setRotation(this.FlagDarkGreen, -0.2602503F, 0F, 0F);

        this.FlagLightRed = new ModelRenderer(this, 108, 28);
        this.FlagLightRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagLightRed.setPos(0F, -12F, 8F);
        setRotation(this.FlagLightRed, -0.2602503F, 0F, 0F);

        this.FlagRed = new ModelRenderer(this, 108, 24);
        this.FlagRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagRed.setPos(0F, -12F, 8F);
        setRotation(this.FlagRed, -0.2602503F, 0F, 0F);

        this.FlagWhite = new ModelRenderer(this, 108, 20);
        this.FlagWhite.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagWhite.setPos(0F, -12F, 8F);
        setRotation(this.FlagWhite, -0.2602503F, 0F, 0F);

        this.FlagGrey = new ModelRenderer(this, 108, 16);
        this.FlagGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagGrey.setPos(0F, -12F, 8F);
        setRotation(this.FlagGrey, -0.2602503F, 0F, 0F);

        this.FlagOrange = new ModelRenderer(this, 88, 24);
        this.FlagOrange.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagOrange.setPos(0F, -12F, 8F);
        setRotation(this.FlagOrange, -0.2602503F, 0F, 0F);

        this.NeckU = new ModelRenderer(this, 20, 0);
        this.NeckU.addBox(-1F, -12F, -4F, 2, 5, 2);
        this.NeckU.setPos(0F, 3F, -6F);

        this.NeckL = new ModelRenderer(this, 20, 7);
        this.NeckL.addBox(-1F, -8F, -2.5F, 2, 5, 2);
        this.NeckL.setPos(0F, 3F, -6F);
        setRotation(this.NeckL, 0.2007129F, 0F, 0F);

        this.NeckHarness = new ModelRenderer(this, 0, 11);
        this.NeckHarness.addBox(-2F, -3F, -2.5F, 4, 1, 4);
        this.NeckHarness.setPos(0F, 3F, -6F);
        setRotation(this.NeckHarness, 0.4363323F, 0F, 0F);

        this.NeckHarness2 = new ModelRenderer(this, 84, 55);
        this.NeckHarness2.addBox(-3F, -2.5F, -2F, 6, 1, 1);
        this.NeckHarness2.setPos(0F, 3F, -6F);

        this.NeckHarnessRight = new ModelRenderer(this, 84, 45);
        this.NeckHarnessRight.addBox(-2.3F, -3.5F, -0.5F, 0, 3, 12);
        this.NeckHarnessRight.setPos(0F, 3F, -6F);
        setRotation(this.NeckHarnessRight, 0.8983798F, 0F, 0F);

        this.NeckHarnessLeft = new ModelRenderer(this, 84, 45);
        this.NeckHarnessLeft.addBox(2.3F, -3.5F, -0.5F, 0, 3, 12);
        this.NeckHarnessLeft.setPos(0F, 3F, -6F);
        setRotation(this.NeckHarnessLeft, 0.8983798F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -16F, -4.5F, 3, 4, 3);
        this.Head.setPos(0F, 3F, -6F);

        this.UniHorn = new ModelRenderer(this, 0, 8);
        this.UniHorn.addBox(-0.5F, -21F, 0.5F, 1, 6, 1);
        this.UniHorn.setPos(0F, 3F, -6F);
        setRotation(this.UniHorn, 0.3171542F, 0F, 0F);

        this.HelmetLeather = new ModelRenderer(this, 66, 0);
        this.HelmetLeather.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetLeather.setPos(0F, 3F, -6F);

        this.HelmetIron = new ModelRenderer(this, 84, 46);
        this.HelmetIron.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetIron.setPos(0F, 3F, -6F);

        this.HelmetGold = new ModelRenderer(this, 112, 64);
        this.HelmetGold.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetGold.setPos(0F, 3F, -6F);

        this.HelmetDiamond = new ModelRenderer(this, 96, 64);
        this.HelmetDiamond.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetDiamond.setPos(0F, 3F, -6F);

        this.HelmetHide = new ModelRenderer(this, 96, 5);
        this.HelmetHide.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetHide.setPos(0F, 3F, -6F);

        this.HelmetNeckHide = new ModelRenderer(this, 58, 0);
        this.HelmetNeckHide.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
        this.HelmetNeckHide.setPos(0F, 3F, -6F);

        this.HelmetHideEar1 = new ModelRenderer(this, 84, 9);
        this.HelmetHideEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
        this.HelmetHideEar1.setPos(0F, 3F, -6F);
        //setRotation(HelmetHideEar1, 0F, 0F, 0.4363323F);

        this.HelmetHideEar2 = new ModelRenderer(this, 90, 9);
        this.HelmetHideEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
        this.HelmetHideEar2.setPos(0F, 3F, -6F);
        //setRotation(HelmetHideEar2, 0F, 0F, -0.4363323F);

        this.HelmetFur = new ModelRenderer(this, 84, 0);
        this.HelmetFur.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetFur.setPos(0F, 3F, -6F);

        this.HelmetNeckFur = new ModelRenderer(this, 96, 0);
        this.HelmetNeckFur.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
        this.HelmetNeckFur.setPos(0F, 3F, -6F);

        this.HelmetFurEar1 = new ModelRenderer(this, 66, 9);
        this.HelmetFurEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
        this.HelmetFurEar1.setPos(0F, 3F, -6F);

        this.HelmetFurEar2 = new ModelRenderer(this, 76, 9);
        this.HelmetFurEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
        this.HelmetFurEar2.setPos(0F, 3F, -6F);

        this.HelmetReptile = new ModelRenderer(this, 64, 64);
        this.HelmetReptile.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetReptile.setPos(0F, 3F, -6F);

        this.HelmetReptileEar2 = new ModelRenderer(this, 114, 45);
        this.HelmetReptileEar2.addBox(2.5F, -16.5F, -2F, 0, 5, 5);
        this.HelmetReptileEar2.setPos(0F, 3F, -6F);
        setRotation(this.HelmetReptileEar2, 0F, 0.6108652F, 0F);

        this.HelmetReptileEar1 = new ModelRenderer(this, 114, 50);
        this.HelmetReptileEar1.addBox(-2.5F, -16.5F, -2F, 0, 5, 5);
        this.HelmetReptileEar1.setPos(0F, 3F, -6F);
        setRotation(this.HelmetReptileEar1, 0F, -0.6108652F, 0F);

        this.HelmetGreenChitin = new ModelRenderer(this, 80, 64);
        this.HelmetGreenChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetGreenChitin.setPos(0F, 3F, -6F);

        this.HelmetYellowChitin = new ModelRenderer(this, 0, 64);
        this.HelmetYellowChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetYellowChitin.setPos(0F, 3F, -6F);

        this.HelmetBlueChitin = new ModelRenderer(this, 16, 64);
        this.HelmetBlueChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetBlueChitin.setPos(0F, 3F, -6F);

        this.HelmetBlackChitin = new ModelRenderer(this, 32, 64);
        this.HelmetBlackChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetBlackChitin.setPos(0F, 3F, -6F);

        this.HelmetRedChitin = new ModelRenderer(this, 48, 64);
        this.HelmetRedChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetRedChitin.setPos(0F, 3F, -6F);

        /*
         * Tail = new ModelRenderer(this, 30, 28); Tail.addBox(-2.5F, 3F, 16F,
         * 5, 5, 5); Tail.setRotationPoint(0F, 0F, -6F); Tailpart1 = new
         * ModelRenderer(this, 30, 28); Tailpart1.addBox(-2.5F, -3.2F, 21F, 5,
         * 5, 5); Tailpart1.setRotationPoint(0F, 0F, -6F);
         * setRotation(Tailpart1, -0.2974289F, 0F, 0F); Tailpart2 = new
         * ModelRenderer(this, 60, 73); Tailpart2.addBox(-2.5F, -8.8F, 24.6F, 5,
         * 5, 8); Tailpart2.setRotationPoint(0F, 0F, -6F);
         * setRotation(Tailpart2, -0.5205006F, 0F, 0F); Tailpart3 = new
         * ModelRenderer(this, 60, 86); Tailpart3.addBox(-2F, 1.5F, 32.6F, 4, 4,
         * 7); Tailpart3.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart3,
         * -0.2230717F, 0F, 0F); Tailpart4 = new ModelRenderer(this, 60, 97);
         * Tailpart4.addBox(-1.5F, 13F, 36.6F, 3, 3, 7);
         * Tailpart4.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart4,
         * 0.0743572F, 0F, 0F); Tailpart5 = new ModelRenderer(this, 60, 107);
         * Tailpart5.addBox(-1F, 26.5F, 35.9F, 2, 2, 5);
         * Tailpart5.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart5,
         * 0.4089647F, 0F, 0F);
         */

        this.Tail = new ModelRenderer(this, 30, 28);
        this.Tail.addBox(-2.5F, -1F, 0F, 5, 5, 5);
        this.Tail.setPos(0F, 4F, 10F);

        this.Tailpart1 = new ModelRenderer(this, 30, 28);
        this.Tailpart1.addBox(-2.5F, -2.2F, 5F, 5, 5, 5);
        this.Tailpart1.setPos(0F, 4F, 10F);
        setRotation(this.Tailpart1, -0.2974289F, 0F, 0F);

        this.Tailpart2 = new ModelRenderer(this, 60, 73);
        this.Tailpart2.addBox(-2.5F, -4.3F, 9F, 5, 5, 8);
        this.Tailpart2.setPos(0F, 4F, 10F);
        setRotation(this.Tailpart2, -0.5205006F, 0F, 0F);

        this.Tailpart3 = new ModelRenderer(this, 60, 86);
        this.Tailpart3.addBox(-2F, 1F, 16F, 4, 4, 7);
        this.Tailpart3.setPos(0F, 4F, 10F);
        setRotation(this.Tailpart3, -0.2230717F, 0F, 0F);

        this.Tailpart4 = new ModelRenderer(this, 60, 97);
        this.Tailpart4.addBox(-1.5F, 8F, 20.6F, 3, 3, 7);
        this.Tailpart4.setPos(0F, 4F, 10F);
        setRotation(this.Tailpart4, 0.0743572F, 0F, 0F);

        this.Tailpart5 = new ModelRenderer(this, 60, 107);
        this.Tailpart5.addBox(-1F, 16.5F, 22.9F, 2, 2, 5);
        this.Tailpart5.setPos(0F, 4F, 10F);
        setRotation(this.Tailpart5, 0.4089647F, 0F, 0F);
    }

    /**
     * Sets this entity's model rotation angles
     *
     * @param entityIn
     * @param limbSwing
     * @param limbSwingAmount
     * @param ageInTicks
     * @param netHeadYaw
     * @param headPitch
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.typeI = (byte) ((MoCEntityOstrich) entityIn).getSubType();
        openMouth = (((MoCEntityOstrich) entityIn).mouthCounter != 0);
        isSaddled = ((MoCEntityOstrich) entityIn).getIsRideable();
        boolean isHiding = ((MoCEntityOstrich) entityIn).getHiding();
        boolean wingFlap = (((MoCEntityOstrich) entityIn).wingCounter != 0);
        bagged = ((MoCEntityOstrich) entityIn).getIsChested();
        rider = (((MoCEntityOstrich) entityIn).isVehicle());
        int jumpCounter = ((MoCEntityOstrich) entityIn).jumpCounter;
        boolean floating = (((MoCEntityOstrich) entityIn).isFlyer() && ((MoCEntityOstrich) entityIn).isOnAir());

        this.helmet = ((MoCEntityOstrich) entityIn).getHelmet();
        this.flagColor = ((MoCEntityOstrich) entityIn).getFlagColor();
        //super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, isHiding, wingFlap, rider, jumpCounter, floating);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third
     * float params here are the same second and third as in the
     * setRotationAngles method.
     */
    /*
     * public void setLivingAnimations(EntityLiving entityliving, float par2,
     * float par3, float par4) { super.setLivingAnimations(entityliving, par2,
     * par3, par4); this.Head.rotationPointY = 6.0F +
     * ((EntitySheep)entityliving).func_44003_c(par4) * 9.0F; this.field_44016_o
     * = ((EntitySheep)entityliving).func_44002_d(par4); }
     */

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, boolean hiding, boolean wing, boolean rider,
            int jumpCounter, boolean floating) {
        float LLegXRot = MathHelper.cos(f * 0.4F) * 1.1F * f1;
        float RLegXRot = MathHelper.cos((f * 0.4F) + 3.141593F) * 1.1F * f1;

        if (hiding) {
            this.Head.y = 9.0F;
            this.Head.xRot = 2.61799F;
            this.Head.yRot = 0.0F;

        } else {
            this.Head.y = 3.0F;
            this.Head.xRot = (RLegXRot / 20F) + (-f4 / (180F / (float) Math.PI));
            this.Head.yRot = f3 / (180F / (float) Math.PI);
        }

        if (rider) {
            if (floating) {
                this.Head.xRot = 0F;
            } else {
                this.Head.xRot = (RLegXRot / 20F);
            }
        }

        this.UBeak.y = this.Head.y;
        this.UBeakb.y = this.Head.y;
        this.UBeak2.y = this.Head.y;
        this.UBeak2b.y = this.Head.y;
        this.LBeak.y = this.Head.y;
        this.LBeakb.y = this.Head.y;
        this.LBeak2.y = this.Head.y;
        this.LBeak2b.y = this.Head.y;
        this.NeckU.y = this.Head.y;
        this.NeckD.y = this.Head.y;
        this.NeckL.y = this.Head.y;

        this.UBeak.xRot = this.Head.xRot;
        this.UBeak.yRot = this.Head.yRot;
        this.UBeak2.xRot = this.Head.xRot;
        this.UBeak2.yRot = this.Head.yRot;
        this.LBeak.xRot = this.Head.xRot;
        this.LBeak.yRot = this.Head.yRot;
        this.LBeak2.xRot = this.Head.xRot;
        this.LBeak2.yRot = this.Head.yRot;
        this.NeckU.xRot = this.Head.xRot;
        this.NeckU.yRot = this.Head.yRot;
        this.NeckD.xRot = 0.4363323F + this.Head.xRot;
        this.NeckD.yRot = this.Head.yRot;
        this.NeckL.xRot = (11.5F / this.radianF) + this.Head.xRot;
        this.NeckL.yRot = this.Head.yRot;

        this.UBeakb.xRot = -0.0698132F + this.Head.xRot;
        this.UBeakb.yRot = this.Head.yRot;
        this.UBeak2b.xRot = -0.0698132F + this.Head.xRot;
        this.UBeak2b.yRot = this.Head.yRot;

        this.LBeakb.xRot = (7F / this.radianF) + this.Head.xRot;
        this.LBeakb.yRot = this.Head.yRot;
        this.LBeak2b.xRot = (7F / this.radianF) + this.Head.xRot;
        this.LBeak2b.yRot = this.Head.yRot;

        this.NeckUFeather.y = this.Head.y;
        this.NeckLFeather.y = this.Head.y;
        this.UniHorn.y = this.Head.y;

        this.NeckUFeather.xRot = this.Head.xRot;
        this.NeckUFeather.yRot = this.Head.yRot;
        this.NeckLFeather.xRot = (11.5F / this.radianF) + this.Head.xRot;
        this.NeckLFeather.yRot = this.Head.yRot;
        this.UniHorn.xRot = (18F / this.radianF) + this.Head.xRot;
        this.UniHorn.yRot = this.Head.yRot;

        this.NeckHarness.y = this.Head.y;
        this.NeckHarness2.y = this.Head.y;
        this.NeckHarnessLeft.y = this.Head.y;
        this.NeckHarnessRight.y = this.Head.y;

        this.NeckHarness.xRot = (25F / this.radianF) + this.Head.xRot;
        this.NeckHarness.yRot = this.Head.yRot;
        this.NeckHarness2.xRot = this.Head.xRot;
        this.NeckHarness2.yRot = this.Head.yRot;
        this.NeckHarnessLeft.xRot = (50F / this.radianF) + this.Head.xRot;
        this.NeckHarnessLeft.yRot = this.Head.yRot;
        this.NeckHarnessRight.xRot = (50F / this.radianF) + this.Head.xRot;
        this.NeckHarnessRight.yRot = this.Head.yRot;

        //helmets

        switch (this.helmet) {
            case 1:

                this.HelmetLeather.y = this.Head.y;
                this.HelmetLeather.xRot = this.Head.xRot;
                this.HelmetLeather.yRot = this.Head.yRot;
                break;
            case 2:

                this.HelmetIron.y = this.Head.y;
                this.HelmetIron.xRot = this.Head.xRot;
                this.HelmetIron.yRot = this.Head.yRot;
                break;
            case 3:

                this.HelmetGold.y = this.Head.y;
                this.HelmetGold.xRot = this.Head.xRot;
                this.HelmetGold.yRot = this.Head.yRot;
                break;
            case 4:
                this.HelmetDiamond.y = this.Head.y;
                this.HelmetDiamond.xRot = this.Head.xRot;
                this.HelmetDiamond.yRot = this.Head.yRot;
                break;
            case 5:
                this.HelmetHide.y = this.Head.y;
                this.HelmetHide.xRot = this.Head.xRot;
                this.HelmetHide.yRot = this.Head.yRot;
                this.HelmetNeckHide.y = this.Head.y;
                this.HelmetNeckHide.xRot = this.Head.xRot;
                this.HelmetNeckHide.yRot = this.Head.yRot;
                this.HelmetHideEar1.y = this.Head.y;
                this.HelmetHideEar1.xRot = this.Head.xRot;
                this.HelmetHideEar1.yRot = this.Head.yRot;
                this.HelmetHideEar2.y = this.Head.y;
                this.HelmetHideEar2.xRot = this.Head.xRot;
                this.HelmetHideEar2.yRot = this.Head.yRot;
                break;
            case 6:
                this.HelmetFur.y = this.Head.y;
                this.HelmetFur.xRot = this.Head.xRot;
                this.HelmetFur.yRot = this.Head.yRot;
                this.HelmetNeckFur.y = this.Head.y;
                this.HelmetNeckFur.xRot = this.Head.xRot;
                this.HelmetNeckFur.yRot = this.Head.yRot;
                this.HelmetFurEar1.y = this.Head.y;
                this.HelmetFurEar1.xRot = this.Head.xRot;
                this.HelmetFurEar1.yRot = this.Head.yRot;
                this.HelmetFurEar2.y = this.Head.y;
                this.HelmetFurEar2.xRot = this.Head.xRot;
                this.HelmetFurEar2.yRot = this.Head.yRot;
                break;
            case 7:
                this.HelmetReptile.y = this.Head.y;
                this.HelmetReptile.xRot = this.Head.xRot;
                this.HelmetReptile.yRot = this.Head.yRot;
                this.HelmetReptileEar1.y = this.Head.y;
                this.HelmetReptileEar1.xRot = this.Head.xRot;
                this.HelmetReptileEar1.yRot = (-35F / this.radianF) + this.Head.yRot;
                this.HelmetReptileEar2.y = this.Head.y;
                this.HelmetReptileEar2.xRot = this.Head.xRot;
                this.HelmetReptileEar2.yRot = (35F / this.radianF) + this.Head.yRot;
                break;
            case 8:
                this.HelmetGreenChitin.y = this.Head.y;
                this.HelmetGreenChitin.xRot = this.Head.xRot;
                this.HelmetGreenChitin.yRot = this.Head.yRot;
                break;
            case 9:
                this.HelmetYellowChitin.y = this.Head.y;
                this.HelmetYellowChitin.xRot = this.Head.xRot;
                this.HelmetYellowChitin.yRot = this.Head.yRot;
                break;
            case 10:
                this.HelmetBlueChitin.y = this.Head.y;
                this.HelmetBlueChitin.xRot = this.Head.xRot;
                this.HelmetBlueChitin.yRot = this.Head.yRot;
                break;
            case 11:
                this.HelmetBlackChitin.y = this.Head.y;
                this.HelmetBlackChitin.xRot = this.Head.xRot;
                this.HelmetBlackChitin.yRot = this.Head.yRot;
                break;
            case 12:
                this.HelmetRedChitin.y = this.Head.y;
                this.HelmetRedChitin.xRot = this.Head.xRot;
                this.HelmetRedChitin.yRot = this.Head.yRot;
                break;

        }

        //flag
        float flagF = MathHelper.cos(f * 0.8F) * 0.1F * f1;

        switch (this.flagColor) {
            case 1:
                this.FlagOrange.yRot = flagF;
                break;
            case 2:
                this.FlagPurple.yRot = flagF;
                break;
            case 3:
                this.FlagLightBlue.yRot = flagF;
                break;
            case 4:
                this.FlagYellow.yRot = flagF;
                break;
            case 5:
                this.FlagGreen.yRot = flagF;
                break;
            case 6:
                this.FlagLightRed.yRot = flagF;
                break;
            case 7:
                this.FlagDarkGrey.yRot = flagF;
                break;
            case 8:
                this.FlagGrey.yRot = flagF;
                break;
            case 9:
                this.FlagCyan.yRot = flagF;
                break;
            case 10:
                this.FlagDarkPurple.yRot = flagF;
                break;
            case 11:
                this.FlagDarkBlue.yRot = flagF;
                break;
            case 12:
                this.FlagBrown.yRot = flagF;
                break;
            case 13:
                this.FlagDarkGreen.yRot = flagF;
                break;
            case 14:
                this.FlagRed.yRot = flagF;
                break;
            case 15:
                this.FlagBlack.yRot = flagF;
                break;
            case 16:
                this.FlagWhite.yRot = flagF;
                break;
        }

        //legs

        if ((this.typeI == 5 || this.typeI == 6) && floating) {
            this.LLegC.y = 8F;
            this.LLegC.z = 17F;
            this.RLegC.y = 8F;
            this.RLegC.z = 17F;
            this.LFoot.y = -5F;
            this.LFoot.z = -3F;
            this.RFoot.y = -5F;
            this.RFoot.z = -3F;

            this.LLegA.xRot = 40F / this.radianF;
            this.LLegB.xRot = this.LLegA.xRot;
            this.LLegC.xRot = -85F / this.radianF;
            this.LFoot.xRot = 25F / this.radianF;

            this.RLegA.xRot = 40F / this.radianF;
            this.RLegB.xRot = this.RLegA.xRot;
            this.RLegC.xRot = -85F / this.radianF;
            this.RFoot.xRot = 25F / this.radianF;
        } else {

            this.LLegC.y = 5F;
            this.LLegC.z = 4F;
            this.RLegC.y = 5F;
            this.RLegC.z = 4F;
            this.LFoot.y = 5F;
            this.LFoot.z = 4F;
            this.RFoot.y = 5F;
            this.RFoot.z = 4F;

            this.LLegA.xRot = 0.1745329F + LLegXRot;
            this.LLegB.xRot = this.LLegA.xRot;
            this.LLegC.xRot = -0.2617994F + +LLegXRot;
            this.LFoot.xRot = this.LLegA.xRot;
            this.RLegA.xRot = 0.1745329F + RLegXRot;
            this.RLegB.xRot = this.RLegA.xRot;
            this.RLegC.xRot = -0.2617994F + +RLegXRot;
            this.RFoot.xRot = this.RLegA.xRot;
        }

        //wings
        float wingF = 0F;
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        if (this.typeI == 5 || this.typeI == 6) {

            if (jumpCounter != 0) {
                wingF = (-40F / this.radianF) + MathHelper.cos(jumpCounter * 0.3F) * 1.3F;
            } else if (rider && floating) {
                wingF = MathHelper.cos(f2 * 0.8F) * 0.2F;
            } else {
                wingF = MathHelper.cos(f * 0.3F) * f1;
            }

            this.LWingD.zRot = (-20F / this.radianF) - wingF;
            this.LWingE.zRot = (-20F / this.radianF) - wingF;

            this.RWingD.zRot = (20F / this.radianF) + wingF;
            this.RWingE.zRot = (20F / this.radianF) + wingF;

        } else {
            wingF = (10F / this.radianF) + MathHelper.cos(f * 0.6F) * 0.2F * f1;
            if (wing) {
                wingF += (50 / 57.29578F);
            }
            this.LWingB.yRot = 0.0872665F + wingF;
            this.LWingC.yRot = 0.0872665F + wingF;

            this.RWingB.yRot = -0.0872665F - wingF;
            this.RWingC.yRot = -0.0872665F - wingF;

            this.LWingB.xRot = 0.0872665F + (RLegXRot / 10F);
            this.LWingC.xRot = (RLegXRot / 10F);

            this.RWingB.xRot = 0.0872665F + (RLegXRot / 10F);
            this.RWingC.xRot = (RLegXRot / 10F);

        }

        if (rider) {
            this.SaddleL.xRot = -60F / this.radianF;
            this.SaddleL2.xRot = this.SaddleL.xRot;
            this.SaddleR.xRot = -60F / this.radianF;
            ;
            this.SaddleR2.xRot = this.SaddleR.xRot;
            ;

            this.SaddleL.zRot = -40F / this.radianF;
            this.SaddleL2.zRot = this.SaddleL.zRot;
            this.SaddleR.zRot = 40F / this.radianF;
            this.SaddleR2.zRot = this.SaddleR.zRot;
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

        if (this.typeI == 6) {
            float f6 = 15F;
            float rotF = MathHelper.cos(f * 0.5F) * 0.3F * f1;
            this.Tail.yRot = rotF;
            rotF += (rotF / f6);
            this.Tailpart1.yRot = rotF;//MathHelper.cos(f * 0.6662F) * 0.7F * f1;
            rotF += (rotF / f6);
            this.Tailpart1.yRot = rotF;
            rotF += (rotF / f6);
            this.Tailpart2.yRot = rotF;
            rotF += (rotF / f6);
            this.Tailpart3.yRot = rotF;
            rotF += (rotF / f6);
            this.Tailpart4.yRot = rotF;
            rotF += (rotF / f6);
            this.Tailpart5.yRot = rotF;
        }

    }

    ModelRenderer UBeak;
    ModelRenderer UBeak2;
    ModelRenderer UBeakb;
    ModelRenderer UBeak2b;
    ModelRenderer LBeak;
    ModelRenderer LBeakb;
    ModelRenderer LBeak2;
    ModelRenderer LBeak2b;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer LLegA;
    ModelRenderer LLegB;
    ModelRenderer LLegC;
    ModelRenderer LFoot;
    ModelRenderer RLegA;
    ModelRenderer RLegB;
    ModelRenderer RLegC;
    ModelRenderer RFoot;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer LWingB;
    ModelRenderer LWingC;
    ModelRenderer LWingD;
    ModelRenderer LWingE;
    ModelRenderer RWingB;
    ModelRenderer RWingC;
    ModelRenderer RWingD;
    ModelRenderer RWingE;
    ModelRenderer SaddleA;
    ModelRenderer SaddleB;
    ModelRenderer SaddleL;
    ModelRenderer SaddleR;
    ModelRenderer SaddleL2;
    ModelRenderer SaddleR2;
    ModelRenderer SaddleC;
    ModelRenderer NeckLFeather;
    ModelRenderer NeckUFeather;
    ModelRenderer NeckD;
    ModelRenderer Saddlebag;
    ModelRenderer Flagpole;
    ModelRenderer FlagBlack;
    ModelRenderer FlagDarkGrey;
    ModelRenderer FlagYellow;
    ModelRenderer FlagBrown;
    ModelRenderer FlagGreen;
    ModelRenderer FlagCyan;
    ModelRenderer FlagLightBlue;
    ModelRenderer FlagDarkBlue;
    ModelRenderer FlagPurple;
    ModelRenderer FlagDarkPurple;
    ModelRenderer FlagDarkGreen;
    ModelRenderer FlagLightRed;
    ModelRenderer FlagRed;
    ModelRenderer FlagWhite;
    ModelRenderer FlagGrey;
    ModelRenderer FlagOrange;
    ModelRenderer NeckU;
    ModelRenderer NeckL;
    ModelRenderer NeckHarness;
    ModelRenderer NeckHarness2;
    ModelRenderer NeckHarnessRight;
    ModelRenderer NeckHarnessLeft;
    ModelRenderer Head;
    ModelRenderer UniHorn;
    ModelRenderer HelmetLeather;
    ModelRenderer HelmetIron;
    ModelRenderer HelmetGold;
    ModelRenderer HelmetDiamond;
    ModelRenderer HelmetHide;
    ModelRenderer HelmetNeckHide;
    ModelRenderer HelmetHideEar1;
    ModelRenderer HelmetHideEar2;
    ModelRenderer HelmetFur;
    ModelRenderer HelmetNeckFur;
    ModelRenderer HelmetFurEar1;
    ModelRenderer HelmetFurEar2;
    ModelRenderer HelmetReptile;
    ModelRenderer HelmetReptileEar2;
    ModelRenderer HelmetReptileEar1;
    ModelRenderer HelmetGreenChitin;
    ModelRenderer HelmetYellowChitin;
    ModelRenderer HelmetBlueChitin;
    ModelRenderer HelmetBlackChitin;
    ModelRenderer HelmetRedChitin;
    ModelRenderer Tailpart1;
    ModelRenderer Tailpart2;
    ModelRenderer Tailpart3;
    ModelRenderer Tailpart4;
    ModelRenderer Tailpart5;

    private int helmet;
    private byte typeI;
    private int flagColor;
    private float radianF = 57.29578F;

    private boolean isSaddled = false;
    private boolean rider = false;
    private boolean openMouth = false;
    private boolean bagged = false;

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.NeckU.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.NeckD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.NeckL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LLegC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.LFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RLegC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.RFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        if (this.typeI == 8) {
            this.UniHorn.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.typeI == 5 || this.typeI == 6) //demon and darkness ostriches
        {
            this.LWingD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingE.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingE.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.NeckUFeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.NeckLFeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        } else {
            this.LWingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LWingC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.RWingC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (this.typeI == 6) //darkness ostrich
        {

            this.Tailpart1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tailpart2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tailpart3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tailpart4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tailpart5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        } else {
            this.Tail1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tail2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tail3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (openMouth) {
            this.UBeakb.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.UBeak2b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LBeakb.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LBeak2b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        } else {
            this.UBeak.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.UBeak2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LBeak.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.LBeak2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }

        if (isSaddled) {
            this.SaddleA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleL2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.SaddleR2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.NeckHarness.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.NeckHarness2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            if (rider) {
                this.NeckHarnessLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.NeckHarnessRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }

        }

        if (bagged) {
            this.Saddlebag.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Flagpole.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            switch (this.flagColor) {
                //case 0:
                //    FlagWhite.render(f5);
                //   break;
                case 1:
                    this.FlagOrange.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 2:
                    this.FlagPurple.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 3:
                    this.FlagLightBlue.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 4:
                    this.FlagYellow.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 5:
                    this.FlagGreen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 6:
                    this.FlagLightRed.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 7:
                    this.FlagDarkGrey.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 8:
                    this.FlagGrey.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 9:
                    this.FlagCyan.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 10:
                    this.FlagDarkPurple.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 11:
                    this.FlagDarkBlue.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 12:
                    this.FlagBrown.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 13:
                    this.FlagDarkGreen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 14:
                    this.FlagRed.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 15:
                    this.FlagBlack.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
                case 16:
                    this.FlagWhite.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                    break;
            }
        }

        switch (this.helmet) {
            case 1:
                this.HelmetLeather.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 2:
                this.HelmetIron.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 3:
                this.HelmetGold.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 4:
                this.HelmetDiamond.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 5:
                this.HelmetHide.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetNeckHide.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetHideEar1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetHideEar2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 6:
                this.HelmetFur.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetNeckFur.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetFurEar1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetFurEar2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 7:
                this.HelmetReptile.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetReptileEar1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                this.HelmetReptileEar2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 8:
                this.HelmetGreenChitin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 9:
                this.HelmetYellowChitin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 10:
                this.HelmetBlueChitin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 11:
                this.HelmetBlackChitin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;
            case 12:
                this.HelmetRedChitin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                break;

        }
    }
}
