package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MoCEntityLeopard extends MoCEntityBigCat {

    public MoCEntityLeopard(EntityType<? extends MoCEntityLeopard> type, World world) {
        super(type, world);
    }

    @Override
    public void selectType() {

        if (getSubType() == 0) {
            checkSpawningBiome();
        }
        super.selectType();
    }

    /*@Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(2); //snow leopard
                return true;
            }
        } catch (Exception e) {
        }
        setType(1);
        return true;
    }*/

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bcleopard.png");
            case 2:
                return MoCreatures.getTexture("bcsnowleopard.png");
            default:
                return MoCreatures.getTexture("bcleopard.png");
        }
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isCrouching()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return "Pantard";//"Panther";
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1) {
            return "Leoger";//"Tiger";
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return "Liard";//"Lion";
        }
        return "Leopard";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1) {
            return 1;//4; //leoger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return 1;//4; //liard
        }
        return this.getSubType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == this.getSubType())
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2);
    }

    @Override
    public float calculateMaxHealth() {
        return 25F;
    }

    @Override
    public double calculateAttackDmg() {
        return 5D;
    }

    @Override
    public double getAttackRange() {
        return 6D;
    }

    @Override
    public int getMaxEdad() {
        return 95;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLeopard) {
            return false;
        }
        return entity.getHeight() < 1.3F && entity.getWidth() < 1.3F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
