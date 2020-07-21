package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGreenOgre extends MoCEntityOgre{

    public MoCEntityGreenOgre(EntityType<? extends MoCEntityGreenOgre> type, World world) {
        super(type, world);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("ogregreen.png");
    }
    
    /**
     * Returns the strength of the blasting power
     * @return
     */
    @Override
    public float getDestroyForce() {
            return MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.ogreStrength.get().floatValue();
    }

}
