package com.cat.file.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.aspose.words.Document;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.HorizontalAlignment;
import com.aspose.words.License;
import com.aspose.words.Paragraph;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.aspose.words.VerticalAlignment;
import com.aspose.words.WrapType;
import com.cat.boot.util.StringUtil;
import com.cat.file.utils.FileUtils;

public class AsposeUtil {

	static {
		try {
			InputStream is = AsposeUtil.class.getClassLoader().getResourceAsStream("aspose.license.xml");
			License aposeLic = new License();
			aposeLic.setLicense(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File wordToPdf(String srcPath, String outPath) throws IOException {
		File file = new File(outPath);
		FileOutputStream os = null;
		try {
			FileUtils.createNewFile(file);
			os = new FileOutputStream(file);
			Document doc = new Document(srcPath);
			insertWatermarkText(doc, "水印");
			doc.save(os, SaveFormat.PDF);

			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
		return null;
	}

	public static File wordToPdf(String srcPath, String outPath, String watermark) throws IOException {
		File file = new File(outPath);
		FileOutputStream os = null;
		try {
			FileUtils.createNewFile(file);
			os = new FileOutputStream(file);
			Document doc = new Document(srcPath);
			if (!StringUtil.isEmpty(watermark)) {
				insertWatermarkText(doc, watermark);
			}
			doc.save(os, SaveFormat.PDF);

			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static File execute(Map<String, Map<String, Object>> map, String templatePath, String tmpName,
			String fileUrl) throws Exception {
		// 1 读取模板
		Document doc = new Document(templatePath + "\\" + tmpName + ".doc");
		if (map.containsKey("dataMap")) {
			Map<String, Object> data = map.get("dataMap");
			// 填充单个数据源
			if (data != null && !data.isEmpty()) {
				String[] keys = (String[]) data.keySet().toArray(new String[data.keySet().size()]);
				Object[] values = data.values().toArray();
				doc.getMailMerge().execute(keys, values);
			}
		}
		if (map.containsKey("dataList")) {
			Map<String, Object> dataList = map.get("dataList");
			// 填充list数据源
			if (dataList != null && !dataList.isEmpty()) {
				for (String tmp : dataList.keySet()) {
					doc.getMailMerge().executeWithRegions(
							new MailMergeDataSource((List<Map<String, Object>>) dataList.get(tmp), tmp));
				}
			}
		}
		// 2 填充数据源
		// doc.getMailMerge().execute();
		File file = new File(fileUrl + ".doc");
		FileOutputStream os = null;
		try {
			FileUtils.createNewFile(file);
			os = new FileOutputStream(file);
			doc.save(os, SaveFormat.DOC);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
		return file;
	}

	public static void insertWatermarkText(Document doc, String watermarkText) {
		// 居中
		insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
			@Override
			public Object apply(Shape watermark) {
				// Place the watermark in the page center.
				watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
				watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
				watermark.setWrapType(WrapType.NONE); // TOP_BOTTOM : 将所设置位置的内容往上下顶出去
				watermark.setVerticalAlignment(VerticalAlignment.CENTER);
				watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);

				return null;
			}
		});
		// 顶部
		insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
			@Override
			public Object apply(Shape watermark) {
				watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
				watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
				watermark.setWrapType(WrapType.NONE);
				// 我们需要自定义距离顶部的高度
				watermark.setVerticalAlignment(VerticalAlignment.TOP);
				watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
				// 设置距离顶部的高度
				// watermark.setTop(160);

				return null;
			}
		});
		// 底部
		insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
			@Override
			public Object apply(Shape watermark) {
				watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
				watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
				watermark.setWrapType(WrapType.NONE);
				// 我们需要自定义距离顶部的高度
				watermark.setVerticalAlignment(VerticalAlignment.BOTTOM);
				watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
				// 设置距离顶部的高度
				// watermark.setTop(480);

				return null;
			}
		});
	}

	private static void insertWatermarkText(Document doc, String watermarkText,
			Function<Shape, Object> watermaskPositionConfigFunc) {
		// Create a watermark shape. This will be a WordArt shape.
		// You are free to try other shape types as watermarks.
		Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
		// Set up the text of the watermark.
		watermark.getTextPath().setText(watermarkText);

		// Set up the text of the watermark.
		// 这里设置为宋体可以保证在转换为PDF时中文不是乱码.
		watermark.getTextPath().setFontFamily("宋体");// Arial;
		try {
			// 水印大小
			watermark.setWidth(200);
			watermark.setHeight(50);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// Text will be directed from the bottom-left to the top-right corner.
		// 左下到右上
		watermark.setRotation(-40);

		// Remove the following two lines if you need a solid black text.
		final String colorStr = "E0E0E0";
		watermark.getFill().setColor(new java.awt.Color(Integer.parseInt(colorStr, 16))); // Try Color.lightGray to get
																							// more Word-style watermark
		watermark.setStrokeColor(new java.awt.Color(Integer.parseInt(colorStr, 16))); // Try Color.lightGray to get more
		// Word-style watermark
		watermark.setZOrder(10000);
		// Place the watermark in the special location of page .
		watermaskPositionConfigFunc.apply(watermark);

		// Create a new paragraph and append the watermark to this paragraph.
		Paragraph watermarkPara = new Paragraph(doc);
		watermarkPara.appendChild(watermark);

		// Insert the watermark into all headers of each document section.
		for (Section sect : doc.getSections()) {
			// There could be up to three different headers in each section, since we want
			// the watermark to appear on all pages, insert into all headers.
			insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
			// 以下注释掉不影响效果, 未作深入研究, 时间有限.
			// insertWatermarkIntoHeader(watermarkPara, sect,
			// HeaderFooterType.HEADER_FIRST);
			// insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
		}
		// 参考下API :
		// https://apireference.aspose.com/java/words/com.aspose.words/ShapeBase

	}

	private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect, int headerType) {
		HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);

		if (header == null) {
			// There is no header of the specified type in the current section, create it.
			header = new HeaderFooter(sect.getDocument(), headerType);
			sect.getHeadersFooters().add(header);
		}

		// Insert a clone of the watermark into the header.
		try {
			header.appendChild(watermarkPara.deepClone(true));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
