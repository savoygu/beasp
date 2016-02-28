package org.huel.beasp.entity.book;

/**
 * 书籍语言
 * @author 001
 *
 */
public enum Language {
	/**中文**/
	CHINESE {
		@Override
		public String getName() {
			return "简体中文";
		}
	},
	/**英文**/
	ENGLISH {
		@Override
		public String getName() {
			return "英文";
		}
	};	

	public abstract String getName();
}
