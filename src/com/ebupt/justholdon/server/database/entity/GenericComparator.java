package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
@SuppressWarnings("rawtypes")
public class GenericComparator {
	public GenericComparator() {
	};

	private Comparator dateComparator = new Comparator<BaseEntity<Number>>() {
		@Override
		public int compare(BaseEntity<Number> arg0, BaseEntity<Number> arg1) {
			Long comp =   arg1.getCreateTime().getTime() - arg0.getCreateTime()
					.getTime();
			if( 0 == comp)
				return (int) (arg1.getId().longValue() - arg0.getId().longValue());
			if(comp > 0) return 1;
			return -1;
		}

	};

	private Comparator idComparator = new Comparator<BaseEntity<Number>>() {
		@Override
		public int compare(BaseEntity<Number> arg0, BaseEntity<Number> arg1) {
			return (int) (arg1.getId().longValue() - arg0.getId().longValue());
		}

	};

	public Comparator getDateComparator() {
		return dateComparator;
	}

	private static GenericComparator instance = new GenericComparator();

	public static GenericComparator getInstance() {
		return instance;
	}

	public Comparator getIdComparator() {
		return idComparator;
	}

}
