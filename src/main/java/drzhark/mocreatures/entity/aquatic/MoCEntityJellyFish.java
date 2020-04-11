package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityJellyFish extends MoCEntityTameableAquatic {

    private int poisoncounter;
    private static final DataParameter<Boolean> GLOWS = EntityDataManager.<Boolean>createKey(MoCEntityJellyFish.class, DataSerializers.BOOLEAN);
    
    public MoCEntityJellyFish(EntityType<? extends MoCEntityJellyFish> type, World world) {
        super(type, world);
        setEdad(50 + (this.rand.nextInt(50)));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 0.5D, 120));
    }
    
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GLOWS, Boolean.valueOf(false));
    }

    public void setGlowing(boolean flag) {
        this.dataManager.set(GLOWS, Boolean.valueOf(flag));
    }

    public boolean isGlowing() {
        return (((Boolean)this.dataManager.get(GLOWS)).booleanValue());
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.02F;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("jellyfisha.png");
            case 2:
                return MoCreatures.getTexture("jellyfishb.png");
            case 3:
                return MoCreatures.getTexture("jellyfishc.png");
            case 4:
                return MoCreatures.getTexture("jellyfishd.png");
            case 5:
                return MoCreatures.getTexture("jellyfishe.png");
            case 6:
                return MoCreatures.getTexture("jellyfishf.png");
            case 7:
                return MoCreatures.getTexture("jellyfishg.png");
            case 8:
                return MoCreatures.getTexture("jellyfishh.png");
            case 9:
                return MoCreatures.getTexture("jellyfishi.png");
            case 10:
                return MoCreatures.getTexture("jellyfishj.png");
            case 11:
                return MoCreatures.getTexture("jellyfishk.png");
            case 12:
                return MoCreatures.getTexture("jellyfishl.png");

            default:
                return MoCreatures.getTexture("jellyfisha.png");
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {

            if (this.rand.nextInt(200) == 0) {
                setGlowing(!this.world.isDaytime());
            }

            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.shouldAttackPlayers()) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    this.poisoncounter = 0;
                }
            }
        }
    }

    @Override
    protected Item getDropItem() {
        boolean flag = this.rand.nextInt(2) == 0;
        if (flag) {
            return Items.SLIME_BALL;
        }
        return null;
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        int yOff = (int) (getEdad() * -1 / 2.3);
        return yOff;
    }

    @Override
    public float getSizeFactor() {
        float myMoveSpeed = MoCTools.getMyMovementSpeed(this);
        float pulseSpeed = 0.08F;
        if (myMoveSpeed > 0F)
            pulseSpeed = 0.5F;
        float pulseSize = MathHelper.cos(this.ticksExisted * pulseSpeed) * 0.2F;
        return getEdad() * 0.01F + (pulseSize / 5);
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int getMaxEdad() {
        return 100;
    }
}
