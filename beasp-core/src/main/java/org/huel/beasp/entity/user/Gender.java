package org.huel.beasp.entity.user;
/**
 * 用户性别
 * @author 001
 *
 */
public enum Gender {
	/**男**/
	MAN {
		@Override
		public String getName() {
			return "男";
		}
	},
	/**女**/
	WOMEN {
		@Override
		public String getName() {
			return "女";
		}
	},
	/**保密**/
	SECRET {
		@Override
		public String getName() {
			return "保密";
		}
	};
	public abstract String getName();
}
