package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelBunny<T extends MoCEntityBunny> extends SegmentedModel<T> {

    public ModelRenderer part1;
    public ModelRenderer part2;
    public ModelRenderer part3;
    public ModelRenderer part4;
    public ModelRenderer part5;
    public ModelRenderer part6;
    public ModelRenderer part7;
    public ModelRenderer part8;
    public ModelRenderer part9;
    public ModelRenderer part10;
    public ModelRenderer part11;
    private boolean bunnyHat;

    public MoCModelBunny() {
        byte byte0 = 16;
        this.part1 = new ModelRenderer(this, 0, 0);
        this.part1.addBox(-2F, -1F, -4F, 4, 4, 6, 0.0F);
        this.part1.setPos(0.0F, -1 + byte0, -4F);
        this.part8 = new ModelRenderer(this, 14, 0);
        this.part8.addBox(-2F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part8.setPos(0.0F, -1 + byte0, -4F);
        this.part9 = new ModelRenderer(this, 14, 0);
        this.part9.addBox(1.0F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part9.setPos(0.0F, -1 + byte0, -4F);
        this.part10 = new ModelRenderer(this, 20, 0);
        this.part10.addBox(-4F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part10.setPos(0.0F, -1 + byte0, -4F);
        this.part11 = new ModelRenderer(this, 20, 0);
        this.part11.addBox(2.0F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part11.setPos(0.0F, -1 + byte0, -4F);
        this.part2 = new ModelRenderer(this, 0, 10);
        this.part2.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        this.part2.setPos(0.0F, 0 + byte0, 0.0F);
        this.part3 = new ModelRenderer(this, 0, 24);
        this.part3.addBox(-2F, 4F, -2F, 4, 3, 4, 0.0F);
        this.part3.setPos(0.0F, 0 + byte0, 0.0F);
        this.part4 = new ModelRenderer(this, 24, 16);
        this.part4.addBox(-2F, 0.0F, -1F, 2, 2, 2);
        this.part4.setPos(3F, 3 + byte0, -3F);
        this.part5 = new ModelRenderer(this, 24, 16);
        this.part5.addBox(0.0F, 0.0F, -1F, 2, 2, 2);
        this.part5.setPos(-3F, 3 + byte0, -3F);
        this.part6 = new ModelRenderer(this, 16, 24);
        this.part6.addBox(-2F, 0.0F, -4F, 2, 2, 4);
        this.part6.setPos(3F, 3 + byte0, 4F);
        this.part7 = new ModelRenderer(this, 16, 24);
        this.part7.addBox(0.0F, 0.0F, -4F, 2, 2, 4);
        this.part7.setPos(-3F, 3 + byte0, 4F);
    }

    @Override
    public void setupAnim(T entity, float legMove1, float legMove2, float v2, float pitch, float yaw) {
        bunnyHat = entity.getVehicle() != null;
        this.part1.xRot = -(yaw / 57.29578F);
        this.part1.yRot = pitch / 57.29578F;

        this.part8.xRot = this.part1.xRot;
        this.part8.yRot = this.part1.yRot;
        this.part9.xRot = this.part1.xRot;
        this.part9.yRot = this.part1.yRot;
        this.part10.xRot = this.part1.xRot;
        this.part10.yRot = this.part1.yRot;
        this.part11.xRot = this.part1.xRot;
        this.part11.yRot = this.part1.yRot;
        this.part2.xRot = 1.570796F;
        this.part3.xRot = 1.570796F;
        if (!this.bunnyHat) {
            this.part4.xRot = MathHelper.cos(legMove1 * 0.6662F) * 1.0F * legMove2;
            this.part6.xRot = MathHelper.cos((legMove1 * 0.6662F) + 3.141593F) * 1.2F * legMove2;
            this.part5.xRot = MathHelper.cos(legMove1 * 0.6662F) * 1.0F * legMove2;
            this.part7.xRot = MathHelper.cos((legMove1 * 0.6662F) + 3.141593F) * 1.2F * legMove2;
        }

    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(
                part1,
                part2,
                part3,
                part4,
                part5,
                part6,
                part7,
                part8,
                part9,
                part10,
                part11
        );
    }
}
