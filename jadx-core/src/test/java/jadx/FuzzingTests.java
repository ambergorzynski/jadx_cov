package jadx.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import jadx.core.utils.files.FileUtils;
import jadx.core.xmlgen.ResContainer;
import jadx.plugins.input.dex.DexInputPlugin;

import static jadx.tests.api.utils.assertj.JadxAssertions.assertThat;

public class FuzzingTests {

	@Test
	public void testFuzzer() {
		File sampleApk = new File("/data/work/fuzzflesh/coverage/jadx/TestCase.class");
		File outDir = new File("/data/work/fuzzflesh/coverage/jadx/jadx-test-output");

		// test simple apk loading
		JadxArgs args = new JadxArgs();
		args.getInputFiles().add(sampleApk);
		args.setOutDir(outDir);

		try (JadxDecompiler jadx = new JadxDecompiler(args)) {
			jadx.load();
			jadx.save();
			jadx.printErrorsReport();

			// test class print
			for (JavaClass cls : jadx.getClasses()) {
				System.out.println(cls.getCode());
			}

			assertThat(jadx.getClasses()).hasSize(1);
			assertThat(jadx.getErrorsCount()).isEqualTo(0);
		}
	}
}
