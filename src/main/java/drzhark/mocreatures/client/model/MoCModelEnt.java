package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.passive.MoCEntityEnt;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelEnt<T extends MoCEntityEnt> extends SegmentedModel<T> {

    ModelRenderer Body;
    ModelRenderer LShoulder;
    ModelRenderer LArm;
    ModelRenderer LWrist;
    ModelRenderer LHand;
    ModelRenderer LFingers;
    ModelRenderer RShoulder;
    ModelRenderer RArm;
    ModelRenderer RWrist;
    ModelRenderer RHand;
    ModelRenderer RFingers;
    ModelRenderer LLeg;
    ModelRenderer LThigh;
    ModelRenderer LKnee;
    ModelRenderer LAnkle;
    ModelRenderer LFoot;
    ModelRenderer RLeg;
    ModelRenderer RThigh;
    ModelRenderer RKnee;
    ModelRenderer RAnkle;
    ModelRenderer RFoot;
    ModelRenderer Neck;
    ModelRenderer Face;
    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Mouth;
    ModelRenderer TreeBase;
    ModelRenderer Leave1;
    ModelRenderer Leave2;
    ModelRenderer Leave3;
    ModelRenderer Leave4;
    ModelRenderer Leave5;
    ModelRenderer Leave6;
    ModelRenderer Leave7;
    ModelRenderer Leave8;
    ModelRenderer Leave9;
    ModelRenderer Leave10;
    ModelRenderer Leave11;
    ModelRenderer Leave12;
    ModelRenderer Leave13;
    ModelRenderer Leave14;
    ModelRenderer Leave15;
    ModelRenderer Leave16;

    public MoCModelEnt() {
        this.texWidth = 128;
        this.texHeight = 256;

        this.Body = new ModelRenderer(this, 68, 36);
        this.Body.addBox(-7.5F, -12.5F, -4.5F, 15, 25, 9);
        this.Body.setPos(0F, -31F, 0F);
        this.LShoulder = new ModelRenderer(this, 48, 108);
        this.LShoulder.addBox(6F, -14F, -4.8F, 9, 7, 7);
        this.LShoulder.setPos(0F, -31F, 0F);
        setRotation(this.LShoulder, 0F, 0F, -0.1745329F);
        this.LArm = new ModelRenderer(this, 80, 108);
        this.LArm.addBox(0F, -4F, -5F, 6, 24, 6);
        this.LArm.setPos(10F, -42F, 1F);
        setRotation(this.LArm, 0F, 0F, -0.1745329F);
        this.LWrist = new ModelRenderer(this, 0, 169);
        this.LWrist.addBox(2F, 17F, -6F, 8, 15, 8);
        this.LWrist.setPos(10F, -42F, 1F);
        this.LHand = new ModelRenderer(this, 88, 241);
        this.LHand.addBox(1F, 28F, -7F, 10, 5, 10);
        this.LHand.setPos(10F, -42F, 1F);
        this.LFingers = new ModelRenderer(this, 88, 176);
        this.LFingers.addBox(1F, 33F, -7F, 10, 15, 10);
        this.LFingers.setPos(10F, -42F, 1F);
        this.RShoulder = new ModelRenderer(this, 48, 122);
        this.RShoulder.addBox(-15F, -14F, -4.8F, 9, 7, 7);
        this.RShoulder.setPos(0F, -31F, 0F);
        setRotation(this.RShoulder, 0F, 0F, 0.1745329F);
        this.RArm = new ModelRenderer(this, 104, 108);
        this.RArm.addBox(-6F, -4F, -5F, 6, 24, 6);
        this.RArm.setPos(-10F, -42F, 1F);
        setRotation(this.RArm, 0F, 0F, 0.1745329F);
        this.RWrist = new ModelRenderer(this, 32, 169);
        this.RWrist.addBox(-10F, 17F, -6F, 8, 15, 8);
        this.RWrist.setPos(-10F, -42F, 1F);
        this.RHand = new ModelRenderer(this, 88, 226);
        this.RHand.addBox(-11F, 28F, -7F, 10, 5, 10);
        this.RHand.setPos(-10F, -42F, 1F);
        this.RFingers = new ModelRenderer(this, 88, 201);
        this.RFingers.addBox(-11F, 33F, -7F, 10, 15, 10);
        this.RFingers.setPos(-10F, -42F, 1F);
        this.LLeg = new ModelRenderer(this, 0, 90);
        this.LLeg.addBox(3F, 0F, -3F, 6, 20, 6);
        this.LLeg.setPos(0F, -21F, 0F);
        this.LThigh = new ModelRenderer(this, 24, 64);
        this.LThigh.addBox(2.5F, 4F, -3.5F, 7, 12, 7);
        this.LThigh.setPos(0F, -21F, 0F);
        this.LKnee = new ModelRenderer(this, 0, 0);
        this.LKnee.addBox(2F, 20F, -4F, 8, 24, 8);
        this.LKnee.setPos(0F, -21F, 0F);
        this.LAnkle = new ModelRenderer(this, 32, 29);
        this.LAnkle.addBox(1.5F, 25F, -4.5F, 9, 20, 9);
        this.LAnkle.setPos(0F, -21F, 0F);
        this.LFoot = new ModelRenderer(this, 0, 206);
        this.LFoot.addBox(1.5F, 38F, -23.5F, 9, 5, 9);
        this.LFoot.setPos(0F, -21F, 0F);
        setRotation(this.LFoot, 0.2617994F, 0F, 0F);
        this.RLeg = new ModelRenderer(this, 0, 64);
        this.RLeg.addBox(-9F, 0F, -3F, 6, 20, 6);
        this.RLeg.setPos(0F, -21F, 0F);
        this.RThigh = new ModelRenderer(this, 24, 83);
        this.RThigh.addBox(-9.5F, 4F, -3.5F, 7, 12, 7);
        this.RThigh.setPos(0F, -21F, 0F);
        this.RKnee = new ModelRenderer(this, 0, 32);
        this.RKnee.addBox(-10F, 20F, -4F, 8, 24, 8);
        this.RKnee.setPos(0F, -21F, 0F);
        this.RAnkle = new ModelRenderer(this, 32, 0);
        this.RAnkle.addBox(-10.5F, 25F, -4.5F, 9, 20, 9);
        this.RAnkle.setPos(0F, -21F, 0F);
        this.RFoot = new ModelRenderer(this, 0, 192);
        this.RFoot.addBox(-10.5F, 38F, -23.5F, 9, 5, 9);
        this.RFoot.setPos(0F, -21F, 0F);
        setRotation(this.RFoot, 0.2617994F, 0F, 0F);
        this.Neck = new ModelRenderer(this, 52, 90);
        this.Neck.addBox(-4F, -8F, -5.8F, 8, 10, 8);
        this.Neck.setPos(0F, -44F, 0F);
        setRotation(this.Neck, 0.5235988F, 0F, 0F);
        this.Face = new ModelRenderer(this, 52, 70);
        this.Face.addBox(-4.5F, -11F, -9F, 9, 7, 8);
        this.Face.setPos(0F, -44F, 0F);
        this.Head = new ModelRenderer(this, 84, 88);
        this.Head.addBox(-6F, -20.5F, -9.5F, 12, 10, 10);
        this.Head.setPos(0F, -44F, 0F);
        this.Nose = new ModelRenderer(this, 82, 88);
        this.Nose.addBox(-1.5F, -12F, -12F, 3, 7, 3);
        this.Nose.setPos(0F, -44F, 0F);
        setRotation(this.Nose, -0.122173F, 0F, 0F);
        this.Mouth = new ModelRenderer(this, 77, 36);
        this.Mouth.addBox(-3F, -8F, -6.8F, 6, 2, 1);
        this.Mouth.setPos(0F, -44F, 0F);
        setRotation(this.Mouth, 0.5235988F, 0F, 0F);
        this.TreeBase = new ModelRenderer(this, 0, 136);
        this.TreeBase.addBox(-10F, -31.5F, -11.5F, 20, 13, 20);
        this.TreeBase.setPos(0F, -44F, 0F);
        this.Leave1 = new ModelRenderer(this, 0, 224);
        this.Leave1.addBox(-16F, -45F, -17F, 16, 16, 16);
        this.Leave1.setPos(0F, -44F, 0F);
        this.Leave2 = new ModelRenderer(this, 0, 224);
        this.Leave2.addBox(0F, -45F, -17F, 16, 16, 16);
        this.Leave2.setPos(0F, -44F, 0F);
        this.Leave3 = new ModelRenderer(this, 0, 224);
        this.Leave3.addBox(0F, -45F, -1F, 16, 16, 16);
        this.Leave3.setPos(0F, -44F, 0F);
        this.Leave4 = new ModelRenderer(this, 0, 224);
        this.Leave4.addBox(-16F, -45F, -1F, 16, 16, 16);
        this.Leave4.setPos(0F, -44F, 0F);
        this.Leave5 = new ModelRenderer(this, 0, 224);
        this.Leave5.addBox(-16F, -45F, -33F, 16, 16, 16);
        this.Leave5.setPos(0F, -44F, 0F);
        this.Leave6 = new ModelRenderer(this, 0, 224);
        this.Leave6.addBox(0F, -45F, -33F, 16, 16, 16);
        this.Leave6.setPos(0F, -44F, 0F);
        this.Leave7 = new ModelRenderer(this, 0, 224);
        this.Leave7.addBox(16F, -45F, -17F, 16, 16, 16);
        this.Leave7.setPos(0F, -44F, 0F);
        this.Leave8 = new ModelRenderer(this, 0, 224);
        this.Leave8.addBox(16F, -45F, -1F, 16, 16, 16);
        this.Leave8.setPos(0F, -44F, 0F);
        this.Leave9 = new ModelRenderer(this, 0, 224);
        this.Leave9.addBox(0F, -45F, 15F, 16, 16, 16);
        this.Leave9.setPos(0F, -44F, 0F);
        this.Leave10 = new ModelRenderer(this, 0, 224);
        this.Leave10.addBox(-16F, -45F, 15F, 16, 16, 16);
        this.Leave10.setPos(0F, -44F, 0F);
        this.Leave11 = new ModelRenderer(this, 0, 224);
        this.Leave11.addBox(-32F, -45F, -1F, 16, 16, 16);
        this.Leave11.setPos(0F, -44F, 0F);
        this.Leave12 = new ModelRenderer(this, 0, 224);
        this.Leave12.addBox(-32F, -45F, -17F, 16, 16, 16);
        this.Leave12.setPos(0F, -44F, 0F);
        this.Leave13 = new ModelRenderer(this, 0, 224);
        this.Leave13.addBox(-16F, -61F, -17F, 16, 16, 16);
        this.Leave13.setPos(0F, -44F, 0F);
        this.Leave14 = new ModelRenderer(this, 0, 224);
        this.Leave14.addBox(0F, -61F, -17F, 16, 16, 16);
        this.Leave14.setPos(0F, -44F, 0F);
        this.Leave15 = new ModelRenderer(this, 0, 224);
        this.Leave15.addBox(0F, -61F, -1F, 16, 16, 16);
        this.Leave15.setPos(0F, -44F, 0F);
        this.Leave16 = new ModelRenderer(this, 0, 224);
        this.Leave16.addBox(-16F, -61F, -1F, 16, 16, 16);
        this.Leave16.setPos(0F, -44F, 0F);

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(Body, LShoulder, LArm, LWrist, LHand, LFingers, RShoulder, RArm, RWrist, RHand, RFingers, LLeg, LThigh, LKnee,
                LAnkle, LFoot, RLeg, RThigh, RKnee, RAnkle, RFoot, Neck, Face, Head, Nose, Mouth, TreeBase, Leave1, Leave2, Leave3, Leave4,
                Leave5, Leave6, Leave7, Leave8, Leave9, Leave10, Leave11, Leave12, Leave13, Leave14, Leave15, Leave16);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        float radianF = 57.29578F;

        float RArmXRot = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
        float LArmXRot = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        float RLegXRot = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.0F * f1;

        this.LWrist.zRot = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
        this.LWrist.xRot = LArmXRot;
        this.RWrist.zRot = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.RWrist.xRot = RArmXRot;

        this.LHand.xRot = this.LFingers.xRot = this.LArm.xRot = this.LWrist.xRot;
        this.LHand.zRot = this.LFingers.zRot = this.LWrist.zRot;
        this.LArm.zRot = (-10F / radianF) + this.LWrist.zRot;

        this.RHand.xRot = this.RFingers.xRot = this.RArm.xRot = this.RWrist.xRot;
        this.RHand.zRot = this.RFingers.zRot = this.RWrist.zRot;
        this.RArm.zRot = (10F / radianF) + this.RWrist.zRot;

        this.RLeg.xRot = RLegXRot;
        this.LLeg.xRot = LLegXRot;
        this.LThigh.xRot = this.LKnee.xRot = this.LAnkle.xRot = this.LLeg.xRot;
        this.RThigh.xRot = this.RKnee.xRot = this.RAnkle.xRot = this.RLeg.xRot;

        this.LFoot.xRot = (15F / radianF) + this.LLeg.xRot;
        this.RFoot.xRot = (15F / radianF) + this.RLeg.xRot;
        this.Neck.yRot = f3 / radianF; //this moves head to left and right
        //Neck.rotateAngleX = f4/ radianF;

        this.Mouth.yRot =
                this.Face.yRot = this.Nose.yRot = this.Head.yRot = this.TreeBase.yRot = this.Neck.yRot;
        this.Leave1.yRot =
                this.Leave2.yRot =
                        this.Leave3.yRot =
                                this.Leave4.yRot = this.Leave5.yRot = this.Leave6.yRot = this.Neck.yRot;
        this.Leave7.yRot =
                this.Leave8.yRot = this.Leave9.yRot = this.Leave10.yRot = this.Leave11.yRot = this.Neck.yRot;
        this.Leave12.yRot =
                this.Leave13.yRot =
                        this.Leave14.yRot = this.Leave15.yRot = this.Leave16.yRot = this.Neck.yRot;

    }

}
