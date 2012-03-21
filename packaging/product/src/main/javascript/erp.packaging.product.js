eXo.require("eXo.projects.Module") ;
eXo.require("eXo.projects.Product") ;

function getProduct(version) {
  var product = new Product();
  product.name = "eXoPortal" ;
  product.portalwar = "portal.war" ;
  product.codeRepo = "portal" ;//module in modules/portal/module.js
  product.serverPluginVersion = "${org.exoplatform.portal.version}"; // CHANGED for Social to match portal version. It was 1.2.8-SNAPSHOT
  
  // get erp with dependencies of 'erp'
  var erp = Module.GetModule("erp", {});
  product.addDependencies(erp.component.core);
  product.addDependencies(erp.webapp.sample);

  product.addServerPatch("tomcat", erp.server.tomcat.patch);
  product.module = erp ;

  return product ;
}
