default:
	ant

clean:
	rm -rf tests/*.il

tests: 
	for f in tests/*.nl ; do \
		/Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java -jar out/artifacts/NL_Compiler_jar/NL\ Compiler.jar $$f > tests/$$(basename $$f .nl).il ; \
	done
	ls -la tests/

.PHONY: tests clean
