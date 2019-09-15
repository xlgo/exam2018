package cn.hnzxl.exam.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamCacheInfo {
	public static final String KEY_PREFIX = "exam:2019:info";

	public enum Type {
		EXAM_SAVE, EXAM_START
	}

	// 什么类型的数据
	private Type type;
	// 数据所在的key
	private String dataKey;
	// 用户id -1 不需要管用户
	private Long userId;

	public static ExamCacheInfo getSave(Long userId) {
		ExamCacheInfo info = new ExamCacheInfo();
		info.setUserId(userId);
		info.setType(Type.EXAM_SAVE);
		info.setDataKey(KEY_PREFIX+":"+Type.EXAM_SAVE.name());
		return info;
	}

	public static ExamCacheInfo getStart(Long userId) {
		ExamCacheInfo info = new ExamCacheInfo();
		info.setUserId(userId);
		info.setType(Type.EXAM_START);
		info.setDataKey(KEY_PREFIX+":"+Type.EXAM_START.name());
		return info;
	}

}
