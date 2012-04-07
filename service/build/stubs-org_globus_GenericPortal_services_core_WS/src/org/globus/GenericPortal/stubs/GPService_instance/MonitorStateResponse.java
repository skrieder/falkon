/**
 * MonitorStateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class MonitorStateResponse  implements java.io.Serializable {
    private int resourceAllocated;
    private int resourceRegistered;
    private int resourceFree;
    private int resourcePending;
    private int resourceActive;
    private int taskNumQueues;
    private int taskSubmit;
    private int taskWaitQueue;
    private int taskDispatch;
    private int taskActive;
    private int taskDeliveryQueue;
    private int taskDelivered;
    private int cacheSize;
    private int cacheHitsGlobal;
    private int cacheHitsLocal;
    private int cacheMisses;
    private int systemNumThreads;
    private int systemCPUuser;
    private int systemCPUsystem;
    private int systemCPUidle;
    private int systemHeapSize;
    private int systemHeapFree;
    private int systemHeapMax;
    private boolean valid;

    public MonitorStateResponse() {
    }

    public MonitorStateResponse(
           int cacheHitsGlobal,
           int cacheHitsLocal,
           int cacheMisses,
           int cacheSize,
           int resourceActive,
           int resourceAllocated,
           int resourceFree,
           int resourcePending,
           int resourceRegistered,
           int systemCPUidle,
           int systemCPUsystem,
           int systemCPUuser,
           int systemHeapFree,
           int systemHeapMax,
           int systemHeapSize,
           int systemNumThreads,
           int taskActive,
           int taskDelivered,
           int taskDeliveryQueue,
           int taskDispatch,
           int taskNumQueues,
           int taskSubmit,
           int taskWaitQueue,
           boolean valid) {
           this.resourceAllocated = resourceAllocated;
           this.resourceRegistered = resourceRegistered;
           this.resourceFree = resourceFree;
           this.resourcePending = resourcePending;
           this.resourceActive = resourceActive;
           this.taskNumQueues = taskNumQueues;
           this.taskSubmit = taskSubmit;
           this.taskWaitQueue = taskWaitQueue;
           this.taskDispatch = taskDispatch;
           this.taskActive = taskActive;
           this.taskDeliveryQueue = taskDeliveryQueue;
           this.taskDelivered = taskDelivered;
           this.cacheSize = cacheSize;
           this.cacheHitsGlobal = cacheHitsGlobal;
           this.cacheHitsLocal = cacheHitsLocal;
           this.cacheMisses = cacheMisses;
           this.systemNumThreads = systemNumThreads;
           this.systemCPUuser = systemCPUuser;
           this.systemCPUsystem = systemCPUsystem;
           this.systemCPUidle = systemCPUidle;
           this.systemHeapSize = systemHeapSize;
           this.systemHeapFree = systemHeapFree;
           this.systemHeapMax = systemHeapMax;
           this.valid = valid;
    }


    /**
     * Gets the resourceAllocated value for this MonitorStateResponse.
     * 
     * @return resourceAllocated
     */
    public int getResourceAllocated() {
        return resourceAllocated;
    }


    /**
     * Sets the resourceAllocated value for this MonitorStateResponse.
     * 
     * @param resourceAllocated
     */
    public void setResourceAllocated(int resourceAllocated) {
        this.resourceAllocated = resourceAllocated;
    }


    /**
     * Gets the resourceRegistered value for this MonitorStateResponse.
     * 
     * @return resourceRegistered
     */
    public int getResourceRegistered() {
        return resourceRegistered;
    }


    /**
     * Sets the resourceRegistered value for this MonitorStateResponse.
     * 
     * @param resourceRegistered
     */
    public void setResourceRegistered(int resourceRegistered) {
        this.resourceRegistered = resourceRegistered;
    }


    /**
     * Gets the resourceFree value for this MonitorStateResponse.
     * 
     * @return resourceFree
     */
    public int getResourceFree() {
        return resourceFree;
    }


    /**
     * Sets the resourceFree value for this MonitorStateResponse.
     * 
     * @param resourceFree
     */
    public void setResourceFree(int resourceFree) {
        this.resourceFree = resourceFree;
    }


    /**
     * Gets the resourcePending value for this MonitorStateResponse.
     * 
     * @return resourcePending
     */
    public int getResourcePending() {
        return resourcePending;
    }


    /**
     * Sets the resourcePending value for this MonitorStateResponse.
     * 
     * @param resourcePending
     */
    public void setResourcePending(int resourcePending) {
        this.resourcePending = resourcePending;
    }


    /**
     * Gets the resourceActive value for this MonitorStateResponse.
     * 
     * @return resourceActive
     */
    public int getResourceActive() {
        return resourceActive;
    }


    /**
     * Sets the resourceActive value for this MonitorStateResponse.
     * 
     * @param resourceActive
     */
    public void setResourceActive(int resourceActive) {
        this.resourceActive = resourceActive;
    }


    /**
     * Gets the taskNumQueues value for this MonitorStateResponse.
     * 
     * @return taskNumQueues
     */
    public int getTaskNumQueues() {
        return taskNumQueues;
    }


    /**
     * Sets the taskNumQueues value for this MonitorStateResponse.
     * 
     * @param taskNumQueues
     */
    public void setTaskNumQueues(int taskNumQueues) {
        this.taskNumQueues = taskNumQueues;
    }


    /**
     * Gets the taskSubmit value for this MonitorStateResponse.
     * 
     * @return taskSubmit
     */
    public int getTaskSubmit() {
        return taskSubmit;
    }


    /**
     * Sets the taskSubmit value for this MonitorStateResponse.
     * 
     * @param taskSubmit
     */
    public void setTaskSubmit(int taskSubmit) {
        this.taskSubmit = taskSubmit;
    }


    /**
     * Gets the taskWaitQueue value for this MonitorStateResponse.
     * 
     * @return taskWaitQueue
     */
    public int getTaskWaitQueue() {
        return taskWaitQueue;
    }


    /**
     * Sets the taskWaitQueue value for this MonitorStateResponse.
     * 
     * @param taskWaitQueue
     */
    public void setTaskWaitQueue(int taskWaitQueue) {
        this.taskWaitQueue = taskWaitQueue;
    }


    /**
     * Gets the taskDispatch value for this MonitorStateResponse.
     * 
     * @return taskDispatch
     */
    public int getTaskDispatch() {
        return taskDispatch;
    }


    /**
     * Sets the taskDispatch value for this MonitorStateResponse.
     * 
     * @param taskDispatch
     */
    public void setTaskDispatch(int taskDispatch) {
        this.taskDispatch = taskDispatch;
    }


    /**
     * Gets the taskActive value for this MonitorStateResponse.
     * 
     * @return taskActive
     */
    public int getTaskActive() {
        return taskActive;
    }


    /**
     * Sets the taskActive value for this MonitorStateResponse.
     * 
     * @param taskActive
     */
    public void setTaskActive(int taskActive) {
        this.taskActive = taskActive;
    }


    /**
     * Gets the taskDeliveryQueue value for this MonitorStateResponse.
     * 
     * @return taskDeliveryQueue
     */
    public int getTaskDeliveryQueue() {
        return taskDeliveryQueue;
    }


    /**
     * Sets the taskDeliveryQueue value for this MonitorStateResponse.
     * 
     * @param taskDeliveryQueue
     */
    public void setTaskDeliveryQueue(int taskDeliveryQueue) {
        this.taskDeliveryQueue = taskDeliveryQueue;
    }


    /**
     * Gets the taskDelivered value for this MonitorStateResponse.
     * 
     * @return taskDelivered
     */
    public int getTaskDelivered() {
        return taskDelivered;
    }


    /**
     * Sets the taskDelivered value for this MonitorStateResponse.
     * 
     * @param taskDelivered
     */
    public void setTaskDelivered(int taskDelivered) {
        this.taskDelivered = taskDelivered;
    }


    /**
     * Gets the cacheSize value for this MonitorStateResponse.
     * 
     * @return cacheSize
     */
    public int getCacheSize() {
        return cacheSize;
    }


    /**
     * Sets the cacheSize value for this MonitorStateResponse.
     * 
     * @param cacheSize
     */
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }


    /**
     * Gets the cacheHitsGlobal value for this MonitorStateResponse.
     * 
     * @return cacheHitsGlobal
     */
    public int getCacheHitsGlobal() {
        return cacheHitsGlobal;
    }


    /**
     * Sets the cacheHitsGlobal value for this MonitorStateResponse.
     * 
     * @param cacheHitsGlobal
     */
    public void setCacheHitsGlobal(int cacheHitsGlobal) {
        this.cacheHitsGlobal = cacheHitsGlobal;
    }


    /**
     * Gets the cacheHitsLocal value for this MonitorStateResponse.
     * 
     * @return cacheHitsLocal
     */
    public int getCacheHitsLocal() {
        return cacheHitsLocal;
    }


    /**
     * Sets the cacheHitsLocal value for this MonitorStateResponse.
     * 
     * @param cacheHitsLocal
     */
    public void setCacheHitsLocal(int cacheHitsLocal) {
        this.cacheHitsLocal = cacheHitsLocal;
    }


    /**
     * Gets the cacheMisses value for this MonitorStateResponse.
     * 
     * @return cacheMisses
     */
    public int getCacheMisses() {
        return cacheMisses;
    }


    /**
     * Sets the cacheMisses value for this MonitorStateResponse.
     * 
     * @param cacheMisses
     */
    public void setCacheMisses(int cacheMisses) {
        this.cacheMisses = cacheMisses;
    }


    /**
     * Gets the systemNumThreads value for this MonitorStateResponse.
     * 
     * @return systemNumThreads
     */
    public int getSystemNumThreads() {
        return systemNumThreads;
    }


    /**
     * Sets the systemNumThreads value for this MonitorStateResponse.
     * 
     * @param systemNumThreads
     */
    public void setSystemNumThreads(int systemNumThreads) {
        this.systemNumThreads = systemNumThreads;
    }


    /**
     * Gets the systemCPUuser value for this MonitorStateResponse.
     * 
     * @return systemCPUuser
     */
    public int getSystemCPUuser() {
        return systemCPUuser;
    }


    /**
     * Sets the systemCPUuser value for this MonitorStateResponse.
     * 
     * @param systemCPUuser
     */
    public void setSystemCPUuser(int systemCPUuser) {
        this.systemCPUuser = systemCPUuser;
    }


    /**
     * Gets the systemCPUsystem value for this MonitorStateResponse.
     * 
     * @return systemCPUsystem
     */
    public int getSystemCPUsystem() {
        return systemCPUsystem;
    }


    /**
     * Sets the systemCPUsystem value for this MonitorStateResponse.
     * 
     * @param systemCPUsystem
     */
    public void setSystemCPUsystem(int systemCPUsystem) {
        this.systemCPUsystem = systemCPUsystem;
    }


    /**
     * Gets the systemCPUidle value for this MonitorStateResponse.
     * 
     * @return systemCPUidle
     */
    public int getSystemCPUidle() {
        return systemCPUidle;
    }


    /**
     * Sets the systemCPUidle value for this MonitorStateResponse.
     * 
     * @param systemCPUidle
     */
    public void setSystemCPUidle(int systemCPUidle) {
        this.systemCPUidle = systemCPUidle;
    }


    /**
     * Gets the systemHeapSize value for this MonitorStateResponse.
     * 
     * @return systemHeapSize
     */
    public int getSystemHeapSize() {
        return systemHeapSize;
    }


    /**
     * Sets the systemHeapSize value for this MonitorStateResponse.
     * 
     * @param systemHeapSize
     */
    public void setSystemHeapSize(int systemHeapSize) {
        this.systemHeapSize = systemHeapSize;
    }


    /**
     * Gets the systemHeapFree value for this MonitorStateResponse.
     * 
     * @return systemHeapFree
     */
    public int getSystemHeapFree() {
        return systemHeapFree;
    }


    /**
     * Sets the systemHeapFree value for this MonitorStateResponse.
     * 
     * @param systemHeapFree
     */
    public void setSystemHeapFree(int systemHeapFree) {
        this.systemHeapFree = systemHeapFree;
    }


    /**
     * Gets the systemHeapMax value for this MonitorStateResponse.
     * 
     * @return systemHeapMax
     */
    public int getSystemHeapMax() {
        return systemHeapMax;
    }


    /**
     * Sets the systemHeapMax value for this MonitorStateResponse.
     * 
     * @param systemHeapMax
     */
    public void setSystemHeapMax(int systemHeapMax) {
        this.systemHeapMax = systemHeapMax;
    }


    /**
     * Gets the valid value for this MonitorStateResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this MonitorStateResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MonitorStateResponse)) return false;
        MonitorStateResponse other = (MonitorStateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.resourceAllocated == other.getResourceAllocated() &&
            this.resourceRegistered == other.getResourceRegistered() &&
            this.resourceFree == other.getResourceFree() &&
            this.resourcePending == other.getResourcePending() &&
            this.resourceActive == other.getResourceActive() &&
            this.taskNumQueues == other.getTaskNumQueues() &&
            this.taskSubmit == other.getTaskSubmit() &&
            this.taskWaitQueue == other.getTaskWaitQueue() &&
            this.taskDispatch == other.getTaskDispatch() &&
            this.taskActive == other.getTaskActive() &&
            this.taskDeliveryQueue == other.getTaskDeliveryQueue() &&
            this.taskDelivered == other.getTaskDelivered() &&
            this.cacheSize == other.getCacheSize() &&
            this.cacheHitsGlobal == other.getCacheHitsGlobal() &&
            this.cacheHitsLocal == other.getCacheHitsLocal() &&
            this.cacheMisses == other.getCacheMisses() &&
            this.systemNumThreads == other.getSystemNumThreads() &&
            this.systemCPUuser == other.getSystemCPUuser() &&
            this.systemCPUsystem == other.getSystemCPUsystem() &&
            this.systemCPUidle == other.getSystemCPUidle() &&
            this.systemHeapSize == other.getSystemHeapSize() &&
            this.systemHeapFree == other.getSystemHeapFree() &&
            this.systemHeapMax == other.getSystemHeapMax() &&
            this.valid == other.isValid();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getResourceAllocated();
        _hashCode += getResourceRegistered();
        _hashCode += getResourceFree();
        _hashCode += getResourcePending();
        _hashCode += getResourceActive();
        _hashCode += getTaskNumQueues();
        _hashCode += getTaskSubmit();
        _hashCode += getTaskWaitQueue();
        _hashCode += getTaskDispatch();
        _hashCode += getTaskActive();
        _hashCode += getTaskDeliveryQueue();
        _hashCode += getTaskDelivered();
        _hashCode += getCacheSize();
        _hashCode += getCacheHitsGlobal();
        _hashCode += getCacheHitsLocal();
        _hashCode += getCacheMisses();
        _hashCode += getSystemNumThreads();
        _hashCode += getSystemCPUuser();
        _hashCode += getSystemCPUsystem();
        _hashCode += getSystemCPUidle();
        _hashCode += getSystemHeapSize();
        _hashCode += getSystemHeapFree();
        _hashCode += getSystemHeapMax();
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MonitorStateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">MonitorStateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceAllocated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceAllocated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceRegistered");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceRegistered"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceFree");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceFree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourcePending");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourcePending"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceActive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskNumQueues");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskNumQueues"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskSubmit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskSubmit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskWaitQueue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskWaitQueue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskDispatch");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskDispatch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskActive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskDeliveryQueue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskDeliveryQueue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskDelivered");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taskDelivered"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheHitsGlobal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheHitsGlobal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheHitsLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheHitsLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheMisses");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheMisses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemNumThreads");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemNumThreads"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCPUuser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemCPUuser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCPUsystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemCPUsystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemCPUidle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemCPUidle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemHeapSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemHeapSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemHeapFree");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemHeapFree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemHeapMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemHeapMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
