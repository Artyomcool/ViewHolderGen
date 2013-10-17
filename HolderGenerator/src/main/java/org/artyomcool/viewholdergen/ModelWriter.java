package org.artyomcool.viewholdergen;

import java.io.PrintWriter;

public class ModelWriter {

    private final PrintWriter mWriter;
    private String mIndent = "";

    public ModelWriter(PrintWriter writer) {
        mWriter = writer;
    }

    public void write(Model model, String packageName, String layoutId) {
        String className = ViewDescriptor.fieldNameFromId(layoutId);
        if (!packageName.equals("")) {
            write("package " + packageName + ";");
            blank();
        }

        for (Import im : model.getImports()) {
            write("import " + im.toFullQualified() + ";");
        }
        blank();

        writeIndent("public class " + className +" {");
        blank();

        write("//--- GENERATED FIELDS - DO NOT MODIFY");

        doc("Original view");
        write("private View mView;");

        for (ViewDescriptor descriptor : model.getDescriptors()) {
            doc("Field for R.id." + descriptor.getId());
            write("private " + descriptor.getFieldClass() + " " + descriptor.getFieldName() + ";");
        }
        write("//--- END OF GENERATED FIELDS");
        blank();

        write("//--- GENERATED METHODS - DO NOT MODIFY");
        blank();

        writeIndent("protected void init(View view) {");
        write("mView = view;");
        for (ViewDescriptor descriptor : model.getDescriptors()) {
            write(descriptor.getFieldName() + " = view.findViewById(R.id." + descriptor.getId() +");");
            write("afterInit();");
        }
        writeUnindent("}");
        blank();

        doc("Returns original view associated with this holder");
        writeIndent("public View getView() {");
        write("return mView;");
        writeUnindent("}");
        blank();

        doc("Creates holder from {@link LayoutInflater} with parent (can be null)");
        writeIndent("public static " + className + " create(LayoutInflater inflater, ViewParent parent) {");
        write("View view = inflater.inflate(R.layout." + layoutId+", parent, false);");
        write("return create(view);");
        writeUnindent("}");
        blank();

        doc("Creates holder from view");
        writeIndent("public static " + className + " create(View view) {");
        write(className + " holder = new " + className + "();");
        write("holder.init(view);");
        writeUnindent("}");
        blank();

        doc("Convention way to get holder from adapters.",
            "Tries to retrieve holder associated with oldView.",
            "If view is null, new view will be created with passed parent and associated with new holder.",
            "If tag is null, new holder will be created and associated with view.");
        writeIndent("public static " + className + " get(View oldView, ViewParent parent) {"); //TODO use some template
        writeIndent("if (oldView == null) {");
        writeIndent("if (parent == null) {");
        write("throw new NullPointerException(\"Parent can not be null\");");
        writeUnindent("}");
        write("LayoutInflater inflater = LayoutInflater.from(parent.getContext());");
        write("return create(inflater, parent);");
        writeUnindent("}");
        write("Object holder = oldView.getTag();");
        writeIndent("if (holder == null) {");
        write("return create(oldView);");
        writeUnindent("}");
        writeIndent("try {");
        write("return ("+className+")holder;");
        writeUnindentAndIntent("} catch(ClassCastException ex) {");
        write("throw new IllegalArgumentException(\"View has tag that is not UserItemHolder\", ex);");
        writeUnindent("}");
        writeUnindent("}");
        blank();

        write("//-- END OF GENERATED METHODS");
        blank();

        doc("Creates empty " + className+".",
            "It will be invalid until {@link #init(View)} will be called.");
        writeIndent("protected " + className +"() {");
        writeUnindent("}");
        blank();

        doc("Will be called at the end of {@link #init(View)}");
        writeIndent("protected void afterInit(View view) {");
        writeUnindent("}");
        blank();

        writeUnindent("}");

        mWriter.flush();
    }

    private void write(String line) {
        mWriter.print(mIndent);
        mWriter.println(line);
    }

    private void writeIndent(String line) {
        mWriter.print(mIndent);
        mWriter.println(line);
        mIndent += "\t";
    }

    private void writeUnindent(String line) {
        mIndent = mIndent.substring(1);
        mWriter.print(mIndent);
        mWriter.println(line);
    }

    private void writeUnindentAndIntent(String line) {
        mIndent = mIndent.substring(1);
        mWriter.print(mIndent);
        mWriter.println(line);
        mIndent += "\t";
    }

    private void doc(String... lines) {
        write("/**");
        for (String line : lines) {
            write("* " + line);
        }
        write("*/");
    }

    private void blank() {
        mWriter.println();
    }
}
