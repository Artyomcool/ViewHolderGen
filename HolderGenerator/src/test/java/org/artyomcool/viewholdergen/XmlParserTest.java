package org.artyomcool.viewholdergen;

import org.junit.Assert;
import org.junit.Before;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class XmlParserTest {

    private XmlParser mParser;
    private InputStream mTestXml;
    private List<ViewDescriptor> mTestDescriptors;
    private List<Import> mTestImports;

    @Before
    public void setUp() throws Exception {
        mParser = new XmlParser();
        mTestXml = Class.class.getResourceAsStream("/test_layout.xml");
        mTestDescriptors = Arrays.asList(
                new ViewDescriptor("scroll_view", "ScrollView"),
                new ViewDescriptor("logo", "ImageView"),
                new ViewDescriptor("version", "TextView"),
                new ViewDescriptor("background", "ImageView")
        );
        mTestImports = Arrays.asList(
                new Import("ImageView"),
                new Import("ScrollView"),
                new Import("TextView")
        );
    }

    @org.junit.Test
    public void testParse() throws Exception {
        String layoutName = "test_layout";
        String className = "TestLayout";
        String packageName = "org.artyomcool.test";
        Model model = mParser.parse(mTestXml, "org.artyomcool", packageName, layoutName);

        Assert.assertEquals("Model imports are incorrect", mTestImports, model.getImports());
        Assert.assertEquals("Model descriptors are incorrect", mTestDescriptors, model.getDescriptors());
        Assert.assertEquals("Class name is incorrect", className, model.getClassName());
        Assert.assertEquals("Package name is incorrect", packageName, model.getPackageName());
    }

}
