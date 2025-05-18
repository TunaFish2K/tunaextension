package tunafish2k.tunaextension.puppet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import org.lwjgl.Sys;
import tunafish2k.tunaextension.persistent.IPersistentData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static tunafish2k.tunaextension.persistent.PersistentDatas.prepareFile;

public class PuppetPersistentData implements IPersistentData {
    public static final File configFile = new File(Minecraft.getMinecraft().mcDataDir, "/config/tunaextension/puppet.json");

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
        JSONArray result = new JSONArray();
        this.puppets.forEach((key, value) -> {
            result.add(value.serialize());
        });
        return result.toString();
    }

    public void deSerialize(String data) {
        try {
            System.out.println(data);
            JSONArray obj = (JSONArray) JSON.parseArray(data);
            HashMap<String, Puppet> newData = new HashMap<>();
            obj.forEach(puppetData -> {
                Puppet puppet = Puppet.deSerialize((JSONObject) puppetData);
                newData.put(puppet.key, puppet);
            });
            this.puppets = newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Puppet> puppets = new HashMap<>();

    public void addPuppet(String key, String host, Integer port) throws CommandException {
        puppets.put(key, new Puppet(key, host, port));
    }

    public void removePuppet(String key) throws CommandException {
        if (puppets.get(key) == null) {
            throw new CommandException(String.format("puppet %s doesn't exists!", key));
        }
        puppets.remove(key);
    }
}
