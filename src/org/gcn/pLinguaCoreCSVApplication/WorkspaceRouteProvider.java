package org.gcn.pLinguaCoreCSVApplication;

public class WorkspaceRouteProvider {
	public String getWorkspaceRoute(){
		return System.getenv().get("WORKSPACE").replace('\\', '/');
	}

}
