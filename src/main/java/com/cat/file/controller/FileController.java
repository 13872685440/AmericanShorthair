package com.cat.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cat.boot.exception.CatException;

import com.cat.boot.jsonbean.ResultBean;
import com.cat.boot.service.BasePrintService;
import com.cat.boot.service.BaseService;
import com.cat.boot.util.NameQueryUtil;
import com.cat.boot.util.SpringContextUtil;
import com.cat.boot.util.StringUtil;
import com.cat.file.config.PathConfig;
import com.cat.file.jsonbean.FileBean;
import com.cat.file.jsonbean.FileInfoBean;
import com.cat.file.jsonbean.PrintBean;
import com.cat.file.jsonbean.ResultFileBean;
import com.cat.file.jsonbean.UploadBean;
import com.cat.file.model.FileInfo;
import com.cat.file.model.YeWuBLZLWD;
import com.cat.file.model.YeWuBanLiZlLx;
import com.cat.file.utils.AsposeUtil;
import com.cat.file.utils.FileUtils;

import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private BaseService baseService;

	@Autowired(required = true)
	public PathConfig pathConfig;

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file, UploadBean bean)
			throws CatException, IOException {
		FileBean filebean = new FileBean(file);
		String wdid = saveFile(response, bean, filebean);
		if (!StringUtil.isEmpty(wdid)) {
			return ResultFileBean.getSucess(wdid);
		} else {
			return ResultFileBean.getError();
		}
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @RequestParam String blwdId, @RequestParam String isOnLine)
			throws Exception {
		YeWuBLZLWD wd = (YeWuBLZLWD) baseService.findById(YeWuBLZLWD.class, blwdId);
		if (wd == null) {
			return;
		} else {
			FileInfo info = wd.getFileinfo();
			File file = new File(
					pathConfig.getPath("uploadpath") + info.getUrlName() + File.separator + info.getOriginalName());
			FileUtils.download(response, file, isOnLine);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadFile", method = RequestMethod.GET)
	public String loadFile(UploadBean bean) throws Exception {
		Map<Object, Object> maps = NameQueryUtil.setParams("ebcn", bean.getEbcn(), "keyValue", bean.getKeyValue(),
				"sign", bean.getSign());
		List<YeWuBLZLWD> datas = (List<YeWuBLZLWD>) baseService.getList("YeWuBLZLWD", "o.ct asc", true, maps);
		return ResultBean.getSucess(FileInfoBean.setThis(datas));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/printFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, PrintBean bean) throws Exception {
		// 构建路径 templatepath / service / fileName

		BasePrintService service = (BasePrintService) SpringContextUtil.getBean(bean.getService());
		Map<String, Map<String, Object>> dataMap = (Map<String, Map<String, Object>>) service
				.getDataMap(bean.getKeyValue());
		String fileUrl = pathConfig.getPath("printTempPath") + File.separator + bean.getService() + File.separator
				+ bean.getKeyValue() + File.separator + bean.getFileName();
		String templatepath = pathConfig.getPath("templatepath") + File.separator + bean.getService();
		AsposeUtil.execute(dataMap, templatepath, bean.getFileName(), fileUrl);
		File file = AsposeUtil.wordToPdf(fileUrl + ".doc", fileUrl + ".pdf", bean.getWatermark());
		FileUtils.download(response, file, "0");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	public String deleteFile(Model model, @RequestParam String id) {
		YeWuBLZLWD data = (YeWuBLZLWD) baseService.findById(YeWuBLZLWD.class, id);
		data.setDictionaryData("0");
		baseService.save(data);

		return ResultBean.getSucess("");
	}

	public void replaceTmpId(String tmpId, String entityId, String ebcn) {
		baseService.update("YeWuBLZLWD", "file", "YeWuBLZLWD_replaceTmpId",
				NameQueryUtil.setParams("entityId", entityId, "tmpId", tmpId, "ebcn", ebcn));
	}

	private String saveFile(HttpServletResponse response, UploadBean bean, FileBean filebean) throws IOException {
		String sign = "";
		String path = "";
		if (!StringUtil.isEmpty(bean.getCode())) {
			YeWuBanLiZlLx zllx = (YeWuBanLiZlLx) baseService.findById(YeWuBanLiZlLx.class, bean.getCode());
			path = zllx.getPath() + File.separator + bean.getKeyValue() + File.separator + zllx.getName()
					+ File.separator + Calendar.getInstance().getTimeInMillis();
			sign = zllx.getCode();
		} else {
			path = bean.getEbcn() + File.separator + bean.getKeyValue() + File.separator
					+ Calendar.getInstance().getTimeInMillis();
			sign = bean.getSign();
		}
		String uploadDir = pathConfig.getPath("uploadpath") + path;

		return saveWd(response, filebean, path, uploadDir, sign, bean);
	}

	private String saveWd(HttpServletResponse response, FileBean filebean, String path, String uploadDir, String sign,
			UploadBean bean) throws IOException {
		byte[] bytes = filebean.getBytes();

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		String tempUrl = java.net.URLDecoder.decode(filebean.getTitle(), "UTF-8");
		File uploadedFile = new File(uploadDir + File.separator + tempUrl);
		if (uploadedFile.exists()) {

			return "";
		}
		FileCopyUtils.copy(bytes, uploadedFile);
		FileInfo doc = new FileInfo();
		// 生成缩略图
		if ("img".equals(bean.getFiletype())) {
			File thumUploadedFile = new File(
					uploadDir + File.separator + FileUtils.transformPath(tempUrl, "_" + bean.getWidth()));
			Thumbnails.of(uploadedFile).size(Integer.valueOf(bean.getWidth()), Integer.valueOf(bean.getHeight()))
					.toFile(thumUploadedFile);
			doc.setScaleName(thumUploadedFile.getName());
		}
		doc.setOriginalName(filebean.getTitle());
		doc.setUrlName(path);
		doc.setSize(Double.valueOf(filebean.getSize()));
		baseService.save(doc, false);

		YeWuBLZLWD blwd = new YeWuBLZLWD();
		blwd.setSign(sign);
		blwd.setFileinfo(doc);
		blwd.setEbcn(bean.getEbcn());

		blwd.setKeyValue(bean.getKeyValue());
		blwd.setNowinsert("1");

		baseService.save(blwd, false);

		return doc.getId();
	}
}
