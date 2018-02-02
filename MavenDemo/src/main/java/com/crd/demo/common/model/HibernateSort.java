package com.crd.demo.common.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 用于封装Hiberante

 */
@Getter
@Setter
public class HibernateSort {
	
private List<Sort> sorts;
	
	public static enum Direction {
		ASC, DESC
	}
	
	public HibernateSort(List<Sort> sorts) {
		this.sorts = sorts;
	}
	
	public HibernateSort(Sort... sorts) {
		this.sorts = Arrays.asList(sorts);
	}
	
	@Getter
	@Setter
	public static class Sort {
		
		private Direction direction;
		private String paramName;
		
		public Sort(Direction direction, String paramName) {
			this.direction = direction;
			this.paramName = paramName;
		}
		
	}
}
