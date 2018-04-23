install: release

release:
	rm -rf out && mkdir out && zip out/ACCOuNT.templates.zip *.xlsx
