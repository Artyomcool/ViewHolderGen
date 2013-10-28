package org.artyomcool.viewholdergen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {

    private static final List<Import> STANDART_IMPORTS = Arrays.asList(
            new Import("android.view.LayoutInflater"),
            new Import("android.view.View"),
            new Import("android.view.ViewGroup")
    );

    private final String packageName;
    private final String className;
    private final List<ViewDescriptor> descriptors;
    private final List<Import> imports;
    private final String layoutId;

    public Model(String packageName, String className, String layoutId,
                 List<ViewDescriptor> descriptors, List<Import> imports) {
        this.packageName = packageName;
        this.className = className;
        this.layoutId = layoutId;
        this.descriptors = descriptors;
        this.imports = imports;
    }

    Model(Map<String, String> idToClass, String basePackage, String packageName, String className, String layoutId) {
        this.packageName = packageName;
        this.className = className;
        this.layoutId = layoutId;

        List<ViewDescriptor> descriptors = new ArrayList<>();
        Set<Import> imports = new HashSet<>(STANDART_IMPORTS);
        imports.add(new Import(basePackage + ".R"));

        for (Map.Entry<String, String> entry : idToClass.entrySet()) {
            descriptors.add(new ViewDescriptor(entry.getKey(), entry.getValue()));
            imports.add(new Import(entry.getValue()));
        }

        this.descriptors = Collections.unmodifiableList(descriptors);

        List<Import> importList = new ArrayList<>(imports);
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

    public String getLayoutId() {
        return layoutId;
    }

    @Override
    public String toString() {
        return "Model{" +
                "descriptors=" + descriptors +
                ", imports=" + imports +
                '}';
    }
}
