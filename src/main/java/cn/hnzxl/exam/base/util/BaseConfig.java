package cn.hnzxl.exam.base.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BaseConfig {
	/**域名*/
	@Value("${base.hostName}")
	private String hostName;
}
