/**
 * WorkerResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class WorkerResult  implements java.io.Serializable {
    private java.lang.String machID;
    private int numTasks;
    private org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks;
    private boolean valid;
    private int numCacheEvicted;
    private java.lang.String[] cacheEvicted;
    private boolean shutingDown;

    public WorkerResult() {
    }

    public WorkerResult(
           java.lang.String[] cacheEvicted,
           java.lang.String machID,
           int numCacheEvicted,
           int numTasks,
           boolean shutingDown,
           org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks,
           boolean valid) {
           this.machID = machID;
           this.numTasks = numTasks;
           this.tasks = tasks;
           this.valid = valid;
           this.numCacheEvicted = numCacheEvicted;
           this.cacheEvicted = cacheEvicted;
           this.shutingDown = shutingDown;
    }


    /**
     * Gets the machID value for this WorkerResult.
     * 
     * @return machID
     */
    public java.lang.String getMachID() {
        return machID;
    }


    /**
     * Sets the machID value for this WorkerResult.
     * 
     * @param machID
     */
    public void setMachID(java.lang.String machID) {
        this.machID = machID;
    }


    /**
     * Gets the numTasks value for this WorkerResult.
     * 
     * @return numTasks
     */
    public int getNumTasks() {
        return numTasks;
    }


    /**
     * Sets the numTasks value for this WorkerResult.
     * 
     * @param numTasks
     */
    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }


    /**
     * Gets the tasks value for this WorkerResult.
     * 
     * @return tasks
     */
    public org.globus.GenericPortal.stubs.GPService_instance.Task[] getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this WorkerResult.
     * 
     * @param tasks
     */
    public void setTasks(org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks) {
        this.tasks = tasks;
    }

    public org.globus.GenericPortal.stubs.GPService_instance.Task getTasks(int i) {
        return this.tasks[i];
    }

    public void setTasks(int i, org.globus.GenericPortal.stubs.GPService_instance.Task _value) {
        this.tasks[i] = _value;
    }


    /**
     * Gets the valid value for this WorkerResult.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this WorkerResult.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }


    /**
     * Gets the numCacheEvicted value for this WorkerResult.
     * 
     * @return numCacheEvicted
     */
    public int getNumCacheEvicted() {
        return numCacheEvicted;
    }


    /**
     * Sets the numCacheEvicted value for this WorkerResult.
     * 
     * @param numCacheEvicted
     */
    public void setNumCacheEvicted(int numCacheEvicted) {
        this.numCacheEvicted = numCacheEvicted;
    }


    /**
     * Gets the cacheEvicted value for this WorkerResult.
     * 
     * @return cacheEvicted
     */
    public java.lang.String[] getCacheEvicted() {
        return cacheEvicted;
    }


    /**
     * Sets the cacheEvicted value for this WorkerResult.
     * 
     * @param cacheEvicted
     */
    public void setCacheEvicted(java.lang.String[] cacheEvicted) {
        this.cacheEvicted = cacheEvicted;
    }

    public java.lang.String getCacheEvicted(int i) {
        return this.cacheEvicted[i];
    }

    public void setCacheEvicted(int i, java.lang.String _value) {
        this.cacheEvicted[i] = _value;
    }


    /**
     * Gets the shutingDown value for this WorkerResult.
     * 
     * @return shutingDown
     */
    public boolean isShutingDown() {
        return shutingDown;
    }


    /**
     * Sets the shutingDown value for this WorkerResult.
     * 
     * @param shutingDown
     */
    public void setShutingDown(boolean shutingDown) {
        this.shutingDown = shutingDown;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkerResult)) return false;
        WorkerResult other = (WorkerResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.machID==null && other.getMachID()==null) || 
             (this.machID!=null &&
              this.machID.equals(other.getMachID()))) &&
            this.numTasks == other.getNumTasks() &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              java.util.Arrays.equals(this.tasks, other.getTasks()))) &&
            this.valid == other.isValid() &&
            this.numCacheEvicted == other.getNumCacheEvicted() &&
            ((this.cacheEvicted==null && other.getCacheEvicted()==null) || 
             (this.cacheEvicted!=null &&
              java.util.Arrays.equals(this.cacheEvicted, other.getCacheEvicted()))) &&
            this.shutingDown == other.isShutingDown();
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
        if (getMachID() != null) {
            _hashCode += getMachID().hashCode();
        }
        _hashCode += getNumTasks();
        if (getTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getNumCacheEvicted();
        if (getCacheEvicted() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCacheEvicted());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCacheEvicted(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isShutingDown() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkerResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">WorkerResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("machID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "machID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "Task"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numCacheEvicted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numCacheEvicted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheEvicted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheEvicted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shutingDown");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shutingDown"));
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
