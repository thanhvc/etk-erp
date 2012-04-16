eXo.require("eXo.projects.Module");
eXo.require("eXo.projects.Product");

function getModule(params) {

  var module = new Module();

  module.version = "${project.version}";
  module.relativeMavenRepo = "org/etk/erp";
  module.relativeSRCRepo = "erp";
  module.name = "erp";

  module.component = {} ;

  module.component.core = new Project("org.etk.erp", "etk.erp.component.web.core", "jar", module.version);

  module.webapp = {};
  module.webapp.sample = new Project("org.etk.sample", "etk.sample", "war", module.version);
  module.webapp.sample.deployName = "sample";

  module.demo = {};
 

  module.server = {}

  module.server.tomcat = {}
  module.server.tomcat.patch = new Project("org.etk.erp", "etk.erp.server.tomcat.patch", "jar", module.version);

   
  return module;

}
