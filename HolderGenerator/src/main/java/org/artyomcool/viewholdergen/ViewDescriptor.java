package org.artyomcool.viewholdergen;

public class ViewDescriptor {

    private String mId;
    private String mFieldName;
    private String mFieldClass;

    public ViewDescriptor(String id, String className) {
        mId = id;
        mFieldClass = className;
        mFieldName = fieldNameFromId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewDescriptor that = (ViewDescriptor) o;

        if (!mFieldClass.equals(that.mFieldClass)) return false;
        if (!mId.equals(that.mId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mFieldClass.hashCode();
        return result;
    }

    private static String fieldNameFromId(String id) {
        String[] parts = id.split("\\_");
        StringBuilder result = new StringBuilder("m");
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
                "mId='" + mId + '\'' +
                ", mFieldName='" + mFieldName + '\'' +
                ", mFieldClass='" + mFieldClass + '\'' +
                '}';
    }
}
