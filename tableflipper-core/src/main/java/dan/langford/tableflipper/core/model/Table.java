package dan.langford.tableflipper.core.model;

import java.util.Map;

public class Table {
    String roll, name;
    Map<String,String> results;

    public Table() {
    }

    public String getRoll() {
        return this.roll;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getResults() {
        return this.results;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    public String toString() {
        return "Table(roll=" + this.roll + ", name=" + this.name + ", results=" + this.results + ")";
    }
}
