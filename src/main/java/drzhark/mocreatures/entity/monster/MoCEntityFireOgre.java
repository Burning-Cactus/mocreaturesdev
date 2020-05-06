package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityFireOgre extends MoCEntityOgre{

    public MoCEntityFireOgre(EntityType<? extends MoCEntityFireOgre> type, World world) {
        super(type, world);
       //Fire immunity needs to be set by the EntityType
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("ogrered.png");
    }
    
    @Override
    public boolean isFireStarter() {
        return true;
    }
    
    @Override
    public float getDestroyForce() {
            return MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.fireOgreStrength.get().floatValue();
    }
    
    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
    
//    @Override TODO: Fire Ogre Loot Table
//    protected Item getDropItem() {
//        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
//        if (!flag) {
//            return Item.getItemFromBlock(Blocks.FIRE);
//        }
//        return MoCItems.HEARTFIRE;
//    }
}
