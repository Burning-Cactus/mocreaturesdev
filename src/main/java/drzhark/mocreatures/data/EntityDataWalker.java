package drzhark.mocreatures.data;

import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.IDataWalker;

public class EntityDataWalker implements IDataWalker
{
    @Override
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