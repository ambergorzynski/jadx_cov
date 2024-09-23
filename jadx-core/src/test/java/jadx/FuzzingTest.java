package jadx.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jadx.core.utils.files.FileUtils;
import jadx.core.xmlgen.ResContainer;
import jadx.plugins.input.dex.DexInputPlugin;

import static jadx.tests.api.utils.assertj.JadxAssertions.assertThat;
import jadx.api.FuzzingTestImplementation.FuzzerClassFileTestDataSource;

public class FuzzingTest {

	@ParameterizedTest(name = "[{index}] {0}")
	@FuzzerClassFileTestDataSource("fuzzer_classes.xml")
	public void testFuzzer(Path classFilePath) {

		File outDir = new File("/data/work/fuzzflesh/coverage/jadx/jadx-test-output");

		JadxArgs args = new JadxArgs();
		args.getInputFiles().add(classFilePath.toFile());
		args.setOutDir(outDir);

		try (JadxDecompiler jadx = new JadxDecompiler(args)) {
			jadx.load();
			jadx.save();
			jadx.printErrorsReport();

			// test class print
			for (JavaClass cls : jadx.getClasses()) {
				System.out.println(cls.getCode());
			}

			assertThat(true);
		}
	}
}
