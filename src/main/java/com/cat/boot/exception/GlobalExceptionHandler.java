package com.cat.boot.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cat.boot.service.BaseService;
import com.cat.boot.util.CalendarUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	public BaseService baseService;
	
	private String sql = "insert into LOG_Log_Exception(id, user_id, username,controllermethod,message,stack_trace,BE_ct) values (:id,:userId,:username,:controllermethod,:message,:stackTrace,:BE_ct)";
	
    @ExceptionHandler(CatException.class)
    public String CatException(HttpServletRequest request, CatException exception) throws Exception {
    	handleErrorInfo(request, exception.getMessage(), exception);
    	throw new CatException("系统出现异常,请联系管理员");
    }

    @ExceptionHandler(CatRuntimeException.class)
    public String CatRuntimeException(HttpServletRequest request, CatRuntimeException exception) throws Exception {
    	handleErrorInfo(request, exception.getMessage(), exception);
    	throw new CatRuntimeException("系统出现异常,请联系管理员");
    }

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request,Object handel, Exception exception) throws Exception {
    	handleErrorInfo(request, exception.getMessage(), exception);
    	throw exception;
    }

    private void handleErrorInfo(HttpServletRequest request, String message, Exception exception) {
       try {
    	   if(exception.getMessage().contains("你的主机中的软件中止了一个已建立的连接")) {
    		   return;
    	   }
    	   ByteArrayOutputStream buf = new ByteArrayOutputStream();
    	   exception.printStackTrace(new PrintWriter(buf, true));
    	   
    	   baseService.noAnnotationUpdate(baseService.query(sql, true).setParameter("id",  UUID.randomUUID().toString())
    	   .setParameter("userId", baseService.getUserInfo(request).getId()).setParameter("username", baseService.getUserInfo(request).getName())
    	   .setParameter("controllermethod", request.getRequestURI())
    	   .setParameter("message", message)
    	   .setParameter("stackTrace", buf.toString())
    	   .setParameter("BE_ct", CalendarUtil.getYyyyMmDdHHmmss(Calendar.getInstance())));
       }catch(Exception e) {
    	   e.printStackTrace();
       }
    }
}