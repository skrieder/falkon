/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */
package org.globus.GenericPortal.services.core.WS.impl;

import javax.xml.namespace.QName;

public interface GPConstants {
	public static final String NS = "http://www.globus.org/namespaces/GenericPortal/GPService_instance";

	public static final QName RP_NUMJOBS = new QName(NS, "NumJobs");

	public static final QName RP_STATE = new QName(NS, "State");

        public static final QName RP_NUMWORKERS = new QName(NS, "NumWorkers");

        public static final QName RP_NUMUSERRESULTS = new QName(NS, "NumUserResults");

        public static final QName RP_NUMWORKERRESULTS = new QName(NS, "NumWorkerResults");


	public static final QName RESOURCE_PROPERTIES = new QName(NS,
			"GPResourceProperties");

	public static final QName RESOURCE_REFERENCE = new QName(NS,
			"GPResourceReference");
}