package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.FollowHerdGoal;
import drzhark.mocreatures.entity.ai.MoCNearestAttackableTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPiranha extends MoCEntitySmallFish {

    public static final String fishNames[] = {"Piranha"};

    public MoCEntityPiranha(EntityType<? extends MoCEntityPiranha> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new FollowHerdGoal(this, 0.6D, 4D, 20D, 1));
        this.targetSelector.addGoal(1, new MoCNearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntitySmallFish.registerAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_piranha.png");
    }

    /* protected Entity findPlayerToAttack() {
         if ((this.world.getDifficulty().getDifficultyId() > 0)) {
             EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 12D);
             if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) {
                 return entityplayer;
             }
         }
         return null;
     }*/

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i) && (this.level.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof LivingEntity) {
                if (this.hasIndirectPassenger(entity)) {
                    return true;
                }
                if (entity != this) {
                    this.setTarget((LivingEntity) entity);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

//    @Override //TODO: Piranha loot table
//    protected void dropFewItems(boolean flag, int x) {
//        int i = this.rand.nextInt(100);
//        if (i < 70) {
//            entityDropItem(new ItemStack(Items.COD, 1, 0), 0.0F);
//        } else {
//            int j = this.rand.nextInt(2);
//            for (int k = 0; k < j; k++) {
//                entityDropItem(new ItemStack(MoCItems.MOCEGG, 1, 90), 0.0F);
//            }
//        }
//    }
}
