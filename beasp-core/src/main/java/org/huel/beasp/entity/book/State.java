package org.huel.beasp.entity.book;
/**
 * 书籍状态
 * @author 001
 *
 **/
public enum State {
	/**
	 * 1.书籍状态
	 */
	/**待审核**/
	WAITCONFIRM {
		@Override
		public String getName() {
			return "未审核";
		}
	},
	/**审核失败**/
	CONFIRMFAIL {
		@Override
		public String getName() {
			return "审核失败";
		}
	},
	/**发布**/
	RELEASE {
		@Override
		public String getName() {
			return "发布";
		}
	},
	/**交换**/
	EXCHANGE {
		@Override
		public String getName() {
			return "交换";
		}
	},
	/**交换中**/
	EXCHANGEING {
		@Override
		public String getName() {
			return "交换中";
		}
	},
	/**已交换**/
	EXCHANGED {
		@Override
		public String getName() {
			return "已交换";
		}
	},
	/**交换失败**/
	EXCHANGEFAIL {
		@Override
		public String getName() {
			return "交换失败";
		}
	},
	/**分享**/
	SHARE {
		@Override
		public String getName() {
			return "分享";
		}
	},
	/**分享中**/
	SHAREING {
		@Override
		public String getName() {
			return "分享中";
		}
	},
	/**已分享**/
	SHARED {
		@Override
		public String getName() {
			return "已分享";
		}
	},
	/**分享失败**/
	SHAREFAIL {
		@Override
		public String getName() {
			return "分享失败";
		}
	},
	/**回收站**/
	RECYCLEBIN {
		@Override
		public String getName() {
			return "回收站";
		}
	},
	/**不可见**/
	INVISIBLE {
		@Override
		public String getName() {
			return "不可见";
		}
	},
	/**
	 * 2.人与非所属和所属书籍的关系
	 */
	/** 我自己 **/
	SELF {
		@Override
		public String getName() {
			return "我自己";
		}
	},
	/**收藏 **/
	COLLECTION {
		@Override
		public String getName() {
			return "收藏";
		}
	},
	/** 浏览 **/
	BROWSE {
		@Override
		public String getName() {
			return "浏览";
		}
	},
	/** 点赞 **/
	PRAISE{
		@Override
		public String getName() {
			return "点赞";
		}
	},
	
	/**
	 * 2.人与非所属和所属书籍的关系
	 */
	
	WAITUPLOAD {

		@Override
		public String getName() {
			return "等待上传";
		}
	},
	UPLOADED {

		@Override
		public String getName() {
			return "已经上传";
		}
	},
	
	WAITSURE {

		@Override
		public String getName() {
			return "等待确认";
		}
	},
	WAITVERIFY {

		@Override
		public String getName() {
			return "等待审核";
		}
	},
	VERIFYPASS {

		@Override
		public String getName() {
			return "审核通过";
		}
	},
	VERIFYFAIL {

		@Override
		public String getName() {
			return "审核失败";
		}
	};
	public abstract String getName();
}
