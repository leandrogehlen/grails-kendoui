grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
   
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" 
    legacyResolve false
    repositories {
        grailsCentral()        
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // runtime 'mysql:mysql-connector-java:5.1.21'		
    }

    plugins {
        build(":release:2.2.0",":rest-client-builder:1.0.3") {
            export = false
        }
		compile(":resources:1.1.6")
    }
}
