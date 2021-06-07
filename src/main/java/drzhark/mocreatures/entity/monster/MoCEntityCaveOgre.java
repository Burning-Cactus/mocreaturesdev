package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
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
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        return (!this.level.canSeeSkyFromBelowWater(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY()), MathHelper
                .floor(this.getZ())))) && (this.getY() < 50D) && super.checkSpawnRules(worldIn, reason);
    }
    
    @Override
    public float calculateMaxHealth() {
        return 50F;
    }
    
}