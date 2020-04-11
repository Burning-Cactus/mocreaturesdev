package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityMantaRay extends MoCEntityRay {

    public MoCEntityMantaRay(EntityType<? extends MoCEntityMantaRay> type, World world) {
        super(type, world);
        setEdad(80 + (this.rand.nextInt(100)));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
    }

    @Override
    public int getMaxEdad() {
        return 180;
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("mantray.png");
    }

    @Override
    public boolean isMantaRay() {
        return true;
    }
}
