package tree;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by yannis on 26/03/17.
 */
public class TreeDeserializer implements JsonDeserializer<Tree> {
    public Tree deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Tree tree = new Tree();
        JsonObject mainObject = json.getAsJsonObject();


        Set<Map.Entry<String, JsonElement>> nodes = mainObject.get("nodes").getAsJsonObject().entrySet();

        Iterator ite = nodes.iterator();
        if(ite.hasNext()) {
            Map.Entry<String, JsonElement> elem = (Map.Entry<String, JsonElement>) ite.next();
            if (elem.getValue().getAsJsonObject().has("nodes")) {
                tree = new Tree<Tree>();
            } else {
                tree = new Tree<Organism>();
            }
        }

        for (Map.Entry<String, JsonElement> entry: nodes) {
            if(entry.getValue().getAsJsonObject().has("nodes")) {
                tree.add(entry.getKey(), context.deserialize(entry.getValue(), Tree.class));
            } else {
                tree.add(entry.getKey(), context.deserialize(entry.getValue(), Organism.class));
            }

        }

        return tree;
    }
}