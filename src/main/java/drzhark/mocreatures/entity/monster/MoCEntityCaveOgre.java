package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityCaveOgre extends MoCEntityOgre{

    public MoCEntityCaveOgre(EntityType<? extends MoCEntityCaveOgre> type, World world) {
        super(type, world);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("ogreblue.png");
    }
    
    /**
     * Returns the strength of the blasting power
     * @return
     */
    @Override
    public float getDestroyForce() {
            return MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.caveOgreStrength.get().floatValue();
    }
    
    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return (!this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper
                .floor(this.getPosZ())))) && (this.getPosY() < 50D) && super.getCanSpawnHere();
    }
    
    @Override
    public float calculateMaxHealth() {
        return 50F;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.DIAMOND;
    } 
    
}