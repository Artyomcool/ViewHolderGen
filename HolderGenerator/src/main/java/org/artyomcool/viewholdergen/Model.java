package org.artyomcool.viewholdergen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {

    private final String packageName;
    private final String className;
    private final List<ViewDescriptor> descriptors;
    private final List<Import> imports;

    public Model(String packageName, String className, List<ViewDescriptor> descriptors, List<Import> imports) {
        this.packageName = packageName;
        this.className = className;
        this.descriptors = descriptors;
        this.imports = imports;
    }

    Model(Map<String, String> idToClass, String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
        List<ViewDescriptor> descriptors = new ArrayList<ViewDescriptor>();
        Set<Import> imports = new HashSet<Import>();

        for (Map.Entry<String, String> entry : idToClass.entrySet()) {
            descriptors.add(new ViewDescriptor(entry.getKey(), entry.getValue()));
            imports.add(new Import(entry.getValue()));
        }

        this.descriptors = Collections.unmodifiableList(descriptors);

        List<Import> importList = new ArrayList<Import>(imports);
        Collections.sort(importList);
        this.imports = Collections.unmodifiableList(importList);
    }

    public List<ViewDescriptor> getDescriptors() {
        return descriptors;
    }

    public List<Import> getImports() {
        return imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "Model{" +
                "descriptors=" + descriptors +
                ", imports=" + imports +
                '}';
    }
}
