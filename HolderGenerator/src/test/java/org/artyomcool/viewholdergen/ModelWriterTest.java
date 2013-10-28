package org.artyomcool.viewholdergen;

import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class ModelWriterTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        List<ViewDescriptor> testDescriptors = Arrays.asList(
                new ViewDescriptor("scroll_view", "ScrollView"),
                new ViewDescriptor("logo", "ImageView"),
                new ViewDescriptor("version", "TextView"),
                new ViewDescriptor("background", "ImageView")
        );
        List<Import> testImports = Arrays.asList(
                new Import("ImageView"),
                new Import("ScrollView"),
                new Import("TextView")
        );
        model = new Model("org.artyomcool.test", "TestLayout", "test_layout", testDescriptors, testImports);
    }

    @Test
    public void testWriter() throws Exception {
        new ModelWriter(model).write(new StringWriter());
    }
}
