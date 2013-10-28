package org.artyomcool.viewholdergen;

public class ViewDescriptor {

    private String id;
    private String fieldName;
    private String fieldClass;

    public ViewDescriptor(String id, String className) {
        this.id = id;
        fieldClass = className;
        fieldName = fieldNameFromId(id, true);
    }

    public String getFieldClass() {
        return fieldClass.substring(fieldClass.lastIndexOf('.') + 1);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewDescriptor that = (ViewDescriptor) o;

        if (!fieldClass.equals(that.fieldClass)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + fieldClass.hashCode();
        return result;
    }

    public static String fieldNameFromId(String id, boolean prefix) {
        String[] parts = id.split("\\_");
        StringBuilder result = new StringBuilder(prefix ? "m" : "");
        for(String part : parts) {
            result.append(camelCase(part));
        }
        return result.toString();
    }

    private static char[] camelCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return chars;
    }

    @Override
    public String toString() {
        return "ViewDescriptor{" +
                "id='" + id + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldClass='" + fieldClass + '\'' +
                '}';
    }


}
