import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;

class AdapterTest {

    private Gson gson;

    // Define SimpleItem within the test file for localized use
    public static class SimpleItem {
        private int id;
        private String name;

        // Constructor for SimpleItem
        public SimpleItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getter for id
        public int getId() {
            return id;
        }

        // Getter for name
        public String getName() {
            return name;
        }
    }

    @BeforeEach
    void setUp() {
        // Register the SimpleItem class with the Adapter in GsonBuilder
        gson = new GsonBuilder()
                .registerTypeAdapter(SimpleItem.class, new Adapter<SimpleItem>())
                .create();
    }


    @Test
    void testErrorHandling() {
        String json = "{\"CLASSNAME\":\"dk.dtu.compute.se.pisd.roborally.model.NonExistentClass\",\"INSTANCE\":{}}";
        assertThrows(JsonParseException.class, () -> {
            gson.fromJson(json, SimpleItem.class);
        }, "Deserialization should throw JsonParseException when class is not found.");
    }
}