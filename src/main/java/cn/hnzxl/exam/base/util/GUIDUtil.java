package cn.hnzxl.exam.base.util;

import java.util.UUID;

/**
 * 唯一码生成工具
 * 
 * @author ZXL
 * @date 2014年10月25日 上午1:17:12
 *
 */
public class GUIDUtil {
	/**
	 * UUID 方式生成
	 * @return
	 */
	public static final String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

}
