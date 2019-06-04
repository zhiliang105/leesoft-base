package com.leeframework.common.utils.xml;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leeframework.common.utils.clazz.ClassLoaderHelper;

/**
 * XML辅助工具类
 * @author Lee[lee@leesoft.cn]
 * @datetime 2017年11月25日 下午9:43:56
 */
public abstract class XMLHelper {
    private static Logger log = LoggerFactory.getLogger(XMLHelper.class);

    /**
     * 创建一个dom4j SAXReader
     * @author lee
     * @date 2016年5月15日 下午4:52:49
     * @return
     */
    public static SAXReader createSAXReader() {
        SAXReader saxReader = new SAXReader();
        saxReader.setMergeAdjacentText(true);
        saxReader.setValidation(false);
        return saxReader;
    }

    /**
     * 获取xml文件对应的document
     * @author lee
     * @date 2016年5月15日 下午5:13:08
     * @param file
     * @return dom4j Document
     */
    public static Document getDocument(File file) {
        Document doc;
        try {
            doc = createSAXReader().read(file);
        } catch (DocumentException e) {
            log.error("Failed to parse the xml file {}", file.getAbsolutePath());
            throw new RuntimeException("Failed to create read the file as xml", e);
        }
        return doc;
    }

    /**
     * 获取xml文件对应的document
     * @author lee
     * @date 2016年5月16日 上午9:03:33
     * @param name 文件名称
     * @return dom4j Document
     */
    public static Document getDocuement(String name) {
        Document doc;
        try {
            ClassLoader classLoader = ClassLoaderHelper.getContextClassLoader();
            doc = createSAXReader().read(classLoader.getResourceAsStream(name));
        } catch (DocumentException e) {
            log.error("Failed to parse the xml file {}", name);
            throw new RuntimeException("Failed to create read the file as xml", e);
        }
        return doc;
    }

    /**
     * 创建dom4j DOMReader
     * @author lee
     * @date 2016年5月15日 下午4:53:17
     * @return
     */
    public static DOMReader createDOMReader() {
        DOMReader domReader = new DOMReader();
        return domReader;
    }

    /**
     * 生成dom4j Element
     * @author lee
     * @date 2016年5月15日 下午5:02:02
     * @param elementName 元素名称
     * @return
     */
    public static Element generateDom4jElement(String elementName) {
        return getDocumentFactory().createElement(elementName);
    }

    /**
     * 获取DocumentFactory
     * @author lee
     * @date 2016年5月15日 下午5:02:21
     * @return
     */
    public static DocumentFactory getDocumentFactory() {
        ClassLoader cl = ClassLoaderHelper.getContextClassLoader();
        DocumentFactory factory;
        try {
            Thread.currentThread().setContextClassLoader(XMLHelper.class.getClassLoader());
            factory = DocumentFactory.getInstance();
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
        return factory;
    }

    public static void dump(Element element) {
        try {
            OutputFormat outFormat = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, outFormat);
            writer.write(element);
            writer.flush();
            System.out.println("");
        } catch (Throwable t) {
            log.warn(element.asXML());
        }

    }
}
