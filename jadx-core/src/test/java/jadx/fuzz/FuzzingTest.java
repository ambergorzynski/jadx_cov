package jadx.fuzz;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.params.ParameterizedTest;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;
import jadx.fuzz.FuzzingTestImplementation.FuzzerClassFileTestDataSource;

import static jadx.tests.api.utils.assertj.JadxAssertions.assertThat;

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
