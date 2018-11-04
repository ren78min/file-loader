package me.mren.fileloader.filevisitor;

import java.util.Date;

public class VisitResultView {

	private final String absolutPath;

	private final long size;

	private final Date date;

	VisitResultView(final String absolutPath, final long size, final Date date) {
		this.absolutPath = absolutPath;
		this.size = size;
		this.date = date;
	}

	public String getAbsolutPath() {
		return absolutPath;
	}

	public long getSize() {
		return size;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absolutPath == null) ? 0 : absolutPath.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VisitResultView other = (VisitResultView) obj;
		if (absolutPath == null) {
			if (other.absolutPath != null) {
				return false;
			}
		} else if (!absolutPath.equals(other.absolutPath)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "VisitResultView [absolutPath=" + absolutPath + ", size=" + size + ", date=" + date + "]";
	}

}
