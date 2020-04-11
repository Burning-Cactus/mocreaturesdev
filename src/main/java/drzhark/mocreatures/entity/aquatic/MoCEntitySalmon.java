package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntitySalmon extends MoCEntityMediumFish{

    public MoCEntitySalmon(EntityType<? extends MoCEntitySalmon> type, World world) {
        super(type, world);
        this.setType(1);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("mediumfish_salmon.png");
    }
    
    @Override
    protected int getEggNumber() {
        return 70;
    }

}
