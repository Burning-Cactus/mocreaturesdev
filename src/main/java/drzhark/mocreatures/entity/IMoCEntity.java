package drzhark.mocreatures.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;

import java.util.UUID;

public interface IMoCEntity {

    void selectType();

    String getPetName();

    void setPetName(String name);

    int getOwnerPetId();

    void setOwnerPetId(int petId);

    UUID getOwnerId();

    boolean getIsTamed();

    boolean getIsAdult();

    boolean getIsGhost();

    void setAdult(boolean flag);

    boolean checkSpawningBiome();

    boolean canSpawn(IWorld worldIn, SpawnReason reason);

    void performAnimation(int i);

    boolean renderName();

    int nameYOffset();

    void makeEntityJump();

    void makeEntityDive();

    float getSizeFactor();

    float getAdjustedYOffset();

    void setArmorType(int i);

    EntityType getType();

    int getSubType();

    void setType(int i);

    float rollRotationOffset();

    float pitchRotationOffset();

    void setEdad(int i);

    int getEdad();

    float yawRotationOffset();

    float getAdjustedZOffset();

    float getAdjustedXOffset();

    ResourceLocation getTexture();

    boolean canAttackTarget(LivingEntity entity);

    boolean getIsSitting(); // is the entity sitting, for animations and AI

    boolean isNotScared(); //relentless creature that attacks others used for AI

    boolean isMovementCeased(); //to deactivate path / wander behavior AI

    boolean shouldAttackPlayers();

    double getDivingDepth();

    boolean isDiving();

    void forceEntityJump();

    int maxFlyingHeight();

    int minFlyingHeight();

    boolean isFlyer();

    boolean getIsFlying();

//    String getClazzString();
}
