package drzhark.mocreatures.data;

import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundNBT;

public class EntityDataWalker //implements IDataWalker
{
    //TODO: This might need to be changed to a datafixer.
    //@Override
    public CompoundNBT process(DataFixer fixer, CompoundNBT compound, int version) {
        final String entityId = compound.getString("id");
        if (entityId.contains("mocreatures.")) {
            String entityName = entityId.replace("mocreatures.", "").toLowerCase();
            if (entityName.equalsIgnoreCase("polarbear")) {
                entityName = "wildpolarbear";
            }
            final String fixedEntityId = "mocreatures:" + entityName;
            compound.putString("id", fixedEntityId);
        }
        return compound;
    }
}