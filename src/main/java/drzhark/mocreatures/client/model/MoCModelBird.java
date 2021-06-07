package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelBird<T extends MoCEntityBird> extends SegmentedModel<T> {

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftleg;
    public ModelRenderer rightleg;
    public ModelRenderer rwing;
    public ModelRenderer lwing;
    public ModelRenderer beak;
    public ModelRenderer tail;
    private boolean isOnAir;

    public MoCModelBird() {
        byte byte0 = 16;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.5F, -3F, -2F, 3, 3, 3, 0.0F);
        this.head.setPos(0.0F, -1 + byte0, -4F);
        this.beak = new ModelRenderer(this, 14, 0);
        this.beak.addBox(-0.5F, -1.5F, -3F, 1, 1, 2, 0.0F);
        this.beak.setPos(0.0F, -1 + byte0, -4F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.addBox(-2F, -4F, -3F, 4, 8, 4, 0.0F);
        this.body.setPos(0.0F, 0 + byte0, 0.0F);
        this.body.xRot = 1.047198F;
        this.leftleg = new ModelRenderer(this, 26, 0);
        this.leftleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        this.leftleg.setPos(-2F, 3 + byte0, 1.0F);
        this.rightleg = new ModelRenderer(this, 26, 0);
        this.rightleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        this.rightleg.setPos(1.0F, 3 + byte0, 1.0F);
        this.rwing = new ModelRenderer(this, 24, 13);
        this.rwing.addBox(-1F, 0.0F, -3F, 1, 5, 5);
        this.rwing.setPos(-2F, -2 + byte0, 0.0F);
        this.lwing = new ModelRenderer(this, 24, 13);
        this.lwing.addBox(0.0F, 0.0F, -3F, 1, 5, 5);
        this.lwing.setPos(2.0F, -2 + byte0, 0.0F);
        this.tail = new ModelRenderer(this, 0, 23);
        this.tail.addBox(-6F, 5F, 2.0F, 4, 1, 4, 0.0F);
        this.tail.setPos(4F, -3 + byte0, 0.0F);
        this.tail.xRot = 0.261799F;
    }

    @Override
    public void setupAnim(T bird, float legMove1, float legMove2, float v2, float pitch, float yaw) {
        this.isOnAir = bird.isOnAir() && bird.getVehicle() == null;
        this.head.xRot = -(yaw / 2.0F / 57.29578F);
        //head.rotateAngleY = f3 / 2.0F / 57.29578F; //fixed SMP bug
        this.head.yRot = pitch / 57.29578F;
        this.beak.yRot = this.head.yRot;

        if (this.isOnAir) {
            this.leftleg.xRot = 1.4F;
            this.rightleg.xRot = 1.4F;
        } else {
            this.leftleg.xRot = MathHelper.cos(legMove1 * 0.6662F) * legMove2;
            this.rightleg.xRot = MathHelper.cos((legMove1 * 0.6662F) + 3.141593F) * legMove2;
        }
        this.rwing.zRot = v2;
        this.lwing.zRot = -v2;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        super.renderToBuffer(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(
                head,
                body,
                leftleg,
                rightleg,
                rwing,
                lwing,
                beak,
                tail
        );
    }
}
