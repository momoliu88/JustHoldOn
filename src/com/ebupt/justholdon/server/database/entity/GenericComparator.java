package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;

public class GenericComparator {
	public GenericComparator() {
	};

	private Comparator<BaseEntity<Number>> dateComparator = new Comparator<BaseEntity<Number>>() {
		@Override
		public int compare(BaseEntity<Number> arg0, BaseEntity<Number> arg1) {
			int comp =  (int) (arg1.getCreateTime().getTime() - arg0.getCreateTime()
					.getTime());
			if( 0 == comp)
				return (int) (arg1.getId().longValue() - arg0.getId().longValue());
			return comp;
		}

	};

	private Comparator<BaseEntity<Number>> idComparator = new Comparator<BaseEntity<Number>>() {
		@Override
		public int compare(BaseEntity<Number> arg0, BaseEntity<Number> arg1) {
			return (int) (arg1.getId().longValue() - arg0.getId().longValue());
		}

	};

	@SuppressWarnings("rawtypes")
	public Comparator getDateComparator() {
		return dateComparator;
	}

	private static GenericComparator instance = new GenericComparator();

	public static GenericComparator getInstance() {
		return instance;
	}

	public Comparator<BaseEntity<Number>> getIdComparator() {
		return idComparator;
	}

}
