package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderTurtle extends MoCRenderMoC<MoCEntityTurtle, MoCModelTurtle<MoCEntityTurtle>> {

    public MoCModelTurtle turtly;

    public MoCRenderTurtle(EntityRendererManager manager, MoCModelTurtle modelbase, float f) {
        super(manager, modelbase, f);
        this.turtly = modelbase;
    }

    @Override
    protected void scale(MoCEntityTurtle entityturtle, MatrixStack stack, float f) {
        this.turtly.upsidedown = entityturtle.getIsUpsideDown();
        this.turtly.swingProgress = entityturtle.attackAnim;
        this.turtly.isHiding = entityturtle.getIsHiding();

        if (!entityturtle.level.isClientSide && (entityturtle.getVehicle() != null)) {

            stack.translate(0.0F, 1.3F, 0.0F);

        }
        if (entityturtle.getIsHiding()) {
            adjustHeight(entityturtle, 0.15F * entityturtle.getEdad() * 0.01F);
        } else if (!entityturtle.getIsHiding() && !entityturtle.getIsUpsideDown() && !entityturtle.isInWater()) {
            adjustHeight(entityturtle, 0.05F * entityturtle.getEdad() * 0.01F);
        }
        if (entityturtle.getIsUpsideDown()) {
            rotateAnimal(entityturtle);
        }

        stretch(entityturtle);

    }

    protected void rotateAnimal(MoCEntityTurtle entityturtle) {
        //GL11.glRotatef(180F, -1F, 0.0F, 0.0F); //head up 180
        //GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F); //head around 180

        float f = entityturtle.attackAnim * 10F * entityturtle.getFlipDirection();
        float f2 = entityturtle.attackAnim / 30 * entityturtle.getFlipDirection();
        GL11.glRotatef(180F + f, 0.0F, 0.0F, -1.0F);
        GL11.glTranslatef(0.0F - f2, 0.5F * entityturtle.getEdad() * 0.01F, 0.0F);
    }

    protected void adjustHeight(MoCEntityTurtle entityturtle, float height) {
        GL11.glTranslatef(0.0F, height, 0.0F);
    }

    protected void stretch(MoCEntityTurtle entityturtle) {
        float f = entityturtle.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityTurtle entityturtle) {
        return entityturtle.getTexture();
    }
}
