package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.fixes.JukeboxRecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemRecord extends MusicDiscItem {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation("mocreatures", "shuffling");

    public MoCItemRecord(Item.Properties builder, SoundEvent soundEvent) {
        super(0, soundEvent, builder);
    }

    @OnlyIn(Dist.CLIENT)
    /**
     * Return the title for this record.
     */
    public String getRecordTitle() {
        return "MoC - " + this.getRecordNameLocal();
    }
}
