package me.mren.fileloader;

import java.util.Date;

public class FileView {

	private final String absolutPath;

	private final long size;

	private final Date date;

	FileView(final String absolutPath, final long size, final Date date) {
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
		result = prime * result + absolutPath.hashCode();
		result = prime * result + date.hashCode();
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
		final FileView other = (FileView) obj;
		if (!absolutPath.equals(other.absolutPath)) {
			return false;
		}
		if (!date.equals(other.date)) {
			return false;
		}
		return size == other.size;
	}

	@Override
	public String toString() {
		return "VisitResultView [absolutPath=" + absolutPath + ", size=" + size + ", date=" + date + "]";
	}

}
