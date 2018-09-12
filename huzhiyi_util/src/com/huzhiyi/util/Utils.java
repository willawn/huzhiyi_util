package com.huzhiyi.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 
 * @ClassName: Utils
 * @Description: TODO(������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class Utils {

	/**
	 * XML������
	 */
	private static XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

	/**
	 * Jdom��XML�ļ����
	 * 
	 * @param doc
	 * @param out
	 * @throws IOException
	 */
	public static void jdomDocOutput(Document doc, OutputStream out) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(out, Charset.forName("utf-8"));
		outputter.output(doc, osw);
	}

	/**
	 * ����Jdom��SaxBuilder
	 * 
	 * @param validate
	 * @return
	 */
	public static SAXBuilder createJdomSaxBuilder(boolean validate) {
		SAXBuilder builder = new SAXBuilder();
		if (!validate) {
			builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		}
		return builder;
	}
}