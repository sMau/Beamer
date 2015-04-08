package de.netprojectev.desktop.main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import de.netprojectev.common.utils.MediaFileFilter;

public class MediaFileFilterDesktop extends FileFilter {

	private MediaFileFilter filter;

	public MediaFileFilterDesktop() {
		this.filter = new MediaFileFilter();
	}

	@Override
	public boolean accept(File f) {
		return this.filter.accept(f);
	}

	@Override
	public String getDescription() {
		return filter.getDescription();
	}

}
