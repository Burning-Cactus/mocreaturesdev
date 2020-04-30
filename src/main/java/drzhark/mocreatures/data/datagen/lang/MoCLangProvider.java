package drzhark.mocreatures.data.datagen.lang;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class MoCLangProvider extends LanguageProvider {
    public MoCLangProvider(DataGenerator gen, String locale) {
        super(gen, MoCConstants.MOD_ID, locale);
    }

    @Override
    protected abstract void addTranslations();
}
