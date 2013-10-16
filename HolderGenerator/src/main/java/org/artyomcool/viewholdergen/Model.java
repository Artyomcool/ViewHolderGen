package org.artyomcool.viewholdergen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {

    private List<ViewDescriptor> mDescriptors;
    private List<Import> mImports;

    Model(Map<String, String> idToClass) {
        if (mDescriptors != null || mImports != null) {
            throw new IllegalStateException("Model has been already prepared");
        }

        List<ViewDescriptor> descriptors = new ArrayList<ViewDescriptor>();
        Set<Import> imports = new HashSet<Import>();

        for (Map.Entry<String, String> entry : idToClass.entrySet()) {
            descriptors.add(new ViewDescriptor(entry.getKey(), entry.getValue()));
            imports.add(new Import(entry.getValue()));
        }

        mDescriptors = Collections.unmodifiableList(descriptors);

        List<Import> importList = new ArrayList<Import>(imports);
        Collections.sort(importList);
        mImports = Collections.unmodifiableList(importList);
    }

    public List<ViewDescriptor> getDescriptors() {
        return mDescriptors;
    }

    public List<Import> getImports() {
        return mImports;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mDescriptors=" + mDescriptors +
                ", mImports=" + mImports +
                '}';
    }
}
