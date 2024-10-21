plugins {
	id("jadx-library")
	id("jacoco")
}

dependencies {
	api(project(":jadx-plugins:jadx-input-api"))

	implementation("com.google.code.gson:gson:2.11.0")

	testImplementation("org.apache.commons:commons-lang3:3.17.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testImplementation(project(":jadx-plugins:jadx-dex-input"))
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly(project(":jadx-plugins:jadx-smali-input"))
	testRuntimeOnly(project(":jadx-plugins:jadx-java-convert"))
	testRuntimeOnly(project(":jadx-plugins:jadx-java-input"))
	testRuntimeOnly(project(":jadx-plugins:jadx-raung-input"))

	testImplementation("org.eclipse.jdt:ecj") {
		version {
			prefer("3.33.0")
			strictly("[3.33, 3.34[") // from 3.34 compiled with Java 17
		}
	}
	testImplementation("tools.profiler:async-profiler:3.0")
}

tasks.jacocoTestReport {
	reports {
		xml.required = false
		csv.required = true
	}
}

tasks.test {
	useJUnitPlatform()
	filter {
		includeTestsMatching("FuzzingTest*")
	}
	exclude("**/tmp/*")
	finalizedBy(tasks.jacocoTestReport)
}
