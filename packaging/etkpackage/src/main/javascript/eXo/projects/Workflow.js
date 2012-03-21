eXo.require("eXo.core.IOUtil") ;
eXo.require("eXo.core.TaskDescriptor") ;
eXo.require("eXo.projects.Project");

function Workflow(workflowName, workflowVersion, deployName) {  
  this.name = workflowName;
  this.version = workflowVersion;
	this.workflowDeployName = deployName;
}

Workflow.prototype.configWorkflow = function(product) {

    print("Workflow.js: Configuring workflow version: " + this.version + "   name: " + this.name);

    product.addDependencies(this.getPortlet());
    product.addDependencies(new Project("bsh", "bsh", "jar", "2.0b1"));
    if (this.name == "jbpm") {
        print("Workflow.js: adding dependencies for jbpm");
		product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-facade-jbpm", "jar", this.version));
        product.addDependencies(new Project("org.jbpm.jbpm3", "jbpm-jpdl", "jar", product.workflowJbpmVersion));
        product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-jbpm-payraise", "jar", this.version));
        product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-jbpm-holiday", "jar", this.version));
		if (product.useContentvalidation) {
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-jbpm-content", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-plugin", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-publication", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-webui", "jar", this.version));
        }
    } else if (this.name == "bonita") {
        print("Workflow.js: adding dependencies for bonita");

        product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-facade-bonita", "jar", this.version));
        product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-bonita-holiday", "jar", this.version));
        product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-bonita-payraise", "jar", this.version));
        if (product.useContentvalidation) {
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-plugin", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-publication", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-bonita-content", "jar", this.version));
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-bp-content-webui", "jar", this.version));
        }

        if (product.workflowBonitaVersion == "4.1") {
            product.addDependencies(new Project("org.exoplatform.ecms", "exo-ecms-delivery-wkf-wcm-server-bonita", "jar", this.version));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-api", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-api-accessor", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-api-obj", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-utils", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-util-common", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-identity", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-pvm", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-engine", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-server", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-server-env", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-hibernate", "jar", product.workflowBonitaVersion));
        } else if (product.workflowBonitaVersion == "4.0") {
            product.addDependencies(new Project("org.ow2.bonita", "bonita-api", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.bonita", "bonita-core", "jar", product.workflowBonitaVersion));
            product.addDependencies(new Project("org.ow2.novabpm", "novaBpmIdentity", "jar", "1.0"));
            product.addDependencies(new Project("org.ow2.novabpm", "novaBpmUtil", "jar", "1.0"));
            product.addDependencies(new Project("org.jbpm", "pvm", "jar", "r2175"));
        }

        //Remove duplicate ehcache from Portal
        product.removeDependency(new Project("net.sf.ehcache", "ehcache", "jar", "1.4.1"));

        //Add external dependencies
        product.addDependencies(new Project("net.sf.ehcache", "ehcache", "jar", "1.5.0"));
        product.addDependencies(new Project("backport-util-concurrent", "backport-util-concurrent", "jar", "3.1"));
        product.addDependencies(new Project("org.ow2.util.asm", "asm", "jar", "3.1"));
        product.addServerPatch("jbossear", new Project("org.exoplatform.ecms", "exo-ecms-delivery-wkf-wcm-server-jboss-ear", "jar", this.version));
        product.addServerPatch("jboss", new Project("org.exoplatform.ecms", "exo-ecms-delivery-wkf-wcm-server-jboss", "jar", this.version));
        product.addServerPatch("tomcat", new Project("org.exoplatform.ecms", "exo-ecms-delivery-wkf-wcm-server-tomcat", "jar", this.version));
    }
};

Workflow.prototype.getPortlet = function() {
    return new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-portlet-administration", "exo-portlet", this.version).
			setDeployName(this.workflowDeployName).
	    addDependency(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-api", "jar", this.version)).
	    addDependency(new Project("org.exoplatform.ecms", "exo-ecms-ext-workflow-webui", "jar", this.version));
};

eXo.projects.Workflow = Workflow.prototype.constructor ;
