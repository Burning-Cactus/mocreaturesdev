package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityHippoTang extends MoCEntitySmallFish{

    public MoCEntityHippoTang(EntityType<? extends MoCEntityHippoTang> type, World world) {
        super(type, world);
        this.setType(6);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_hippotang.png");
    }

    @Override
    protected int getEggNumber() {
        return 85;
    }
}
