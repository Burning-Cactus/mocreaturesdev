package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityManderin extends MoCEntitySmallFish{

    public MoCEntityManderin(EntityType<? extends MoCEntityManderin> type, World world) {
        super(type, world);
        this.setType(7);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_manderin.png");
    }

    @Override
    protected int getEggNumber() {
        return 86;
    }
}
