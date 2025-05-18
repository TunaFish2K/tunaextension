package tunafish2k.tunaextension.puppet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class Puppet {
    @JSONField(name = "key")
    public String key;
    @JSONField(name = "host")
    public String host;
    @JSONField(name = "port")
    public Integer port;

    public Puppet(String key, String host, Integer port) {
        this.key = key;
        this.host = host;
        this.port = port;
    }

    public JSONObject serialize() {
        return (JSONObject) JSON.toJSON(this);
    }

    public static Puppet deSerialize (JSONObject obj) {
        return obj.toJavaObject(Puppet.class);
    }
}
