package org.artyomcool.viewholdergen;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public class ModelWriter {

    private static Configuration configuration = new Configuration();
    static {
        configuration.setTemplateLoader(new ClassTemplateLoader(ModelWriter.class, "/"));
    }

    private Model model;

    public ModelWriter(Model model) {
        this.model = model;
    }

    public void write(Writer writer) throws IOException {
        Template template = configuration.getTemplate("Holder.template");
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new RuntimeException("Problem while parsing template", e);
        }
    }

}
