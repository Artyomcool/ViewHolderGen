package org.artyomcool.viewholdergen;

public class Import implements Comparable<Import> {

    private String className;

    public Import(String className) {
        this.className = toFullQualified(className);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Import anImport = (Import) o;

        if (!className.equals(anImport.className)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

    @Override
    public String toString() {
        return "import " + className;
    }

    @Override
    public int compareTo(Import o) {
        return className.compareTo(o.className);
    }

    private static String toFullQualified(String className) {
        if (className.contains(".")) {
            return className;
        }
        return "android.widget." + className;
    }
}
