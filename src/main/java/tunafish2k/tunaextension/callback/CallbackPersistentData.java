package tunafish2k.tunaextension.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.Minecraft;
import scala.Int;
import tunafish2k.tunaextension.persistent.IPersistentData;
import tunafish2k.tunaextension.puppet.Puppet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static tunafish2k.tunaextension.persistent.PersistentDatas.prepareFile;

public class CallbackPersistentData implements IPersistentData {

    public static final File configFile = new File(Minecraft.getMinecraft().mcDataDir, "/config/tunaextension/callback.json");
    public static Integer port = 8083;

    @Override
    public void save() {
        try {
            prepareFile(configFile);
            FileWriter writer = new FileWriter(configFile);
            writer.write(this.serialize());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        if (!configFile.exists()) {
            try {
                System.err.println(String.format("%s not found, creating...", configFile.toString()));
                prepareFile(configFile);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.deSerialize(new String(Files.readAllBytes(configFile.toPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String serialize() {
        JSONObject result = new JSONObject();
        result.put("port", port);
        return result.toString();
    }

    public void deSerialize(String data) {
            JSONObject obj = (JSONObject) JSON.parseObject(data);
            this.port = (Integer)obj.get("port");
    }
}
