package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.CricketEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCModelCricket<T extends CricketEntity> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer Antenna;
    ModelRenderer AntennaB;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer FrontLegs;
    ModelRenderer MidLegs;
    ModelRenderer ThighLeft;
    ModelRenderer ThighLeftB;
    ModelRenderer ThighRight;
    ModelRenderer ThighRightB;
    ModelRenderer LegLeft;
    ModelRenderer LegLeftB;
    ModelRenderer LegRight;
    ModelRenderer LegRightB;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    ModelRenderer FoldedWings;

    boolean isFlying = false;

    public MoCModelCricket() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-0.5F, 0F, -1F, 1, 1, 2);
        this.Head.setPos(0F, 22.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 0, 11);
        this.Antenna.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.Antenna.setPos(0F, 22.5F, -3F);
        setRotation(this.Antenna, -2.736346F, 0F, 0F);

        this.AntennaB = new ModelRenderer(this, 0, 9);
        this.AntennaB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.AntennaB.setPos(0F, 20.7F, -3.8F);
        setRotation(this.AntennaB, 2.88506F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setPos(0F, 21F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Abdomen.setPos(0F, 22F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 4, 9);
        this.TailA.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.TailA.setPos(0F, 22F, 2.8F);
        setRotation(this.TailA, 1.308687F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 4, 7);
        this.TailB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.TailB.setPos(0F, 23F, 2.8F);
        setRotation(this.TailB, 1.665602F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setPos(0F, 23F, -1.8F);
        setRotation(this.FrontLegs, -0.8328009F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 13);
        this.MidLegs.addBox(-2F, 0F, 0F, 4, 2, 0);
        this.MidLegs.setPos(0F, 23F, -1.2F);
        setRotation(this.MidLegs, 1.070744F, 0F, 0F);

        this.ThighLeft = new ModelRenderer(this, 8, 5);
        this.ThighLeft.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeft.setPos(0.5F, 23F, 0F);
        setRotation(this.ThighLeft, -0.4886922F, 0.2617994F, 0F);

        this.ThighLeftB = new ModelRenderer(this, 8, 5);
        this.ThighLeftB.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeftB.setPos(0.5F, 22.5F, 0F);
        setRotation(this.ThighLeftB, -1.762782F, 0F, 0F);

        this.ThighRight = new ModelRenderer(this, 12, 5);
        this.ThighRight.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRight.setPos(-0.5F, 23F, 0F);
        setRotation(this.ThighRight, -0.4886922F, -0.2617994F, 0F);

        this.ThighRightB = new ModelRenderer(this, 12, 5);
        this.ThighRightB.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRightB.setPos(-0.5F, 22.5F, 0F);
        setRotation(this.ThighRightB, -1.762782F, 0F, 0F);

        this.LegLeft = new ModelRenderer(this, 0, 15);
        this.LegLeft.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeft.setPos(2F, 21F, 2.5F);

        this.LegLeftB = new ModelRenderer(this, 4, 15);
        this.LegLeftB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeftB.setPos(1.5F, 23F, 2.9F);
        setRotation(this.LegLeftB, 1.249201F, 0F, 0F);

        this.LegRight = new ModelRenderer(this, 4, 15);
        this.LegRight.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRight.setPos(-2F, 21F, 2.5F);

        this.LegRightB = new ModelRenderer(this, 4, 15);
        this.LegRightB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRightB.setPos(-1.5F, 23F, 2.9F);
        setRotation(this.LegRightB, 1.249201F, 0F, 0F);

        this.LeftWing = new ModelRenderer(this, 0, 30);
        this.LeftWing.addBox(0F, 0F, -1F, 6, 0, 2);
        this.LeftWing.setPos(0F, 20.9F, -1F);
        setRotation(this.LeftWing, 0F, -0.1919862F, 0F);

        this.RightWing = new ModelRenderer(this, 0, 28);
        this.RightWing.addBox(-6F, 0F, -1F, 6, 0, 2);
        this.RightWing.setPos(0F, 20.9F, -1F);
        setRotation(this.RightWing, 0F, 0.1919862F, 0F);

        this.FoldedWings = new ModelRenderer(this, 0, 26);
        this.FoldedWings.addBox(0F, 0F, -1F, 6, 0, 2);
        this.FoldedWings.setPos(0F, 20.9F, -2F);
        setRotation(this.FoldedWings, 0F, -1.570796F, 0F);
    }

    @Override
    public void setupAnim(T entity, float v, float v1, float v2, float v3, float v4) {
        CricketEntity entitycricket = (CricketEntity) entity;
        isFlying = (entitycricket.getIsFlying() || entitycricket.getDeltaMovement().y < -0.1D);
        setRotationAngles(v, v1, v2, isFlying);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public void setRotationAngles(float f, float f1, float f2, boolean isFlying) {

        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;

        if (isFlying) {
            float WingRot = MathHelper.cos((f2 * 2.0F)) * 0.7F;
            this.RightWing.zRot = WingRot;
            this.LeftWing.zRot = -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        this.AntennaB.xRot = 2.88506F - legMov;

        this.FrontLegs.xRot = -0.8328009F + frontLegAdj + legMov;
        this.MidLegs.xRot = 1.070744F + legMovB;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        this.Head.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Antenna.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.AntennaB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Thorax.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Abdomen.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.TailA.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.TailB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.FrontLegs.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.MidLegs.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);

        if (!isFlying) {

            this.ThighLeft.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.ThighRight.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.LegLeft.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.LegRight.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.FoldedWings.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);

        } else {

            this.ThighLeftB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.ThighRightB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.LegLeftB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.LegRightB.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            GL11.glPushMatrix();
            GL11.glEnable(3042 /* GL_BLEND */);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            this.LeftWing.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            this.RightWing.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
            GL11.glDisable(3042/* GL_BLEND */);
            GL11.glPopMatrix();

        }

        /*
         * if (isFlying) { }else { }
         */
    }
}
