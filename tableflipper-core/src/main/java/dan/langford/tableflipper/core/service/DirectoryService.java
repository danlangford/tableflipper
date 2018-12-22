package dan.langford.tableflipper.core.service;

import dan.langford.tableflipper.core.model.DirectoryStructure;
import dan.langford.tableflipper.core.model.Table;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.InputStream;
import java.util.Set;

@Singleton
public class DirectoryService {

    private final DirectoryStructure doc;

    @Inject
    public DirectoryService(){
        Yaml dirYml = new Yaml(new Constructor(DirectoryStructure.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("5e-srd-5_1.yml");
        doc = dirYml.load(inputStream);
    }

    public DirectoryStructure getDoc() {
        return doc;
    }

    public Set<String> getTableNames() {
        return doc.getTables().keySet();
    }
    public Table getTable(String name) {
        return doc.getTables().get(name);
    }

    public String getTemplate(String name) {
        return doc.getTemplates().get(name);
    }

    public String getTemplate(String name, String defaultVal) {
        return doc.getTemplates().getOrDefault(name, defaultVal);
    }

    public String getDescription(String name) {
        return doc.getDescriptions().get(name);
    }

    public String getDescription(String name, String defaultVal) {
        return doc.getDescriptions().getOrDefault(name, defaultVal);
    }
}
