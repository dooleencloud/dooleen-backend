package com.dooleen.service;

import java.io.*;
import java.nio.charset.Charset;

import com.dooleen.service.MyFontsProvider;
import com.itextpdf.text.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dooleen.common.core.common.entity.NullEntity;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

@Service
public class PdfExportService {

	public static void main(String[] args) {
		PdfExportService pdfExportService = new PdfExportService();
		try {
			pdfExportService.sendMailExamPdf();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 输出并发邮件
	public void sendMailExamPdf() throws Exception {
		byte[] bytes = genExamPdf();
		FileOutputStream fos = new FileOutputStream("/Users/liqiuhong/Desktop/test002.pdf");
		fos.write(bytes);
		fos.close();
	}

	// 输出PDF流
	public byte[] genExamPdf() throws Exception {
		String html = "/Users/liqiuhong/Desktop/test002.html";
		// 转换成pdf流
		byte[] bytes = convertToPDF(html);
		return bytes;
	}

	/**
	 * @description PDF文件生成
	 */
	private static byte[] convertToPDF(String htmlString) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream inputStream = new FileInputStream(htmlString); // 文件里面写Hello World!, 文件可以保存任意地方

		// 设置文档大小
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, out);

		// 输出为PDF文件
		document.open();
		MyFontsProvider fontProvider = new MyFontsProvider();
		fontProvider.addFontSubstitute("lowagie", "garamond");
		fontProvider.setUseUnicode(true);

		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		worker.parseXHtml(writer, document,inputStream , Charset.forName("UTF-8"), fontProvider);

		document.close();
		return out.toByteArray();
	}


	public byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;

	}

}
