/**
 * MonitorTaskStateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class MonitorTaskStateResponse  implements java.io.Serializable {
    private java.lang.String[] queuedTasks;
    private int queuedTaskNum;
    private java.lang.String[] activeTasks;
    private int activeTaskNum;
    private java.lang.String[] doneTasks;
    private int doneTaskNum;

    public MonitorTaskStateResponse() {
    }

    public MonitorTaskStateResponse(
           int activeTaskNum,
           java.lang.String[] activeTasks,
           int doneTaskNum,
           java.lang.String[] doneTasks,
           int queuedTaskNum,
           java.lang.String[] queuedTasks) {
           this.queuedTasks = queuedTasks;
           this.queuedTaskNum = queuedTaskNum;
           this.activeTasks = activeTasks;
           this.activeTaskNum = activeTaskNum;
           this.doneTasks = doneTasks;
           this.doneTaskNum = doneTaskNum;
    }


    /**
     * Gets the queuedTasks value for this MonitorTaskStateResponse.
     * 
     * @return queuedTasks
     */
    public java.lang.String[] getQueuedTasks() {
        return queuedTasks;
    }


    /**
     * Sets the queuedTasks value for this MonitorTaskStateResponse.
     * 
     * @param queuedTasks
     */
    public void setQueuedTasks(java.lang.String[] queuedTasks) {
        this.queuedTasks = queuedTasks;
    }

    public java.lang.String getQueuedTasks(int i) {
        return this.queuedTasks[i];
    }

    public void setQueuedTasks(int i, java.lang.String _value) {
        this.queuedTasks[i] = _value;
    }


    /**
     * Gets the queuedTaskNum value for this MonitorTaskStateResponse.
     * 
     * @return queuedTaskNum
     */
    public int getQueuedTaskNum() {
        return queuedTaskNum;
    }


    /**
     * Sets the queuedTaskNum value for this MonitorTaskStateResponse.
     * 
     * @param queuedTaskNum
     */
    public void setQueuedTaskNum(int queuedTaskNum) {
        this.queuedTaskNum = queuedTaskNum;
    }


    /**
     * Gets the activeTasks value for this MonitorTaskStateResponse.
     * 
     * @return activeTasks
     */
    public java.lang.String[] getActiveTasks() {
        return activeTasks;
    }


    /**
     * Sets the activeTasks value for this MonitorTaskStateResponse.
     * 
     * @param activeTasks
     */
    public void setActiveTasks(java.lang.String[] activeTasks) {
        this.activeTasks = activeTasks;
    }

    public java.lang.String getActiveTasks(int i) {
        return this.activeTasks[i];
    }

    public void setActiveTasks(int i, java.lang.String _value) {
        this.activeTasks[i] = _value;
    }


    /**
     * Gets the activeTaskNum value for this MonitorTaskStateResponse.
     * 
     * @return activeTaskNum
     */
    public int getActiveTaskNum() {
        return activeTaskNum;
    }


    /**
     * Sets the activeTaskNum value for this MonitorTaskStateResponse.
     * 
     * @param activeTaskNum
     */
    public void setActiveTaskNum(int activeTaskNum) {
        this.activeTaskNum = activeTaskNum;
    }


    /**
     * Gets the doneTasks value for this MonitorTaskStateResponse.
     * 
     * @return doneTasks
     */
    public java.lang.String[] getDoneTasks() {
        return doneTasks;
    }


    /**
     * Sets the doneTasks value for this MonitorTaskStateResponse.
     * 
     * @param doneTasks
     */
    public void setDoneTasks(java.lang.String[] doneTasks) {
        this.doneTasks = doneTasks;
    }

    public java.lang.String getDoneTasks(int i) {
        return this.doneTasks[i];
    }

    public void setDoneTasks(int i, java.lang.String _value) {
        this.doneTasks[i] = _value;
    }


    /**
     * Gets the doneTaskNum value for this MonitorTaskStateResponse.
     * 
     * @return doneTaskNum
     */
    public int getDoneTaskNum() {
        return doneTaskNum;
    }


    /**
     * Sets the doneTaskNum value for this MonitorTaskStateResponse.
     * 
     * @param doneTaskNum
     */
    public void setDoneTaskNum(int doneTaskNum) {
        this.doneTaskNum = doneTaskNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MonitorTaskStateResponse)) return false;
        MonitorTaskStateResponse other = (MonitorTaskStateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.queuedTasks==null && other.getQueuedTasks()==null) || 
             (this.queuedTasks!=null &&
              java.util.Arrays.equals(this.queuedTasks, other.getQueuedTasks()))) &&
            this.queuedTaskNum == other.getQueuedTaskNum() &&
            ((this.activeTasks==null && other.getActiveTasks()==null) || 
             (this.activeTasks!=null &&
              java.util.Arrays.equals(this.activeTasks, other.getActiveTasks()))) &&
            this.activeTaskNum == other.getActiveTaskNum() &&
            ((this.doneTasks==null && other.getDoneTasks()==null) || 
             (this.doneTasks!=null &&
              java.util.Arrays.equals(this.doneTasks, other.getDoneTasks()))) &&
            this.doneTaskNum == other.getDoneTaskNum();
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
        if (getQueuedTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQueuedTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQueuedTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getQueuedTaskNum();
        if (getActiveTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getActiveTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getActiveTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getActiveTaskNum();
        if (getDoneTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDoneTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDoneTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getDoneTaskNum();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MonitorTaskStateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">MonitorTaskStateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queuedTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queuedTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queuedTaskNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queuedTaskNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeTaskNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeTaskNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doneTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doneTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doneTaskNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doneTaskNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
