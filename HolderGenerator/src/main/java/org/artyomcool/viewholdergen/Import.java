package org.artyomcool.viewholdergen;

public class Import implements Comparable<Import> {

    private String mClassName;

    public Import(String className) {
        mClassName = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Import anImport = (Import) o;

        if (!mClassName.equals(anImport.mClassName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mClassName.hashCode();
    }

    @Override
    public String toString() {
        return "import " + mClassName;
    }

    @Override
    public int compareTo(Import o) {
        return mClassName.compareTo(o.mClassName);
    }

    public String toFullQualified() {
        if (mClassName.contains(".")) {
            return mClassName;
        }
        return "android.widget." + mClassName;
    }
}
