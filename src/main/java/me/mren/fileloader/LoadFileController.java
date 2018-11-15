package me.mren.fileloader;

import java.util.Set;

public interface LoadFileController {

	String hello();

	Set<FileView> loadFromPath(String path);

}
