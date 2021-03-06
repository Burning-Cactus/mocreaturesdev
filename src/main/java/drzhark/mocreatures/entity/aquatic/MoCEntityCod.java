package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityCod extends MoCEntityMediumFish{

    public MoCEntityCod(EntityType<? extends MoCEntityCod> type, World world) {
        super(type, world);
        this.setType(2);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("mediumfish_cod.png");
    }
    
    @Override
    protected int getEggNumber() {
        return 71;
    }

}
