package tree;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by yannis on 26/03/17.
 */
public class TreeSerializer implements JsonSerializer<Tree> {
    public JsonElement serialize(Tree src, Type typeOfSrc, JsonSerializationContext context) {

        final JsonObject jsonMain = new JsonObject();
        final JsonObject jsonNodes = new JsonObject();
        jsonMain.add("nodes", jsonNodes);

        Object[] nodes = src.nodes();
        for (Object node : nodes) {

            if (src.get((String) node) != null && (src.get((String) node)) instanceof Tree) {
                jsonNodes.add((String) node, context.serialize(src.get((String) node), Tree.class));
            } else {
                jsonNodes.add((String) node, context.serialize(src.get((String) node), Organism.class));
            }
        }

        return jsonMain;
    }
}
