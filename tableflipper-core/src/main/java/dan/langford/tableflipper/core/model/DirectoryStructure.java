package dan.langford.tableflipper.core.model;

import java.util.Map;

public class DirectoryStructure {
    private Map<String, Table> tables;
    private Map<String, String> templates;
    private Map<String, String> descriptions;
    private String allEncounterTest;

    public DirectoryStructure() {
    }

    public Map<String, Table> getTables() {
        return this.tables;
    }

    public Map<String, String> getTemplates() {
        return this.templates;
    }

    public Map<String, String> getDescriptions() {
        return this.descriptions;
    }

    public String getAllEncounterTest() {
        return this.allEncounterTest;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public void setAllEncounterTest(String allEncounterTest) {
        this.allEncounterTest = allEncounterTest;
    }

    public String toString() {
        return "DirectoryStructure(tables=" + this.tables + ", templates=" + this.templates + ", descriptions=" + this.descriptions + ", allEncounterTest=" + this.allEncounterTest + ")";
    }
}
