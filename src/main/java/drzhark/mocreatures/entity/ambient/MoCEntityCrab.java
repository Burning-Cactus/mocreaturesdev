package drzhark.mocreatures.entity.ambient;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAmbient;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntityCrab extends MoCEntityTameableAmbient

{

    public MoCEntityCrab(EntityType<? extends MoCEntityCrab> type, World world) {
        super(type, world);
        setEdad(50 + this.rand.nextInt(50));
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(1, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {
            public boolean apply(Entity entity) {
                return !(entity instanceof MoCEntityCrab) && (entity.getHeight() > 0.3F || entity.getWidth() > 0.3F);
            }
        }, 6.0F, 0.6D, 1D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("craba.png");
            case 2:
                return MoCreatures.getTexture("crabb.png");
            case 3:
                return MoCreatures.getTexture("crabc.png");
            case 4:
                return MoCreatures.getTexture("crabd.png");
            case 5:
                return MoCreatures.getTexture("crabe.png");

            default:
                return MoCreatures.getTexture("craba.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

//    @Override TODO: Crab loot table
//    protected Item getDropItem() {
//        return MoCItems.CRABRAW;
//    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getSizeFactor() {
        return 0.7F * getEdad() * 0.01F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFleeing() {
        return MoCTools.getMyMovementSpeed(this) > 0.09F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -20;
    }

    @Override
    public boolean isNotScared() {
        return this.getIsTamed();
    }
}
