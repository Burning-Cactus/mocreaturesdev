package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelSilverSkeleton;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MoCSilverSkeletonRenderer extends MobRenderer<MoCEntitySilverSkeleton, MoCModelSilverSkeleton> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/silverskeleton.png");
    public MoCSilverSkeletonRenderer(EntityRendererManager renderManagerIn, MoCModelSilverSkeleton entityModelIn, float shadowSizeIn) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity
     */
    @Override
    public ResourceLocation getEntityTexture(MoCEntitySilverSkeleton entity) {
        return TEXTURE;
    }
}
