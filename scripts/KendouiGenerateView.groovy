includeTargets << grailsScript("_GrailsCreateArtifacts")
includeTargets << new File(kendouiPluginDir, "scripts/_KendouiGenerate.groovy")

generateViews = true
generateController = false

target(kendouiGenerateView: "Generates CRUD views for a given domain class") {
	depends(checkVersion, parseArguments, packageApp)
	promptForName(type: "Domain Class")

	try {
		def name = argsMap["params"][0]
		if (!name || name == "*") {
			uberGenerate()
		}
		else {
			generateForName = name
			generateForOne()
		}
	}
	catch (Exception e) {
		logError("Error running kendoui-generate-view", e)
		exit(1)
	}
}

setDefaultTarget("kendouiGenerateView")
