package me.mren.fileloader.controller;

import java.util.Set;

import me.mren.fileloader.filevisitor.VisitResultView;

public interface LoadFileController {

	String hello();

	Set<VisitResultView> loadFromPath(String path);

}
