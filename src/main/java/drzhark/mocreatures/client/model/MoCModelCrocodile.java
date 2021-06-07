package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelCrocodile<T extends MoCEntityCrocodile> extends SegmentedModel<T> {

    public MoCModelCrocodile() {
        this.LJaw = new ModelRenderer(this, 42, 0);
        this.LJaw.addBox(-2.5F, 1F, -12F, 5, 2, 6);
        this.LJaw.setPos(0F, 18F, -8F);
        this.LJaw.xRot = 0F;
        this.LJaw.yRot = 0F;
        this.LJaw.zRot = 0F;

        this.TailA = new ModelRenderer(this, 0, 0);
        this.TailA.addBox(-4F, -0.5F, 0F, 8, 4, 8);
        this.TailA.setPos(0F, 17F, 12F);
        this.TailA.xRot = 0F;
        this.TailA.yRot = 0F;
        this.TailA.zRot = 0F;

        this.TailB = new ModelRenderer(this, 2, 0);
        this.TailB.addBox(-3F, 0F, 8F, 6, 3, 8);
        this.TailB.setPos(0F, 17F, 12F);
        this.TailB.xRot = 0F;
        this.TailB.yRot = 0F;
        this.TailB.zRot = 0F;

        this.TailC = new ModelRenderer(this, 6, 2);
        this.TailC.addBox(-2F, 0.5F, 16F, 4, 2, 6);
        this.TailC.setPos(0F, 17F, 12F);
        this.TailC.xRot = 0F;
        this.TailC.yRot = 0F;
        this.TailC.zRot = 0F;

        this.TailD = new ModelRenderer(this, 7, 2);
        this.TailD.addBox(-1.5F, 1F, 22F, 3, 1, 6);
        this.TailD.setPos(0F, 17F, 12F);
        this.TailD.xRot = 0F;
        this.TailD.yRot = 0F;
        this.TailD.zRot = 0F;

        this.UJaw = new ModelRenderer(this, 44, 8);
        this.UJaw.addBox(-2F, -1F, -12F, 4, 2, 6);
        this.UJaw.setPos(0F, 18F, -8F);
        this.UJaw.xRot = 0F;
        this.UJaw.yRot = 0F;
        this.UJaw.zRot = 0F;

        this.Head = new ModelRenderer(this, 0, 16);
        this.Head.addBox(-3F, -2F, -6F, 6, 5, 6);
        this.Head.setPos(0F, 18F, -8F);
        this.Head.xRot = 0F;
        this.Head.yRot = 0F;
        this.Head.zRot = 0F;

        this.Body = new ModelRenderer(this, 4, 7);
        this.Body.addBox(0F, 0F, 0F, 10, 5, 20);
        this.Body.setPos(-5F, 16F, -8F);
        this.Body.xRot = 0F;
        this.Body.yRot = 0F;
        this.Body.zRot = 0F;

        this.Leg1 = new ModelRenderer(this, 49, 21);
        this.Leg1.addBox(1F, 2F, -3F, 3, 2, 4);
        this.Leg1.setPos(5F, 19F, -3F);

        this.Leg1.xRot = 0F;
        this.Leg1.yRot = 0F;
        this.Leg1.zRot = 0F;

        this.Leg3 = new ModelRenderer(this, 48, 20);
        this.Leg3.addBox(1F, 2F, -3F, 3, 2, 5);
        this.Leg3.setPos(5F, 19F, 9F);

        this.Leg3.xRot = 0F;
        this.Leg3.yRot = 0F;
        this.Leg3.zRot = 0F;

        this.Leg2 = new ModelRenderer(this, 49, 21);
        this.Leg2.addBox(-4F, 2F, -3F, 3, 2, 4);
        this.Leg2.setPos(-5F, 19F, -3F);

        this.Leg2.xRot = 0F;
        this.Leg2.yRot = 0F;
        this.Leg2.zRot = 0F;

        this.Leg4 = new ModelRenderer(this, 48, 20);
        this.Leg4.addBox(-4F, 2F, -3F, 3, 2, 5);
        this.Leg4.setPos(-5F, 19F, 9F);

        this.Leg4.xRot = 0F;
        this.Leg4.yRot = 0F;
        this.Leg4.zRot = 0F;

        this.Leg1A = new ModelRenderer(this, 7, 9);
        this.Leg1A.addBox(0F, -1F, -2F, 3, 3, 3);
        this.Leg1A.setPos(5F, 19F, -3F);

        this.Leg1A.xRot = 0F;
        this.Leg1A.yRot = 0F;
        this.Leg1A.zRot = 0F;

        this.Leg2A = new ModelRenderer(this, 7, 9);
        this.Leg2A.addBox(-3F, -1F, -2F, 3, 3, 3);
        this.Leg2A.setPos(-5F, 19F, -3F);

        this.Leg2A.xRot = 0F;
        this.Leg2A.yRot = 0F;
        this.Leg2A.zRot = 0F;

        this.Leg3A = new ModelRenderer(this, 6, 8);
        this.Leg3A.addBox(0F, -1F, -2F, 3, 3, 4);
        this.Leg3A.setPos(5F, 19F, 9F);

        this.Leg3A.xRot = 0F;
        this.Leg3A.yRot = 0F;
        this.Leg3A.zRot = 0F;

        this.Leg4A = new ModelRenderer(this, 6, 8);
        this.Leg4A.addBox(-3F, -1F, -2F, 3, 3, 4);
        this.Leg4A.setPos(-5F, 19F, 9F);

        this.Leg4A.xRot = 0F;
        this.Leg4A.yRot = 0F;
        this.Leg4A.zRot = 0F;

        this.UJaw2 = new ModelRenderer(this, 37, 0);
        this.UJaw2.addBox(-1.5F, -1F, -16F, 3, 2, 4);
        this.UJaw2.setPos(0F, 18F, -8F);
        this.UJaw2.xRot = 0F;
        this.UJaw2.yRot = 0F;
        this.UJaw2.zRot = 0F;

        this.LJaw2 = new ModelRenderer(this, 24, 1);
        this.LJaw2.addBox(-2F, 1F, -16F, 4, 2, 4);
        this.LJaw2.setPos(0F, 18F, -8F);
        this.LJaw2.xRot = 0F;
        this.LJaw2.yRot = 0F;
        this.LJaw2.zRot = 0F;

        this.TeethA = new ModelRenderer(this, 8, 11);
        this.TeethA.addBox(1.6F, 0F, -16F, 0, 1, 4);
        this.TeethA.setPos(0F, 18F, -8F);
        this.TeethA.xRot = 0F;
        this.TeethA.yRot = 0F;
        this.TeethA.zRot = 0F;

        this.TeethB = new ModelRenderer(this, 8, 11);
        this.TeethB.addBox(-1.6F, 0F, -16F, 0, 1, 4);
        this.TeethB.setPos(0F, 18F, -8F);
        this.TeethB.xRot = 0F;
        this.TeethB.yRot = 0F;
        this.TeethB.zRot = 0F;

        this.TeethC = new ModelRenderer(this, 6, 9);
        this.TeethC.addBox(2.1F, 0F, -12F, 0, 1, 6);
        this.TeethC.setPos(0F, 18F, -8F);
        this.TeethC.xRot = 0F;
        this.TeethC.yRot = 0F;
        this.TeethC.zRot = 0F;

        this.TeethD = new ModelRenderer(this, 6, 9);
        this.TeethD.addBox(-2.1F, 0F, -12F, 0, 1, 6);
        this.TeethD.setPos(0F, 18F, -8F);
        this.TeethD.xRot = 0F;
        this.TeethD.yRot = 0F;
        this.TeethD.zRot = 0F;

        this.Leg1A.xRot = 0F;
        this.Leg1A.yRot = 0F;
        this.Leg1A.zRot = 0F;

        this.Leg2A.xRot = 0F;
        this.Leg2A.yRot = 0F;
        this.Leg2A.zRot = 0F;

        this.Leg3A.xRot = 0F;
        this.Leg3A.yRot = 0F;
        this.Leg3A.zRot = 0F;

        this.Leg4A.xRot = 0F;
        this.Leg4A.yRot = 0F;
        this.Leg4A.zRot = 0F;

        this.TeethF = new ModelRenderer(this, 19, 21);
        this.TeethF.addBox(-1F, 0F, -16.1F, 2, 1, 0);
        this.TeethF.setPos(0F, 18F, -8F);

        this.Spike0 = new ModelRenderer(this, 44, 16);
        this.Spike0.addBox(-1F, -1F, 23F, 0, 2, 4);
        this.Spike0.setPos(0F, 17F, 12F);

        this.Spike1 = new ModelRenderer(this, 44, 16);
        this.Spike1.addBox(1F, -1F, 23F, 0, 2, 4);
        this.Spike1.setPos(0F, 17F, 12F);

        this.Spike2 = new ModelRenderer(this, 44, 16);
        this.Spike2.addBox(-1.5F, -1.5F, 17F, 0, 2, 4);
        this.Spike2.setPos(0F, 17F, 12F);

        this.Spike3 = new ModelRenderer(this, 44, 16);
        this.Spike3.addBox(1.5F, -1.5F, 17F, 0, 2, 4);
        this.Spike3.setPos(0F, 17F, 12F);

        this.Spike4 = new ModelRenderer(this, 44, 16);
        this.Spike4.addBox(-2F, -2F, 12F, 0, 2, 4);
        this.Spike4.setPos(0F, 17F, 12F);

        this.Spike5 = new ModelRenderer(this, 44, 16);
        this.Spike5.addBox(2F, -2F, 12F, 0, 2, 4);
        this.Spike5.setPos(0F, 17F, 12F);

        this.Spike6 = new ModelRenderer(this, 44, 16);
        this.Spike6.addBox(-2.5F, -2F, 8F, 0, 2, 4);
        this.Spike6.setPos(0F, 17F, 12F);

        this.Spike7 = new ModelRenderer(this, 44, 16);
        this.Spike7.addBox(2.5F, -2F, 8F, 0, 2, 4);
        this.Spike7.setPos(0F, 17F, 12F);

        this.Spike8 = new ModelRenderer(this, 44, 16);
        this.Spike8.addBox(-3F, -2.5F, 4F, 0, 2, 4);
        this.Spike8.setPos(0F, 17F, 12F);

        this.Spike9 = new ModelRenderer(this, 44, 16);
        this.Spike9.addBox(3F, -2.5F, 4F, 0, 2, 4);
        this.Spike9.setPos(0F, 17F, 12F);

        this.Spike10 = new ModelRenderer(this, 44, 16);
        this.Spike10.addBox(3.5F, -2.5F, 0F, 0, 2, 4);
        this.Spike10.setPos(0F, 17F, 12F);

        this.Spike11 = new ModelRenderer(this, 44, 16);
        this.Spike11.addBox(-3.5F, -2.5F, 0F, 0, 2, 4);
        this.Spike11.setPos(0F, 17F, 12F);

        this.SpikeBack0 = new ModelRenderer(this, 44, 10);
        this.SpikeBack0.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack0.setPos(0F, 14F, 3F);

        this.SpikeBack1 = new ModelRenderer(this, 44, 10);
        this.SpikeBack1.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack1.setPos(0F, 14F, -6F);

        this.SpikeBack2 = new ModelRenderer(this, 44, 10);
        this.SpikeBack2.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack2.setPos(4F, 14F, -8F);

        this.SpikeBack3 = new ModelRenderer(this, 44, 10);
        this.SpikeBack3.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack3.setPos(-4F, 14F, -8F);

        this.SpikeBack4 = new ModelRenderer(this, 44, 10);
        this.SpikeBack4.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack4.setPos(-4F, 14F, 1F);

        this.SpikeBack5 = new ModelRenderer(this, 44, 10);
        this.SpikeBack5.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack5.setPos(4F, 14F, 1F);

        this.SpikeEye = new ModelRenderer(this, 44, 14);
        this.SpikeEye.addBox(-3F, -3F, -6F, 0, 1, 2);
        this.SpikeEye.setPos(0F, 18F, -8F);

        this.SpikeEye1 = new ModelRenderer(this, 44, 14);
        this.SpikeEye1.addBox(3F, -3F, -6F, 0, 1, 2);
        this.SpikeEye1.setPos(0F, 18F, -8F);

        this.TeethA1 = new ModelRenderer(this, 52, 12);
        this.TeethA1.addBox(1.4F, 1F, -16.4F, 0, 1, 4);
        this.TeethA1.setPos(0F, 18F, -8F);

        this.TeethB1 = new ModelRenderer(this, 52, 12);
        this.TeethB1.addBox(-1.4F, 1F, -16.4F, 0, 1, 4);
        this.TeethB1.setPos(0F, 18F, -8F);

        this.TeethC1 = new ModelRenderer(this, 50, 10);
        this.TeethC1.addBox(1.9F, 1F, -12.5F, 0, 1, 6);
        this.TeethC1.setPos(0F, 18F, -8F);

        this.TeethD1 = new ModelRenderer(this, 50, 10);
        this.TeethD1.addBox(-1.9F, 1F, -12.5F, 0, 1, 6);
        this.TeethD1.setPos(0F, 18F, -8F);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float time, float pitch, float yaw) {
        setRotationAngles(limbSwing, limbSwingAmount, time, pitch, yaw);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return null;
    }

    public void model2() {

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.xRot = f4 / 57.29578F;
        this.Head.yRot = f3 / 57.29578F;
        this.SpikeEye.xRot = this.Head.xRot;
        this.SpikeEye.yRot = this.Head.yRot;
        this.SpikeEye1.xRot = this.Head.xRot;
        this.SpikeEye1.yRot = this.Head.yRot;

        //LJaw.rotateAngleX = Head.rotateAngleX;
        this.LJaw.yRot = this.Head.yRot;
        this.LJaw2.yRot = this.Head.yRot;
        //UJaw.rotateAngleX = Head.rotateAngleX;
        this.UJaw.yRot = this.Head.yRot;
        this.UJaw2.yRot = this.Head.yRot;
        if (this.swimming) {
            //Leg1.setRotationPoint(9F, 18F, 0F);
            this.Leg1.x = 9F;
            this.Leg1.y = 18F;
            this.Leg1.z = 0F;
            this.Leg1.xRot = 0F;
            this.Leg1.yRot = -3.14159F;
            //Leg2.setRotationPoint(-9F, 18F, 0F);
            this.Leg2.x = -9F;
            this.Leg2.y = 18F;
            this.Leg2.z = 0F;
            this.Leg2.xRot = 0F;
            this.Leg2.yRot = -3.14159F;
            //Leg3.setRotationPoint(8F, 18F, 12F);
            this.Leg3.x = 8F;
            this.Leg3.y = 18F;
            this.Leg3.z = 12F;
            this.Leg3.xRot = 0F;
            this.Leg3.yRot = -3.14159F;
            //Leg4.setRotationPoint(-8F, 18F, 12F);
            this.Leg4.x = -8F;
            this.Leg4.y = 18F;
            this.Leg4.z = 12F;
            this.Leg4.xRot = 0F;
            this.Leg4.yRot = -3.14159F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            this.Leg1A.xRot = 1.5708F;
            this.Leg1A.x = 5F;
            this.Leg1A.y = 19F;
            this.Leg1A.z = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            this.Leg2A.xRot = 1.5708F;
            this.Leg2A.x = -5F;
            this.Leg2A.y = 19F;
            this.Leg2A.z = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            this.Leg3A.xRot = 1.5708F;
            this.Leg3A.x = 5F;
            this.Leg3A.y = 19F;
            this.Leg3A.z = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            this.Leg4A.xRot = 1.5708F;
            this.Leg4A.x = -5F;
            this.Leg4A.y = 19F;
            this.Leg4A.z = 9F;

            this.Leg1.zRot = 0F;
            this.Leg1A.zRot = 0F;
            this.Leg3.zRot = 0F;
            this.Leg3A.zRot = 0F;

            this.Leg2.zRot = 0F;
            this.Leg2A.zRot = 0F;
            this.Leg4.zRot = 0F;
            this.Leg4A.zRot = 0F;

        } else if (this.resting) {
            //Leg1.setRotationPoint(6F, 17F, -6F);
            this.Leg1.x = 6F;
            this.Leg1.y = 17F;
            this.Leg1.z = -6F;
            this.Leg1.xRot = 0F;
            this.Leg1.yRot = -0.7854F;

            //Leg2.setRotationPoint(-6F, 17F, -6F);
            this.Leg2.yRot = 0.7854F;
            this.Leg2.x = -6F;
            this.Leg2.y = 17F;
            this.Leg2.z = -6F;
            this.Leg2.xRot = 0F;

            //Leg3.setRotationPoint(7F, 17F, 7F);
            this.Leg3.yRot = -0.7854F;
            this.Leg3.x = 7F;
            this.Leg3.y = 17F;
            this.Leg3.z = 7F;
            this.Leg3.xRot = 0F;

            this.Leg4.setPos(-7F, 17F, 7F);
            this.Leg4.yRot = 0.7854F;
            this.Leg4.x = -7F;
            this.Leg4.y = 17F;
            this.Leg4.z = 7F;
            this.Leg4.xRot = 0F;

            //Leg1A.setRotationPoint(5F, 17F, -3F);
            this.Leg1A.x = 5F;
            this.Leg1A.y = 17F;
            this.Leg1A.z = -3F;
            this.Leg1A.xRot = 0F;

            //Leg2A.setRotationPoint(-5F, 17F, -3F);
            this.Leg2A.x = -5F;
            this.Leg2A.y = 17F;
            this.Leg2A.z = -3F;
            this.Leg2A.xRot = 0F;

            //Leg3A.setRotationPoint(5F, 17F, 9F);
            this.Leg3A.x = 5F;
            this.Leg3A.y = 17F;
            this.Leg3A.z = 9F;
            this.Leg3A.xRot = 0F;

            //Leg4A.setRotationPoint(-5F, 17F, 9F);
            this.Leg4A.x = -5F;
            this.Leg4A.y = 17F;
            this.Leg4A.z = 9F;
            this.Leg4A.xRot = 0F;

            this.Leg1.zRot = 0F;
            this.Leg1A.zRot = 0F;
            this.Leg3.zRot = 0F;
            this.Leg3A.zRot = 0F;

            this.Leg2.zRot = 0F;
            this.Leg2A.zRot = 0F;
            this.Leg4.zRot = 0F;
            this.Leg4A.zRot = 0F;
        } else {
            //Leg1.setRotationPoint(5F, 19F, -3F);
            this.Leg1.x = 5F;
            this.Leg1.y = 19F;
            this.Leg1.z = -3F;
            //Leg2.setRotationPoint(-5F, 19F, -3F);
            this.Leg2.x = -5F;
            this.Leg2.y = 19F;
            this.Leg2.z = -3F;
            //Leg3.setRotationPoint(5F, 19F, 9F);
            this.Leg3.x = 5F;
            this.Leg3.y = 19F;
            this.Leg3.z = 9F;
            //Leg4.setRotationPoint(-5F, 19F, 9F);
            this.Leg4.x = -5F;
            this.Leg4.y = 19F;
            this.Leg4.z = 9F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            this.Leg1A.x = 5F;
            this.Leg1A.y = 19F;
            this.Leg1A.z = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            this.Leg2A.x = -5F;
            this.Leg2A.y = 19F;
            this.Leg2A.z = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            this.Leg3A.x = 5F;
            this.Leg3A.y = 19F;
            this.Leg3A.z = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            this.Leg4A.x = -5F;
            this.Leg4A.y = 19F;
            this.Leg4A.z = 9F;

            this.Leg1.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            this.Leg2.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            this.Leg3.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            this.Leg4.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

            this.Leg1.yRot = 0F;
            this.Leg2.yRot = 0F;
            this.Leg3.yRot = 0F;
            this.Leg4.yRot = 0F;

            this.Leg1A.xRot = this.Leg1.xRot;
            this.Leg2A.xRot = this.Leg2.xRot;
            this.Leg3A.xRot = this.Leg3.xRot;
            this.Leg4A.xRot = this.Leg4.xRot;

            float latrot = MathHelper.cos(f / (1.919107651F * 1)) * 0.261799387799149F * f1 * 5;
            this.Leg1.zRot = latrot;
            this.Leg1A.zRot = latrot;
            this.Leg4.zRot = -latrot;
            this.Leg4A.zRot = -latrot;

            this.Leg3.zRot = latrot;
            this.Leg3A.zRot = latrot;

            this.Leg2.zRot = -latrot;
            this.Leg2A.zRot = -latrot;

            //Leg1.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;
            //Leg1A.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;

            //LArm.rotateAngleY = MathHelper.cos(f / (1.919107651F * 1 )) * -0.349065850398866F * f1 + 0.785398163397448F ;

        }
        this.TailA.yRot = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        this.TailB.yRot = this.TailA.yRot;
        this.TailC.yRot = this.TailA.yRot;
        this.TailD.yRot = this.TailA.yRot;
        this.Spike0.yRot = this.TailA.yRot;
        this.Spike1.yRot = this.TailA.yRot;
        this.Spike2.yRot = this.TailA.yRot;
        this.Spike3.yRot = this.TailA.yRot;
        this.Spike4.yRot = this.TailA.yRot;
        this.Spike5.yRot = this.TailA.yRot;
        this.Spike6.yRot = this.TailA.yRot;
        this.Spike7.yRot = this.TailA.yRot;
        this.Spike8.yRot = this.TailA.yRot;
        this.Spike9.yRot = this.TailA.yRot;
        this.Spike10.yRot = this.TailA.yRot;
        this.Spike11.yRot = this.TailA.yRot;
        float f25 = this.biteProgress;
        float f26 = f25;
        if (f25 >= 0.5F) {
            f26 = (0.5F - (f25 - 0.5F));
        }
        this.UJaw.xRot = this.Head.xRot - f26;
        this.UJaw2.xRot = this.UJaw.xRot;
        this.LJaw.xRot = this.Head.xRot + (f26 / 2);
        this.LJaw2.xRot = this.LJaw.xRot;
        this.TeethA.xRot = this.LJaw.xRot;
        this.TeethB.xRot = this.LJaw.xRot;
        this.TeethC.xRot = this.LJaw.xRot;
        this.TeethD.xRot = this.LJaw.xRot;
        this.TeethF.xRot = this.LJaw.xRot;
        this.TeethA.yRot = this.LJaw.yRot;
        this.TeethB.yRot = this.LJaw.yRot;
        this.TeethC.yRot = this.LJaw.yRot;
        this.TeethD.yRot = this.LJaw.yRot;
        this.TeethF.yRot = this.LJaw.yRot;
        this.TeethA1.xRot = this.UJaw.xRot;
        this.TeethB1.xRot = this.UJaw.xRot;
        this.TeethC1.xRot = this.UJaw.xRot;
        this.TeethD1.xRot = this.UJaw.xRot;
        this.TeethA1.yRot = this.UJaw.yRot;
        this.TeethB1.yRot = this.UJaw.yRot;
        this.TeethC1.yRot = this.UJaw.yRot;
        this.TeethD1.yRot = this.UJaw.yRot;

    }

    ModelRenderer LJaw;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;
    ModelRenderer UJaw;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg3;
    ModelRenderer Leg2;
    ModelRenderer Leg4;
    ModelRenderer TailD;
    ModelRenderer Leg1A;
    ModelRenderer Leg2A;
    ModelRenderer Leg3A;
    ModelRenderer Leg4A;
    ModelRenderer UJaw2;
    ModelRenderer LJaw2;
    ModelRenderer TeethA;
    ModelRenderer TeethB;
    ModelRenderer TeethC;
    ModelRenderer TeethD;
    public float biteProgress;
    public boolean swimming;
    public boolean resting;

    ModelRenderer TeethF;
    ModelRenderer Spike0;
    ModelRenderer Spike1;
    ModelRenderer Spike2;
    ModelRenderer Spike3;
    ModelRenderer Spike4;
    ModelRenderer Spike5;
    ModelRenderer Spike6;
    ModelRenderer Spike7;
    ModelRenderer Spike8;
    ModelRenderer Spike9;
    ModelRenderer Spike10;
    ModelRenderer Spike11;
    ModelRenderer SpikeBack0;
    ModelRenderer SpikeBack1;
    ModelRenderer SpikeBack2;
    ModelRenderer SpikeBack3;
    ModelRenderer SpikeBack4;
    ModelRenderer SpikeBack5;
    ModelRenderer SpikeEye;
    ModelRenderer SpikeEye1;
    ModelRenderer TeethA1;
    ModelRenderer TeethB1;
    ModelRenderer TeethC1;
    ModelRenderer TeethD1;
}
