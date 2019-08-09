package com.cat.boot.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressUtil {

	public static Object elValue(String elStr) {
		elStr = StringUtils.trim(elStr);
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(elStr);
		return exp.getValue();
	}

	public static boolean elValue(Object obj, String elStr) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext(obj);  
		boolean result = parser.parseExpression(elStr).getValue(context, boolean.class);  
		return result;
	}
	
}
