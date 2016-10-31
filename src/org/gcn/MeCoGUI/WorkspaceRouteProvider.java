package org.gcn.MeCoGUI;

public class WorkspaceRouteProvider {
	public String getWorkspaceRoute(){
		return System.getenv().get("WORKSPACE").replace('\\', '/');
	}

}
