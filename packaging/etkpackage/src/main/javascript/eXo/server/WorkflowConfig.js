eXo.require("eXo.core.TaskDescriptor");
eXo.require("eXo.projects.Project");
eXo.require("eXo.core.IOUtil");

function WorkflowConfig() {
 
}

WorkflowConfig.prototype.patchWarWorkflow = function(server,product) {
  var descriptor =  new TaskDescriptor("Configure workflow", null) ;
  var workflowName = java.lang.System.getProperty("workflow");
  if (workflowName == null || workflowName == "") workflowName = "bonita";
  descriptor.description = "Configure to use workflow with " + workflowName;
  descriptor.execute =function () {
    var jarFile =  server.deployWebappDir + "/" + product.portalwar;
    var IOUtil =  eXo.core.IOUtil;
    var mentries = new java.util.HashMap();
    var properties = new java.util.HashMap();
    properties.put("${workflow}", workflowName);

    if (product.name == "eXoWCM") {
	    var jarFile =  server.deployWebappDir + "/" + product.ecmdemowar;
	    var IOUtil =  eXo.core.IOUtil;
	    var mentries = new java.util.HashMap();
	    var properties = new java.util.HashMap();
	    eXo.System.info("CONF", "Enable Workflow settings");
	    mentries = IOUtil.patchWar(jarFile, properties, "WEB-INF/conf/sample-portal/portal/group/platform/web-contributors/navigation.workflow.xml", 
	                  "WEB-INF/conf/sample-portal/portal/group/platform/web-contributors/navigation.xml", mentries);
	    var ecmdemowar = server.deployWebappDir + "/" + product.ecmdemowar;
	    eXo.System.info("CONF", "Patching workflow config in " + ecmdemowar + ": \n\t" + mentries.keySet());
	    IOUtil.modifyJar(ecmdemowar, mentries, null);
	    	    
	    mentries = IOUtil.patchWar(jarFile, properties, "WEB-INF/conf/sample-portal/portal/group/platform/web-contributors/pages.workflow.xml", 
	                  "WEB-INF/conf/sample-portal/portal/group/platform/web-contributors/pages.xml", mentries);
	    var ecmdemowar = server.deployWebappDir + "/" + product.ecmdemowar;
	    eXo.System.info("CONF", "Patching workflow config in " + ecmdemowar + ": \n\t" + mentries.keySet());
	    IOUtil.modifyJar(ecmdemowar, mentries, null);
    }
	}
	return descriptor;
}

eXo.server.WorkflowConfig = new WorkflowConfig();
