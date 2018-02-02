package com.crd.demo.common.model;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 作为分页查询的参数封装类

 */
public class PageView {
	// -- 分页参数 --//
		protected int pageNo = 1;
		protected int pageSize = -1;
		protected boolean autoCount = true;

		// -- 返回结果 --//
		@SuppressWarnings("rawtypes")
		protected List<?> result = new ArrayList();
		protected long totalCount = -1;

		public PageView() {
		}

		public PageView(int pageNo, int pageSize) {
			this.pageNo = pageNo;
			this.pageSize = pageSize;
		}

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
			if (pageNo < 1) {
				this.pageNo = 1;
			}
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public boolean isAutoCount() {
			return autoCount;
		}

		public void setAutoCount(boolean autoCount) {
			this.autoCount = autoCount;
		}

		public List<?> getResult() {
			return result;
		}

		public void setResult(List<?> result) {
			this.result = result;
		}

		public long getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(long totalCount) {
			this.totalCount = totalCount;
		}

		public int getViewFirstIndex() {
			return ((pageNo - 1) * pageSize);
		}
}
