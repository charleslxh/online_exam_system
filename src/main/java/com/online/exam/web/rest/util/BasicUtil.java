package com.online.exam.web.rest.util;

import javax.servlet.http.HttpServletRequest;

public class BasicUtil {
	/**
	 * This function is use for get email basic url
	 * @param request
	 * @return basic url
	 */
	public static String getEmailBasicUrl(HttpServletRequest request) {
		String basicUrl = request.getScheme() + // "http"
				"://" +                                // "://"
                request.getServerName() +              // "myhost"
                ":" +                                  // ":"
                request.getServerPort();               // "80"
		return basicUrl;
	}
}
