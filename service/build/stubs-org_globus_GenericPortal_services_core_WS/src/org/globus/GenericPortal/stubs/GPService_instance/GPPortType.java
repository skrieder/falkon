/**
 * GPPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public interface GPPortType extends java.rmi.Remote {
    public org.globus.GenericPortal.stubs.GPService_instance.WorkerRegistrationResponse workerRegistration(org.globus.GenericPortal.stubs.GPService_instance.WorkerRegistration parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.WorkerDeRegistrationResponse workerDeRegistration(org.globus.GenericPortal.stubs.GPService_instance.WorkerDeRegistration parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.UserJobResponse userJob(org.globus.GenericPortal.stubs.GPService_instance.UserJob parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.UserResultResponse userResult(org.globus.GenericPortal.stubs.GPService_instance.UserResult parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.WorkerWorkResponse workerWork(org.globus.GenericPortal.stubs.GPService_instance.WorkerWork parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.WorkerResultResponse workerResult(org.globus.GenericPortal.stubs.GPService_instance.WorkerResult parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.InitResponse init(org.globus.GenericPortal.stubs.GPService_instance.Init parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.DeInitResponse deInit(org.globus.GenericPortal.stubs.GPService_instance.DeInit parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.StatusResponse status(org.globus.GenericPortal.stubs.GPService_instance.Status parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.StatusUserResponse statusUser(org.globus.GenericPortal.stubs.GPService_instance.StatusUser parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.MonitorConfigResponse monitorConfig(org.globus.GenericPortal.stubs.GPService_instance.MonitorConfig parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.MonitorStateResponse monitorState(org.globus.GenericPortal.stubs.GPService_instance.MonitorState parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.MonitorWorkerStateResponse monitorWorkerState(org.globus.GenericPortal.stubs.GPService_instance.MonitorWorkerState parameters) throws java.rmi.RemoteException;
    public org.globus.GenericPortal.stubs.GPService_instance.MonitorTaskStateResponse monitorTaskState(org.globus.GenericPortal.stubs.GPService_instance.MonitorTaskState parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy destroyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.ResourceUnknownFaultType, org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType;
    public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime setTerminationTimeRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType, org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
}
