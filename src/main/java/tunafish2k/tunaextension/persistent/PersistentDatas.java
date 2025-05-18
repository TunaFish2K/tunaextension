package tunafish2k.tunaextension.persistent;

import tunafish2k.tunaextension.callback.CallbackPersistentData;
import tunafish2k.tunaextension.puppet.PuppetPersistentData;

import java.io.File;
import java.io.IOException;



public class PersistentDatas {
    public static PuppetPersistentData puppet = new PuppetPersistentData();
    public static CallbackPersistentData callback = new CallbackPersistentData();

    public static void save() {
        puppet.save();
        callback.save();
    };

    public static void load() {
        puppet.load();
        callback.load();
    };

    public static void prepareFile(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
