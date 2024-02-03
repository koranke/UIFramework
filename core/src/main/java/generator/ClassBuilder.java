package generator;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ClassBuilder {
	private String fileName;
	private String filePackage;
	private String classPackageName;
	private List<String> imports;
	private List<String> classAnnotations;
	private String className;
	private String classDeclaration;
	private List<String> memberDeclarations;
	private List<String> memberInitializations;
	private boolean useInitializerMethod;
	private List<String> constructors;
	private List<String> methods;

	public String build() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("package ").append(classPackageName).append(";\n\n");

		Collections.sort(imports);
		for (String importLine : imports) {
			stringBuilder.append(importLine).append(";\n");
		}
		stringBuilder.append("\n");

		stringBuilder.append(buildClass());

		return stringBuilder.toString();
	}

	private String buildClass() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(buildAnnotations());
		stringBuilder.append("public ").append(classDeclaration).append(" {\n");
		stringBuilder.append(buildDeclarations());

		if (constructors == null) constructors = new ArrayList<>();
		for (String constructor : constructors) {
			stringBuilder.append("\n").append(constructor).append("\n");
		}

		stringBuilder.append(buildInitializerMethod());

		if (methods == null) methods = new ArrayList<>();
		for (String method : methods) {
			stringBuilder.append("\n").append(method).append("\n");
		}

		stringBuilder.append("}");
		return stringBuilder.toString();
	}

	private String buildAnnotations() {
		if (classAnnotations == null) return "";

		StringBuilder stringBuilder = new StringBuilder();
		for (String annotation : classAnnotations) {
			stringBuilder.append(annotation).append("\n");
		}
		return stringBuilder.toString();
	}

	private String buildDeclarations() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String memberDeclaration : memberDeclarations) {
			stringBuilder.append("\t").append(memberDeclaration).append(";\n");
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}

	private String buildInitializerMethod() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\tprotected void initialize() {\n");
		for (String memberInitialization : memberInitializations) {
			stringBuilder.append("\t\t").append(memberInitialization).append(";\n");
		}
		return stringBuilder.append("\t}\n").toString();
	}

	public ClassBuilder withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public ClassBuilder withFilePackage(String filePackage) {
		this.filePackage = filePackage;
		return this;
	}

	public ClassBuilder withClassPackageName(String classPackageName) {
		this.classPackageName = classPackageName;
		return this;
	}

	public ClassBuilder withImports(List<String> imports) {
		this.imports = imports;
		return this;
	}

	public ClassBuilder withClassAnnotations(List<String> classAnnotations) {
		this.classAnnotations = classAnnotations;
		return this;
	}

	public ClassBuilder withClassName(String className) {
		this.className = className;
		return this;
	}

	public ClassBuilder withClassDeclaration(String classDeclaration) {
		this.classDeclaration = classDeclaration;
		return this;
	}

	public ClassBuilder withMemberDeclarations(List<String> memberDeclarations) {
		this.memberDeclarations = memberDeclarations;
		return this;
	}

	public ClassBuilder withMemberInitializations(List<String> memberInitializations) {
		this.memberInitializations = memberInitializations;
		return this;
	}

	public ClassBuilder withMethods(List<String> methods) {
		this.methods = methods;
		return this;
	}
}
