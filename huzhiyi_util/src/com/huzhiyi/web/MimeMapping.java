package com.huzhiyi.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 
 * @ClassName: MimeMapping
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class MimeMapping {
	private File file;
	protected Map<String, String> mimeMap;

	public MimeMapping(File file) {
		this.file = file;
	}

	public String findMimeType(String extension) throws IOException, JDOMException {
		return findMimeType(extension, false);
	}

	public String findMimeType(String extension, boolean isReloadXml) throws IOException, JDOMException {
		if (mimeMap == null || isReloadXml) {
			mimeMap = this.load(file);
		}
		return mimeMap.get(extension);
	}

	protected Map<String, String> load(File file) throws IOException, JDOMException {
		return this.load(new FileInputStream(file));
	}

	protected Map<String, String> load(InputStream in) throws JDOMException, IOException {
		Map<String, String> mimeMap = new HashMap<String, String>();

		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(in);
		Element node = doc.getRootElement();
		putElement(node, mimeMap);

		return mimeMap;
	}

	protected void addMime(Element node, Map<String, String> mimeMap) {
		String extension = node.getChildText("extension");
		String mime_type = node.getChildText("mime-type");
		if (extension != null && mime_type != null) {
			mimeMap.put(extension, mime_type);
		}
	}

	protected void putElement(Element node, Map<String, String> mimeMap) {
		List<Element> elementList = node.getChildren();
		for (Element e : elementList) {
			addMime(e, mimeMap);
			putElement(e, mimeMap);
		}
	}
}